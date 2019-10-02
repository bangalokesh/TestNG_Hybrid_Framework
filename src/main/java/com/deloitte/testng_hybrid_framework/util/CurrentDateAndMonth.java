package com.deloitte.testng_hybrid_framework.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Listeners;
@Listeners(Listener.class)

public class CurrentDateAndMonth{
	
	public static String CurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		String currentDate = sdf.format(new Date());
		return currentDate;
	}
	
	
	public static String CurrentMonth(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/yyyy");
		String currentMonth = sdf1.format(new Date());
		return currentMonth;
	}
	
	public static Timestamp Timestampval(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	public static Timestamp timeStampVal(){
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
}
