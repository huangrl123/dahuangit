package com.dahuangit.iots.perception.dto.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.dahuangit.base.dto.opm.response.OpResponse;
import com.dahuangit.util.bean.dto.Dto;
import com.dahuangit.util.bean.dto.DtoField;
import com.dahuangit.util.spring.JsonDateSerializer;

@Dto
public class PerceptionOpResponse extends OpResponse {
	/** 感知端主键 */
	private Integer perceptionId = null;

	/** 感知端设备类型 */
	private Integer perceptionTypeId = null;

	@DtoField(field = "perceptionType.perceptionTypeName")
	private String perceptionTypeName = null;

	/** 感知端设备地址(mac地址) */
	private String perceptionAddr = null;

	/** 感知端设备名称 */
	private String perceptionName = null;

	/** 安装地点 */
	private String installSite = null;

	/** 创建时间 */
	private Date createDateTime = null;

	/** 在线状态 */
	private String onlineStatus = null;

	private Date lastCommTime = null;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(Date lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPerceptionTypeName() {
		return perceptionTypeName;
	}

	public void setPerceptionTypeName(String perceptionTypeName) {
		this.perceptionTypeName = perceptionTypeName;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

}
