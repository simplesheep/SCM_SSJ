package simple.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import simple.entity.User;

public class UserInterceptor extends HandlerInterceptorAdapter{

	
	@Override//只有该方法返回值为true   afterCompletion：前端控制器调度完试图解析器完成视图解析后被调用，此时视图已经被解析了  postHandle才会执行：在前端控制器调用完视图解析器后，视图解析器解析之前被调用，视图还未被解析，该方法可以修改ModelAndView
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		//进行登陆校验	
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if(user == null) {
			response.sendRedirect("/SCM_SSJ/user/login.html");
			return false;//终止响应
		}
		
		return true;
	}


}
