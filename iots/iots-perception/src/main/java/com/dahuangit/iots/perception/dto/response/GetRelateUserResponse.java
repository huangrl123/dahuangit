package com.dahuangit.iots.perception.dto.response;

import java.util.List;

import com.dahuangit.base.dto.Response;

public class GetRelateUserResponse extends Response {

	private List<QueryUserByPageResponse> allUserList = null;

	private List<QueryUserByPageResponse> relateUserList = null;

	public List<QueryUserByPageResponse> getAllUserList() {
		return allUserList;
	}

	public void setAllUserList(List<QueryUserByPageResponse> allUserList) {
		this.allUserList = allUserList;
	}

	public List<QueryUserByPageResponse> getRelateUserList() {
		return relateUserList;
	}

	public void setRelateUserList(List<QueryUserByPageResponse> relateUserList) {
		this.relateUserList = relateUserList;
	}

}
