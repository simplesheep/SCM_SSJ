package simple.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import simple.entity.User;
import simple.exceptions.UserNameErrorException;
import simple.service.userservice.UserService;

@Controller
@RequestMapping("/user")
public class UserController3 {
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	
	//登陆校验
	@RequestMapping("/dologin3.html")
	public String doLogin(String userCode,String userPassword,HttpSession session) {
		
		
		if(userCode == null) {
			//用户名不存在
			throw new UserNameErrorException("用户名不存在");
			
		}
		
		
		User user = userService.findUserForLogin(userCode);
		if(user == null) {
			
			//用户名不存在
			throw new UserNameErrorException("用户名不存在");
			
		}
		//将查询出来的密码和用户输入的密码比对
		if(!(user.getUserPassword() != null && user.getUserPassword().equals(userPassword)) ) {
			
			
			throw new RuntimeException("密码不正确");
			
			
		}
		
		
		//将用户信息保存在HttpSession
		session.setAttribute("userSession", user);
		
		return "redirect:/user/frame.html";//重定向发起一次新的请求
			
	}
	
	//局部异常处理 仅仅处理当前所在的控制器出现的异常
/*	@ExceptionHandler(value = {RuntimeException.class})
	public String doException(RuntimeException exception,HttpServletRequest request) {
		
		request.setAttribute("error", exception.getMessage());//将异常的错误信息放入作用域传输到jsp页面
		
		return "login";
	}
*/	
	
	
}
