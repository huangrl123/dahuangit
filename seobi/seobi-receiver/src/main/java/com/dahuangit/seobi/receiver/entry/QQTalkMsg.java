package com.dahuangit.seobi.receiver.entry;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import com.dahuangit.base.entry.BaseModel;
import com.opensymphony.xwork2.interceptor.annotations.Allowed;

/**
 * 说说信息表
 * 
 * @author 黄仁良
 * 
 */
@Entity
@Table(name = "T_TALK_MSG")
public class QQTalkMsg extends BaseModel {
	/** TM_ID integer not null comment '说说信息主键' */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TM_ID")
	private Integer tmId = null;

	/** AID integer comment '账号ID' */
	@Column(name = "AID", insertable = false, updatable = false)
	private Integer aid = null;

	/** TALK_CONTENT varchar(256) comment '说说内容' */
	@Column(name = "TALK_CONTENT")
	private String talkContent = null;

	/** PUBLISH_TIME date comment '发表时间' */
	@Column(name = "PUBLISH_TIME")
	private Date publishTime = null;

	/** TM_TX_ID varchar(64) qq说说腾讯id */
	@Column(name = "TM_TX_ID")
	private String tmTxId = null;

	/** BROWSE_COUNT integer comment '浏览次数' */
	@Column(name = "BROWSE_COUNT")
	private Integer browseCount = null;

	/** PRAISE_COUNT integer comment '赞数' */
	@Column(name = "PRAISE_COUNT")
	private Integer praiseCount = null;

	/** FROM_DEVICE varchar(64) comment '来自设备' */
	@Column(name = "FROM_DEVICE")
	private String fromDevice = null;

	/** GPS varchar(128) comment '地理位置信息' */
	@Column(name = "GPS")
	private String gps = null;

	/** REMARK varchar(128) comment '备注' */
	@Column(name = "REMARK")
	private String remark = null;

	/** 是否已经分析过 */
	@Column(name = "analyzed")
	@Type(type = "yes_no")
	private Boolean analyzed = Boolean.FALSE;

	/** 分析时间 */
	@Column(name = "analyze_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date analyzeTime = null;

	/** 原创度*/
	@Column(name = "originality_percent")
	private Double originalityPercent = null;

	/** qq账号信息 */
	@ManyToOne
	@JoinColumn(name = "AID", insertable = true, updatable = true)
	private QQAccount qqAccount;

	/** qq说说图片 */
	@OneToMany(mappedBy = "qqTalkMsg", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<QQTalkImg> qqTalkImgs = null;

	public Integer getTmId() {
		return tmId;
	}

	public void setTmId(Integer tmId) {
		this.tmId = tmId;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getTalkContent() {
		return talkContent;
	}

	public void setTalkContent(String talkContent) {
		this.talkContent = talkContent;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getTmTxId() {
		return tmTxId;
	}

	public void setTmTxId(String tmTxId) {
		this.tmTxId = tmTxId;
	}

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getFromDevice() {
		return fromDevice;
	}

	public void setFromDevice(String fromDevice) {
		this.fromDevice = fromDevice;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public QQAccount getQqAccount() {
		return qqAccount;
	}

	public void setQqAccount(QQAccount qqAccount) {
		this.qqAccount = qqAccount;
	}

	public List<QQTalkImg> getQqTalkImgs() {
		return qqTalkImgs;
	}

	public void setQqTalkImgs(List<QQTalkImg> qqTalkImgs) {
		this.qqTalkImgs = qqTalkImgs;
	}

	public double getOriginalityPercent() {
		return originalityPercent;
	}

	public void setOriginalityPercent(double originalityPercent) {
		this.originalityPercent = originalityPercent;
	}

	public Boolean getAnalyzed() {
		return analyzed;
	}

	public void setAnalyzed(Boolean analyzed) {
		this.analyzed = analyzed;
	}

	public Date getAnalyzeTime() {
		return analyzeTime;
	}

	public void setAnalyzeTime(Date analyzeTime) {
		this.analyzeTime = analyzeTime;
	}

}
