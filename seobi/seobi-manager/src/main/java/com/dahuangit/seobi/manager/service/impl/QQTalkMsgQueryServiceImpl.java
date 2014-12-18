package com.dahuangit.seobi.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.seobi.analyzer.dao.RelatedSearchKeyDao;
import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;
import com.dahuangit.seobi.manager.dto.response.QQTalkMsgResponse;
import com.dahuangit.seobi.manager.dto.response.RelatedSearchKeyResponse;
import com.dahuangit.seobi.manager.service.QQTalkMsgQueryService;
import com.dahuangit.seobi.receiver.dao.QQTalkMsgDao;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;
import com.dahuangit.util.bean.dto.DtoBuilder;

@Component
@Transactional
public class QQTalkMsgQueryServiceImpl implements QQTalkMsgQueryService {

	@Autowired
	private QQTalkMsgDao qqTalkMsgDao = null;

	@Autowired
	private RelatedSearchKeyDao relatedSearchKeyDao = null;

	public PageQueryResult<QQTalkMsgResponse> findByPage(Integer start, Integer limit) {
		PageQueryResult<QQTalkMsgResponse> pageQueryResult = new PageQueryResult<QQTalkMsgResponse>();
		
		String rowshql = "from QQTalkMsg q order by q.publishTime desc";
		List<QQTalkMsg> rows = this.qqTalkMsgDao.findByPage(rowshql, start, limit);
		
		List<QQTalkMsgResponse> results = new ArrayList<QQTalkMsgResponse>();
		
		for (QQTalkMsg msg : rows) {
			QQTalkMsgResponse response = DtoBuilder.buildDto(QQTalkMsgResponse.class, msg);
			
			results.add(response);
		}
		
		String counthql = "select count(*) from QQTalkMsg";
		Long totalCount = this.qqTalkMsgDao.findRecordsCount(counthql);
		pageQueryResult.setTotalCount(totalCount);
		
		pageQueryResult.setResults(results);
		
		return pageQueryResult;
	}
	
	public QQTalkMsgResponse findQQTalkMsg(Integer tmId) {

		QQTalkMsg msg = this.qqTalkMsgDao.get(QQTalkMsg.class, tmId);

		QQTalkMsgResponse response = DtoBuilder.buildDto(QQTalkMsgResponse.class, msg);

		return response;
	}

	public List<RelatedSearchKeyResponse> getRelatedSearchKeysByQQTalkMsgId(Integer qQTalkMsgId) {
		List<RelatedSearchKey> list = relatedSearchKeyDao.findBy("talkId", qQTalkMsgId);
		List<RelatedSearchKeyResponse> responses = new ArrayList<RelatedSearchKeyResponse>();

		for (RelatedSearchKey key : list) {
			RelatedSearchKeyResponse response = DtoBuilder.buildDto(RelatedSearchKeyResponse.class, key);
			responses.add(response);
		}

		return responses;
	}
}
