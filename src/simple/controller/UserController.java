package simple.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import simple.entity.User;
import simple.exceptions.LoginErrorException;
import simple.page.UserPage;
import simple.service.userservice.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	

	@InitBinder
	public void converterStringToDate2(WebDataBinder binder) {
		System.out.println("====类型转换器生效");
		//注册支持的类型转换
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		
	}
	//DataBinder  完成类型的自定义转换 多个会无效  该注解的方法不能有返回值
	@InitBinder
	public void converterStringToDate(WebDataBinder binder) {
		System.out.println("====类型转换器生效");
		//注册支持的类型转换
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy.MM.dd"), true));
		
		
	}
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/converter.html")
	public String testConverter(User user) {
		
		System.out.println("====" + user.getBirthday());
		
		return "login";
	}
	
	
	/**
	 * 
	 * UserCode的重复校验
	 */
	@ResponseBody
	@RequestMapping("/userCodeCheck.json")
	public String userCodeCheck(@RequestParam(value = "userCode", required = false) String userCode) {

		String json = null;
		
		//构造一个HashMap集合
		HashMap<String, String> map = new HashMap<String,String>();
		
		// 根据userCode进行校验
		User user = userService.findUserForLogin(userCode);
		if (user != null) {

			//json = "{\"userCode\":\"exist\"}";
			map.put("userCode", "exist");
			
			
		} else {
			
			//json = "{\"userCode\":\"success\"}";//
			map.put("userCode", "success");
		}
		json = JSONArray.toJSONString(map);
		return json;//目的：不将返回的结果交给视图解析器解析 而是直接以流的形式写会客户端浏览器（写入Http的响应体中）
	}

	@RequestMapping("/springtag.html")
	public String toSpringTag(@ModelAttribute(value = "command") User user, Model model) {
		System.out.println("============================================");
		// model.addAttribute("command", user); 不需要显示的写这句话 因为如果使用了ModelAttribute
		// 那么会自动将对象注入Model中

		return "springtag";
	}

	@RequestMapping("/engineer/{id}/employee/{empId}/month/{month}") //
	public String toLogin2(@PathVariable String id, @PathVariable String empId, @PathVariable String month) {

		return "login";
	}

	@RequestMapping("/login.html")
	public String toLogin() {

		return "login";
	}

	@RequestMapping("/useradd_springtag.html")
	public String toSpringTag2(@ModelAttribute(value = "user") User user) {

		return "useradd_springtag";
	}

	// 用于跳转到用户新增页面

	/**
	 * 
	 * ModelAttribute ： 往Model中加入一个实体类的对象 什么时候加：跳转到form表单之前 ：此时该注解必须放在参数定义位置
	 * 即使参数定义位置不加此注解 也可以使用下面的方式完成springmvc的参数自动封装 但是如果前台使用的spring的表单标签 那么该注解必须加
	 * 否则会报一个command找不到的错误 不能放在方法定义上
	 * 
	 * ModelAttribute ：放在方法定义上 此时不能和谁RequestMapping共存 带有该注解的方法 会在请求到达Request
	 * Mapping之前执行一次 可以用来做一个初始化工作
	 * 
	 */

	@RequestMapping("/useradd.html")

	public String toUserAdd(@ModelAttribute(value = "user") User user) {//

		return "useradd";

		// return "user/useradd";
	}

	// 用于新增用户
	@RequestMapping("/useraddsave1.html") // 如果待注入的User对象中有日期类型的属性 那么需要去属性定义上加一个@DateTimeFormat注解 指定待注入的日期格式
											// 否则会报数据类型转换异常
	public String doUserAddSave1(@Valid User user, BindingResult errors, HttpSession session, Model model,
			@RequestParam(value = "uploadFile", required = false) MultipartFile[] file, HttpServletRequest request) {

		// 设置创建该用户的用户 当前登陆者

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// 说明用户没登陆
			// 抛出一个异常 让用户去登陆
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		// errors.hasErrors();//返回true说明有错误信息
		if (errors.hasErrors()) {

			FieldError msg = errors.getFieldError("userCode");
			String msg2 = msg.getDefaultMessage();// 可以手动获取具体属性的错误信息

			// 直接返回前台页面
			// return "useradd";
			return "user/useradd";
		}

		// 设置文件保存路径
		String realPath = request.getServletContext().getRealPath(File.separator + "iconImages");

		File desFile = new File(realPath);
		if (!desFile.exists()) {

			desFile.mkdirs();// 如果路径不存在则创建

		}
		// 定义保存时的文件名称 省略了文件大小校验 文件类型校验...
		String fileName = null;
		// 解析用户上传的文件 file数组

		// 按前台表单的提交顺序依次解析
		MultipartFile multipartFile = file[0];// 头像
		if (!multipartFile.isEmpty()) {

			// 解析
			fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal."
					+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			desFile = new File(realPath, fileName);
			try {
				multipartFile.transferTo(desFile);
				System.out.println("上传成功！");
				// 将文件路径记录到User对象中
				user.setIconPath(desFile.getAbsolutePath());
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		multipartFile = file[1];// 证件照
		if (!multipartFile.isEmpty()) {

			// 解析
			fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal."
					+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			desFile = new File(realPath, fileName);
			try {
				multipartFile.transferTo(desFile);
				System.out.println("上传成功！");
				// 将文件路径记录到User对象中
				user.setZjzPath(desFile.getAbsolutePath());
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		user.setCreatedBy(user2.getId());// 将登陆者的ID作为创建该用户的创建者

		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "成功");
			return "useradd";

		}

		model.addAttribute("MSG", "失败");
		return "useradd";

	}

	// 用于新增用户
	@RequestMapping("/useraddsave.html") // 如果待注入的User对象中有日期类型的属性 那么需要去属性定义上加一个@DateTimeFormat注解 指定待注入的日期格式 否则会报数据类型转换异常
	public String doUserAddSave(@Valid User user, BindingResult errors, HttpSession session, Model model,
			@RequestParam(value = "idPicPath", required = false) MultipartFile file, HttpServletRequest request,
			@RequestParam(value = "zzPath", required = false) MultipartFile zzFile) {

		// 设置创建该用户的用户 当前登陆者

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// 说明用户没登陆
			// 抛出一个异常 让用户去登陆
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		// errors.hasErrors();//返回true说明有错误信息
		if (errors.hasErrors()) {

			FieldError msg = errors.getFieldError("userCode");
			String msg2 = msg.getDefaultMessage();// 可以手动获取具体属性的错误信息

			// 直接返回前台页面
			// return "useradd";
			return "user/useradd";
		}

		// 设置文件保存路径
		String realPath = request.getServletContext().getRealPath(File.separator + "iconImages");

		File desFile = new File(realPath);
		if (!desFile.exists()) {

			desFile.mkdirs();// 如果路径不存在则创建

		}

		// 解析用户上传的文件 file
		// 获取原文件名
		String oldFileName = file.getOriginalFilename();
		// 限定文件上传的大小
		long fileSize = file.getSize();

		if (fileSize > 5000000) {

			model.addAttribute("MSG", "文件过大");

			return "useradd";
		}

		// 限定文件的类型
		String extensionName = FilenameUtils.getExtension(oldFileName).toLowerCase();

		// 定义一些文件的扩展名
		List<String> fileExtension = Arrays.asList("jpg", "png", "jpeg");

		if (fileExtension.indexOf(extensionName) < 0) {

			// 格式不正确
			model.addAttribute("MSG", "格式不正确");

			return "useradd";

		}

		// 保存文件 文件的保存路径
		// 重新定义文件名称 距离1970.1.1 至今的毫秒数 + UUID随机数 + 名称 + 扩展名
		String fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal." + extensionName;

		desFile = new File(desFile, fileName);// File file = new File("c:\img"); file = new File(file,"c.jpg");
												// c:\img\c.jpg

		try {
			file.transferTo(desFile);// 保存成功//使用提供的工具类进行文件的保存

			System.out.println("保存成功！！" + desFile.getAbsolutePath());
			// 保存
			// 将图片路径赋给user
			user.setIconPath(desFile.getAbsolutePath());

		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 文件保存路径
		oldFileName = zzFile.getOriginalFilename();
		extensionName = FilenameUtils.getExtension(oldFileName);// 获取证件照的扩展名
		fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal." + extensionName;
		desFile = new File(realPath, fileName);

		// 省略大小校验、格式校验
		try {
			zzFile.transferTo(desFile);
			System.out.println("上传成功！" + desFile.getAbsolutePath());
			// 保存
			// 将图片路径赋给user
			user.setZjzPath(desFile.getAbsolutePath());
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.setCreatedBy(user2.getId());// 将登陆者的ID作为创建该用户的创建者

		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "成功");
			return "useradd";

		}

		model.addAttribute("MSG", "失败");
		return "useradd";

	}

	// 用于新增用户
	// @RequestMapping("/useraddsave.html")
	public String doUserAddSave(Model model, HttpSession session,
			@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "userPassword", required = false) String userPassword,
			@RequestParam(value = "ruserPassword", required = false) String ruserPassword,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "birthday", required = false) String birthday,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "userRole", required = false) String userRole) {
		User user = null;
		try {
			user = new User(null, userCode, userName, userPassword, Integer.parseInt(gender), null, phone, address,
					Integer.parseInt(userRole), null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出一个异常 跳回用户添加页

			return "useradd";
		}
		// 设置创建该用户的用户 当前登陆者

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// 说明用户没登陆
			// 抛出一个异常 让用户去登陆
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		user.setCreatedBy(user2.getId());// 将登陆者的ID作为创建该用户的创建者

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);// HandlerAdapter {数据校验、类型转换}
			user.setBirthday(date);// 注入生日
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 保存
		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "成功");
			return "useradd";

		}

		model.addAttribute("MSG", "失败");
		return "useradd";
	}

	// 用户登陆校验
	@RequestMapping("/dologin.html")
	public String doLogin(HttpServletRequest request,
			@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userPassword", required = false) String userPassword, Model model,
			HttpSession session) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		System.out.println(context.getBean("userServiceImpl") + "==================");
		User user = new User();
		user.setUserCode(userCode);
		user.setUserPassword(userPassword);

		user = userService.findUserForLogin(user);

		if (user != null) {

			// 将用户信息保存在HttpSession
			session.setAttribute("userSession", user);
			// return "frame";//不能使用默认的页面跳转默认就是forward 转发 页面地址不变 直接写frame.html会交给视图解析器解析
			// 而不会跳转到控制器
			return "redirect:/user/frame.html";
		}
		// 给与提示信息 除非必要情况 一般不推荐使用原生的ServletAPI 因为springmvc都对其有包装类 或者注解
		// ()比如：HttpSession在SpringMVC中的注解是@SessionAttributes或者@SessionAttribute
		// request.setAttribute("error", "用户名或密码错误！");
		/*
		 * model.addAttribute("error", "用户名或者密码错误！"); if("error".equals(userCode)) {
		 * 
		 * throw new NullPointerException("触发了局部异常！");
		 * 
		 * } return "login";
		 */
		throw new LoginErrorException("用户名或密码错误Error");
	}

	// 用于修改页面地址
	@RequestMapping("/frame.html")
	public String toFrame(HttpSession session) {
		// 判断HttpSession中是否有登陆用户

		if (session.getAttribute("userSession") != null) {

			return "frame";
		}
		return "login";

	}

	// 用于用户退出
	@RequestMapping("/logout.html")
	public String doLogout(HttpSession session) {

		// 从HttpSession中移除当前登陆用户
		Object obj = session.getAttribute("userSession");
		if (obj != null) {

			// session.invalidate();//避免使用session失效方法 防止出现其他数据丢失
			session.removeAttribute("userSession");
		}

		// return "login";//该方式是转发 地址栏不会改变
		return "redirect:/user/login.html";
	}

	// 改方法用于处理该类中出现的异常 但凡该类出现异常都会进该方法 （异常抛出增强）
	@ExceptionHandler(value = { NullPointerException.class, LoginErrorException.class })
	public String toError(RuntimeException exception, HttpServletRequest request) {

		if (exception instanceof LoginErrorException) {
			request.setAttribute("error", exception.getMessage());
			return "login";

		}

		request.setAttribute("exception", exception);

		return "error";
	}

	// 查询用户列表 可以查询所有 可以根据指定用户名称查询
	@RequestMapping("/userlist.html")
	public String doUserList(Model model, String userName, String currentIndex, String userRole, String totalIndex) {

		// 分页
		// 定义所需要的变量
		int current = 1;
		int total = 0;
		int userRoleId = 0;
		try {
			current = Integer.parseInt(currentIndex);

			total = Integer.parseInt(totalIndex);

			// 判断上下限
			if (current <= 0) {
				current = 1;
			}
			if (current >= total) {
				current = total;
			}

		} catch (Exception e) {
			current = 1;

			e.printStackTrace();
		}
		// 用户角色ID不一定需要可能为null 该判断需要和判断当前页的逻辑分开写
		try {
			userRoleId = Integer.parseInt(userRole);
		} catch (Exception e) {
			userRoleId = 0;
		}
		UserPage page = userService.findUserPage(current, userName, userRoleId);
		model.addAttribute("userPage", page);

		return "user/userlist";
	}

	// 根据用户id查找用户
	@RequestMapping("/findUser.html")
	public String getUserById(@RequestParam(value = "id", required = false) String id, Model model) {

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// 提示信息

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	// 根据用户id异步查找用户
	@ResponseBody
	@RequestMapping(value = "/findUser2")//指定响应的mime type类型以及编码方式 报文头,produces= {"application/json;charset=UTF-8"}
	public Object getUserById_ajax(@RequestParam(value = "id", required = false) String id,Model model) {

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// 提示信息

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		
		//硬编码方式  返回值需要解析成json字符串  
		//String json = JSON.toJSONString(user);//fastjson提供的将一个Java对象序列化成一个json字符串的API
		//如果不适用硬编码  改用FastJsonHttpMessageConverter 进行配置  此处不需要解析成json  直接把待解析的java对象返回出去即可。
		//String json = JSON.toJSONString(user, SerializerFeature.WriteDateUseDateFormat);
		
		
		
		//直接返回Java对象   交给FastJsonHttpMessageConverter解析  如果在此处解析 会导致日期格式还是时间戳 
		model.addAttribute("user", user);
		return user;
	}
	

	// 用户修改保存
	@RequestMapping("/usermodifysave.html")
	public String saveUserForModify(User user) {// 该User不可能为空的 即使一个参数没提交 mvc也会帮我们创建一个对象

		boolean result = userService.updateUser(user);
		if (result) {

			return "redirect:userlist.html";// 重定向到另一个处理器 再次查询一次用户列表 而不是直接跳转到jsp

		}

		return "usermodify";

	}
	
	
	
	
	
	
	

	// 使用restful风格api查看用户明细
	@RequestMapping("/view/{id}")
	public String viewUserDetail(@PathVariable String id, Model model) {

		System.out.println(id + "=========================");

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// 提示信息

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		model.addAttribute("user", user);

		return "userview";
	}
	//旧密码正确性校验
	@ResponseBody
	@RequestMapping("/userPassWordCheck.json")
	public String paswordCheck(String oldpassword) {
		//先判断用户是否已登陆
		
		
		//根据密码查询。。
		
		
		//	根据查询结果判断 输出的json格式  构造一个HashMap  或者直接手写json  或者创建java实体类
		
		
		return "";
	}
}
