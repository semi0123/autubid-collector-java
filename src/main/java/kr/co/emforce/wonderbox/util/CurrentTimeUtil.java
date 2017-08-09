package kr.co.emforce.wonderbox.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTimeUtil {
	
	public static String getCurrentTime(){
		return new StringBuffer("[" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "] ").toString();
	}
	
	public static String getCurrentTime(String dateForamt){
		return new StringBuffer("[" + new SimpleDateFormat(dateForamt).format(new Date()) + "] ").toString();
	}
}
