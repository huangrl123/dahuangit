package com.dahuangit.iots.perception.service;

import java.io.IOException;
import java.util.List;

import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.Response;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.request.DelPerceptionVideoRequest;
import com.dahuangit.iots.perception.dto.request.SavePerceptionReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionByPageReq;
import com.dahuangit.iots.perception.dto.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.request.PerceptionParamStatusRequest;
import com.dahuangit.iots.perception.dto.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.request.UploadCurStatusParamRequest;
import com.dahuangit.iots.perception.dto.response.NoticeInfo;
import com.dahuangit.iots.perception.dto.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionParamStatusQueryResponse;
import com.dahuangit.iots.perception.dto.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.dto.response.QueryUserByPageResponse;
import com.dahuangit.iots.perception.dto.response.RemoteQuery2j2MachineResponse;
import com.dahuangit.iots.perception.entry.Perception;
import com.dahuangit.iots.perception.entry.PerceptionType;
import com.dahuangit.iots.perception.tcpserver.dto.PerceptionTcpDto;

public interface PerceptionService {
	/**
	 * 添加设备
	 * 
	 * @param req
	 * @throws IOException
	 */
	public void addPerception(SavePerceptionReq req) throws IOException;

	/**
	 * 通过地址查询设备
	 * 
	 * @param addr
	 * @return
	 */
	public Perception findPerceptionByAddr(String addr);

	/**
	 * 查询设备
	 * 
	 * @param req
	 * @return
	 */
	PageQueryResult<PerceptionOpResponse> findPerceptionByPage(FindPerceptionByPageReq req);

	/**
	 * 查询设备日志
	 * 
	 * @param req
	 * @return
	 */
	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req);

	/**
	 * 获取设备状态属性下拉选的值
	 * 
	 * @param paramId
	 * @return
	 */
	public ComboboxData getPerceptionParamValueListByParam(Integer paramId);

	/**
	 * 保存日志
	 * 
	 * @param perceptionTcpDto
	 */
	public void saveLog(PerceptionTcpDto perceptionTcpDto);

	/**
	 * 获取单个感知端
	 * 
	 * @param perceptionId
	 * @return
	 */
	public PerceptionOpResponse getPerception(Integer perceptionId);

	/**
	 * 远程控制电机
	 * 
	 * @param perceptionId
	 * @param opt
	 */
	public void remoteCtrlMachine2j2(RemoteCtrlPerceptionRequest req);

	/**
	 * 查询2+2电机状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public RemoteQuery2j2MachineResponse remoteQuery2j2Machine(Integer perceptionId);

	/**
	 * 设备上传当前状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response uploadCurStatusParam(UploadCurStatusParamRequest request) throws Exception;

	/**
	 * 远程控制(通用)
	 * 
	 * @param perceptionId
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public Response remoteOperateMachine(Integer perceptionId, Integer paramId, Integer paramValue);

	/**
	 * 查询设备状态
	 * 
	 * @param perceptionId
	 * @return
	 */
	public PerceptionParamStatusQueryResponse queryPerceptionStatus(PerceptionParamStatusRequest request);

	/**
	 * 获取到所有的设备类型
	 * 
	 * @return
	 */
	public List<PerceptionType> getAllPerceptionTypes();

	/**
	 * 根据用户id获取通知
	 * 
	 * @param userId
	 * @return
	 */
	public List<NoticeInfo> getNoticeInfos(Integer userId);

	/**
	 * 删除设备
	 * 
	 * @param perceptionId
	 */
	public void deletePerception(Integer perceptionId);

	/**
	 * 修改设备
	 * 
	 * @param req
	 */
	public void updatePerception(SavePerceptionReq req);

	public List<QueryUserByPageResponse> getRelateUser(Integer perceptionId);

	/**
	 * 修改设备管理员
	 * 
	 * @param req
	 */
	public void updatePerceptionManagers(Integer perceptionId, List<Integer> userIdList);

	/**
	 * 从ftp服务器上删除设备的视频文件
	 * 
	 * @param req
	 * @throws Exception
	 */
	public void delPerceptionVideo(DelPerceptionVideoRequest req) throws Exception;
}
