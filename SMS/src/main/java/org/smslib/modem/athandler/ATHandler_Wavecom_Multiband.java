package org.smslib.modem.athandler;

import org.smslib.modem.ModemGateway;

public class ATHandler_Wavecom_Multiband extends ATHandler {

	public ATHandler_Wavecom_Multiband(ModemGateway myGateway) {
		super(myGateway);
		/*20150818 Start*/
		setStorageLocations("SM");
		/*20150818 End*/
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Wavecom Modem requires a DCS identifier with the CUSD command.
	 * Only a value of 15 (7-bit alphabet, language unspecified) seems to work
	 */
	@Override
	protected String formatUSSDCommand(String ussdCommand)
	{
		return formatUSSDCommand("1", ussdCommand, "15");
	}

}
