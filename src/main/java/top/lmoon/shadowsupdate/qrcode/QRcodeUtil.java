package top.lmoon.shadowsupdate.qrcode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.lmoon.shadowsupdate.vo.ConfVO;
import top.lmoon.util.ExceptionUtil;

public class QRcodeUtil {
	
//	private static final Logger logger = LoggerFactory.getLogger(QRcodeUtil.class);
	
	public static void createQRCode(List<ConfVO> list,String path) {
		List<String> strList = confStr4QRCode(list);
		for (int i = 0; i < strList.size(); i++) {
			try {
				File file = new File(path);
				if(!file.isDirectory()){
					file.mkdir();
				}
				Base64Coder.createQRCodePic4Base64(strList.get(i), path
						+ list.get(i).getServer() + ".jpg");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();System.out.println(ExceptionUtil.getExceptionMessage(e));
//				logger.error("create QRCode:", e);
			}
		}
	}

	public static List<String> confStr4QRCode(List<ConfVO> list) {
		List<String> rList = new ArrayList<String>();
		for (ConfVO vo : list) {
			StringBuffer sb = new StringBuffer();
			sb.append(getConfStrFromVO(vo));
			rList.add(sb.toString());
		}
		return rList;
	}
	
	public static String getConfStrFromVO(ConfVO vo){
		return vo.getMethod()+":"+vo.getPassword()+"@"+vo.getServer()+":"+vo.getServerPort();
	}

}
