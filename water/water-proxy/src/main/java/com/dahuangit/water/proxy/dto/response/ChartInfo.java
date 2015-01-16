package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ChartInfo {

	private List<String> categories = new ArrayList<String>();

	private List<Float> data = new ArrayList<Float>();

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}

}
