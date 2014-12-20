package com.dahuangit.iots.perception.dto.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.dahuangit.util.bean.dto.Dto;
import com.dahuangit.util.bean.dto.DtoField;
import com.dahuangit.util.spring.JsonDateSerializer;

@Dto
public class PerceptionRuntimeLogResponse {
	private Integer perceptionRuntimeLogId = null;

	private Integer perceptionId = null;

	@DtoField(field = "perception.perceptionName")
	private String perceptionName = null;

	@DtoField(field = "perception.perceptionAddr")
	private String perceptionAddr = null;

	private Integer perceptionParamId = null;

	@DtoField(field = "perceptionParam.perceptionParamName")
	private String perceptionParamName = null;

	private Integer perceptionTypeId = null;

	@DtoField(field = "perceptionType.perceptionTypeName")
	private String perceptionTypeName = null;

	private Integer perceptionParamValueInfoId = null;

	@DtoField(field = "perceptionParamValueInfo.perceptionParamValueDesc")
	private String perceptionParamValueDesc = null;

	private Date createDateTime = null;

	private String remark = null;

	private String hex = null;

	public Integer getPerceptionRuntimeLogId() {
		return perceptionRuntimeLogId;
	}

	public void setPerceptionRuntimeLogId(Integer perceptionRuntimeLogId) {
		this.perceptionRuntimeLogId = perceptionRuntimeLogId;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

	public String getPerceptionParamName() {
		return perceptionParamName;
	}

	public void setPerceptionParamName(String perceptionParamName) {
		this.perceptionParamName = perceptionParamName;
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

	public Integer getPerceptionParamValueInfoId() {
		return perceptionParamValueInfoId;
	}

	public void setPerceptionParamValueInfoId(Integer perceptionParamValueInfoId) {
		this.perceptionParamValueInfoId = perceptionParamValueInfoId;
	}

	public String getPerceptionParamValueDesc() {
		return perceptionParamValueDesc;
	}

	public void setPerceptionParamValueDesc(String perceptionParamValueDesc) {
		this.perceptionParamValueDesc = perceptionParamValueDesc;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

}
