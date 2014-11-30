package com.dahuangit.iots.perception.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Request;

public class PerceptionVediaFileUploadNoticeRequest extends Request {
	private String perceptionAddr = null;

	private List<PerceptionVediaFileInfo> fileInfos = new ArrayList<PerceptionVediaFileInfo>();

	public List<PerceptionVediaFileInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<PerceptionVediaFileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

}
