package com.itsv.gbp.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class BeanUtil {

	
	/**
	 *将source 对象不等于NULl的值复制到target对象内  
	 * 暂不支持 集合的复制
	 */
	public static Object copyProperty(Object targer, Object source) {
		
		
		Class class_source=source.getClass();
		Class class_target=targer.getClass();
		String fieldType="",setMethodName="",getMethodName="";
		
		/**
		 * 读取源对象所有声明的字段
		 */
		Field[] fileds=class_source.getDeclaredFields();
		Object value=null;
		for (Field field : fileds) {
			
			if("serialVersionUID".equals(field.getName()))continue;
			
			String fieldName = field.getName().substring(0, 1).toUpperCase()+ field.getName().substring(1);
			fieldType=String.valueOf(field.getGenericType());
			setMethodName = "set" + fieldName;
			getMethodName = "get" + fieldName;
			
			try {
				Method getMethod = class_source.getMethod(getMethodName);
				String type = field.getGenericType().toString();
				value = getMethod.invoke(source);
				if("class java.util.Date".equals(type)){
					if(value != null){
						if("class java.util.Date".equals(value.getClass().toString())){
							Method setMethod = class_target.getMethod(setMethodName,value.getClass());
							setMethod.invoke(targer, value);
						}else if("class java.sql.Date".equals(value.getClass().toString())){
							Method setMethod = class_target.getMethod(setMethodName,(DateTool.objToDate(value)).getClass());
							setMethod.invoke(targer, value);
						}
					}
				}else{
					if(value instanceof List)
					{
						continue;
						
					}else				  {
						if (value != null) {
							Method setMethod = class_target.getMethod(setMethodName,value.getClass());
							setMethod.invoke(targer, value);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return targer;
	}
}
