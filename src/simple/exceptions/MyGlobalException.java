package simple.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyGlobalException implements HandlerExceptionResolver {

	// 定义我们自定义的异常对象

	private LoginErrorException loginError;
	private UserNameErrorException userNameError;

	@Override // request response 异常处理器对象（类比动态代理InvocationHandler） 方法抛出的异常对象
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ModelAndView modelAndView = new ModelAndView();
		// 判断传递来的Exception属于哪一种
		if (ex instanceof LoginErrorException) {

			loginError = (LoginErrorException) ex;
			modelAndView.addObject("exception", ex.getMessage());
			modelAndView.setViewName("login");

		} else if (ex instanceof UserNameErrorException) {
			userNameError = (UserNameErrorException) ex;
			modelAndView.addObject("exception", ex.getMessage());
			modelAndView.setViewName("login");
		} else {

			modelAndView.addObject("exception", ex.getMessage());
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

}
