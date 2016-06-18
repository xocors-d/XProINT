// SMSLib for Java v3
// A Java API library for sending and receiving SMS via a GSM modem
// or other supported gateways.
// Web Site: http://www.smslib.org
//
// Copyright (C) 2002-2012, Thanasis Delenikas, Athens/GREECE.
// SMSLib is distributed under the terms of the Apache License version 2.0
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.smslib.smsserver;

import org.smslib.*;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.helper.Logger;
import org.smslib.modem.ModemGateway;
import org.smslib.smsserver.gateways.AGateway;
import org.smslib.smsserver.interfaces.Interface;
import org.smslib.smsserver.interfaces.Interface.InterfaceTypes;
import org.springframework.core.env.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

/**
 * SMSServer Application.
 */
//Change to a thread
//20150729 public class SMSServer
public class SMSServer implements Runnable //20150729
{

	Environment environment;

	Properties props;

	boolean shutdown = false;

	List<Interface<? extends Object>> infList;

	InboundNotification inboundNotification;

	OutboundNotification outboundNotification;

	CallNotification callNotification;

	QueueSendingNotification queueSendingNotification;

	OrphanedMessageNotification orphanedMessageNotification;

	InboundPollingThread inboundPollingThread;

	OutboundPollingThread outboundPollingThread;
//	String serverIP;
//	int serverPort;

	//Gateway ID vs Mobile number
	static Map<String, String> gtwNumMap;
	static Map<String, String> numGtwMap;

	boolean optRunOnce = false;

	private boolean registered = false;

	boolean loadConfigCompleted = false;

	//XProSMSDispatcher xproSMSDispatcher;

	//public SMSServer(XProSMSDispatcher xproSMSDispatcher, Environment environment)
	public SMSServer(Environment environment)
	{
		System.setProperty("smslib.queuedir", "temp");//Initialize queue folder. 20160404

		this.infList = new ArrayList<Interface<? extends Object>>();
		this.environment = environment;
		//20150815 Runtime.getRuntime().addShutdownHook(new Shutdown());
		//20150731 this.inboundNotification = new InboundNotification();
		//20150917 this.inboundNotification = new InboundNotification(environment.getProperty("xpro.server.ip"), Integer.parseInt(environment.getProperty("xpro.server.port"))); //20150831 Introduce call back function.
		this.outboundNotification = new OutboundNotification();
		this.callNotification = new CallNotification();
		this.queueSendingNotification = new QueueSendingNotification();
		orphanedMessageNotification = new OrphanedMessageNotification();
		this.inboundPollingThread = null;
		this.outboundPollingThread = null;
		this.gtwNumMap = new HashMap<String, String>();
		this.numGtwMap = new HashMap<String, String>();
		//this.xproSMSDispatcher = xproSMSDispatcher; //20150730
		//20150917 Service.getInstance().setInboundMessageNotification(this.inboundNotification); //Comment this due to interrupted issue.
		Service.getInstance().setOutboundMessageNotification(this.outboundNotification);
		Service.getInstance().setCallNotification(this.callNotification);
		Service.getInstance().setQueueSendingNotification(this.queueSendingNotification);
		Service.getInstance().setOrphanedMessageNotification(this.orphanedMessageNotification);

	}

	public List<Interface<? extends Object>> getInfList()
	{
		return this.infList;
	}

	public boolean getShutdown()
	{
		return shutdown;
	}

	public Properties getProperties()
	{
		return this.props;
	}

