/**
 * CustomBigDecimalSerializer.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2010 Shenzhen KNet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * TODO 吴林洪 -- 描述CustomBigDecimalSerializer的功能
 *
 * @author 吴林洪 
 * 
 * 创建于 2010-11-25 下午09:31:08 
 */
public class CustomBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        DecimalFormat df = new DecimalFormat("##.00");
        String formattedBd = df.format(value.doubleValue());
        jgen.writeString(formattedBd);
    }
    
    
    
}
