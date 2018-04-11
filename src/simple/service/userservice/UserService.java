package simple.service.userservice;

import java.util.List;

import simple.entity.User;
import simple.page.UserPage;

public interface UserService {
	
	
	public List<User> findUser(User user);
	
	public boolean updateUser(User user);
	public boolean deleteUser(User user);
	public boolean insertUser(User user);
	
	public User findUserForLogin(User user);
	
	public User findUserForLogin(String userCode);
	//参数当前页  用户名  角色编码
	public UserPage findUserPage(int current,String userName,int userRole);//可能后期业务要求：自定义页面大小  自定义排序  QueryParam 
	
	
}
