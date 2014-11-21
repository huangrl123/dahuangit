package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

@Entity
@Table(name = "t_perception_type")
public class PerceptionType extends BaseModel {
	@Id
	@GeneratedValue
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	@Column(name = "pt_name")
	private String perceptionTypeName = null;

	@Column(name = "remark")
	private String remark = null;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
