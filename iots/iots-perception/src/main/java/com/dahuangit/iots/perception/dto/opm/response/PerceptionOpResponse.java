package com.dahuangit.iots.perception.dto.opm.response;

import java.util.Date;

import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.util.bean.dto.Dto;

@Dto
public class PerceptionOpResponse extends OpResponse {
	/** 感知端主键 */
	private Integer pid = null;

	/** 感知端设备类型 1:2+2 设备 2:6+6设备 */
	private String perceptionType = null;

	/** 感知端设备地址(mac地址) */
	private String perceptionAddr = null;

	/** 感知端设备名称 */
	private String perceptionName = null;

	/** 安装地点 */
	private String installSite = null;

	/** 创建时间 */
	private Date createTime = null;

	/** 最后修改时间 */
	private Date lastModifyTime = null;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(String perceptionType) {
		this.perceptionType = perceptionType;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getInstallSite() {
		return installSite;
	}

	public void setInstallSite(String installSite) {
		this.installSite = installSite;
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
