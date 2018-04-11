package simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import simple.entity.Role;
import simple.service.roleservice.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	
	// 需要一个RoleService
	@Autowired
	private RoleService roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@ResponseBody
	@RequestMapping("/getRoleList.json")
	public String getRoleList(HttpServletRequest request) {

		request.getHeader("Accept"); // if mime == text/html 会交给InterRes..ViewResolver jsp视图解析器
										// if mime == applucation/json 会交给HttpMessage... （该对象其实是一个输出流）
										// 直接将结果输出回响应体(content-body)

		List<Role> list = roleService.findRoleList();
		String json = JSON.toJSONString(list);

		return json;

	}

	@RequestMapping("/roleDate.html")
	public String testBindData(Role role) {

		System.out.println(role.getModifyDate());

		return "login";

	}

	@RequestMapping("/role.html")
	public String toRole() {

		return "role";
	}

}
