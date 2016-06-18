package org.smslib.modem.athandler;
import java.io.IOException;

import org.smslib.GatewayException;
import org.smslib.TimeoutException;
//org.smslib.modem.athandler.ATHandler_DLink_MF112D
import org.smslib.modem.ModemGateway;

public class ATHandler_DLink_MF112D extends ATHandler {

	public ATHandler_DLink_MF112D(ModemGateway myGateway) {
		super(myGateway);
		// TODO Auto-generated constructor stub
		/*20150818 Start*/
		setStorageLocations("SM");
		/*20150818 End*/
	}
	
	
	/**
	 * DLink Modem requires a DCS identifier with the CUSD command.
	 * Only a value of 15 (7-bit alphabet, language unspecified) seems to work
	 */
	@Override
	protected String formatUSSDCommand(String ussdCommand)
	{
		return formatUSSDCommand("1", ussdCommand, "15");
	}
	
	
	/**
	 * Get NetOperator Name only. 
	 * 
	 */
	@Override
	public String getNetworkOperator() throws TimeoutException, GatewayException, IOException, InterruptedException
	{
		String[] resp = super.getNetworkOperator().split(",");
		
		return (resp[2].replaceAll("\"",""));
	}
}
