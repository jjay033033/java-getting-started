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

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import top.lmoon.shadowsupdate.ShadowsUpdate;
import top.lmoon.shadowsupdate.vo.ConfWebVO;

@Controller
@SpringBootApplication
public class Main {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping("/")
	String index() {
		return "index";
	}
	
	private File getFile(String str){
		return new File(str+"shadowsocks/config.xml");
	}

	@RequestMapping("/conf")
	String test(Map<String, Object> map) {
		System.err.println("Hello, test!");
		map.put("list", "");
		return "conf";
//		return "<a href=\"ss://cmM0LW1kNTo3MTk3MzU1NkAxMzguNjguNjEuNDI6MjM0NTYK\">hello world!</a>";
	}

	@RequestMapping("/ss")
	String ssUpdate(Map<String, Object> map) {
		System.err.println("Hello, logs!");
		map.put("list", ShadowsUpdate.getss());
		return "ss";
		// String url =
		// Thread.currentThread().getContextClassLoader().getResource("").toString().replace("file:/",
		// "");
		// File f1 = new File(url+"templates/db.html");
		// File f2 = new File("shadowsocks/config.xml");
		// File f3 = new File("d:/bb.xls");
		// String a = f2.getPath();
		// String b = f1.getPath();
		// return
		// f1.exists()+"_"+f2.exists()+"_"+f3.exists()+"_"+a+"_"+b+"_"+f3.getPath()+"_"+url;
	}

	@RequestMapping("/db")
	String db(Map<String, Object> model) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			ArrayList<String> output = new ArrayList<String>();
			while (rs.next()) {
				output.add("Read from DB: " + rs.getTimestamp("tick"));
			}

			model.put("records", output);
			return "db";
		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "error";
		}
	}
	
	@RequestMapping("/selectConf")
	String selectConf(@ModelAttribute(value="vo") ConfWebVO vo) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS confs (id int NOT NULL,conf text,PRIMARY KEY(id))");
			ResultSet rs = stmt.executeQuery("SELECT conf FROM confs where id=1");

			String conf = "";
			if (rs.next()) {
				conf = rs.getString("conf");
			}

			vo.setConf(conf);
			return "conf";
		} catch (Exception e) {
//			map.put("message", e.getMessage());
			return "error";
		}
	}
	
	@RequestMapping(value="/updateConf", method=RequestMethod.POST)
	String updateConf(@ModelAttribute(value="vo") ConfWebVO vo) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS confs (id int NOT NULL,conf text,PRIMARY KEY(id))");
			stmt.executeUpdate("insert into conf(id,conf) values (1,'"+vo.getConf()+"')");
//			PreparedStatement ps = connection.prepareStatement("insert into conf(id,conf) values (?,?)");
//			ps.setInt(1, 1);
//			ps.setString(2, vo.getConf());
//			int result = ps.executeUpdate();
			return "conf";
		} catch (Exception e) {
//			map.put("message", e.getMessage());
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

}
