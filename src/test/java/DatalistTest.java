

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.junit.Test;

public class DatalistTest {

	@Test
	public void test() {

		List<Date> dates = new ArrayList<Date>();

		String str_date = "2018-06-10";
		String end_date = "2018-08-10";
		
		dates = datelist(str_date,end_date);
		
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < dates.size(); i++) {
			Date lDate = (Date) dates.get(i);
			String ds = formatter.format(lDate);
			System.out.println(" Date is ..." + ds);
		}
	}
	
	public List<Date> datelist(String start_date,String end_date){
		List<Date> dates = new ArrayList<Date>();
		DateFormat formatter;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate= null;
		Date endDate = null;
		try {
			startDate = (Date) formatter.parse(start_date);
			endDate = (Date) formatter.parse(end_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dates.add(new Date(curTime));
			curTime += interval;
		}
		return dates;
	}

}
