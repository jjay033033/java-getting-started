/**
 * 
 */
package top.lmoon.heroku.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author LMoon
 * @date 2017年8月3日
 * 
 */
@Component
public class BaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);
	
	@Autowired
	private DataSource dataSource;
	
	public String selectConf() {
		String conf = "";
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS confs (id int NOT NULL,conf text,PRIMARY KEY(id))");
			ResultSet rs = stmt.executeQuery("SELECT conf FROM confs where id=1");			
			if (rs.next()) {
				conf = rs.getString("conf");
			}
		} catch (Exception e) {
			logger.error("",e);
			throw new RuntimeException(e);
		}
		return conf;
	}

}
