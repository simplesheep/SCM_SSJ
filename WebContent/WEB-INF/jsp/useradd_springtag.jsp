<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right">


	<c:if test="${not empty MSG}">

		<c:if test="${MSG eq 'NOLOGIN'}">


			<script type="text/javascript">
				alert('请登陆！');
				location.href = '/SCM_SSJ/user/login.html';
			</script>


		</c:if>
		<c:if test="${MSG eq '成功'}">


			<script type="text/javascript">
				alert('新增成功');
			</script>


		</c:if>
		<c:if test="${MSG eq '失败'}">


			<script type="text/javascript">
				alert('新增用户失败');
			</script>


		</c:if>


	</c:if>




	<div class="location">
		<strong>你现在所在的位置是:</strong> <span>用户管理页面 >> 用户添加页面</span>
	</div>
	<div class="providerAdd">
		<fm:form modelAttribute="user" id="userForm" name="userForm" method="post"
			action="${pageContext.request.contextPath }/user/useraddsave.html">
			<!-- <input type="hidden" name="method" value="add"> -->
			<!--div的class 为error是验证错误，ok是验证成功-->
			<div>
				<label for="userCode">用户编码：</label> 
				<fm:input
					path="userCode" id="userCode" value=""/>
				<!-- 放置提示信息 -->
				<font color="red"></font>
			</div>
			<div>
				<label for="userName">用户名称：</label>
				 <fm:input path="userName" id="userName" value=""/> <font color="red"></font>
			</div>
			<div>
				<label for="userPassword">用户密码：</label> 
				<fm:password
					path="userPassword" id="userPassword" value=""/> <font
					color="red"></font>
			</div>
			<%-- <div>
				<label for="ruserPassword">确认密码：</label> <fm:password
					path="ruserPassword" id="ruserPassword" value=""/> <font
					color="red"></font>
			</div> --%>
			<div>
				<label>用户性别：</label>
				 <fm:select path="gender" id="gender">
				
					<fm:option value="1" selected="selected">男</fm:option>
					<fm:option value="2">女</fm:option>
				</fm:select>
			</div>
			<div>
				<label for="birthday">出生日期：</label> 
				<fm:input cssClass="Wdate"
					id="birthday" path="birthday" readonly="readonly"
					onclick="WdatePicker();"/> <font color="red"></font>
			</div>
			<div>
				<label for="phone">用户电话：</label> <fm:input path="phone"
					id="phone" value=""/> <font color="red"></font>
			</div>
			<div>
				<label for="address">用户地址：</label> <fm:input path="address"
					id="address" value=""/>
			</div>
			<div>
				<label>用户角色：</label>
				<!-- 列出所有的角色分类 -->
				<!-- <select name="userRole" id="userRole"></select> -->
				<fm:select path="userRole" id="userRole">
					<fm:option value="1">系统管理员</fm:option>
					<fm:option value="2">经理</fm:option>
					<fm:option value="3" selected="selected">普通用户</fm:option>
				</fm:select> <font color="red"></font>
			</div>
			<div class="providerAddBtn">
				<input type="button" name="add" id="add" value="保存"> <input
					type="button" id="back" name="back" value="返回">
			</div>
		</fm:form>
	</div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
