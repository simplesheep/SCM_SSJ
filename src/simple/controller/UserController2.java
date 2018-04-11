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

	// �û���½У��
	@RequestMapping("/dologin2.html")
	public String doLogin(@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userPassword", required = false) String userPassword,Model model,HttpSession session,HttpServletRequest request) {

		User user = new User();
		user.setUserCode(userCode);
		user.setUserPassword(userPassword);

		user = userService.findUserForLogin(user);

		if (user != null) {
			
			//���û���Ϣ������HttpSession
			session.setAttribute("userSession", user);
			//return "frame";//����ʹ��Ĭ�ϵ�ҳ����תĬ�Ͼ���forward  ת�� ҳ���ַ����  ֱ��дframe.html�ύ����ͼ���������� ��������ת��������
			return "redirect:/user/frame2.html";
		}
		//������ʾ��Ϣ ���Ǳ�Ҫ��� һ�㲻�Ƽ�ʹ��ԭ����ServletAPI  ��Ϊspringmvc�������а�װ�� ����ע�� ()���磺HttpSession��SpringMVC�е�ע����@SessionAttributes����@SessionAttribute
		//request.setAttribute("error", "�û������������");
		model.addAttribute("error", "�û��������������");
		if("error".equals(userCode)) {
			
			throw new NullPointerException("�����˾ֲ��쳣��");
			
		}
		return "login";
	}
	//�����޸�ҳ���ַ
	@RequestMapping("/frame2.html")
	public String toFrame(HttpSession session) {
		//�ж�HttpSession���Ƿ��е�½�û�
		
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
