package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

/**
 * 参数值
 * 
 * @author 大黄
 * 
 *         2015年1月22日下午2:28:52
 */
@Entity
@Table(name = "t_perception_param_value_info")
public class PerceptionParamValueInfo extends BaseModel {
	
	/**参数值主键id*/
	@Id
	@GeneratedValue
	@Column(name = "ppv_id")
	private Integer perceptionParamValueInfoId = null;

	/**所属的设备类型*/
	@Column(name = "pt_id")
	private Integer perceptionTypeId = null;

	/**所属参数*/
	@Column(name = "pp_id")
	private Integer perceptionParamId = null;

	/**具体值*/
	@Column(name = "pp_value")
	private Integer perceptionParamValue = null;

	/**参数值描述*/
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
