package com.dahuangit.iots.pcserver.service;

import java.util.List;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.pcserver.dto.request.SaveUserRequest;
import com.dahuangit.iots.pcserver.dto.request.QueryUserByPageRequest;
import com.dahuangit.iots.pcserver.dto.request.UserLoginRequest;
import com.dahuangit.iots.pcserver.dto.response.HeartResponse;
import com.dahuangit.iots.pcserver.dto.response.UserLoginResponse;
import com.dahuangit.iots.perception.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.perception.entry.User;

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

	/**
	 * 添加用户
	 * 
	 * @param request
	 */
	public void addUser(SaveUserRequest request);

	/**
	 * 修改用户
	 * 
	 * @param request
	 */
	public void updateUser(SaveUserRequest request);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(Integer userId);

	public User getUser(Integer userId);

	public List<QueryUserByPageResponse> getAllUser();
}
