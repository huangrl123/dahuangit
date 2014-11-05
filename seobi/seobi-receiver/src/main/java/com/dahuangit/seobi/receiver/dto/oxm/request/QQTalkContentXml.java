package com.dahuangit.seobi.receiver.dto.oxm.request;

import java.util.ArrayList;
import java.util.List;

public class QQTalkContentXml {
	/** 说说内容 */
	private String talkContent = null;

	/** 发布时间 */
	private String publishTime = null;

	/** 说说腾讯ID */
	private String talkTxId = null;

	/** 浏览次数 */
	private Integer browseCount = null;

	/** 赞数 */
	private Integer praiseCount = null;

	/** 来自设备 */
	private String fromDevice = null;

	/** 地理位置信息 */
	private String gps = null;

	/** 备注 */
	private String remark = null;

	/** 说说图片内容集合数组 */
	private List<QQTalkImgXml> qqTalkImgXmls = new ArrayList<QQTalkImgXml>();

	public String getTalkContent() {
		return talkContent;
	}

	public void setTalkContent(String talkContent) {
		this.talkContent = talkContent;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getTalkTxId() {
		return talkTxId;
	}

	public void setTalkTxId(String talkTxId) {
		this.talkTxId = talkTxId;
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

	public List<QQTalkImgXml> getQqTalkImgXmls() {
		return qqTalkImgXmls;
	}

	public void setQqTalkImgXmls(List<QQTalkImgXml> qqTalkImgXmls) {
		this.qqTalkImgXmls = qqTalkImgXmls;
	}

}
