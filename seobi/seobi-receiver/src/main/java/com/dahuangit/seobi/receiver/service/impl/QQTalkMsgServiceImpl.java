package com.dahuangit.seobi.receiver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.exception.GenericException;
import com.dahuangit.base.service.BaseService;
import com.dahuangit.seobi.receiver.dao.QQAccountDao;
import com.dahuangit.seobi.receiver.dao.QQTalkMsgDao;
import com.dahuangit.seobi.receiver.dto.oxm.request.QQTalkContentXml;
import com.dahuangit.seobi.receiver.dto.oxm.request.QQTalkImgXml;
import com.dahuangit.seobi.receiver.dto.oxm.request.QQTalkMsgXml;
import com.dahuangit.seobi.receiver.dto.oxm.request.QQTalkMsgsReqXml;
import com.dahuangit.seobi.receiver.entry.QQAccount;
import com.dahuangit.seobi.receiver.entry.QQTalkImg;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;
import com.dahuangit.seobi.receiver.service.QQTalkMsgService;
import com.dahuangit.util.NumberUtils;
import com.dahuangit.util.date.DateUtils;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.http.HttpKit;

@Component
@Transactional
public class QQTalkMsgServiceImpl extends BaseService implements QQTalkMsgService {

	private final static Logger log = Log4jUtils.getLogger(QQTalkMsgServiceImpl.class);

	@Autowired
	private QQAccountDao accountDao = null;

	@Autowired
	private QQTalkMsgDao qqTalkMsgDao = null;

	@Value("${receiver.authentication.password}")
	private String authenticationPassword = null;

	@Value("${analyzer.analyzeShuoshuoBaiduOriginarityPercent.url}")
	private String qqTalkOriginatyPercentAnalyzeUrl = null;

	@Autowired
	private ExecutorService executorService = null;

	@Override
	public void addQQTalkMsg(QQTalkMsgsReqXml qqTalkMsgsReqXml) {
		// 采集器客户端密码校验
		String clientpwd = qqTalkMsgsReqXml.getClientpwd();
		if (!authenticationPassword.equals(clientpwd)) {
			new GenericException("密码校验校验失败");
		}

		List<QQTalkMsgXml> qqTalkMsgXmls = qqTalkMsgsReqXml.getQqTalkMsgXmls();

		boolean isNewAccount = false;

		List<QQTalkMsg> QQTalkMsgList = new ArrayList<QQTalkMsg>();

		for (QQTalkMsgXml qqTalkMsgXml : qqTalkMsgXmls) {
			QQAccount qqAccount = accountDao.findUniqueBy("qq", qqTalkMsgXml.getQq());

			if (null == qqAccount) {
				isNewAccount = true;

				qqAccount = new QQAccount();
				qqAccount.setQq(qqTalkMsgXml.getQq());
				qqAccount.setQqName(qqTalkMsgXml.getQqName());
			}

			List<QQTalkContentXml> qqTalkContentXmls = qqTalkMsgXml.getQqTalkContentXmls();
			List<QQTalkMsg> qqTalkMsgs = new ArrayList<QQTalkMsg>();

			for (QQTalkContentXml qqTalkContentXml : qqTalkContentXmls) {
				String content = qqTalkContentXml.getTalkContent();
				if (null == content || content.length() < 6) {
					log.error("说说[" + content + "]内容长度不够，系统将其抛弃，不保存.");
					continue;
				}

				QQTalkMsg qqTalkMsg = null;
				try {
					qqTalkMsg = new QQTalkMsg();
					qqTalkMsg.setBrowseCount(qqTalkContentXml.getBrowseCount());
					qqTalkMsg.setFromDevice(qqTalkContentXml.getFromDevice());
					qqTalkMsg.setGps(qqTalkContentXml.getGps());
					qqTalkMsg.setPraiseCount(qqTalkContentXml.getPraiseCount());

					Date publishTime = DateUtils.parse(qqTalkContentXml.getPublishTime());
					qqTalkMsg.setPublishTime(publishTime);

					qqTalkMsg.setQqAccount(qqAccount);

					List<QQTalkImgXml> qqTalkImgXmls = qqTalkContentXml.getQqTalkImgXmls();
					List<QQTalkImg> imgs = new ArrayList<QQTalkImg>();

					for (QQTalkImgXml qqTalkImgXml : qqTalkImgXmls) {
						String imgPath = qqTalkImgXml.getImgPath();

						if (null == imgPath) {
							continue;
						}

						QQTalkImg img = new QQTalkImg();
						img.setImgPath(qqTalkImgXml.getImgPath());

						img.setQqTalkMsg(qqTalkMsg);

						imgs.add(img);
					}

					qqTalkMsg.setQqTalkImgs(imgs);

					qqTalkMsg.setRemark(qqTalkContentXml.getRemark());
					qqTalkMsg.setTalkContent(content);
					qqTalkMsg.setTmTxId(qqTalkContentXml.getTalkTxId());

					// 如果非新账户，则只需将说说信息、说说图片信息一起添加到数据库
					if (isNewAccount) {
						qqTalkMsgs.add(qqTalkMsg);
					} else {
						qqTalkMsgDao.addQQTalkMsg(qqTalkMsg);
					}
				} catch (Exception e) {
					log.error("qq说说[" + content + "]保存失败,失败原因:" + e.getMessage());
					e.printStackTrace();
					continue;
				}

				if (null != qqTalkMsg) {
					QQTalkMsgList.add(qqTalkMsg);
				}
			}

			// 如果是新账户，则将账号信息、说说信息、说说图片信息一起添加到数据库
			if (isNewAccount) {
				qqAccount.setQqTalkMsgs(qqTalkMsgs);
				this.accountDao.addQQAccount(qqAccount);
			}

			// 启动线程分析这些qq说说的原创度
			final List<Integer> idList = new ArrayList<Integer>();
			for (QQTalkMsg qm : QQTalkMsgList) {
				if (qm == null) {
					continue;
				}

				idList.add(qm.getTmId());
			}

			analyzeOriginatyPercent(idList);
		}
	}

	private void analyzeOriginatyPercent(final List<Integer> talkIdsList) {
		Thread t = new Thread() {

			@Override
			public void run() {
				log.debug("启动线程分析这些qq说说的原创度");

				Map<String, String> params = new HashMap<String, String>();
				params.put("idsStr", NumberUtils.integerList2idsStr(talkIdsList));

				try {
					HttpKit.doHttpRequest(qqTalkOriginatyPercentAnalyzeUrl, params);
					log.debug("qq说说的原创度ok");
				} catch (Exception e) {
					log.error("qq说说的原创度ok失败,错误原因:" + e.getMessage());
					e.printStackTrace();
				}
			}

		};

		this.executorService.execute(t);
	}
}
