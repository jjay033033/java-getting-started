/**
 * 
 */
package top.lmoon.shadowsupdate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.lmoon.Main;
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

	private static final Logger logger = Logger.getLogger(Main.class);

	private static final String HOME_PATH = "res/";

	private static final String PATH_NAME = HOME_PATH + "gui-config.json";
	private static final String EXE_NAME = "Shadowsocks.exe";
	private static final String EXE_PATH = HOME_PATH + EXE_NAME;
	private static final String QRCODE_PATH = HOME_PATH + "QRCode/";

	private static final String SLEEP_TIME = "sleepTime";

	public static String getss() {

		try {	
			List<ConfVO> newList = getConfListFromServer();
			if(newList==null||newList.isEmpty()){
				return "";
			}
			ConfVO vo = newList.get(0);
			return "ss://"+Base64Coder.encodeBase64(QRcodeUtil.getConfStrFromVO(vo));
//			List<ConfVO> oldList = getConfListFromJson(FileUtil.readFile(PATH_NAME));
//			Map<String, Object> compareMap = ConfListUtil.CompareList(oldList, newList);

//			List<ConfVO> list = (List<ConfVO>) compareMap.get("confList");
//			String content = buildContent(list);
//			FileUtil.writeFile(content, PATH_NAME);
//			QRcodeUtil.createQRCode(list, QRCODE_PATH);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			return "";
		}

	}

	private static List<ConfVO> getConfListFromServer() {
		List<ConfVO> list = new ArrayList<ConfVO>();
		ConfigList c;

		Map<String, ConfigList> cMap = ConfigListFactory.getInstance().getConfigListMap();
		for (Iterator<Entry<String, ConfigList>> it = cMap.entrySet().iterator(); it.hasNext();) {
			c = it.next().getValue();
			if (c != null) {
				List<ConfVO> cList = c.getConfigList();
				if (cList != null && !cList.isEmpty()) {
					list.addAll(cList);
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
