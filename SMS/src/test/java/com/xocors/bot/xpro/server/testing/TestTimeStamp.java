package com.xocors.bot.xpro.server.testing;


import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class TestTimeStamp {

	public TestTimeStamp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		long timeStamp = LocalTime.now().toDateTimeToday().getMillis();
		System.out.println(timeStamp);
		long inputTime = 1441977995083L;
		String inputStr = "14fba922628";
		inputStr="14fc14a6970";
		LocalDateTime dt = new LocalDateTime(inputTime);
		System.out.println("IR: "+dt.toString());
		inputTime = Long.parseLong(inputStr.trim(), 16);
		dt = new LocalDateTime(inputTime);
		System.out.println("UK: "+dt.toString());
		inputStr = "14ff2cba737";
		inputTime = Long.parseLong(inputStr.trim(), 16);
		dt = new LocalDateTime(inputTime);
		System.out.println("HK: "+dt.toString());
		//inputTime = 1442878055225L;
		inputTime = 1442884741153L;
		dt = new LocalDateTime(inputTime);
		System.out.println("IR Timestamp: "+dt.toString());
		
		long lastUpdateTime = LocalTime.now().toDateTimeToday().getMillis();
		System.out.println(lastUpdateTime);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(LocalTime.now().toDateTimeToday().getMillis());
		System.out.println("This thread sleep for "+(LocalTime.now().toDateTimeToday().getMillis()-lastUpdateTime));
		
		

	}

}
