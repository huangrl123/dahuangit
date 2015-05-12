package com.dahuangit.iots.pcserver.dto.request;

public class AppPerceptionVideoPlayRequest {
	private Integer userId = null;

	private Integer perceptionId = null;

	private String perceptionAddr = null;

	private String perceptionName = null;

	private String videoUrl = null;

	private String fileName = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
