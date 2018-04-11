package simple.dao.userdao;

import java.sql.Connection;
import java.util.List;

import simple.entity.User;

public interface UserDao {

	
	public int updateUser(Connection connection,String sql,Object[] params);
	
	
	public List<User> findUser(Connection connection,String sql,Object[] params);
	
	
	public User findUserForLogin(Connection connection,String sql,Object[] params);
	
	//查询记录条数
	
	public int findCount(Connection connection,String sql,Object[] params);


	public List<User> findUser(User user);//mybatis


	public int updateUser(User user);


	public int insertUser(User user);


	public User findUserForLogin(User user);
}
