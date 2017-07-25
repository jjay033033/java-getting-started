package top.lmoon.shadowsupdate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import top.lmoon.shadowsupdate.util.XmlUtil;

public class XmlMap {

	private Map map;
	
	private String path;

	public XmlMap(String path) throws FileNotFoundException {
		this.path = path;
		init();
	}

	private void init() throws FileNotFoundException {
//		FileInputStream fis = new FileInputStream(new File(path));
//		map = XmlUtil.toMap(fis);
//		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><configs>	<sleepTime>300000</sleepTime>	<servers>		<server><id>ishadowsocks</id><url>http://isx.yt</url><begin>id=&quot;portfolio&quot;</begin><end>id=&quot;footer&quot;</end><type>0</type><serverIpBegin>IP Address:&lt;span[^&gt;]*&gt;</serverIpBegin><serverIpEnd>&lt;</serverIpEnd><serverPortBegin>Port：</serverPortBegin><serverPortEnd>&lt;</serverPortEnd><passwordBegin>Password:&lt;span[^&gt;]*&gt;</passwordBegin><passwordEnd>&lt;</passwordEnd><encryptionBegin>Method:</encryptionBegin><encryptionEnd>&lt;</encryptionEnd>		</server>		<server><id>shadowsocks8</id><url>http://abc.shadowsocks8.org/</url><begin>&lt;section id=&quot;free&quot;</begin><end>&lt;section id=&quot;sslist&quot;</end><type>1</type><picUrlBegin>&lt;img src=&quot;</picUrlBegin><picUrlEnd>&quot;</picUrlEnd><severPicFlag>server</severPicFlag>		</server>		<!-- <server><id>freessr</id><url>https://freessr.xyz/</url><begin>免费shadowsocks账号</begin><end>SS免费分享站</end><type>0</type><serverIpBegin>服务器地址:</serverIpBegin><serverIpEnd>&lt;</serverIpEnd><serverPortBegin>端口:</serverPortBegin><serverPortEnd>&lt;</serverPortEnd><passwordBegin>密码:</passwordBegin><passwordEnd>&lt;</passwordEnd><encryptionBegin>加密方式:</encryptionBegin><encryptionEnd>&lt;</encryptionEnd>		</server> -->		<server><id>freevpnss</id><url>https://get.freevpnss.me/</url><begin>免费SS帐号</begin><end>商家推荐</end><type>0</type><serverIpBegin>服务器地址：</serverIpBegin><serverIpEnd>&lt;</serverIpEnd><serverPortBegin>端口：</serverPortBegin><serverPortEnd>&lt;</serverPortEnd><passwordBegin>码：</passwordBegin><passwordEnd>&lt;</passwordEnd><encryptionBegin>加密方式：</encryptionBegin><encryptionEnd>&lt;</encryptionEnd>		</server>	</servers></configs>";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><configs>	<sleepTime>300000</sleepTime>	<servers>		<server><id>ishadowsocks</id><url>http://isx.yt</url><begin>id=&quot;portfolio&quot;</begin><end>id=&quot;footer&quot;</end><type>0</type><serverIpBegin>IP Address:&lt;span[^&gt;]*&gt;</serverIpBegin><serverIpEnd>&lt;</serverIpEnd><serverPortBegin>Port：</serverPortBegin><serverPortEnd>&lt;</serverPortEnd><passwordBegin>Password:&lt;span[^&gt;]*&gt;</passwordBegin><passwordEnd>&lt;</passwordEnd><encryptionBegin>Method:</encryptionBegin><encryptionEnd>&lt;</encryptionEnd>		</server>	</servers></configs>";
		map = XmlUtil.toMap(xml);
	}

	public Map getConfigMap() throws FileNotFoundException {
		if (map == null) {
			init();
		}
		return map;
	}
	
}
