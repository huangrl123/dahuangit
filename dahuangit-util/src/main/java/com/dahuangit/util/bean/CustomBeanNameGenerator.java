/**
 * CustomBeanNameGenerator.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2012 Shenzhen Knet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 为所有spring bean生成以fqn为bean名称。默认情况下只使用类名，容易造成多模块间的重复。
 *
 * @author 吴林洪 
 * 
 * 创建于 2012-10-12 上午12:22:38 
 */
public class CustomBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return definition.getBeanClassName();
    }

}
