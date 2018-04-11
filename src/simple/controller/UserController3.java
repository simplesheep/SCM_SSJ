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

	
	
	//��½У��
	@RequestMapping("/dologin3.html")
	public String doLogin(String userCode,String userPassword,HttpSession session) {
		
		
		if(userCode == null) {
			//�û���������
			throw new UserNameErrorException("�û���������");
			
		}
		
		
		User user = userService.findUserForLogin(userCode);
		if(user == null) {
			
			//�û���������
			throw new UserNameErrorException("�û���������");
			
		}
		//����ѯ������������û����������ȶ�
		if(!(user.getUserPassword() != null && user.getUserPassword().equals(userPassword)) ) {
			
			
			throw new RuntimeException("���벻��ȷ");
			
			
		}
		
		
		//���û���Ϣ������HttpSession
		session.setAttribute("userSession", user);
		
		return "redirect:/user/frame.html";//�ض�����һ���µ�����
			
	}
	
	//�ֲ��쳣���� ��������ǰ���ڵĿ��������ֵ��쳣
/*	@ExceptionHandler(value = {RuntimeException.class})
	public String doException(RuntimeException exception,HttpServletRequest request) {
		
		request.setAttribute("error", exception.getMessage());//���쳣�Ĵ�����Ϣ�����������䵽jspҳ��
		
		return "login";
	}
*/	
	
	
}
