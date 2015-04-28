package com.dahuangit.iots.pcserver.service;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.pcserver.dto.request.QueryUserByPageRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;

public interface UserService {
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	public UserLoginResponse login(UserLoginRequest request);

	/**
	 * 退出登录
	 */
	public void logout(Integer userId);

	/**
	 * 心跳
	 * 
	 * @param userName
	 */
	public HeartResponse heart(Integer userId);

	/**
	 * 通过分页查询用户
	 * 
	 * @param req
	 * @return
	 */
	public PageQueryResult<QueryUserByPageResponse> queryUserByPage(QueryUserByPageRequest req);
}
