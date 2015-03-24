package com.dahuangit.iots.pcserver.entry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;
import com.dahuangit.iots.perception.entry.Perception;

/**
 * 用户实体类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月12日 上午8:55:54
 */
@Entity
@Table(name = "t_user")
public class User extends BaseModel {
	/** 用户主键ID */
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer userId = null;

	/** 用户名称 */
	@Column(name = "user_name")
	private String userName = null;

	/** 用户昵称 */
	@Column(name = "user_abbr")
	private String userAbbr = null;

	/** 用户登录密码 */
	@Column(name = "pwd")
	private String password = null;

	/** 备注 */
	@Column(name = "remark")
	private String remark = null;

	/** 拥有的感知端 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "t_user_perception", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "perception_id", referencedColumnName = "p_id") })
	private List<Perception> perceptions = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAbbr() {
		return userAbbr;
	}

	public void setUserAbbr(String userAbbr) {
		this.userAbbr = userAbbr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Perception> getPerceptions() {
		return perceptions;
	}

	public void setPerceptions(List<Perception> perceptions) {
		this.perceptions = perceptions;
	}

}
