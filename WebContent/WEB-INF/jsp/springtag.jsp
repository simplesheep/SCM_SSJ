<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

		
		<fm:form method="post" modelAttribute="command" action="/SCM_SSJ/user/springtag.html">
		
			用户编码：<fm:input path="userCode"/><!-- spring自带的会进行数据回显 -->
			
			用户名：<input name="userName" value="${command.userName}"/><!-- 原生的html标签要想实现数据回县需要手动去作用域中获取一下值 -->
			<input type="submit" value="提交"/>
		
		
		</fm:form>



</body>
</html>