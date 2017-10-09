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
import top.lmoon.myspider.vo.ApeInfoVO;
import top.lmoon.vipvideo.vo.VipVideoVO;

/**
 * @author LMoon
 * @date 2017年8月3日
 * 
 */
@Component("vipVideoDAO")
public class ApeInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(ApeInfoDAO.class);

	@Autowired
	private DataSource dataSource;

	public List<VipVideoVO> select(int pageNo, int pageSize) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM apeinfo limit ? offset ?";
			return JdbcTemplate.queryForList(connection, sql, new RowMapper<VipVideoVO>() {

				@Override
				public VipVideoVO mapRow(ResultSet rs, int rowIndex) throws SQLException {
					VipVideoVO vo = new VipVideoVO();
					Timestamp ctime = rs.getTimestamp("ctime");
					vo.setCtime(new Timestamp(ctime.getTime() + 8L * 60 * 60 * 1000).toString());
					vo.setRemark(rs.getString("remark"));
					return vo;
				}
			}, new Object[] { pageSize, (pageNo - 1) * pageSize });
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	public int selectCount() {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT count(1) FROM apeinfo";
			return JdbcTemplate.queryForInt(connection, sql, new Object[0]);
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	public int insert(ApeInfoVO vo) {
		try {
			Connection connection = dataSource.getConnection();
			// JdbcTemplate.executeUpdate(connection,
			// "CREATE TABLE IF NOT EXISTS vipvideo (remark varchar(32),ctime
			// timestamp)", new Object[0]);
			String sql = "insert into apeinfo(songId,singerId,songIdForSinger,singer,"
					+ "title,link,pw,album,size,language,remark) values (?,?,?,?,?,?,?,?,?,?,?)";
			return JdbcTemplate.executeUpdate(connection, sql,
					new Object[] { vo.getSongId(), vo.getSingerId(), vo.getSongIdForSinger(), vo.getSinger(),
							vo.getTitle(), vo.getLink(), vo.getPw(), vo.getAlbum(), vo.getSize(), vo.getLanguage(),
							vo.getRemark() });
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	public int dropTable() {
		try {
			Connection connection = dataSource.getConnection();
			String sql = "drop table apeinfo";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	public int createTable() {
		try {
			Connection connection = dataSource.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS apeinfo (songId varchar(32) PRIMARY KEY NOT NUL,singerId int,songIdForSinger int,"
					+ "singer varchar(64),title varchar(64),link varchar(64),pw varchar(8),"
					+ "album varchar(64),size varchar(16),language varchar(16),remark varchar(1024))";
			return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}

	// public int alterTable(){
	// try {
	// Connection connection = dataSource.getConnection();
	// //postgresql
	// String sql = "alter table vipvideo alter column remark TYPE text";
	// return JdbcTemplate.executeUpdate(connection, sql, new Object[0]);
	// } catch (Exception e) {
	// System.err.println(e);
	// logger.error("", e);
	// throw new RuntimeException(e);
	// }
	// }

}
