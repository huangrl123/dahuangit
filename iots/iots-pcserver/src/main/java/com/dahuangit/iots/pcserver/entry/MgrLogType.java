package com.dahuangit.iots.pcserver.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

/**
 * 管理日志
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午9:40:17
 */
@Entity
@Table(name = "t_mgr_log_type")
public class MgrLogType extends BaseModel {

	/** 管理日志主键id */
	@Id
	@GeneratedValue
	@Column(name = "mgr_log_type_id")
	private Integer mgrLogTypeId = null;

	/** 管理日志类型名称 */
	@Column(name = "mgr_log_type_name")
	private String mgrLogTypeName = null;

	public Integer getMgrLogTypeId() {
		return mgrLogTypeId;
	}

	public void setMgrLogTypeId(Integer mgrLogTypeId) {
		this.mgrLogTypeId = mgrLogTypeId;
	}

	public String getMgrLogTypeName() {
		return mgrLogTypeName;
	}

	public void setMgrLogTypeName(String mgrLogTypeName) {
		this.mgrLogTypeName = mgrLogTypeName;
	}

}
