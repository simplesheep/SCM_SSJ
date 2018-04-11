package simple.service.userservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simple.dao.BaseDao;
import simple.dao.userdao.UserDao;
import simple.entity.User;
import simple.page.UserPage;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> findUser(User user) {
		// �ȶ������Ҫ�ı���

		List<User> list = null;
		list = userDao.findUser(user);
/*		Connection connection = BaseDao.getConnection();
		String sql = "select u.*,r.roleName from smbms_user u inner join smbms_role r where 1 = 1";
		// �����ѯ�����б�
		List<Object> objects = new ArrayList<Object>();
		if (user != null) {

			if (user.getUserName() != null) {

				objects.add(user.getUserName());
				sql = sql + " and userName like concat('%',?,'%')";

			}
			if (user.getUserRole() != null) {
				objects.add(user.getUserRole());
				sql = sql + " and userRole = ?";
			}
			if (user.getId() != null) {

				objects.add(user.getId());
				sql = sql + " and u.id = ?";

			}
		}
		try {
			if (objects.size() > 0) {

				Object[] params = objects.toArray();
				list = userDao.findUser(connection, sql, params);

			} else {

				list = userDao.findUser(connection, sql, null);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.close(connection, null, null);
		}
*/
		return list;
	}

	@Override
	public boolean updateUser(User user) {
		int result = 0;
		
		result = userDao.updateUser(user);
		if(result > 0) {
			return true;
		}
		/*String sql = "update smbms_user set userName = ?,gender = ?,birthday = ?,phone = ?,address = ?,userRole = ? where id = ?";

		Connection connection = BaseDao.getConnection();
		int result = 0;
		// ���������Զ��ύ ��Ҫ�ֶ�commit
		try {
			connection.setAutoCommit(false);
			Object[] params = { user.getUserName(), user.getGender(), user.getBirthday(), user.getPhone(),
					user.getAddress(), user.getUserRole(), user.getId() };
			
			result = userDao.updateUser(connection, sql, params);

			connection.commit();
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			BaseDao.close(connection, null, null);
		}
*/
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
	
		return false;
	}

	@Override
	public boolean insertUser(User user) {
		int result = 0;
		
		
		result = userDao.insertUser(user);
		
		
		if (result > 0) {
			return true;
		}
		/*String sql = "insert into smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,iconPath,zjzPath)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";

		Connection connection = BaseDao.getConnection();

		Object[] params = { user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(),
				user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(), user.getCreatedBy(),user.getIconPath(),user.getZjzPath() };
		int result = 0;
		try {
			result = userDao.updateUser(connection, sql, params);
		} finally {

			BaseDao.close(connection, null, null);
		}

		if (result > 0) {
			return true;
		}
*/
		return false;
	}

	@Override
	public User findUserForLogin(User user) {
		
		
		user = userDao.findUserForLogin(user);
		
		
		/*Connection connection = BaseDao.getConnection();

		String sql = "select userName,userCode,userPassword,userRole from smbms_user where userCode = ? and userPassword = ?";

		Object[] params = { user.getUserCode(), user.getUserPassword() };

		// ��user��Ϊnull

		user = null;

		try {
			user = userDao.findUserForLogin(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.close(connection, null, null);
		}*/

		return user;
	}

	@Override
	public User findUserForLogin(String userCode) {
		User user = new User();
		user.setUserCode(userCode);
		user = userDao.findUserForLogin(user);
		
		/*Connection connection = BaseDao.getConnection();

		String sql = "select userName,userCode,userPassword,userRole from smbms_user where userCode = ?";

		Object[] params = { userCode };

		// ��user��Ϊnull

		User user = null;

		try {
			user = userDao.findUserForLogin(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.close(connection, null, null);
		}
*/
		return user;
	}

	@Override
	public UserPage findUserPage(int currentIndex, String userName, int userRole) {
		Connection connection = BaseDao.getConnection();
		// ��ʼ��UserPage
		UserPage page = new UserPage();
		// ע�뵱ǰҳ
		page.setCurrentIndex(currentIndex);
		// ע�������ݼ�¼����
		// ��ѯ�ܼ�¼��sql
		String sql = "select count(1) from smbms_user where 1 = 1";
		List<Object> list = new ArrayList<Object>();
		if (userName != null) {
			sql = sql + " and userName like concat('%',?,'%')";
			// ����
			list.add(userName);
		}
		if (userRole != 0) {

			sql = sql + " and userRole = ?";
			list.add(userRole);

		}
		Object[] params = list.toArray();
		int count = userDao.findCount(connection, sql, params);
		page.setTotalCount(count);

		// ע����ҳ��
		int pageSize = page.getPageSize();
		int totalIndex = (count % pageSize == 0) ? ((count / pageSize)) : ((count / pageSize) + 1);
		page.setTotalIndex(totalIndex);

		// ע��ҳ����ʾ������

		int start = (currentIndex - 1) * pageSize;
		int end = pageSize;

		// ��ѯ���ݵ�sql
		sql = "select u.*,r.roleName from smbms_user u inner join smbms_role r where 1 = 1";
		list = new ArrayList<Object>();//
		if (userName != null) {
			sql = sql + " and userName like concat('%',?,'%')";
			// ����
			list.add(userName);
		}
		if (userRole != 0) {

			sql = sql + " and userRole = ?";
			list.add(userRole);

		}
		// ƴ��limit

		sql = sql + " limit ?,?";
		list.add(start);
		list.add(end);
		Object[] paObjects = list.toArray();// ���������� ת�ɲ�������
		List<User> users = userDao.findUser(connection, sql, paObjects);
		page.setUsers(users);

		BaseDao.close(connection, null, null);

		return page;
	}

}
