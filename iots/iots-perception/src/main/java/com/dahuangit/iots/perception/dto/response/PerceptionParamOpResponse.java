package com.dahuangit.iots.perception.dto.response;

import java.util.Date;

import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.util.bean.dto.Dto;

@Dto
public class PerceptionParamOpResponse extends OpResponse {
	/** 感知端设备参数主键ID */
	private Integer ppId = null;

	/** 所属感知端设备 */
	private Integer pid = null;

	/** 知端设备参数参数键 */
	private String paramKey = null;

	/** 感知端设备参数参数值 */
	private String paramValue = null;

	/** 感知端设备参数参数描述 */
	private String paramDesc = null;

	/** 创建时间 */
	private Date createTime = null;

	/** 最后修改时间 */
	private Date lastModifyTime = null;

	public Integer getPpId() {
		return ppId;
	}

	public void setPpId(Integer ppId) {
		this.ppId = ppId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
