package org.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

	public static void main(String[] args) {
		Date preResignTime = null;//可离职日期：站长通过时间后延一个月
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(java.util.Calendar.DAY_OF_MONTH, 30);
		
		try {
			preResignTime = df.parse(df.format(cal.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(df.format(cal.getTime()));
		System.out.println(preResignTime);

	}

}
