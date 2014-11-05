package com.dahuangit.seobi.receiver.dto.oxm.request;

import java.util.ArrayList;
import java.util.List;

public class QQTalkMsgsReqXml {
	/** 客户端密码 */
	private String clientpwd = null;

	private List<QQTalkMsgXml> qqTalkMsgXmls = new ArrayList<QQTalkMsgXml>();

	public String getClientpwd() {
		return clientpwd;
	}

	public void setClientpwd(String clientpwd) {
		this.clientpwd = clientpwd;
	}

	public List<QQTalkMsgXml> getQqTalkMsgXmls() {
		return qqTalkMsgXmls;
	}

	public void setQqTalkMsgXmls(List<QQTalkMsgXml> qqTalkMsgXmls) {
		this.qqTalkMsgXmls = qqTalkMsgXmls;
	}

}
