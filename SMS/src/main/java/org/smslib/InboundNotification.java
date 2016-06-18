package org.smslib;

import java.io.IOException;

import org.smslib.Message.MessageTypes;

public class InboundNotification implements IInboundMessageNotification {
	
	public InboundNotification(){
		
	}

	public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
	{
		if (msgType == MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
		else if (msgType == MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
		System.out.println(msg.getText());//Test
		try {
			Service.getInstance().deleteMessage(msg);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
