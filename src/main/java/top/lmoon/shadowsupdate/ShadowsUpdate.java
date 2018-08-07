/**
 * 
 */
package top.lmoon.shadowsupdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.lmoon.mail.MailUtil;
import top.lmoon.shadowsupdate.config.ConfigList;
import top.lmoon.shadowsupdate.config.ConfigListFactory;
import top.lmoon.shadowsupdate.qrcode.Base64Coder;
import top.lmoon.shadowsupdate.qrcode.QRcodeUtil;
import top.lmoon.shadowsupdate.vo.ConfVO;

/**
 * @author LMoon
 * @date 2017年7月25日
 * 
 */
public class ShadowsUpdate {

	// static {
	// PropertyConfigurator.configure(System.getProperty("user.dir") +
	// "/res/log4j.properties");
	// }

	private static final Logger logger = Logger.getLogger(ShadowsUpdate.class);

	private static List<Map<String, Object>> ssList = null;

//	private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	private static long lastTime = 0;

	private static final long DELAY_TIME_SECONDS = 60L;

	private static final long LIMIT_TIME_MILLIS = 2L * DELAY_TIME_SECONDS * 1000;

//	public static void start() {
//		Runnable r = new Runnable() {
//
//			@Override
//			public void run() {
//				lastTime = System.currentTimeMillis();
//				ssList = getssFromServer();				
//			}
//		};
//
//		executorService.scheduleAtFixedRate(r, 0, DELAY_TIME_SECONDS, TimeUnit.SECONDS);
//	}

	public static List<Map<String, Object>> getss() {
//		if (ssList == null) {
//			ssList = getssFromServer();
//		} else if (System.currentTimeMillis() - lastTime > LIMIT_TIME_MILLIS) {
//			start();
//			MailUtil.asyncSendErrorEmail("获取ss线程挂了。。。已重新启动。。。");
//		}
		ssList = getssFromServer();
		return ssList;
	}

	private static List<Map<String, Object>> getssFromServer() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			List<ConfVO> newList = getConfListFromServer();
			if (newList == null || newList.isEmpty()) {
				return list;
			}
			// ConfVO vo = newList.get(0);
			boolean hasFirst = false;
			for (int i = 0; i < newList.size(); i++) {
				// if(vo.getServer().contains("jp")){
				ConfVO vo = newList.get(i);
				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("url", "ss://" + Base64Coder.encodeBase64(QRcodeUtil.getConfStrFromVO(vo)));

					map.put("server", vo.getServer());
					map.put("method", vo.getMethod());
					map.put("password", vo.getPassword());
					map.put("remarks", vo.getRemarks());
					map.put("serverPort", vo.getServerPort());

					if (!hasFirst && (vo.getServer().contains("jp") || vo.getServer().contains("isxa"))) {
						map.put("id", "ids");
						hasFirst = true;
					} else {
						map.put("id", "ids" + i);
					}
					list.add(map);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("", e1);
				}
				// }
			}

			// List<ConfVO> oldList = getConfListFromJson(FileUtil.readFile(PATH_NAME));
			// Map<String, Object> compareMap = ConfListUtil.CompareList(oldList, newList);

			// List<ConfVO> list = (List<ConfVO>) compareMap.get("confList");
			// String content = buildContent(list);
			// FileUtil.writeFile(content, PATH_NAME);
			// QRcodeUtil.createQRCode(list, QRCODE_PATH);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);

		}
		return list;
	}

	private static List<ConfVO> getConfListFromServer() {
		List<ConfVO> list = new ArrayList<ConfVO>();
		ConfigList c;

		Map<String, ConfigList> cMap = ConfigListFactory.getInstance().getConfigListMap();
		for (Iterator<Entry<String, ConfigList>> it = cMap.entrySet().iterator(); it.hasNext();) {
			c = it.next().getValue();
			if (c != null) {
				try {
					List<ConfVO> cList = c.getConfigList();
					if (cList != null && !cList.isEmpty()) {
						list.addAll(cList);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("", e);
					MailUtil.asyncSendErrorEmail(e);
				}
			}
		}

		return list;
	}

	private static String buildContent(List<ConfVO> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("configs", list);
		map.put("strategy", "com.shadowsocks.strategy.ha");
		map.put("index", -1);
		map.put("global", false);
		map.put("enabled", true);
		map.put("shareOverLan", false);
		map.put("isDefault", false);
		map.put("localPort", 1080);
		map.put("pacUrl", null);
		map.put("useOnlinePac", false);
		JSONObject jo = JSONObject.fromObject(map);
		return jo.toString();
	}

	private static List<ConfVO> getConfListFromJson(String jsonStr) {
		try {
			if (jsonStr == null || jsonStr.isEmpty()) {
				return null;
			}
			JSONObject jo = JSONObject.fromObject(jsonStr);
			JSONArray confJa = jo.getJSONArray("configs");
			if (confJa == null || confJa.isEmpty()) {
				return null;
			}
			List<ConfVO> list = (List<ConfVO>) JSONArray.toCollection(confJa, ConfVO.class);
			return list;
		} catch (Exception e) {
			logger.error("getConfListFromJson:", e);
			e.printStackTrace();
			return null;
		}

	}

}
