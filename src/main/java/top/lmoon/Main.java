/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.lmoon;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.sf.json.JSONObject;
import top.lmoon.heroku.dao.ConfsDAO;
import top.lmoon.heroku.dao.VipVideoDAO;
import top.lmoon.shadowsupdate.ShadowsUpdate;
import top.lmoon.shadowsupdate.qrcode.Base64Coder;
import top.lmoon.shadowsupdate.vo.ConfWebVO;
import top.lmoon.vipvideo.vo.VipVideoVO;

@Controller
@SpringBootApplication
public class Main {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Autowired
	private ConfsDAO confsDAO;
	
	@Autowired
	private VipVideoDAO vipVideoDAO;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/test")
	@ResponseBody
	String test(Map<String, Object> map) {
		System.err.println("Hello, test!");
//		map.put("list", "");
		return HtmlUtils.htmlUnescape(confsDAO.selectConf());
//		return "<a href=\"ss://cmM0LW1kNTo3MTk3MzU1NkAxMzguNjguNjEuNDI6MjM0NTYK\">hello world!</a>";
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	String test2(String cookie) {
		System.err.println("Hello, test2!");
		
		try {
			String a = URLDecoder.decode(new String(Base64Coder.decodeBase64(cookie)), "utf-8");
//			String b = URLDecoder.decode(new String(Base64Coder.decodeBase64(cookie),"utf-8"), "utf-8");
//			String c = new String(Base64Coder.decodeBase64(cookie),"utf-8");
			return a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@RequestMapping("/ss")
	String ssUpdate(Map<String, Object> map) {
		System.err.println("Hello, logs!");
		map.put("list", ShadowsUpdate.getss());
		return "ss";
	}
	
//	@RequestMapping("/retoreVip")
//	@ResponseBody
//	String retoreVip() {
//		try {
//			System.err.println("retoreVip!");
//			int result = vipVideoDAO.dropTable();
//			int result2 = vipVideoDAO.createTable();
//			return "r1:"+result+"r2:"+result2;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}
//	}
	
	@RequestMapping("/alterVip")
	@ResponseBody
	String retoreVip() {
		try {
			System.err.println("alterVip!");
			int result = vipVideoDAO.alterTable();
			return "r1:"+result;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping("/vipAdd")
	@ResponseBody
	String vipAdd(String datas) {
		try {
			System.err.println("vipAdd!");
			int result = vipVideoDAO.insert(URLDecoder.decode(new String(Base64Coder.decodeBase64(datas)), "utf-8"));
			return "{result:"+result+"}";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping("/vipGet")
	String vipGet(Map<String, Object> map,Integer pageNo) {
		try {
			System.err.println("vipGet!");
//			if(pageNo==null||pageNo<1){
//				pageNo=1;
//			}
//			int pageSize = 20;
//			List<VipVideoVO> list = vipVideoDAO.select(pageNo,pageSize);
//			int total = vipVideoDAO.selectCount();
//			int totalPage = (total-1)/pageSize+1;
//			map.put("list", list);
//			map.put("total", total);
//			map.put("totalPage", totalPage);
//			map.put("pageNo", pageNo);
			return "vipvideo";
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping("/vipGetJson")
	String vipGetJson(Integer pageNo) {
		try {
			System.err.println("vipGetJson!");
			if(pageNo==null||pageNo<1){
				pageNo=1;
			}
			int pageSize = 20;
			List<VipVideoVO> list = vipVideoDAO.select(pageNo,pageSize);
			int total = vipVideoDAO.selectCount();
			int totalPage = (total-1)/pageSize+1;
			Map map = new HashMap<>();
			map.put("list", list);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("pageNo", pageNo);
			JSONObject fromObject = JSONObject.fromObject(map);
			return fromObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
	}

	
	@RequestMapping("/selectConf")
	String selectConf(@ModelAttribute(value="vo") ConfWebVO vo) {
		try {
			vo.setConf(confsDAO.selectConf());
			return "conf";
		} catch (Exception e) {
			vo.setMessage(e.getMessage());
			return "error";
		}
	}
	
	@RequestMapping(value="/updateConf", method=RequestMethod.POST)
	String updateConf(@ModelAttribute(value="vo") ConfWebVO vo) {
		try{
			confsDAO.updateConf(vo);
			return "conf";
		} catch (Exception e) {
//			map.put("message", e.getMessage());
			vo.setMessage(e.getMessage());
			return "error";
		}
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
	
//	@RequestMapping("/db")
//	String db(Map<String, Object> model) {
//		try (Connection connection = dataSource.getConnection()) {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
//			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
//			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
//
//			ArrayList<String> output = new ArrayList<String>();
//			while (rs.next()) {
//				output.add("Read from DB: " + rs.getTimestamp("tick"));
//			}
//
//			model.put("records", output);
//			return "db";
//		} catch (Exception e) {
//			model.put("message", e.getMessage());
//			return "error";
//		}
//	}

}
