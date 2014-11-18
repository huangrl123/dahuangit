package com.dahuangit.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * 在系统启动时，更换mvc:annotation-driven中加载的AnnotationMethodHandlerAdapter内的StringHttpMessageConverter，
 * 使其可默认编码方式为UTF-8.
 *
 * @author 黄仁良
 * 
 * 创建于 2010-9-5 上午02:21:36 
 */
public class StringHttpMessageConverterAdjust implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter 
            = applicationContext.getBean(AnnotationMethodHandlerAdapter.class);
        HttpMessageConverter<?>[] messageConverters = annotationMethodHandlerAdapter.getMessageConverters();
        for(int i = 0; i < messageConverters.length; i++) {
            HttpMessageConverter<?> messageConverter = messageConverters[i];
            if(messageConverter instanceof StringHttpMessageConverter) {
                messageConverters[i] = new ConfigurableStringHttpMessageConverter();
                return;
            }
        }
    }
}
