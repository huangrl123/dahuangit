package com.dahuangit.seobi.analyzer.dto;

import java.util.List;

import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;

public class OriginarityPercentDto {
	/** 百度原创度 */
	private Double originarityPercent = null;

	/** 相关关键字 */
	private List<RelatedSearchKey> keys = null;

	public Double getOriginarityPercent() {
		return originarityPercent;
	}

	public void setOriginarityPercent(Double originarityPercent) {
		this.originarityPercent = originarityPercent;
	}

	public List<RelatedSearchKey> getKeys() {
		return keys;
	}

	public void setKeys(List<RelatedSearchKey> keys) {
		this.keys = keys;
	}

}
