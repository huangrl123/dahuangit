package com.dahuangit.upgrader.dao;

import org.springframework.stereotype.Component;

import com.dahuangit.base.dao.BaseDao;
import com.dahuangit.upgrader.entry.DownloadRecord;

/**
 * 下载记录dao类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:36:56
 */
@Component
public class DownloadRecordDao extends BaseDao<DownloadRecord, String> {

	/**
	 * 添加下载记录
	 * 
	 * @param rec
	 */
	public void addDownloadRecord(DownloadRecord rec) {
		this.add(rec);
	}
}
