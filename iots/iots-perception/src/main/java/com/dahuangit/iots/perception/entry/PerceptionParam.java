package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

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
	@Id
	@GeneratedValue
	@Column(name = "pp_id")
	private Integer perceptionParamId = null;

	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	@Column(name = "pp_name")
	private String perceptionParamName = null;

	public Integer getPerceptionParamId() {
		return perceptionParamId;
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

	public String getPerceptionParamName() {
		return perceptionParamName;
	}

	public void setPerceptionParamName(String perceptionParamName) {
		this.perceptionParamName = perceptionParamName;
	}

}
