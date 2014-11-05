package com.dahuangit.seobi.receiver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.dahuangit.util.date.DateUtils;

@Component
@Transactional
public class QQTalkMsgServiceImpl extends BaseService implements QQTalkMsgService {

	private final Logger LOG = Logger.getLogger(getClass());

	@Autowired
	private QQAccountDao accountDao = null;

	@Autowired
	private QQTalkMsgDao qqTalkMsgDao = null;

	@Value("${receiver.authentication.password}")
	private String authenticationPassword = null;

	@Override
	public void addQQTalkMsg(QQTalkMsgsReqXml qqTalkMsgsReqXml) {
		// 采集器客户端密码校验
		String clientpwd = qqTalkMsgsReqXml.getClientpwd();
		if (!authenticationPassword.equals(clientpwd)) {
			new GenericException("密码校验校验失败");
		}

		List<QQTalkMsgXml> qqTalkMsgXmls = qqTalkMsgsReqXml.getQqTalkMsgXmls();

		boolean isNewAccount = false;

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
				QQTalkMsg qqTalkMsg = new QQTalkMsg();
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
					QQTalkImg img = new QQTalkImg();
					img.setImgPath(qqTalkImgXml.getImgPath());

					img.setQqTalkMsg(qqTalkMsg);

					imgs.add(img);
				}

				qqTalkMsg.setQqTalkImgs(imgs);

				qqTalkMsg.setRemark(qqTalkContentXml.getRemark());
				qqTalkMsg.setTalkContent(qqTalkContentXml.getTalkContent());
				qqTalkMsg.setTmTxId(qqTalkContentXml.getTalkTxId());

				// 如果非新账户，则只需将说说信息、说说图片信息一起添加到数据库
				if (isNewAccount) {
					qqTalkMsgs.add(qqTalkMsg);
				} else {
					qqTalkMsgDao.addQQTalkMsg(qqTalkMsg);
				}

			}

			// 如果是新账户，则将账号信息、说说信息、说说图片信息一起添加到数据库
			if (isNewAccount) {
				qqAccount.setQqTalkMsgs(qqTalkMsgs);
				this.accountDao.addQQAccount(qqAccount);
			}
		}
	}

}
