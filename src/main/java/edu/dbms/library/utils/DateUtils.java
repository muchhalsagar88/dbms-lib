package edu.dbms.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class DateUtils {
	
	public static boolean isCameraCheckoutFriday() {
		
		LocalDateTime localTime = LocalDateTime.now();
		if(localTime.dayOfWeek().get() != DateTimeConstants.FRIDAY)
			return false;
		
		if(localTime.getHourOfDay() < 9 || localTime.getHourOfDay() > 12)
			return false;
		
		return true;
	}
	
	public static LocalDate getNextFriday() {
		
		LocalDate currDate = LocalDate.now();
		int currDay = currDate.getDayOfWeek();
		
		int diff = DateTimeConstants.FRIDAY - currDay;
		if(diff < 0) 
			diff += 7;
		
		LocalDate nextFriday = currDate.plusDays(diff);
		return nextFriday;
	}
	
	public static Date getNextThursday(LocalDate fromFriday) {
		
		LocalDate nextThursday = fromFriday.plusDays(7);
		LocalDateTime time = nextThursday.toLocalDateTime(LocalTime.MIDNIGHT);
		time = time.plusHours(18);
		
		return time.toDate();
	}
	
	public static Date formatToQueryDate(LocalDate localDate) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(localDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/*public static void main(String []args) {
		System.out.println(LocalDateTime.now().dayOfWeek().get());
		System.out.println(DateTimeConstants.FRIDAY);
	}*/
}
