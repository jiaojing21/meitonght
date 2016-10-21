package com.itsv.annotation.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;
/**
 * @author wy
 * ��дjpson��ʽ���ӿڸ�ʽ��ʱ�����ͣ�yyyy-MM-dd��
 */
@Component 
public class CustomDateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}

}
