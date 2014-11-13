package com.dahuangit.seobi.analyzer.entry;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.dahuangit.base.entry.BaseModel;
import com.dahuangit.seobi.receiver.entry.QQTalkMsg;

/**
 * qq说说搜索关联关键字
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月14日 上午11:20:51
 */
@Entity
@Table(name = "t_talk_msg_related_search_key")
public class TalkRelatedSearchKey extends BaseModel {

	/** 主键ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tm_rsk_id")
	private Integer tmRskId = null;

	/** 所属说说内容 */
	@ManyToOne
	@JoinColumn(name = "tm_id", insertable = true, updatable = true)
	private QQTalkMsg qqTalkMsg = null;

	/** 本条说说关键字所包含的所有词 */
	@OneToMany
	@JoinColumn(name = "tm_id", insertable = true, updatable = true)
	private List<RelatedSearchKey> relatedSearchKeys = null;

	public Integer getTmRskId() {
		return tmRskId;
	}

	public void setTmRskId(Integer tmRskId) {
		this.tmRskId = tmRskId;
	}

	public QQTalkMsg getQqTalkMsg() {
		return qqTalkMsg;
	}

	public void setQqTalkMsg(QQTalkMsg qqTalkMsg) {
		this.qqTalkMsg = qqTalkMsg;
	}

	public List<RelatedSearchKey> getRelatedSearchKeys() {
		return relatedSearchKeys;
	}

	public void setRelatedSearchKeys(List<RelatedSearchKey> relatedSearchKeys) {
		this.relatedSearchKeys = relatedSearchKeys;
	}

}
