package com.dahuangit.iots.perception.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseAuditableModel;

/**
 * 感知端参数实体类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月19日 上午9:26:24
 */
@Entity
@Table(name = "t_perceptoin_param")
public class PerceptionParam extends BaseAuditableModel {
	/** 感知端设备参数主键ID */
	@Id
	@GeneratedValue
	@Column(name = "ppid")
	private Integer ppId = null;

	/** 所属感知端设备id */
	@Column(name = "pid", insertable = true, updatable = true)
	private Integer pid = null;

	/** 知端设备参数参数键 */
	@Column(name = "pp_param_key")
	private Integer paramKey = null;

	/** 感知端设备参数参数值 */
	@Column(name = "pp_param_value")
	private Integer paramValue = null;

	/** 感知端设备参数参数描述 */
	@Column(name = "pp_param_desc")
	private String paramDesc = null;

	/** 所属的感知端 多对一双向关联 */
	@ManyToOne
	@JoinColumn(name = "pid", insertable = false, updatable = false)
	private Perception perception = null;

	public Integer getPpId() {
		return ppId;
	}

	public void setPpId(Integer ppId) {
		this.ppId = ppId;
	}

	public Integer getParamKey() {
		return paramKey;
	}

	public void setParamKey(Integer paramKey) {
		this.paramKey = paramKey;
	}

	public Integer getParamValue() {
		return paramValue;
	}

	public void setParamValue(Integer paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public Perception getPerception() {
		return perception;
	}

	public void setPerception(Perception perception) {
		this.perception = perception;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
