package com.dahuangit.seobi.analyzer.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;

/**
 * 相关搜索关键字
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月10日 下午2:48:44
 */
@Entity
@Table(name = "t_related_search_key")
public class RelatedSearchKey extends BaseModel {
	/** 主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rsk_id")
	private Integer rskId = null;

	/** 相关搜索的关键字 */
	@Column(name = "related_search_key")
	private String relatedSearchKey = null;

	/** 最后修改时间 */
	@Column(name = "last_modify_time")
	private Date lastModifyTime = new Date();

	/** 所属说说内容 */
	@Column(name = "tm_id")
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
