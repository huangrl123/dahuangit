package com.dahuangit.seobi.manager.dto.response;

import java.util.Date;

import com.dahuangit.base.dto.Response;
import com.dahuangit.util.bean.dto.Dto;

@Dto
public class RelatedSearchKeyResponse extends Response {
	/** 主键 */
	private Integer rskId = null;

	/** 相关搜索的关键字 */
	private String relatedSearchKey = null;

	/** 最后修改时间 */
	private Date lastModifyTime = new Date();

	/** 所属说说内容 */
	private Integer talkId = null;

	public Integer getRskId() {
		return rskId;
	}

	public void setRskId(Integer rskId) {
		this.rskId = rskId;
	}

	public String getRelatedSearchKey() {
		return relatedSearchKey;
	}

	public void setRelatedSearchKey(String relatedSearchKey) {
		this.relatedSearchKey = relatedSearchKey;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getTalkId() {
		return talkId;
	}

	public void setTalkId(Integer talkId) {
		this.talkId = talkId;
	}

}
