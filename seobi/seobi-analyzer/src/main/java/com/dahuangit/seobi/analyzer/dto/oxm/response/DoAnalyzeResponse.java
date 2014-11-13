package com.dahuangit.seobi.analyzer.dto.oxm.response;

import com.dahuangit.base.dto.oxm.response.OxResponse;

public class DoAnalyzeResponse extends OxResponse {

	private double originarityPercent = 0;

	public double getOriginarityPercent() {
		return originarityPercent;
	}

	public void setOriginarityPercent(double originarityPercent) {
		this.originarityPercent = originarityPercent;
	}

}