	class Shutdown extends Thread
	{
		@Override
		public void run()
		{
			Logger.getInstance().logInfo("SMSServer shutting down, please wait...", null, null);
			SMSServer.this.shutdown = true;
			try
			{
				stopInterfaces();
				if (Service.getInstance().getQueueManager() != null) Service.getInstance().getQueueManager().removeAllPendingMessages();
				if (Service.getInstance().getQueueManager() != null) Service.getInstance().getQueueManager().removeAllDelayedMessages();
				Service.getInstance().stopService();
				if (SMSServer.this.inboundPollingThread != null)
				{
					SMSServer.this.inboundPollingThread.interrupt();
					SMSServer.this.inboundPollingThread.join();
				}
				if (SMSServer.this.outboundPollingThread != null)
				{
					SMSServer.this.outboundPollingThread.interrupt();
					SMSServer.this.outboundPollingThread.join();
				}
			}
			catch (Exception e)
			{
				Logger.getInstance().logError("Shutdown hook error.", e, null);
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void loadConfiguration() throws Exception
	{
		FileInputStream f = null;


		try
		{
//			serverIP = environment.getProperty("xpro.server.ip");
//			serverPort = Integer.valueOf(environment.getProperty("xpro.server.port"));
			this.props = new Properties();

			props.load(ClassLoader.getSystemResourceAsStream("SMSServer.conf"));// change to load SMSServer.conf from class path

			/* change to load SMSServer.conf from class path
			if (System.getProperty("smsserver.configdir") != null) f = new FileInputStream(System.getProperty("smsserver.configdir") + "SMSServer.conf");
			else if (System.getProperty("smsserver.configfile") != null) f = new FileInputStream(System.getProperty("smsserver.configfile"));
			else f = new FileInputStream("SMSServer.conf");
			getProperties().load(f);
			 */
			if (getProperties().getProperty("smsserver.balancer", "").length() > 0)
			{
				try
				{
					Class<?> c = Class.forName((getProperties().getProperty("smsserver.balancer", "").indexOf('.') == -1 ? "org.smslib.balancing." : "") + getProperties().getProperty("smsserver.balancer", ""));
					Constructor<?> constructor = c.getConstructor();
					org.smslib.balancing.LoadBalancer balancer = (org.smslib.balancing.LoadBalancer) constructor.newInstance();
					Service.getInstance().setLoadBalancer(balancer);
					Logger.getInstance().logInfo("SMSServer: set balancer to: " + getProperties().getProperty("smsserver.balancer", ""), null, null);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Logger.getInstance().logError("SMSServer: error setting custom balancer!", null, null);
				}
			}
			if (getProperties().getProperty("smsserver.router", "").length() > 0)
			{
				try
				{
					Class<?> c = Class.forName((getProperties().getProperty("smsserver.router", "").indexOf('.') == -1 ? "org.smslib.routing." : "") + getProperties().getProperty("smsserver.router", ""));
					Constructor<?> constructor = c.getConstructor();
					org.smslib.routing.Router router = (org.smslib.routing.Router) constructor.newInstance();
					Service.getInstance().setRouter(router);
					Logger.getInstance().logInfo("SMSServer: set router to: " + getProperties().getProperty("smsserver.router", ""), null, null);
				}
				catch (Exception e)
				{
					Logger.getInstance().logError("SMSServer: error setting custom balancer!", null, null);
				}
			}
			for (int i = 0; i < Integer.MAX_VALUE; i++)
			{
				try
				{
					String propName = "gateway." + i;
					String propValue = getProperties().getProperty(propName, "");
					if (propValue.length() == 0) break;
					StringTokenizer tokens = new StringTokenizer(propValue, ",");
					String gtwId = tokens.nextToken().trim();
					String gtwClass = tokens.nextToken().trim();
					Object[] args = new Object[] { gtwId, getProperties(), this };
					Class<?>[] argsClass = new Class[] { String.class, Properties.class, SMSServer.class };
					Class<?> c = Class.forName((gtwClass.indexOf('.') == -1 ? "org.smslib.smsserver.gateways." : "") + gtwClass);
					Constructor<?> constructor = c.getConstructor(argsClass);
					AGateway gtw = (AGateway) constructor.newInstance(args);
					gtw.create();
					Service.getInstance().addGateway(gtw.getGateway());
					gtwNumMap.put(gtw.getGatewayId(), getProperties().getProperty(gtwId+".outbound_number", gtwId));//Load gateway ID and number mapping.
					numGtwMap.put(getProperties().getProperty(gtwId+".outbound_number", gtwId), gtw.getGatewayId());//Load gateway ID and number mapping.
					//Load Gateway ID Logger.getInstance().logInfo("SMSServer: added gateway " + gtwId + " / " + gtw.getDescription(), null, null);
					Logger.getInstance().logInfo("SMSServer: added gateway " + gtwId + " / " + gtw.getDescription() + "@" + gtwNumMap.get(gtw.getGatewayId()) , null, null);
				}
				catch (Exception e)
				{
					Logger.getInstance().logError("SMSServer: Unknown Gateway in configuration file!", null, null);
					e.printStackTrace();//Print error when initiating Gateway failed.
				}
			}
			for (int i = 0; i < Integer.MAX_VALUE; i++)
			{
				try
				{
					String propName = "interface." + i;
					String propValue = getProperties().getProperty(propName, "");
					if (propValue.length() == 0) break;
					StringTokenizer tokens = new StringTokenizer(propValue, ",");
					String infId = tokens.nextToken().trim();
					String infClass = tokens.nextToken().trim();
					InterfaceTypes infType = null;
					String sinfType = tokens.hasMoreTokens() ? tokens.nextToken() : "inoutbound";
					sinfType = sinfType.trim();
					if ("inbound".equalsIgnoreCase(sinfType))
					{
						infType = InterfaceTypes.INBOUND;
					}
					else if ("outbound".equalsIgnoreCase(sinfType))
					{
						infType = InterfaceTypes.OUTBOUND;
					}
					else
					{ // NULL or other crap
						infType = InterfaceTypes.INOUTBOUND;
					}
					Object[] args = new Object[] { infId, getProperties(), this, infType };
					Class<?>[] argsClass = new Class[] { String.class, Properties.class, SMSServer.class, InterfaceTypes.class };
					Class<?> c = Class.forName((infClass.indexOf('.') == -1 ? "org.smslib.smsserver.interfaces." : "") + infClass);
					Constructor<?> constructor = c.getConstructor(argsClass);
					Interface<? extends Object> inf = (Interface<? extends Object>) constructor.newInstance(args);
					getInfList().add(inf);
					Logger.getInstance().logInfo("SMSServer: added interface " + infId + " / " + inf.getDescription() + " / " + inf.getType(), null, null);
				}
				/* Check for exceptions thrown by the constructor itself */
				catch (InvocationTargetException e)
				{
					Logger.getInstance().logError("SMSServer: Illegal Interface configuration: " + e.getCause().getMessage(), null, null);
				}
				catch (Exception e)
				{
					Logger.getInstance().logError("SMSServer: Unknown Interface in configuration file!", null, null);
				}
			}

			/*20150730 Start
			if(getProperties().getProperty("settins.clear_pending_queue_on_startup", "no").equalsIgnoreCase("yes")){
				Logger.getInstance().logError("SMSServer: Clearing pending message(s)!", null, null);
				Service.getInstance().getQueueManager().removeAllPendingMessages();	
			}			
			20150730 End*/

		}
		finally
		{
			if (f != null) f.close();
		}
	}

	class InboundPollingThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				while (!SMSServer.this.shutdown)
				{
					Logger.getInstance().logDebug("InboundPollingThread() run.", null, null);
					readMessages();
					if (SMSServer.this.optRunOnce)
					{
						SMSServer.this.shutdown = true;
						new Shutdown().start();
						break;
					}
					Thread.sleep(Integer.parseInt(getProperties().getProperty("settings.inbound_interval", "60")) * 1000);
				}
			}
			catch (InterruptedException e)
			{
				Logger.getInstance().logDebug("InboundPollingThread() interrupted.", null, null);
			}
			catch (Exception e)
			{
				Logger.getInstance().logDebug("Error in InboundPollingThread()", e, null);
			}
		}
	}

