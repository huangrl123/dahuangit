package com.dahuangit.seobi.receiver.entry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.dahuangit.base.entry.BaseModel;

/**
 * 账号表
 * 
 * @author 黄仁良
 * 
 */
@Entity
@Table(name = "T_ACCOUNT")
public class QQAccount extends BaseModel{
	/** AID integer not null comment '账号ID' */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AID")
	private Integer aid = null;

	@Column(name = "QQ")
	/** QQ varchar(10) comment 'QQ号码' */
	private String qq = null;

	@Column(name = "QQ_NAME")
	/** QQ_NAME varchar(128) comment 'QQ昵称' */
	private String qqName = null;

	/** qq 说说内容 */
	@OneToMany(mappedBy = "qqAccount", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<QQTalkMsg> qqTalkMsgs;

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQqName() {
		return qqName;
	}

	public void setQqName(String qqName) {
		this.qqName = qqName;
	}

	public List<QQTalkMsg> getQqTalkMsgs() {
		return qqTalkMsgs;
	}

	public void setQqTalkMsgs(List<QQTalkMsg> qqTalkMsgs) {
		this.qqTalkMsgs = qqTalkMsgs;
	}

}
