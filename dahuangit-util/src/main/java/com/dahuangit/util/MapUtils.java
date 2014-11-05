/**
 * MapUtils.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2010 Shenzhen KNet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * 扩展Commons的MapUtils
 *
 * @author 吴林洪 
 * 
 * 创建于 2010-11-1 下午02:43:59 
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {

    /**
     * 用指定的Key和Value构建Map
     * 
     * @param keys
     * @param values
     * @return
     */
    public static Map<String, Object> createMap(String[] keys, Object[] values) {
        Validate.notNull(keys, "keys");
        Validate.notNull(values, "values");
        Validate.isTrue(keys.length == values.length);
        
        Map<String, Object> map = new HashMap<String, Object>();
        for(int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
    
}
