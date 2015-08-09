package com.dahuangit.upgrader.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.upgrader.dao.DownloadRecordDao;
import com.dahuangit.upgrader.dto.AddDownloadRecordRequest;
import com.dahuangit.upgrader.entry.DownloadRecord;
import com.dahuangit.upgrader.service.DownloadRecordService;

/**
 * 下载记录业务实现类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:38:47
 */
@Component
@Transactional
public class DownloadRecordServiceImpl implements DownloadRecordService{

	@Autowired
	private DownloadRecordDao downloadRecordDao = null;

	/**
	 * 添加下载记录
	 * 
	 * @param rec
	 */
	public void addDownloadRecord(AddDownloadRecordRequest req) {
		DownloadRecord record = new DownloadRecord();
		record.setClientAddr(req.getClientAddr());
		record.setDownloadTime(new Date());
		this.downloadRecordDao.add(record);
	}
}
