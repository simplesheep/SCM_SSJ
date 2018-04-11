package simple.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import simple.entity.User;
import simple.service.userservice.UserService;

@Controller
@RequestMapping("/user")
public class UserController2{

	
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login2.html")
	public String toLogin() {

		return "login";
	}

	// 用户登陆校验
	@RequestMapping("/dologin2.html")
	public String doLogin(@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userPassword", required = false) String userPassword,Model model,HttpSession session,HttpServletRequest request) {

		User user = new User();
		user.setUserCode(userCode);
		user.setUserPassword(userPassword);

		user = userService.findUserForLogin(user);

		if (user != null) {
			
			//将用户信息保存在HttpSession
			session.setAttribute("userSession", user);
			//return "frame";//不能使用默认的页面跳转默认就是forward  转发 页面地址不变  直接写frame.html会交给视图解析器解析 而不会跳转到控制器
			return "redirect:/user/frame2.html";
		}
		//给与提示信息 除非必要情况 一般不推荐使用原生的ServletAPI  因为springmvc都对其有包装类 或者注解 ()比如：HttpSession在SpringMVC中的注解是@SessionAttributes或者@SessionAttribute
		//request.setAttribute("error", "用户名或密码错误！");
		model.addAttribute("error", "用户名或者密码错误！");
		if("error".equals(userCode)) {
			
			throw new NullPointerException("触发了局部异常！");
			
		}
		return "login";
	}
	//用于修改页面地址
	@RequestMapping("/frame2.html")
	public String toFrame(HttpSession session) {
		//判断HttpSession中是否有登陆用户
		
		if(session.getAttribute("userSession") != null) {
			
			return "frame";
		}
		return "login";
		
		
	}
	public static void main(String[] args) {
		int i = 3;
		System.out.println(i |= 2);//0011 0010 0011 
	}
}
