package kr.co.emforce.wonderbox.util;

import java.util.Date;

public class TimePositionMaker {
	public static int makeTimePosition(){
		Date now = new Date();
		// 스케쥴 월요일부터 시작
		return ((now.getDay() == 0) ? 6 : now.getDay()-1) * 24 + now.getHours() + 1; // MYSQL Split Index 1부터 시작
	}
}
