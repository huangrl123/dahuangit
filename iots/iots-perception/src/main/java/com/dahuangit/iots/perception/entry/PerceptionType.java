package com.dahuangit.iots.perception.entry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.dahuangit.base.entry.BaseModel;

/**
 * 设备类型
 * 
 * @author 大黄
 * 
 *         2015年1月22日下午2:32:05
 */
@Entity
@Table(name = "t_perception_type")
public class PerceptionType extends BaseModel {
	/** 设备类型主键id */
	@Id
	@GeneratedValue
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	/** 设备类型名称 */
	@Column(name = "pt_name")
	private String perceptionTypeName = null;

	/** 所拥有的参数 */
	@OneToMany(mappedBy = "perceptionType")
	private List<PerceptionParam> params = null;

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

	public List<PerceptionParam> getParams() {
		return params;
	}

	public void setParams(List<PerceptionParam> params) {
		this.params = params;
	}

}
