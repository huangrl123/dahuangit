package com.dahuangit.iots.pcserver.dto.response;

import java.util.List;

import com.dahuangit.base.dto.Response;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;

public class HeartResponse extends Response {

	private List<NoticeInfo> noticeInfos = null;

	public List<NoticeInfo> getNoticeInfos() {
		return noticeInfos;
	}

	public void setNoticeInfos(List<NoticeInfo> noticeInfos) {
		this.noticeInfos = noticeInfos;
	}


}
