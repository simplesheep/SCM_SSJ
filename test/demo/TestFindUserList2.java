package demo;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import simple.entity.User;
import simple.service.userservice.UserService;

public class TestFindUserList2 {

	@Test
	public void junitTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-jdbc.xml");

		UserService userService = (UserService) context.getBean("userServiceImpl");
		User user = new User();
		// user.setUserRole(1);
		user.setUserName("№ЬАн");

		List<User> list = userService.findUser(user);
		System.out.println(list.get(0).getUserName());

	}
}
