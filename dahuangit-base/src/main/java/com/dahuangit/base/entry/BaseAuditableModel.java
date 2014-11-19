package com.dahuangit.base.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * 可审计的model类
 * 
 * @author 黄仁良
 * 
 *         创建时间2014年8月19日下午3:16:31
 */
@MappedSuperclass
public abstract class BaseAuditableModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/** 创建时间 */
	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	/** 最后编辑时间 */
	@Column(name = "last_modify_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifyDateTime;

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getLastModifyDateTime() {
		return lastModifyDateTime;
	}

	public void setLastModifyDateTime(Date lastModifyDateTime) {
		this.lastModifyDateTime = lastModifyDateTime;
	}

}
