package com.dahuangit.iots.perception.entry;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dahuangit.base.entry.BaseModel;

/**
 * 感知端视频文件
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月29日 下午9:42:31
 */
@Entity
@Table(name = "t_perception_vedia_file")
public class PerceptionVediaFile extends BaseModel {
	@Id
	@GeneratedValue
	@Column(name = "f_id")
	private Integer fileId = null;

	@Column(name = "p_id")
	private Integer perceptionId = null;

	@Column(name = "file_name")
	private String fileName = null;

	@Column(name = "file_path")
	private String filePath = null;

	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
