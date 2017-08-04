/**
 * 
 */
package top.lmoon.heroku.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public List<VipVideoVO> select() {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM vipvideo";
			return JdbcTemplate.queryForList(connection, sql, new RowMapper<VipVideoVO>() {

				@Override
				public VipVideoVO mapRow(ResultSet rs, int rowIndex) throws SQLException {
					VipVideoVO vo = new VipVideoVO();
					vo.setCtime(rs.getTimestamp("ctime"));
					vo.setRemark(rs.getString("remark"));
					return vo;
				}
			}, new Object[0]);
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

}
