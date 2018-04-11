package simple.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyGlobalException implements HandlerExceptionResolver {

	// ���������Զ�����쳣����

	private LoginErrorException loginError;
	private UserNameErrorException userNameError;

	@Override // request response �쳣������������ȶ�̬����InvocationHandler�� �����׳����쳣����
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ModelAndView modelAndView = new ModelAndView();
		// �жϴ�������Exception������һ��
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
