package com.dahuangit.water.proxy.dto.request;

import com.dahuangit.base.dto.Request;

/**
 * 登录请求
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午3:42:02
 */
public class LoginRequest extends Request {
	/** 操作员编号 */
	private String operatorNo = null;

	/** 操作员密码 */
	private String password = null;

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
