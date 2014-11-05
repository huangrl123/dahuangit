package com.dahuangit.seobi.receiver.dto.oxm.request;

import java.util.ArrayList;
import java.util.List;

public class QQTalkMsgXml {

	/** qq */
	private String qq = null;

	/** qq名字 */
	private String qqName = null;

	/** qq说说信息实体集合数组 */
	private List<QQTalkContentXml> qqTalkContentXmls = new ArrayList<QQTalkContentXml>();

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

	public List<QQTalkContentXml> getQqTalkContentXmls() {
		return qqTalkContentXmls;
	}

	public void setQqTalkContentXmls(List<QQTalkContentXml> qqTalkContentXmls) {
		this.qqTalkContentXmls = qqTalkContentXmls;
	}

}
