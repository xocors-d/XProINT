package com.gwzone.bot.xpro.sms.testing;

import org.smslib.AGateway;
import org.smslib.AGateway.Protocols;
import org.smslib.ICallNotification;
import org.smslib.IUSSDNotification;
import org.smslib.InboundNotification;
import org.smslib.Library;
import org.smslib.Service;
import org.smslib.USSDResponse;
import org.smslib.modem.SerialModemGateway;

public class MakeCalls {
	
	private CallNotification callNotification;
	
	public MakeCalls() {
		// TODO Auto-generated constructor stub
	}
	
	public void doIt() throws Exception
	{
		Service srv;
		USSDNotification ussdNotification = new USSDNotification();
		callNotification = new CallNotification();
		System.out.println("Example: Send USSD Command from a serial gsm modem.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		//srv = new Service();
		srv = Service.getInstance();
		SerialModemGateway gateway = new SerialModemGateway("Wavecom", "COM11", 115200, "Wavecom", "Multiband");
		gateway.setProtocol(Protocols.PDU);
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("0000");
		//gateway.getATHandler().setStorageLocations("SMMTME");
		//gateway.getATHandler().setStorageLocations("SMSR");
		srv.setUSSDNotification(ussdNotification);
		srv.setCallNotification(callNotification);
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
		//String resp = gateway.sendUSSDCommand("*111#"); //SMARTONE Check balance.
		String resp = gateway.sendUSSDCommand("ATD51231041;"); //SMARTONE Call a number.
		//String resp = gateway.sendUSSDCommand("*111#");
		//resp = gateway.sendCustomATCommand("ATD51231041;");
			
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
		MakeCalls app = new MakeCalls();
		try
		{
			app.doIt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	class CallNotification implements ICallNotification
	{
		@Override
		public void process(org.smslib.AGateway gateway, String callerId)
		{
			
		}
	}

}
