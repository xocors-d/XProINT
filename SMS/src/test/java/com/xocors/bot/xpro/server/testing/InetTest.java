package com.xocors.bot.xpro.server.testing;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class InetTest {

	public static void main(String[] args){
		Enumeration<NetworkInterface> netInterfaces = null;
		
			try {
				netInterfaces = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(netInterfaces.hasMoreElements()){
				NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
				System.out.println(ni.getName() + " : " + ni.getDisplayName()+" - "+ni.getInterfaceAddresses().toString());
				Enumeration<InetAddress> intF =  ni.getInetAddresses();
				if (intF.hasMoreElements()){
					InetAddress ia = (InetAddress) intF.nextElement();
					CloseableHttpResponse response1 = null;
					try {
						CloseableHttpClient httpclient = HttpClients.createDefault();
						RequestConfig requestConfig = RequestConfig.custom()
								.setSocketTimeout(1000)
								.setConnectTimeout(1000)
								.setLocalAddress(ia)
								.build();

						HttpGet httpPost = new HttpGet("https://www.apple.com");
						httpPost.setConfig(requestConfig);
						response1 = httpclient.execute(httpPost);

						HttpEntity entity1 = response1.getEntity();
						System.out.println(EntityUtils.toString(entity1));
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						//response1.close();
					}


					/*
					 * HttpClientContext context = HttpClientContext.create();

					HttpClientConnectionManager connMrg = new BasicHttpClientConnectionManager();
					//HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
					HttpRoute route = new HttpRoute(new HttpHost("www.gwzone.com", 8080), ia, false);
					// Request new connection. This can be a long process
					ConnectionRequest connRequest = connMrg.requestConnection(route, null);
					// Wait for connection up to 10 sec
					HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
					try {
						// If not open
						if (!conn.isOpen()) {
							// establish connection based on its route info
							connMrg.connect(conn, route, 1000, context);
							// and mark it as route complete
							connMrg.routeComplete(conn, route, context);
						}

						System.out.println("Connection: "+conn.toString());
						// Do useful things with the connection.
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
					}
					 */
				}
			}
		
	}


}
