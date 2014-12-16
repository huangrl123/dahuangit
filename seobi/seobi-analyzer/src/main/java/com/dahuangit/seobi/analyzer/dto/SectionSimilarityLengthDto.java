package com.dahuangit.seobi.analyzer.dto;

import java.util.List;

import com.dahuangit.seobi.analyzer.entry.RelatedSearchKey;

public class SectionSimilarityLengthDto {

	/** 百度相似长度 */
	private Double SectionSimilarityLength = null;

	/** 相关关键字 */
	private List<RelatedSearchKey> keys = null;

	public Double getSectionSimilarityLength() {
		return SectionSimilarityLength;
	}

	public void setSectionSimilarityLength(Double sectionSimilarityLength) {
		SectionSimilarityLength = sectionSimilarityLength;
	}

	public List<RelatedSearchKey> getKeys() {
		return keys;
	}

	public void setKeys(List<RelatedSearchKey> keys) {
		this.keys = keys;
	}

}
