package com.dahuangit.util.bean.dto;

/**
 * Built Dto后所触发的监听
 * 
 * @author 黄仁良
 * 
 * @param <D>
 */
public interface BuildDtoEventListener<D> {

	void afterBuildDto(D dto, Object... models);

}
