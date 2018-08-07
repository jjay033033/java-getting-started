package top.lmoon.shadowsupdate.config;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import top.lmoon.heroku.dao.ConfsDAO;
import top.lmoon.shadowsupdate.SysConstants;
import top.lmoon.shadowsupdate.util.XmlUtil;
import top.lmoon.shadowsupdate.vo.ServerConfigVO;
import top.lmoon.spring.util.SpringContextUtil;
import top.lmoon.util.ExceptionUtil;

public class XmlConfig {

//	private static final Logger logger = LoggerFactory.getLogger(XmlConfig.class);

	// private static XmlConfig xmlConfig;

	private static Map map;

	private static Map<String, ServerConfigVO> serverMap = new LinkedHashMap<String, ServerConfigVO>();

//	private static String configPath;
	
//	private static long lastModify;
	
	private static final String SLEEP_TIME = "sleepTime";

	private static final String LOCAL_PORT = "localPort";
	
	private static final String GET_FROM = "getFrom";
	
	private static ConfsDAO confsDAO = (ConfsDAO) SpringContextUtil.getBean("confsDAO");

//	/** 重加载的间隔时间 **/
//	private static final long RELOAD_TIME = 30L;

	private XmlConfig() {

	}

	public static void init() {
//		XmlConfig.configPath = configPath;
		load();
//		ConfigLoader.init(outPath, qrcodePath, jsonFilePathName);;
		//启动定时任务
//		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
//			@Override
//			public void run() {
//				load();
//			}
//		}, RELOAD_TIME, RELOAD_TIME, TimeUnit.SECONDS);
	}

	private static void load() {
		try {
			map = XmlUtil.toMap(confsDAO.selectConf());
			if (map == null || map.isEmpty()) {
				throw new FileNotFoundException();
			}
//			logger.info("config.xml is updated！");
			initServerMap();
			ConfigListFactory.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println(ExceptionUtil.getExceptionMessage(e));
//			logger.error("config.xml初始化失败:", e);
		}
	}

	private static void initServerMap() {
		try {
			Map servers = (Map) map.get("servers");
			if (servers == null || servers.isEmpty()) {
				throw new Exception("没有servers项！");
			}
			Object o = servers.get("server");
			if (o == null) {
				throw new Exception("没有server项！");
			}
			List items;
			if (o instanceof List) {
				items = (List) o;
			} else {
				items = new ArrayList();
				items.add((Map) o);
			}

			if (items == null || items.isEmpty()) {
				throw new Exception("没有server项！");
			}
			serverMap.clear();
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				Map item = (Map) iter.next();
				if (item != null && item.get("id") != null) {
					ServerConfigVO vo = new ServerConfigVO();
					vo.setBegin((String) item.get("begin"));
					vo.setEnd((String) item.get("end"));
					vo.setId((String) item.get("id"));
					vo.setUrl((String) item.get("url"));
					vo.setType(Integer.parseInt((String) item.get("type")));
					if (vo.getType() == SysConstants.ServerType.TEXT) {
						vo.setServerIpBegin((String) item.get("serverIpBegin"));
						vo.setServerIpEnd((String) item.get("serverIpEnd"));
						vo.setServerPortBegin((String) item.get("serverPortBegin"));
						vo.setServerPortEnd((String) item.get("serverPortEnd"));
						vo.setPasswordBegin((String) item.get("passwordBegin"));
						vo.setPasswordEnd((String) item.get("passwordEnd"));
						vo.setEncryptionBegin((String) item.get("encryptionBegin"));
						vo.setEncryptionEnd((String) item.get("encryptionEnd"));
					} else if (vo.getType() == SysConstants.ServerType.PIC) {
						vo.setPicUrlBegin((String) item.get("picUrlBegin"));
						vo.setPicUrlEnd((String) item.get("picUrlEnd"));
						vo.setSeverPicFlag((String) item.get("severPicFlag"));
					}
					serverMap.put((String) item.get("id"), vo);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println(ExceptionUtil.getExceptionMessage(e));
//			logger.error("servers初始化失败:", e);
		}
	}

	public static void resetInstance() {
		load();
	}

	/**
	 * 整个xml文件的map
	 * 
	 * @return
	 */
	public static Map getMap() {
		return map;
	}

	/**
	 * 整个xml文件map根据key取value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (map != null) {
			return (String) map.get(key);
		}
		return null;
	}
	
	public static int getLocalPort(){
		try {
			int port = Integer.parseInt(getValue(LOCAL_PORT));
			return port;
		} catch (NumberFormatException e) {
//			logger.error("",e);	
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取检查ss账号密码间隔时间（秒）
	 */
	public static long getSleepTime(){
		try {
			long sleepTime = Long.parseLong(getValue(SLEEP_TIME));
			return sleepTime;
		} catch (NumberFormatException e) {
//			logger.error("",e);	
		}
		return 300;
	}
	
	public static int getFrom(){
		try {
			int from = Integer.parseInt(getValue(GET_FROM));
			return from;
		} catch (NumberFormatException e) {
//			logger.error("",e);	
			throw new RuntimeException(e);
		}
	}

	/**
	 * 服务器配置（servers标签下）的map
	 * 
	 * @return
	 */
	public static Map<String, ServerConfigVO> getServerConfigMap() {
		return serverMap;
	}

}
