package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ChartInfoZhutu {

	private List<String> categories = new ArrayList<String>();

	private List<DataItem> data = new ArrayList<DataItem>();

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}

}
