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
	//������ǰҳ  �û���  ��ɫ����
	public UserPage findUserPage(int current,String userName,int userRole);//���ܺ���ҵ��Ҫ���Զ���ҳ���С  �Զ�������  QueryParam 
	
	
}
