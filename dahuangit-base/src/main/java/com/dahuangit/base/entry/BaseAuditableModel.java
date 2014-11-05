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
public abstract class BaseAuditableModel extends BaseModel implements Auditable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	/**
	 * 是否有效
	 */
	@Column(name = "is_active", length = 1)
	@Type(type = "yes_no")
	private Boolean isActive = Boolean.TRUE;

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
