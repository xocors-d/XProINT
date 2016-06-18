package com.xocors.bot.xpro.server.testing;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PartNumValidator {

	public PartNumValidator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		File jsonInput = new File("C:\\temp\\partNumbers.json");
		//boolean isExists = jsonInput.exists();
		try {
			String jsonStr = new String(Files.readAllBytes(jsonInput.toPath()));
			JSONObject partNumJson = new JSONObject(jsonStr);
			JSONArray partNums = partNumJson.getJSONArray("partNumbers");
			for(int i=0; i<partNums.length(); i++){
				JSONObject obj = partNums.optJSONObject(i);
				//System.out.println(obj.getString("part_number") + ":"+obj.getString("productDescription")+"<->"+XProIphone.getDesc(obj.getString("part_number")));
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
