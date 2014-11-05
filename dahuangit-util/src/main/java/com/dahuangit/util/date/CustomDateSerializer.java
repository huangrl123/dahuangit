/**
 * CustomDateSerializer.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2010 Shenzhen Knet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util.date;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 提供给Jackson转换pojo到json，以改变默认的日期转换形式
 * 
 * @author 吴林洪
 * 
 *         创建于 2010-7-30 下午02:22:42
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String formattedDate = DateUtils.format(value);
        jgen.writeString(formattedDate);
    }
}