	class OutboundPollingThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				while (!SMSServer.this.shutdown)
				{
					Logger.getInstance().logDebug("OutboundPollingThread() run.", null, null);
					sendMessages();
					if (SMSServer.this.optRunOnce) break;
					Thread.sleep(Integer.parseInt(getProperties().getProperty("settings.outbound_interval", "60")) * 1000);
				}
			}
			catch (InterruptedException e)
			{
				Logger.getInstance().logDebug("OutboundPollingThread() interrupted.", null, null);
			}
			catch (Exception e)
			{
				Logger.getInstance().logDebug("Error in OutboundPollingThread()", e, null);
			}
		}
	}

	private void process() throws Exception
	{
		this.inboundPollingThread = new InboundPollingThread();
		this.inboundPollingThread.setName("SMSServer - InboundPollingThread");
		this.inboundPollingThread.start();
		this.outboundPollingThread = new OutboundPollingThread();
		this.outboundPollingThread.setName("SMSServer - OutboundPollingThread");
		this.outboundPollingThread.start();
		while (!this.shutdown)
			Thread.sleep(1000);
	}

	void startInterfaces() throws Exception
	{
		for (Interface<? extends Object> inf : getInfList())
			inf.start();
	}

	void stopInterfaces() throws Exception
	{
		for (Interface<? extends Object> inf : getInfList())
			inf.stop();
	}

	//for XPro private void run() throws Exception
	public void run()//20150729
	{
		try
		{	
			loadConfiguration();
			loadConfigCompleted = true;//20150729
			startInterfaces();
			Service.getInstance().startService();
			process();
		}
		catch (Exception e)
		{
			Logger.getInstance().logError("SMSServer error!", e, null);
			try{ //20150729
				stopInterfaces();
				Service.getInstance().stopService();
				if (this.inboundPollingThread != null)
				{
					this.inboundPollingThread.interrupt();
					this.inboundPollingThread.join();
				}
				if (this.outboundPollingThread != null)
				{
					this.outboundPollingThread.interrupt();
					this.outboundPollingThread.join();
				}	
				/*20150729 Start*/
			}catch (Exception stopE){
				stopE.printStackTrace();
			}
			/*20150729 End*/

		}
	}

	void readMessages()
	{
		List<InboundMessage> msgList = new ArrayList<InboundMessage>();
		try
		{
			Service.getInstance().readMessages(msgList, MessageClasses.ALL);
			if (msgList.size() > 0)
			{
				for (Interface<? extends Object> inf : getInfList()){
					if (inf.isInbound()) inf.messagesReceived(msgList);
				}

				if (getProperties().getProperty("settings.delete_after_processing", "no").equalsIgnoreCase("yes")){
					//System.out.println("Deleting messages.");
					for (InboundMessage msg : msgList) {
						/*20150917 Start*/
						//Add received message handling.
						Logger.getInstance().logInfo("Message received message " + msg.getText(), null, msg.getGatewayId());

						//Extracting received SMS from Apple.
						String[] msgPaser;
						/*20160425 Start*/
						if(msg==null){
							continue;
						}
						/*20160425 End*/
						if(msg.getText().indexOf("：")!=-1){
							msgPaser = msg.getText().split("：");
						}else{
							msgPaser = msg.getText().split(" ");
						}

						//String[] msgPaser = msg.getText().split("：");
						String receivedCode = "";

						for(int i=msgPaser.length-1; i >= 0; i--){
							if(msgPaser[i].indexOf('.')!=-1){
								msgPaser[i] = msgPaser[i].substring(0, msgPaser[i].indexOf('.'));	
							}							
							if(msgPaser[i].matches("[A-Z0-9]*") && msgPaser[i].equals(msgPaser[i].toUpperCase())){
								receivedCode = msgPaser[i];
								Logger.getInstance().logInfo("Message received: " + msgPaser[i], null, msg.getGatewayId());
								break;
							}
						}

						if (receivedCode.length()<=3 ){
							Logger.getInstance().logInfo("Message doesn't contain registration code.", null, msg.getGatewayId());
							Service.getInstance().deleteMessage(msg);
							break;
						}

//						if(msg.getOriginator().indexOf(getAppleSMSNumber())==-1 && msg.getText().toUpperCase().indexOf("APPLE")==-1){
//							Logger.getInstance().logInfo("Message is not from Apple. It will be skipped.", null, msg.getGatewayId());
//							Service.getInstance().deleteMessage(msg);
//							break;
//						}

						//Revert to XPro server for further processing.
						//String appleID = xproSMSDispatcher.retrieveAppleIDByGtwID(msg.getGatewayId());
						String appleID = null;
						if(appleID==null){
							Logger.getInstance().logInfo("Gateway " +msg.getGatewayId() +" is not mapped. Message "+receivedCode+" is dropped.", null, msg.getGatewayId());
							Service.getInstance().deleteMessage(msg);
							break;
						}

//						XProServerUpdater serverUpdater = new XProServerUpdater(serverIP, serverPort);
//						SMS sms = new SMS(appleID, gtwNumMap.get(msg.getGatewayId()), receivedCode);
//						serverUpdater.setSms(sms);
//						Thread serverUpdaterThread = new Thread(serverUpdater, receivedCode+"@"+msg.getGatewayId()+"("+sms.getMobileNum()+")");
//						serverUpdaterThread.start();
						/*20150917 End*/

						Service.getInstance().deleteMessage(msg);
					}
				}

			}
		}
		catch (Exception e)
		{
			Logger.getInstance().logError("SMSServer: reading messages exception!", e, null);
		}
	}

	void sendMessages()
	{
		boolean foundOutboundGateway = false;
		for (org.smslib.AGateway gtw : Service.getInstance().getGateways())
			if (gtw.isOutbound())
			{
				foundOutboundGateway = true;
				break;
			}
		if (foundOutboundGateway)
		{
			List<OutboundMessage> msgList = new ArrayList<OutboundMessage>();
			try
			{
				for (Interface<? extends Object> inf : getInfList())
					if (inf.isOutbound()) msgList.addAll(inf.getMessagesToSend());
			}
			catch (Exception e)
			{
				Logger.getInstance().logError("SMSServer: sending messages exception!", e, null);
			}
			if (getProperties().getProperty("settings.send_mode", "sync").equalsIgnoreCase(("sync")))
			{
				Logger.getInstance().logInfo("SMSServer: sending synchronously...", null, null);
				for (OutboundMessage msg : msgList)
				{
					try
					{
						Service.getInstance().sendMessage(msg);
						for (Interface<? extends Object> inf : getInfList())
							if (inf.isOutbound()) inf.markMessage(msg);
					}
					catch (Exception e)
					{
						Logger.getInstance().logError("SMSServer: sending messages exception!", e, null);
						try
						{
							for (Interface<? extends Object> inf : getInfList())
								if (inf.isOutbound()) inf.markMessage(msg);
						}
						catch (Exception e1)
						{
							Logger.getInstance().logError("SMSServer: sending messages exception!", e1, null);
						}
					}
				}
			}
			else
			{	

				if (msgList.size()>0){
					Logger.getInstance().logInfo("SMSServer: sending asynchronously... [" + msgList.size() + "]", null, null);
				}
				for (OutboundMessage msg : msgList)
				{
					if (!Service.getInstance().queueMessage(msg))
					{
						try
						{
							for (Interface<? extends Object> inf : getInfList())
								if (inf.isOutbound()) inf.markMessage(msg);
						}
						catch (Exception e)
						{
							Logger.getInstance().logError("SMSServer: sending messages exception!", e, null);
						}
					}
				}
			}
		}
	}

	class InboundNotification implements IInboundMessageNotification
	{
		/*20150801 Start*/
//		private String serverIP;
//		private int serverPort;
//
//		public InboundNotification (String serverIP, int serverPort){
//			this.serverIP = serverIP;
//			this.serverPort = serverPort;
//		}

		/*20150801 End*/
		@Override
		public void process(org.smslib.AGateway gateway, MessageTypes msgType, InboundMessage msg)
		{
			List<InboundMessage> msgList = new ArrayList<InboundMessage>();
			msgList.add(msg);

			for (Interface<? extends Object> inf : getInfList())
				if (inf.isInbound())
				{
					try
					{
						//inf.messagesReceived(msgList);
						/*20150731 Start*/
						//Add received message handling.
						Logger.getInstance().logInfo("Message received message " + msg.getText(), null, gateway.getGatewayId());

						/*20150902 Start*/
						//Write received SMS to database;
						//inf.messagesReceived(msgList);
						/*20150902 End*/

						//Extracting received SMS from Apple.
						String[] msgPaser = msg.getText().split(" ");
						String receivedCode = "";

						for(int i=msgPaser.length-1; i >= 0; i--){
							if(msgPaser[i].indexOf('.')!=-1){
								msgPaser[i] = msgPaser[i].substring(0, msgPaser[i].indexOf('.'));	
							}							
//							if(msgPaser[i].matches("[A-Z0-9]*") && msgPaser[i].equals(msgPaser[i].toUpperCase())){
//								Logger.getInstance().logInfo("Message received SMS Code " + msgPaser[i], null, gateway.getGatewayId());
//								receivedCode = msgPaser[i];
//								break;
//							}
						}

						if (receivedCode.length()==0 || receivedCode.length()<=7 ){
							Logger.getInstance().logInfo("Message \""+msg.getText() +"\" doesn't contain registration code.", null, gateway.getGatewayId());
							break;
						}

						//Revert to XPro server for further processing.
						//String appleID = xproSMSDispatcher.retrieveAppleIDByGtwID(gateway.getGatewayId());
						String appleID = null;
						if(appleID==null){
							Logger.getInstance().logInfo("Gateway " +gateway.getGatewayId() +" is not mapped. Message "+receivedCode+" is dropped.", null, gateway.getGatewayId());
							break;
						}
						/*
						XProSMSRequest smsReq = xproSMSDispatcher.retrieveSMSReqByGtwID(gateway.getGatewayId());
						if (smsReq==null){
							Logger.getInstance().logInfo("Gateway " +gateway.getGatewayId() +" is not mapped. Message "+receivedCode+" is dropped.", null, gateway.getGatewayId());
							break;
						}
						 */

//						XProServerUpdater serverUpdater = new XProServerUpdater(serverIP, serverPort);
//						SMS sms = new SMS(appleID, gtwNumMap.get(gateway.getGatewayId()), receivedCode);
//						serverUpdater.setSms(sms);
//						Thread serverUpdaterThread = new Thread(serverUpdater, receivedCode+"@"+gateway.getGatewayId()+"("+sms.getMobileNum()+")");
//						serverUpdaterThread.start();
						/*20150731 End*/

					}
					catch (Exception e)
					{
						Logger.getInstance().logError("Error receiving message!", e, null);
					}
				}
			if (getProperties().getProperty("settings.delete_after_processing", "no").equalsIgnoreCase("yes"))
			{
				try
				{
					Service.getInstance().deleteMessage(msg);
				}
				catch (Exception e)
				{
					Logger.getInstance().logError("Error deleting received message!", e, null);
				}
			}
			msgList.clear();
		}
	}

	class OutboundNotification implements IOutboundMessageNotification
	{
		@Override
		public void process(org.smslib.AGateway gateway, org.smslib.OutboundMessage msg)
		{
			try
			{
				for (Interface<? extends Object> inf : getInfList())
					if (inf.isOutbound()) inf.markMessage(msg);
			}
			catch (Exception e)
			{
				Logger.getInstance().logError("IOutboundMessageNotification error.", e, null);
			}
		}
	}

	class CallNotification implements ICallNotification
	{
		@Override
		public void process(org.smslib.AGateway gateway, String callerId)
		{
			try
			{
				for (Interface<? extends Object> inf : getInfList())
					inf.callReceived(gateway.getGatewayId(), callerId);
			}
			catch (Exception e)
			{
				Logger.getInstance().logError("ICallNotification error.", e, null);
			}
		}
	}

	class QueueSendingNotification implements IQueueSendingNotification
	{
		@Override
		public void process(org.smslib.AGateway gateway, OutboundMessage msg)
		{
			Logger.getInstance().logInfo("**** >>>> Now Sending: " + msg.getRecipient(), null, gateway.getGatewayId());
		}
	}

	class OrphanedMessageNotification implements IOrphanedMessageNotification
	{
		@Override
		public boolean process(org.smslib.AGateway gateway, InboundMessage msg)
		{
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("&&&&&&&&&&&&&&&&& ORPHANED INFO &&&&&&&&&&&&&&&&&");
			System.out.println("&&& ORPHANED INFO WILL BE DELETED IMMEDIATELY &&&");//Eric20160404
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println(msg);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("&&&&&&&&&&&&&&&&& ORPHANED INFO &&&&&&&&&&&&&&&&&");
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			// Return FALSE to leave orphaned parts in memory.
			//Eric20160404return false;
			//Eric20160404 Return TRUE to remove orphaned parts from memory.
			return true;//Eric20160404
		}
	}

	public boolean checkPriorityTimeFrame(int priority)
	{
		String timeFrame;
		String from, to, current;
		Calendar cal = Calendar.getInstance();
		if (priority < 0) timeFrame = getProperties().getProperty("settings.timeframe.low", "0000-2359");
		else if (priority == 0) timeFrame = getProperties().getProperty("settings.timeframe.normal", "0000-2359");
		else if (priority >= 0) timeFrame = getProperties().getProperty("settings.timeframe.high", "0000-2359");
		else timeFrame = "0000-2359";
		from = timeFrame.substring(0, 4);
		to = timeFrame.substring(5, 9);
		cal.setTime(new Date());
		current = cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + cal.get(Calendar.HOUR_OF_DAY) : "" + cal.get(Calendar.HOUR_OF_DAY);
		current += cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : "" + cal.get(Calendar.MINUTE);
		if ((Integer.parseInt(current) >= Integer.parseInt(from)) && (Integer.parseInt(current) < Integer.parseInt(to))) return true;
		return false;
	}

	/*20150730 Start
	public static void main(String[] args)
	{
		System.out.println(Library.getLibraryDescription());
		System.out.println("\nSMSLib API version: " + Library.getLibraryVersion());
		System.out.println("SMSServer version: " + Library.getLibraryVersion());
		SMSServer app = new SMSServer();
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equalsIgnoreCase("-runonce")) app.optRunOnce = true;
			else System.out.println("Invalid argument: " + args[i]);
		}
		try
		{
			app.run();
			Logger.getInstance().logInfo("SMSServer exiting normally.", null, null);
		}
		catch (Exception e)
		{
			Logger.getInstance().logError("SMSServer Error: ", e, null);
			try
			{
				Service.getInstance().stopService();
			}
			catch (Exception e1)
			{
				Logger.getInstance().logError("SMSServer error while shutting down: ", e1, null);
			}
		}
	}
	20150730 End*/
	//Retrieve Apple SMS number from properties file.
