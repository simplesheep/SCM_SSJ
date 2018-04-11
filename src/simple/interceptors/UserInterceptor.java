package simple.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import simple.entity.User;

public class UserInterceptor extends HandlerInterceptorAdapter{

	
	@Override//ֻ�и÷�������ֵΪtrue   afterCompletion��ǰ�˿�������������ͼ�����������ͼ�����󱻵��ã���ʱ��ͼ�Ѿ���������  postHandle�Ż�ִ�У���ǰ�˿�������������ͼ����������ͼ����������֮ǰ�����ã���ͼ��δ���������÷��������޸�ModelAndView
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		//���е�½У��	
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if(user == null) {
			response.sendRedirect("/SCM_SSJ/user/login.html");
			return false;//��ֹ��Ӧ
		}
		
		return true;
	}


}
