/**
 * CustomDateDeserializer.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2010 Shenzhen KNet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util.date;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * 提供给Jackson解析json，以改变默认的日期转换形式
 *
 * @author 吴林洪 
 * 
 * 创建于 2010-8-30 上午01:27:05 
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            String dateString = jp.getText();
            return DateUtils.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
    
}
