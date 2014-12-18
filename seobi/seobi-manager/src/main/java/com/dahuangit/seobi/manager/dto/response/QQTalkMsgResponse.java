package com.dahuangit.seobi.manager.dto.response;

import java.util.Date;

import com.dahuangit.base.dto.Response;
import com.dahuangit.util.bean.dto.Dto;

@Dto
public class QQTalkMsgResponse extends Response {
	/** TM_ID integer not null comment '说说信息主键' */
	private Integer tmId = null;

	/** AID integer comment '账号ID' */
	private Integer aid = null;

	/** TALK_CONTENT varchar(256) comment '说说内容' */
	private String talkContent = null;

	/** PUBLISH_TIME date comment '发表时间' */
	private Date publishTime = null;

	/** TM_TX_ID varchar(64) qq说说腾讯id */
	private String tmTxId = null;

	/** BROWSE_COUNT integer comment '浏览次数' */
	private Integer browseCount = null;

	/** PRAISE_COUNT integer comment '赞数' */
	private Integer praiseCount = null;

	/** FROM_DEVICE varchar(64) comment '来自设备' */
	private String fromDevice = null;

	/** GPS varchar(128) comment '地理位置信息' */
	private String gps = null;

	/** REMARK varchar(128) comment '备注' */
	private String remark = null;

	/** 是否已经分析过 */
	private Boolean analyzed = Boolean.FALSE;

	/** 分析时间 */
	private Date analyzeTime = null;

	/** 原创度 */
	private Double originalityPercent = null;

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

	public Double getOriginalityPercent() {
		return originalityPercent;
	}

	public void setOriginalityPercent(Double originalityPercent) {
		this.originalityPercent = originalityPercent;
	}

}
