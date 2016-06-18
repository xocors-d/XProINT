package com.xocors.bot.xpro.server.testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class TestUUID {

	
	private static int p = new Date(2005, 0, 15).getTimezoneOffset();
	private static int q = new Date(2005, 6, 15).getTimezoneOffset();
	
	
	public TestUUID() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		//String uuidStr = "dd43b9d8-7bb1-4882-b859-6a6e4e3a5141";
		//String uuidStr = "0e5ac0fa-ca66-40a0-a5fb-45fa73ce7641";
		//String uuidStr = "08B0E5C0-4FCB-11CF-AAA5-00401C608500";
		String uuidStr = "8bd44697-36d1-44e4-a762-4723924b5c82";
		
		UUID uuid = UUID.fromString(uuidStr);
		System.out.println("UUID Value is : "+uuid.version());
		System.out.println("UUID Value is : "+LocalTime.fromMillisOfDay(uuid.timestamp()).toString());
		System.out.println("UUID Least is : "+uuid.getLeastSignificantBits());
		System.out.println("UUID Most is : "+uuid.getMostSignificantBits());
		//LocalDate lDt = LocalDate.of(2015, 0, 15);
		//Calendar.getInstance().
		
		System.out.println(p);
		System.out.println(q);
		
		System.out.println(LocalDateTime.now().toString("m/d/yyyy h:m:s a"));
		System.out.println(escape("12/27/2015 12:14:50 AM"));
		
		SimpleDateFormat sdfgmt = new SimpleDateFormat("d/m/yyyy, h:m:s");
	    sdfgmt.setTimeZone(TimeZone.getTimeZone("UST"));
	    try {
			sdfgmt.parse("5/7/2005, 21:33:44");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(StringEscapeUtils.escapeHtml4("14/27/2015 12:14:50 AM"));
//		System.out.println(StringEscapeUtils.escapeEcmaScript("14/27/2015 12:14:50 AM"));
//		System.out.println(StringEscapeUtils.escapeJson("14/27/2015 12:14:50 AM"));
		
	    System.out.println(sdfgmt.getCalendar().getInstance().toString());
	    System.out.println(sdfgmt.toLocalizedPattern());
	}
	
	public static String escape(String str){
		ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from String
        String output = null;
        try {
			output = (String) engine.eval("escape('"+str+"')");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return output;
	}

}
