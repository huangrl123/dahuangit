package com.dahuangit.iots.perception.entry;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dahuangit.base.entry.BaseModel;

/**
 * 设备运行日志
 * 
 * @author 大黄
 * 
 *         2015年1月22日下午3:34:37
 */
@Entity
@Table(name = "t_perception_runtime_log")
public class PerceptionRuntimeLog extends BaseModel {
	/** 主键id */
	@Id
	@GeneratedValue
	@Column(name = "prl_id")
	private Integer perceptionRuntimeLogId = null;

	/** 设备id */
	@Column(name = "p_id")
	private Integer perceptionId = null;

	/** 所属设备参数 */
	@Column(name = "pp_id")
	private Integer perceptionParamId = null;

	/** 所属设备类型 */
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	/** 所属参数值 */
	@Column(name = "ppv_id")
	private Integer perceptionParamValueInfoId = null;

	/** 创建时间 */
	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	/** 备注 */
	@Column(name = "remark")
	private String remark = null;

	/** 报文 */
	@Column(name = "msg_hex")
	private String hex = null;

	/** 所属设备 */
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "p_id", insertable = false, updatable = false)
	private Perception perception = null;

	/** 所属设备类型 */
	@ManyToOne
	@JoinColumn(name = "pt_id", insertable = false, updatable = false)
	private PerceptionType perceptionType = null;

	/** 所属参数 */
	@ManyToOne
	@JoinColumn(name = "pp_id", insertable = false, updatable = false)
	private PerceptionParam perceptionParam = null;

	/** 所属参数值 */
	@ManyToOne
	@JoinColumn(name = "ppv_id", insertable = false, updatable = false)
	private PerceptionParamValueInfo perceptionParamValueInfo = null;

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

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public Integer getPerceptionParamValueInfoId() {
		return perceptionParamValueInfoId;
	}

	public void setPerceptionParamValueInfoId(Integer perceptionParamValueInfoId) {
		this.perceptionParamValueInfoId = perceptionParamValueInfoId;
	}

	public PerceptionParamValueInfo getPerceptionParamValueInfo() {
		return perceptionParamValueInfo;
	}

	public void setPerceptionParamValueInfo(PerceptionParamValueInfo perceptionParamValueInfo) {
		this.perceptionParamValueInfo = perceptionParamValueInfo;
	}

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

	public Perception getPerception() {
		return perception;
	}

	public void setPerception(Perception perception) {
		this.perception = perception;
	}

	public PerceptionType getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(PerceptionType perceptionType) {
		this.perceptionType = perceptionType;
	}

	public PerceptionParam getPerceptionParam() {
		return perceptionParam;
	}

	public void setPerceptionParam(PerceptionParam perceptionParam) {
		this.perceptionParam = perceptionParam;
	}

}
