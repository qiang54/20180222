package com.store.mycoverter;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class MyConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {

		//clazz 转换目标类型
	    //value 要转换的对象
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date date = sdf.parse( (String)value);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
