package com.dahuangit.base.entry;

import java.util.Date;

/**
 * 可审计对象接口
 * 
 * @author 黄仁良
 * 
 *         创建时间2014年8月19日下午3:11:13
 */
public interface Auditable {

	/**
	 * 得到创建时间
	 * 
	 * @return
	 */
	Date getCreateDateTime();

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	Boolean getIsActive();

	/**
	 * 设置是否有效
	 * 
	 * @param isActive
	 */
	void setIsActive(Boolean isActive);
}