//	public String getAppleSMSNumber(){
//		return getProperties().getProperty("apple.smsNumber", "64500366");
//	}

//	public Map<String, String> getGtwNumMap(){
//		return gtwNumMap;
//	}

	public boolean isLoadConfigCompleted(){
		return loadConfigCompleted;
	}

	public void shutdown(){
		Shutdown sd = new Shutdown();
		sd.start();
	}

	public void makeACall(String mobileNum, String gtwID){
		ModemGateway gtw = (ModemGateway)Service.getInstance().getGateway(gtwID);
		//String result = "";
		try {
			//gtw.sendCustATCommand("ATD"+mobileNum+";\r");
			gtw.sendCustomATCommand("ATD"+mobileNum+";\r");
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
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

	public void sendUSSD(String ussdCommand, String receipient){

		ModemGateway gtw = (ModemGateway)Service.getInstance().getGateway(getGwtByReceipient(receipient));

		try {
			//Send customized USSD command.
			//AT+CUSD=1,"*555*87*1234#"
			//AT+CUSD=1,"1"
			//gtw.sendUSSDCommand("AT+CUSD=1,\""+ussdCommand+"\"");
			//System.out.println(gtw.sendCustomATCommand("AT+CUSD=1,\""+ussdCommand+"\", 15"));
			gtw.sendUSSDCommand(ussdCommand);
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
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
	/* excluded from 20160404 commit
	public void sendHeartBeatMessage(){
		HeartBeatMSG hbMsg = new HeartBeatMSG();
		hbMsg.setModuleID(Modules.SMS_SERVER);
		hbMsg.setServerIP(serverIP);
		hbMsg.setServerPort(serverPort);
		XProHeartBeatUpdater hbUpdater = new XProHeartBeatUpdater(hbMsg);
		Thread hbUpdaterThread = new Thread(hbUpdater, "SMS_HEART_BEAT_THREAD");
		hbUpdaterThread.start();	
	}

	public void registerSMSModule(){
		Module smsModule = new Module(Modules.SMS_SERVER);
		smsModule.setName("SMS Module");

		XProRegister register = new XProRegister(smsModule, serverIP, serverPort);
		Thread hbUpdaterThread = new Thread(register, "SERVER_REGISTER");
		hbUpdaterThread.start();
	}

	public boolean isRegistered(){
		return registered;
	}
	 */

	private String getGwtByReceipient(String receiptient){
		return numGtwMap.get(receiptient);
	}

}
