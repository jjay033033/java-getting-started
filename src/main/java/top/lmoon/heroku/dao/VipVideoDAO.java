/**
 * 
 */
package top.lmoon.heroku.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.lmoon.jdbc.JdbcTemplate;
import top.lmoon.jdbc.RowMapper;
import top.lmoon.vipvideo.vo.VipVideoVO;

/**
 * @author LMoon
 * @date 2017年8月3日
 * 
 */
@Component("vipVideoDAO")
public class VipVideoDAO {

	private static final Logger logger = LoggerFactory.getLogger(VipVideoDAO.class);

	@Autowired
	private DataSource dataSource;

	public List<VipVideoVO> select(int pageNo,int pageSize) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM vipvideo order by ctime desc limit ? offset ?";
			return JdbcTemplate.queryForList(connection, sql, new RowMapper<VipVideoVO>() {

				@Override
				public VipVideoVO mapRow(ResultSet rs, int rowIndex) throws SQLException {
					VipVideoVO vo = new VipVideoVO();
					Timestamp ctime = rs.getTimestamp("ctime");
					vo.setCtime(new Timestamp(ctime.getTime()+8L*60*60*1000).toString());
					vo.setRemark(rs.getString("remark"));
					return vo;
				}
			}, new Object[]{pageSize,(pageNo-1)*pageSize});
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	public int selectCount() {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT count(1) FROM vipvideo";
			return JdbcTemplate.queryForInt(connection, sql, new Object[0]);
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	public int insert(String remark) {
		try {
			Connection connection = dataSource.getConnection();
//			JdbcTemplate.executeUpdate(connection,
//					"CREATE TABLE IF NOT EXISTS vipvideo (remark varchar(32),ctime timestamp)", new Object[0]);
			String sql = "insert into vipvideo(remark,ctime) values (?,now())";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[] { remark });
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	public int dropTable(){
		try {
			Connection connection = dataSource.getConnection();
			String sql = "drop table vipvideo";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	public int createTable(){
		try {
			Connection connection = dataSource.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS vipvideo (remark text,ctime timestamp)";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
		
	public int alterTable(){
		try {
			Connection connection = dataSource.getConnection();
			//postgresql
			String sql = "alter table vipvideo alter column remark TYPE text";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

}
