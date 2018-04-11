<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm"%>
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
		<!--判断文件类型  -->
		<c:if test="${MSG eq '格式不正确'}">

			<script type="text/javascript">
				alert('新增用户失败,图片格式不正确');
			</script>

		</c:if>

	</c:if>




	<div class="location">
		<strong>你现在所在的位置是:</strong> <span>用户管理页面 >> 用户添加页面</span>
	</div>
	<div class="providerAdd">
		<form enctype="multipart/form-data" id="userForm" name="userForm"
			method="post"
			action="${pageContext.request.contextPath }/user/useraddsave1.html">
			<input type="hidden" name="method" value="add">
			<!--div的class 为error是验证错误，ok是验证成功-->
			<div>
				<fm:errors path="userCode"></fm:errors>
				<label for="userCode">用户编码：</label> <input type="text"
					name="userCode" id="userCode" value="">
				<!-- 放置提示信息 -->
				<font color="red"></font>
			</div>
			<div>
				<label for="userName">用户名称：</label> <input type="text"
					name="userName" id="userName" value=""> <font color="red"></font>
			</div>
			<div>
				<label for="userPassword">用户密码：</label> <input type="password"
					name="userPassword" id="userPassword" value=""> <font
					color="red"></font>
			</div>
			<div>

				<label for="icon">头像：</label><input id="icon" type="file"
					name="uploadFile" />


			</div>
			<div>

				<label for="icon">证件照：</label><input id="icon" type="file"
					name="uploadFile" />


			</div>
			<!-- <div>

				<label for="icon">头像：</label><input id="icon" type="file"
					name="idPicPath" />


			</div>
			<div>

				<label for="icon">证件照：</label><input id="icon" type="file"
					name="zzPath" />


			</div> -->
			<div>
				<label for="ruserPassword">确认密码：</label> <input type="password"
					name="ruserPassword" id="ruserPassword" value=""> <font
					color="red"></font>
			</div>
			<div>
				<label>用户性别：</label> <select name="gender" id="gender">
					<option value="1" selected="selected">男</option>
					<option value="2">女</option>
				</select>
			</div>
			<div>
				<label for="birthday">出生日期：</label> <input type="text" Class="Wdate"
					id="birthday" name="birthday" readonly="readonly"
					onclick="WdatePicker();"> <font color="red"></font>
			</div>
			<div>
				<label for="phone">用户电话：</label> <input type="text" name="phone"
					id="phone" value=""> <font color="red"></font>
			</div>
			<div>
				<label for="address">用户地址：</label> <input name="address"
					id="address" value="">
			</div>
			<div>
				<label>用户角色：</label>
				<!-- 列出所有的角色分类 -->
				<!-- <select name="userRole" id="userRole"></select> -->
				<select name="userRole" id="userRole">
				<!-- 异步加载角色列表信息 -->
					
					<!--  <option value="1">系统管理员</option>
					<option value="2">经理</option>
					<option value="3" selected="selected">普通用户</option>-->
				</select> <font color="red"></font>
					<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/statics/js/jquery-1.8.3.min.js"></script>
				<script type="text/javascript">
					
				//获取select下拉框对象
				var roleView = $("#userRole");
				$.ajax({
					
					"url":"/SCM_SSJ/role/getRoleList.json",
					"type":"GET",
					"dataType":"json",
					"success":function(data){
						var option = "";
						for(var i in data){
							if(data[i].roleName == '普通员工'){
							option = option + "<option selected='selected' value='"+data[i].id+"'>"+data[i].roleName+"</option>";
							}else{
								
								option = option + "<option value='"+data[i].id+"'>"+data[i].roleName+"</option>";
								
								
							}
							
						}
						//注入进select
						roleView.html(option);
						
						
						
					}
					
					
				});
					
				
				
				</script>
				
			</div>
			<div class="providerAddBtn">
				<input type="button" name="add" id="add" value="保存"> <input
					type="button" id="back" name="back" value="返回">
			</div>
		</form>
	</div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
