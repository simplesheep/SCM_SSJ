package simple.dao.userdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import simple.dao.BaseDao;
import simple.entity.User;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

	@Override
	public int updateUser(Connection connection, String sql, Object[] params) {
		int result = 0;

		result = BaseDao.executeUpdate(connection, sql, params);

		return result;
	}

	@Override
	public List<User> findUser(Connection connection, String sql, Object[] params) {
		ResultSet rs = null;

		List<User> users = null;

		User user = null;

		rs = BaseDao.executeQuery(connection, sql, params);

		if (rs != null) {
			users = new ArrayList<User>();
			try {
				while (rs.next()) {

					user = new User(rs.getInt("id"), rs.getString("userCode"), rs.getString("userName"),
							rs.getString("userPassword"), rs.getInt("gender"), rs.getDate("birthday"),
							rs.getString("phone"), rs.getString("address"), rs.getInt("userRole"),
							rs.getInt("createdBy"), rs.getDate("creationDate"), rs.getInt("modifyBy"), null);
					user.setUserRoleName(rs.getString("roleName"));
					users.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return users;
	}

	@Override
	public User findUserForLogin(Connection connection, String sql, Object[] params) {
		ResultSet rs = null;

		User user = null;

		rs = BaseDao.executeQuery(connection, sql, params);

		if (rs != null) {

			try {
				while (rs.next()) {

					user = new User();
					user.setUserName(rs.getString("userName"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return user;
	}

	@Override
	public int findCount(Connection connection, String sql, Object[] params) {
		ResultSet rs = null;

		int count = 0;

		rs = BaseDao.executeQuery(connection, sql, params);

		if (rs != null) {

			try {
				while (rs.next()) {
					count = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return count;
	}

	@Override
	public List<User> findUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findUserForLogin(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
