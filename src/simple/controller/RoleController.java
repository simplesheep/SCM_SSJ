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

	
	// ��Ҫһ��RoleService
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

		request.getHeader("Accept"); // if mime == text/html �ύ��InterRes..ViewResolver jsp��ͼ������
										// if mime == applucation/json �ύ��HttpMessage... ���ö�����ʵ��һ���������
										// ֱ�ӽ�����������Ӧ��(content-body)

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
