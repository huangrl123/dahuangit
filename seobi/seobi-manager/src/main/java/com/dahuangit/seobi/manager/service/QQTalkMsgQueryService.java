package com.dahuangit.seobi.manager.service;

import java.util.List;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.manager.dto.response.QQTalkMsgResponse;
import com.dahuangit.seobi.manager.dto.response.RelatedSearchKeyResponse;

public interface QQTalkMsgQueryService {

	public PageQueryResult<QQTalkMsgResponse> findByPage(Integer start, Integer limit);

	public List<RelatedSearchKeyResponse> getRelatedSearchKeysByQQTalkMsgId(Integer qQTalkMsgId);

	public QQTalkMsgResponse findQQTalkMsg(Integer tmId);
}
