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
import org.springframework.web.bind.annotation.ModelAttribute;

import top.lmoon.jdbc.JdbcTemplate;
import top.lmoon.shadowsupdate.vo.ConfWebVO;

/**
 * @author LMoon
 * @date 2017年8月3日
 * 
 */
@Component("confsDAO")
public class ConfsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfsDAO.class);
	
	@Autowired
	private DataSource dataSource;
	
	public String selectConf() {
		try (Connection connection = dataSource.getConnection()) {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS confs (id int NOT NULL,conf text,PRIMARY KEY(id))");
//			ResultSet rs = stmt.executeQuery("SELECT conf FROM confs where id=1");	
//			if (rs.next()) {
//				conf = rs.getString("conf");
//			}
			String sql = "SELECT conf FROM confs where id=1";
			return JdbcTemplate.queryForString(connection, sql, new Object[0]);			
		} catch (Exception e) {
			logger.error("",e);
			throw new RuntimeException(e);
		}
	}
	
	public int updateConf(ConfWebVO vo) {
		return updateConf(vo.getConf());
	}
	
	public int updateConf(String conf) {
		try (Connection connection = dataSource.getConnection()) {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS confs (id int NOT NULL,conf text,PRIMARY KEY(id))");
//			stmt.executeUpdate("insert into confs(id,conf) values (1,'"+vo.getConf()+"')");
//			return stmt.executeUpdate("update confs set conf='"+vo.getConf()+"' where id=1");
//			PreparedStatement ps = connection.prepareStatement("insert into confs(id,conf) values (?,?)");
//			ps.setInt(1, 1);
//			ps.setString(2, vo.getConf());
//			int result = ps.executeUpdate();
			String sql = "update confs set conf=? where id=1";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[]{conf});
		} catch (Exception e) {
			logger.error("",e);
			throw new RuntimeException(e);
		}
	}

}
