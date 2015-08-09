package com.dahuangit.upgrader.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dahuangit.base.entry.BaseModel;

/**
 * 下载记录实体类
 * 
 * @author 黄仁良
 *
 *         2015年8月8日下午3:26:36
 */
@Entity
@Table(name = "t_download_rec")
public class DownloadRecord extends BaseModel {

	/** 主键id */
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "download_rec_id")
	private String downloadRecordId = null;

	/** 客户端ip */
	@Column(name = "client_addr")
	private String clientAddr = null;

	/** 下载时间 */
	@Column(name = "download_time")
	private Date downloadTime = null;

	public String getDownloadRecordId() {
		return downloadRecordId;
	}

	public void setDownloadRecordId(String downloadRecordId) {
		this.downloadRecordId = downloadRecordId;
	}

	public String getClientAddr() {
		return clientAddr;
	}

	public void setClientAddr(String clientAddr) {
		this.clientAddr = clientAddr;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

}
