package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

@Entity
@Table(name = "t_perception_param_value_info")
public class PerceptionParamValueInfo extends BaseModel {
	@Id
	@GeneratedValue
	@Column(name = "ppv_id")
	private Integer perceptionParamValueInfoId = null;

	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	@Column(name = "pp_id")
	private Integer perceptionParamId = null;

	@Column(name = "pp_value")
	private Integer perceptionParamValue = null;

	@Column(name = "param_value_desc")
	private String perceptionParamValueDesc = null;

	public Integer getPerceptionParamValueInfoId() {
		return perceptionParamValueInfoId;
	}

	public void setPerceptionParamValueInfoId(Integer perceptionParamValueInfoId) {
		this.perceptionParamValueInfoId = perceptionParamValueInfoId;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public Integer getPerceptionParamValue() {
		return perceptionParamValue;
	}

	public void setPerceptionParamValue(Integer perceptionParamValue) {
		this.perceptionParamValue = perceptionParamValue;
	}

	public String getPerceptionParamValueDesc() {
		return perceptionParamValueDesc;
	}

	public void setPerceptionParamValueDesc(String perceptionParamValueDesc) {
		this.perceptionParamValueDesc = perceptionParamValueDesc;
	}

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

}
