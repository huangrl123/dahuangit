package com.dahuangit.iots.perception.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dao.PerceptionDao;
import com.dahuangit.iots.perception.dao.PerceptionVediaFileDao;
import com.dahuangit.iots.perception.dto.request.PerceptionVediaFileInfo;
import com.dahuangit.iots.perception.dto.request.PerceptionVediaFileUploadNoticeRequest;
import com.dahuangit.iots.perception.dto.response.PercetionVediaFileResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionVediaFile;
import com.dahuangit.iots.perception.service.PerceptionVediaService;
import com.dahuangit.util.bean.dto.DtoBuilder;
import com.dahuangit.util.log.Log4jUtils;
import com.dahuangit.util.net.ftp.FTPHelper;

/**
 * 感知端视频文件服务类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月29日 下午9:51:29
 */
@Transactional
@Component
public class PerceptionVediaFileServiceImpl implements PerceptionVediaService {

	@Autowired
	private PerceptionVediaFileDao perceptionVediaFileDao = null;

	@Autowired
	private PerceptionDao perceptionDao = null;

	private static final Logger log = Log4jUtils.getLogger(PerceptionVediaFileServiceImpl.class);

	@Value("${perception.ftpHost}")
	private String ftpHost = null;
	@Value("${perception.ftpUserName}")
	private String ftpUserName = null;
	@Value("${perception.ftpPassword}")
	private String ftpPassword = null;
	@Value("${perception.ftpPort}")
	private Integer ftpPort = null;

	public PageQueryResult<PercetionVediaFileResponse> findPerceptionByPage(Integer perceptionId, Integer start,
			Integer limit) {
		PageQueryResult<PercetionVediaFileResponse> pageQueryResult = new PageQueryResult<PercetionVediaFileResponse>();

		List<PercetionVediaFileResponse> rows = new ArrayList<PercetionVediaFileResponse>();
		List<PerceptionVediaFile> list = this.perceptionVediaFileDao.findPerceptionVediaFileByPage(perceptionId, start,
				limit);

		for (PerceptionVediaFile p : list) {
			PercetionVediaFileResponse por = DtoBuilder.buildDto(PercetionVediaFileResponse.class, p);
			rows.add(por);
		}

		Long totalCount = this.perceptionVediaFileDao.findPerceptionVediaFileCount(perceptionId);

		pageQueryResult.setRows(rows);
		pageQueryResult.setTotal(totalCount);

		return pageQueryResult;
	}

	public void fileUploadNotice(PerceptionVediaFileUploadNoticeRequest req) throws Exception {
		String perceptionAddr = req.getPerceptionAddr();
		Perception p = perceptionDao.findUniqueBy("perceptionAddr", perceptionAddr);
		Integer perceptionId = p.getPerceptionId();

		FTPHelper ftpHelper = new FTPHelper(ftpHost, ftpUserName, ftpPassword, ftpPort);

		for (PerceptionVediaFileInfo info : req.getFileInfos()) {
			if (null == info) {
				continue;
			}

			String fileName = info.getFileName();
			String filePath = info.getFilePath();

			PerceptionVediaFile file = new PerceptionVediaFile();
			file.setPerceptionId(perceptionId);
			file.setFileName(fileName);
			file.setFilePath(filePath);
			file.setCreateDateTime(new Date());

			try {
				boolean exsits = ftpHelper.exsitsFile(filePath, fileName);
				if (!exsits) {
					continue;
				}

				log.debug("文件路径[" + filePath + "]下存在文件[" + fileName + ",将文件信息保存数据库!");

				PerceptionVediaFile pf = this.perceptionVediaFileDao.findUniqueBy("fileName", fileName);
				if (null != pf) {
					log.debug("文件路径[" + filePath + "]下的文件[" + fileName + "已经通知过,将不在继续新增保存!");
					continue;
				}

				this.perceptionVediaFileDao.add(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ftpHelper.close();
	}
}
