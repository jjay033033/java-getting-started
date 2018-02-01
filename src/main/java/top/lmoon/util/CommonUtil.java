/**
 * 
 */
package top.lmoon.util;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * @author LMoon
 * @date 2018年2月1日
 * 
 */
public class CommonUtil {
	
	/**
	 * telnet 检测是否能连通
	 * 
	 * @param remoteServer
	 * @return
	 */
	public static boolean isConnected(String host,int port) {
		try {
			TelnetClient client = new TelnetClient();
			client.setDefaultTimeout(3000);
			client.setConnectTimeout(3000);
			client.connect(host, port);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
