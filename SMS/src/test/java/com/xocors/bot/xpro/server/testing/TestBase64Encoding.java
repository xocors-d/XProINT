package com.xocors.bot.xpro.server.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


public class TestBase64Encoding {

	public TestBase64Encoding() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		File file = new File("_captcha.jpeg");
		if (!file.exists()){
			System.out.println("File not found.");
			return;
		}
		
		try {
			byte[] bytes = Files.readAllBytes(file.toPath());

			Base64.Encoder encoder = Base64.getEncoder();
			String imgBase64Str =  encoder.encode(bytes).toString();

			//String imgBase64Str = Base64.encode(bytes);
			System.out.println(imgBase64Str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
