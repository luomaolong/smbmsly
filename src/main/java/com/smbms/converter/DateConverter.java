package com.smbms.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 日期转换器类
 * @author Administrator
 *
 */
public class DateConverter implements Converter<String, Date> {
	
	private String[] formatStr;
	

	public Date convert(String dateStr) {
		SimpleDateFormat format = null;
		for(int i=0;i<formatStr.length;i++){
			format = new SimpleDateFormat(formatStr[i]);
			try {
				Date date = format.parse(dateStr);
				return date;
			} catch (ParseException e) {
				continue;
			}
		}
		return null;
	}


	public void setFormatStr(String[] formatStr) {
		this.formatStr = formatStr;
	}
	
	
	

}
