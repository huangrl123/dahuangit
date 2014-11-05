package com.dahuangit.seobi.receiver.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dahuangit.base.entry.BaseModel;

/**
 * 说说图片表
 */
@Entity
@Table(name = "T_TALK_IMG")
public class QQTalkImg extends BaseModel {
	/** TI_ID integer not null comment '说说图片表主键' */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TI_ID")
	private Integer tiId = null;

	/** TM_ID integer comment '说说信息主键' */
	@Column(name = "TM_ID", insertable = false, updatable = false)
	private Integer tmId = null;

	/** IMG_PATH varchar(256) comment '图片地址' */
	@Column(name = "IMG_PATH")
	private String imgPath = null;

	/** qq说说信息 */
	@ManyToOne
	@JoinColumn(name = "TM_ID", insertable = true, updatable = true)
	private QQTalkMsg qqTalkMsg = null;

	public Integer getTiId() {
		return tiId;
	}

	public void setTiId(Integer tiId) {
		this.tiId = tiId;
	}

	public Integer getTmId() {
		return tmId;
	}

	public void setTmId(Integer tmId) {
		this.tmId = tmId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public QQTalkMsg getQqTalkMsg() {
		return qqTalkMsg;
	}

	public void setQqTalkMsg(QQTalkMsg qqTalkMsg) {
		this.qqTalkMsg = qqTalkMsg;
	}

}
