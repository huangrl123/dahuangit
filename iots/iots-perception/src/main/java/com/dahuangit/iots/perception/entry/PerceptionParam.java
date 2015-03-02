package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;
import com.dahuangit.iots.perception.enums.ParamType;

/**
 * 感知端参数实体类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:26:24
 */
@Entity
@Table(name = "t_perception_param")
public class PerceptionParam extends BaseModel {
	/** 主键id */
	@Id
	@GeneratedValue
	@Column(name = "pp_id")
	private Integer perceptionParamId = null;

	/** 所属设备类型 */
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	/** 默认值 */
	@Column(name = "default_value")
	private Integer defaultValue = null;

	/** 参数类型 */
	@Column(name = "param_type")
	@Enumerated(EnumType.STRING)
	private ParamType paramType = null;

	/** 参数名称(中文描述) */
	@Column(name = "pp_name")
	private String perceptionParamDesc = null;

	/** 所属设备类型 */
	@ManyToOne
	@JoinColumn(name = "pt_id", insertable = false, updatable = false)
	private PerceptionType perceptionType = null;

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPerceptionParamDesc() {
		return perceptionParamDesc;
	}

	public void setPerceptionParamDesc(String perceptionParamDesc) {
		this.perceptionParamDesc = perceptionParamDesc;
	}

	public ParamType getParamType() {
		return paramType;
	}

	public void setParamType(ParamType paramType) {
		this.paramType = paramType;
	}

	public PerceptionType getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(PerceptionType perceptionType) {
		this.perceptionType = perceptionType;
	}

}
