package com.gwzone.bot.xpro.sms.testing;

//SendUSSD.java - Sample application.
//
//This application shows you the basic procedure for sending messages.
//You will find how to send synchronous and asynchronous messages.
//
//For asynchronous dispatch, the example application sets a callback
//notification, to see what's happened with messages.


import org.smslib.AGateway;
import org.smslib.AGateway.Protocols;
import org.smslib.IUSSDNotification;
import org.smslib.InboundNotification;
import org.smslib.Library;
import org.smslib.Service;
import org.smslib.USSDResponse;
import org.smslib.modem.SerialModemGateway;

public class SendUSSD
{
	public void doIt() throws Exception
	{
		Service srv;
		USSDNotification ussdNotification = new USSDNotification();
		System.out.println("Example: Send USSD Command from a serial gsm modem.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		//srv = new Service();
		srv = Service.getInstance();
		SerialModemGateway gateway = new SerialModemGateway("Wavecom", "COM5", 115200, "Wavecom", "Multiband");
		gateway.setProtocol(Protocols.PDU);
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("0000");
		//gateway.getATHandler().setStorageLocations("SMMTME");
		gateway.getATHandler().setStorageLocations("SM");
		srv.setUSSDNotification(ussdNotification);
		srv.setInboundMessageNotification(new InboundNotification());
		srv.addGateway(gateway);
		srv.startService();
		System.out.println();
		System.out.println("Modem Information:");
		System.out.println("  Manufacturer: " + gateway.getManufacturer());
		System.out.println("  Model: " + gateway.getModel());
		System.out.println("  Serial No: " + gateway.getSerialNo());
		System.out.println("  SIM IMSI: " + gateway.getImsi());
		System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
		System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
		System.out.println();
		

		//EasyCall String resp = gateway.sendUSSDCommand("#117*2#"); // not working
		//String resp = gateway.sendUSSDCommand("##107#"); //3HK
		//String resp = gateway.sendUSSDCommand("##122#"); //PCCW
		//String resp = gateway.sendUSSDCommand("*#130#"); //CMHK
		String resp = gateway.sendUSSDCommand("*111#"); //SMARTONE Check balance.
		//String resp = gateway.sendUSSDCommand("*#123#"); //SMARTONE Check balance.
		//String resp = gateway.sendUSSDCommand("*111*111111#"); //SMARTONE change password to 111111.
		//String resp = gateway.sendCustomATCommand("AT+CUSD=1,\"*888#\",15\r"); // working
		//String resp = gateway.sendCustomATCommand("AT+CUSD=1,\"#117*2#\",15\r"); // working
		System.out.println(resp);
		
	
		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		srv.stopService();
	}

	public class USSDNotification implements IUSSDNotification
	{
		public void process(AGateway gateway, USSDResponse response) {
			System.out.println("USSD handler called from Gateway: " + gateway.getGatewayId());
			System.out.println(response);
		}
	}

	public static void main(String args[])
	{
		SendUSSD app = new SendUSSD();
		try
		{
			app.doIt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
