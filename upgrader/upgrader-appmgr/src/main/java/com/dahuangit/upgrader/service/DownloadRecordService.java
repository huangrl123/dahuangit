package com.dahuangit.upgrader.service;

import com.dahuangit.upgrader.dto.AddDownloadRecordRequest;

/**
 * 下载记录业务类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:38:47
 */
public interface DownloadRecordService {
	/**
	 * 添加下载记录
	 * 
	 * @param rec
	 */
	void addDownloadRecord(AddDownloadRecordRequest req);
}
