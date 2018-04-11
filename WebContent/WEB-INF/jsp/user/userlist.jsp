<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>


<div class="right">
	<h1>查询用户列表</h1>
	<form action="${pageContext.request.contextPath }/user/userlist.html"
		method="post">
		用户名称：<input type="text" name="userName" value="" /> <input
			type="submit" name="查询" />
	</form>


	<a href="${pageContext.request.contextPath }/user/useradd.html">新增用户</a>


	<table id="userView">
		<tr>
			<td>ID</td>
			<td>编码</td>
			<td>用户名称</td>
			<td>用户密码</td>
			<td>用户生日</td>
			<td>用户地址</td>
			<td>操作</td>
		</tr>
		<c:forEach var="user" items="${userPage.users}">


			<tr>
				<td>${user.id }</td>
				<td>${user.userCode }</td>
				<td>${user.userName }</td>
				<td>${user.userPassword }</td>
				<td>${user.birthday }</td>
				<td>${user.address }</td>
				<td><a
					href="${pageContext.servletContext.contextPath}/user/findUser.html?id=${user.id}">修改</a>
					<!--/view/{id}  --> <a
					href="${pageContext.servletContext.contextPath}/user/findUser.json" id="${user.id}"
					class="catuserdetail">查看</a></td>
			</tr>
		</c:forEach>

		<tr>
			<td colspan="7"><a
				href="${pageContext.servletContext.contextPath}/user/userlist.html?currentIndex=1&totalIndex=${userPage.totalIndex}">首页</a>
				<a
				href="${pageContext.servletContext.contextPath}/user/userlist.html?currentIndex=${userPage.currentIndex - 1}&totalIndex=${userPage.totalIndex}">上一页</a>
				<a
				href="${pageContext.servletContext.contextPath}/user/userlist.html?currentIndex=${userPage.currentIndex + 1}&totalIndex=${userPage.totalIndex}">下一页</a>
				<a
				href="${pageContext.servletContext.contextPath}/user/userlist.html?currentIndex=${userPage.totalIndex}&totalIndex=${userPage.totalIndex}">末页</a>
				<span>
					第${userPage.currentIndex}页/共${userPage.totalIndex}(${userPage.totalCount})

			</span></td>

		</tr>
	</table>


	<!-- 展示用户详细信息 -->
	<div id="userdetail">
		<div>
			<label>用户编码：</label> 
			<input type="text" id="v_userCode" value=""
				readonly="readonly">
		</div>
		<div>
			<label>用户名称：</label> <input type="text" id="v_userName" value=""
				readonly="readonly">
		</div>
		<div>
			<label>用户性别：</label> <input type="text" id="v_gender" value=""
				readonly="readonly">
		</div>
		<div>
			<label>出生日期：</label> <input type="text" Class="Wdate" id="v_birthday"
				value="" readonly="readonly" onclick="WdatePicker();">
		</div>
		<div>
			<label>用户电话：</label> <input type="text" id="v_phone" value=""
				readonly="readonly">
		</div>
		<div>
			<label>用户角色：</label> <input type="text" id="v_userRoleName" value=""
				readonly="readonly">
		</div>
		<div>
			<label>用户地址：</label> <input type="text" id="v_address" value=""
				readonly="readonly">
		</div>
	</div>





	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/statics/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		
		$(function() {

			//绑定查看的单机事件

			$("#userView tr td .catuserdetail").click(
					function() {

						//获取请求的url

						var url = $(this).attr("href");
						var id = $(this).attr("id");
						$.ajax({
							type : "GET",//请求类型
							url : url,//请求的url
							
							data:{id:id},
							
							dataType : "json",//ajax接口（请求url）返回的数据类型

							success : function(data) {//data：返回数据（json对象）
								//alert($("#userdetail div:eq(0) input").val());
								$("#userdetail div:eq(0) input").val(
										data.userCode);

								$("#userdetail div:eq(1) input").val(
										data.userName);
								if (data.gender == 1) {
									$("#userdetail div:eq(2) input").val("男");
								} else {
									$("#userdetail div:eq(2) input").val("女");
								}

								$("#userdetail div:eq(3) input").val(
										data.birthday);
								$("#userdetail div:eq(4) input")
										.val(data.phone);
								$("#userdetail div:eq(5) input").val(
										data.userRoleName);
								$("#userdetail div:eq(6) input").val(
										data.address);

							},
							error : function(data) {//当访问时候，404，500 等非200的错误状态码
								validateTip(userCode.next(), {
									"color" : "red"
								}, imgNo + " 您访问的页面不存在", false);
							}
						});

						return false;//阻止超链接跳转

					});

		});
	</script>



</div>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
