package com.dahuangit.util.bean.dto;

/**
 * Merge Model后所触发的监听
 * 
 * @author 黄仁良
 * 
 */
public interface MergeModelEventListener {

	void afterMergeModels(Object dto, Object... models);

}
