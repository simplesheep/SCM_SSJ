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
		System.out.println("====����ת������Ч");
		//ע��֧�ֵ�����ת��
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		
	}
	//DataBinder  ������͵��Զ���ת�� �������Ч  ��ע��ķ��������з���ֵ
	@InitBinder
	public void converterStringToDate(WebDataBinder binder) {
		System.out.println("====����ת������Ч");
		//ע��֧�ֵ�����ת��
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
	 * UserCode���ظ�У��
	 */
	@ResponseBody
	@RequestMapping("/userCodeCheck.json")
	public String userCodeCheck(@RequestParam(value = "userCode", required = false) String userCode) {

		String json = null;
		
		//����һ��HashMap����
		HashMap<String, String> map = new HashMap<String,String>();
		
		// ����userCode����У��
		User user = userService.findUserForLogin(userCode);
		if (user != null) {

			//json = "{\"userCode\":\"exist\"}";
			map.put("userCode", "exist");
			
			
		} else {
			
			//json = "{\"userCode\":\"success\"}";//
			map.put("userCode", "success");
		}
		json = JSONArray.toJSONString(map);
		return json;//Ŀ�ģ��������صĽ��������ͼ���������� ����ֱ����������ʽд��ͻ����������д��Http����Ӧ���У�
	}

	@RequestMapping("/springtag.html")
	public String toSpringTag(@ModelAttribute(value = "command") User user, Model model) {
		System.out.println("============================================");
		// model.addAttribute("command", user); ����Ҫ��ʾ��д��仰 ��Ϊ���ʹ����ModelAttribute
		// ��ô���Զ�������ע��Model��

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

	// ������ת���û�����ҳ��

	/**
	 * 
	 * ModelAttribute �� ��Model�м���һ��ʵ����Ķ��� ʲôʱ��ӣ���ת��form��֮ǰ ����ʱ��ע�������ڲ�������λ��
	 * ��ʹ��������λ�ò��Ӵ�ע�� Ҳ����ʹ������ķ�ʽ���springmvc�Ĳ����Զ���װ �������ǰ̨ʹ�õ�spring�ı���ǩ ��ô��ע������
	 * ����ᱨһ��command�Ҳ����Ĵ��� ���ܷ��ڷ���������
	 * 
	 * ModelAttribute �����ڷ��������� ��ʱ���ܺ�˭RequestMapping���� ���и�ע��ķ��� �������󵽴�Request
	 * Mapping֮ǰִ��һ�� ����������һ����ʼ������
	 * 
	 */

	@RequestMapping("/useradd.html")

	public String toUserAdd(@ModelAttribute(value = "user") User user) {//

		return "useradd";

		// return "user/useradd";
	}

	// ���������û�
	@RequestMapping("/useraddsave1.html") // �����ע���User���������������͵����� ��ô��Ҫȥ���Զ����ϼ�һ��@DateTimeFormatע�� ָ����ע������ڸ�ʽ
											// ����ᱨ��������ת���쳣
	public String doUserAddSave1(@Valid User user, BindingResult errors, HttpSession session, Model model,
			@RequestParam(value = "uploadFile", required = false) MultipartFile[] file, HttpServletRequest request) {

		// ���ô������û����û� ��ǰ��½��

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// ˵���û�û��½
			// �׳�һ���쳣 ���û�ȥ��½
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		// errors.hasErrors();//����true˵���д�����Ϣ
		if (errors.hasErrors()) {

			FieldError msg = errors.getFieldError("userCode");
			String msg2 = msg.getDefaultMessage();// �����ֶ���ȡ�������ԵĴ�����Ϣ

			// ֱ�ӷ���ǰ̨ҳ��
			// return "useradd";
			return "user/useradd";
		}

		// �����ļ�����·��
		String realPath = request.getServletContext().getRealPath(File.separator + "iconImages");

		File desFile = new File(realPath);
		if (!desFile.exists()) {

			desFile.mkdirs();// ���·���������򴴽�

		}
		// ���屣��ʱ���ļ����� ʡ�����ļ���СУ�� �ļ�����У��...
		String fileName = null;
		// �����û��ϴ����ļ� file����

		// ��ǰ̨�����ύ˳�����ν���
		MultipartFile multipartFile = file[0];// ͷ��
		if (!multipartFile.isEmpty()) {

			// ����
			fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal."
					+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			desFile = new File(realPath, fileName);
			try {
				multipartFile.transferTo(desFile);
				System.out.println("�ϴ��ɹ���");
				// ���ļ�·����¼��User������
				user.setIconPath(desFile.getAbsolutePath());
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		multipartFile = file[1];// ֤����
		if (!multipartFile.isEmpty()) {

			// ����
			fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal."
					+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			desFile = new File(realPath, fileName);
			try {
				multipartFile.transferTo(desFile);
				System.out.println("�ϴ��ɹ���");
				// ���ļ�·����¼��User������
				user.setZjzPath(desFile.getAbsolutePath());
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		user.setCreatedBy(user2.getId());// ����½�ߵ�ID��Ϊ�������û��Ĵ�����

		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "�ɹ�");
			return "useradd";

		}

		model.addAttribute("MSG", "ʧ��");
		return "useradd";

	}

	// ���������û�
	@RequestMapping("/useraddsave.html") // �����ע���User���������������͵����� ��ô��Ҫȥ���Զ����ϼ�һ��@DateTimeFormatע�� ָ����ע������ڸ�ʽ ����ᱨ��������ת���쳣
	public String doUserAddSave(@Valid User user, BindingResult errors, HttpSession session, Model model,
			@RequestParam(value = "idPicPath", required = false) MultipartFile file, HttpServletRequest request,
			@RequestParam(value = "zzPath", required = false) MultipartFile zzFile) {

		// ���ô������û����û� ��ǰ��½��

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// ˵���û�û��½
			// �׳�һ���쳣 ���û�ȥ��½
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		// errors.hasErrors();//����true˵���д�����Ϣ
		if (errors.hasErrors()) {

			FieldError msg = errors.getFieldError("userCode");
			String msg2 = msg.getDefaultMessage();// �����ֶ���ȡ�������ԵĴ�����Ϣ

			// ֱ�ӷ���ǰ̨ҳ��
			// return "useradd";
			return "user/useradd";
		}

		// �����ļ�����·��
		String realPath = request.getServletContext().getRealPath(File.separator + "iconImages");

		File desFile = new File(realPath);
		if (!desFile.exists()) {

			desFile.mkdirs();// ���·���������򴴽�

		}

		// �����û��ϴ����ļ� file
		// ��ȡԭ�ļ���
		String oldFileName = file.getOriginalFilename();
		// �޶��ļ��ϴ��Ĵ�С
		long fileSize = file.getSize();

		if (fileSize > 5000000) {

			model.addAttribute("MSG", "�ļ�����");

			return "useradd";
		}

		// �޶��ļ�������
		String extensionName = FilenameUtils.getExtension(oldFileName).toLowerCase();

		// ����һЩ�ļ�����չ��
		List<String> fileExtension = Arrays.asList("jpg", "png", "jpeg");

		if (fileExtension.indexOf(extensionName) < 0) {

			// ��ʽ����ȷ
			model.addAttribute("MSG", "��ʽ����ȷ");

			return "useradd";

		}

		// �����ļ� �ļ��ı���·��
		// ���¶����ļ����� ����1970.1.1 ����ĺ����� + UUID����� + ���� + ��չ��
		String fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal." + extensionName;

		desFile = new File(desFile, fileName);// File file = new File("c:\img"); file = new File(file,"c.jpg");
												// c:\img\c.jpg

		try {
			file.transferTo(desFile);// ����ɹ�//ʹ���ṩ�Ĺ���������ļ��ı���

			System.out.println("����ɹ�����" + desFile.getAbsolutePath());
			// ����
			// ��ͼƬ·������user
			user.setIconPath(desFile.getAbsolutePath());

		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �ļ�����·��
		oldFileName = zzFile.getOriginalFilename();
		extensionName = FilenameUtils.getExtension(oldFileName);// ��ȡ֤���յ���չ��
		fileName = System.currentTimeMillis() + "" + UUID.randomUUID() + "_personal." + extensionName;
		desFile = new File(realPath, fileName);

		// ʡ�Դ�СУ�顢��ʽУ��
		try {
			zzFile.transferTo(desFile);
			System.out.println("�ϴ��ɹ���" + desFile.getAbsolutePath());
			// ����
			// ��ͼƬ·������user
			user.setZjzPath(desFile.getAbsolutePath());
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.setCreatedBy(user2.getId());// ����½�ߵ�ID��Ϊ�������û��Ĵ�����

		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "�ɹ�");
			return "useradd";

		}

		model.addAttribute("MSG", "ʧ��");
		return "useradd";

	}

	// ���������û�
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
			// �׳�һ���쳣 �����û����ҳ

			return "useradd";
		}
		// ���ô������û����û� ��ǰ��½��

		User user2 = (User) session.getAttribute("userSession");
		if (user2 == null) {

			// ˵���û�û��½
			// �׳�һ���쳣 ���û�ȥ��½
			model.addAttribute("MSG", "NOLOGIN");

			return "useradd";
		}

		user.setCreatedBy(user2.getId());// ����½�ߵ�ID��Ϊ�������û��Ĵ�����

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);// HandlerAdapter {����У�顢����ת��}
			user.setBirthday(date);// ע������
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����
		boolean result = userService.insertUser(user);
		if (result) {
			model.addAttribute("MSG", "�ɹ�");
			return "useradd";

		}

		model.addAttribute("MSG", "ʧ��");
		return "useradd";
	}

	// �û���½У��
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

			// ���û���Ϣ������HttpSession
			session.setAttribute("userSession", user);
			// return "frame";//����ʹ��Ĭ�ϵ�ҳ����תĬ�Ͼ���forward ת�� ҳ���ַ���� ֱ��дframe.html�ύ����ͼ����������
			// ��������ת��������
			return "redirect:/user/frame.html";
		}
		// ������ʾ��Ϣ ���Ǳ�Ҫ��� һ�㲻�Ƽ�ʹ��ԭ����ServletAPI ��Ϊspringmvc�������а�װ�� ����ע��
		// ()���磺HttpSession��SpringMVC�е�ע����@SessionAttributes����@SessionAttribute
		// request.setAttribute("error", "�û������������");
		/*
		 * model.addAttribute("error", "�û��������������"); if("error".equals(userCode)) {
		 * 
		 * throw new NullPointerException("�����˾ֲ��쳣��");
		 * 
		 * } return "login";
		 */
		throw new LoginErrorException("�û������������Error");
	}

	// �����޸�ҳ���ַ
	@RequestMapping("/frame.html")
	public String toFrame(HttpSession session) {
		// �ж�HttpSession���Ƿ��е�½�û�

		if (session.getAttribute("userSession") != null) {

			return "frame";
		}
		return "login";

	}

	// �����û��˳�
	@RequestMapping("/logout.html")
	public String doLogout(HttpSession session) {

		// ��HttpSession���Ƴ���ǰ��½�û�
		Object obj = session.getAttribute("userSession");
		if (obj != null) {

			// session.invalidate();//����ʹ��sessionʧЧ���� ��ֹ�����������ݶ�ʧ
			session.removeAttribute("userSession");
		}

		// return "login";//�÷�ʽ��ת�� ��ַ������ı�
		return "redirect:/user/login.html";
	}

	// �ķ������ڴ�������г��ֵ��쳣 ������������쳣������÷��� ���쳣�׳���ǿ��
	@ExceptionHandler(value = { NullPointerException.class, LoginErrorException.class })
	public String toError(RuntimeException exception, HttpServletRequest request) {

		if (exception instanceof LoginErrorException) {
			request.setAttribute("error", exception.getMessage());
			return "login";

		}

		request.setAttribute("exception", exception);

		return "error";
	}

	// ��ѯ�û��б� ���Բ�ѯ���� ���Ը���ָ���û����Ʋ�ѯ
	@RequestMapping("/userlist.html")
	public String doUserList(Model model, String userName, String currentIndex, String userRole, String totalIndex) {

		// ��ҳ
		// ��������Ҫ�ı���
		int current = 1;
		int total = 0;
		int userRoleId = 0;
		try {
			current = Integer.parseInt(currentIndex);

			total = Integer.parseInt(totalIndex);

			// �ж�������
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
		// �û���ɫID��һ����Ҫ����Ϊnull ���ж���Ҫ���жϵ�ǰҳ���߼��ֿ�д
		try {
			userRoleId = Integer.parseInt(userRole);
		} catch (Exception e) {
			userRoleId = 0;
		}
		UserPage page = userService.findUserPage(current, userName, userRoleId);
		model.addAttribute("userPage", page);

		return "user/userlist";
	}

	// �����û�id�����û�
	@RequestMapping("/findUser.html")
	public String getUserById(@RequestParam(value = "id", required = false) String id, Model model) {

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// ��ʾ��Ϣ

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	// �����û�id�첽�����û�
	@ResponseBody
	@RequestMapping(value = "/findUser2")//ָ����Ӧ��mime type�����Լ����뷽ʽ ����ͷ,produces= {"application/json;charset=UTF-8"}
	public Object getUserById_ajax(@RequestParam(value = "id", required = false) String id,Model model) {

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// ��ʾ��Ϣ

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		
		//Ӳ���뷽ʽ  ����ֵ��Ҫ������json�ַ���  
		//String json = JSON.toJSONString(user);//fastjson�ṩ�Ľ�һ��Java�������л���һ��json�ַ�����API
		//���������Ӳ����  ����FastJsonHttpMessageConverter ��������  �˴�����Ҫ������json  ֱ�ӰѴ�������java���󷵻س�ȥ���ɡ�
		//String json = JSON.toJSONString(user, SerializerFeature.WriteDateUseDateFormat);
		
		
		
		//ֱ�ӷ���Java����   ����FastJsonHttpMessageConverter����  ����ڴ˴����� �ᵼ�����ڸ�ʽ����ʱ��� 
		model.addAttribute("user", user);
		return user;
	}
	

	// �û��޸ı���
	@RequestMapping("/usermodifysave.html")
	public String saveUserForModify(User user) {// ��User������Ϊ�յ� ��ʹһ������û�ύ mvcҲ������Ǵ���һ������

		boolean result = userService.updateUser(user);
		if (result) {

			return "redirect:userlist.html";// �ض�����һ�������� �ٴβ�ѯһ���û��б� ������ֱ����ת��jsp

		}

		return "usermodify";

	}
	
	
	
	
	
	
	

	// ʹ��restful���api�鿴�û���ϸ
	@RequestMapping("/view/{id}")
	public String viewUserDetail(@PathVariable String id, Model model) {

		System.out.println(id + "=========================");

		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (Exception e) {

			// ��ʾ��Ϣ

		}

		List<User> users = userService.findUser(user);
		user = users.get(0);
		model.addAttribute("user", user);

		return "userview";
	}
	//��������ȷ��У��
	@ResponseBody
	@RequestMapping("/userPassWordCheck.json")
	public String paswordCheck(String oldpassword) {
		//���ж��û��Ƿ��ѵ�½
		
		
		//���������ѯ����
		
		
		//	���ݲ�ѯ����ж� �����json��ʽ  ����һ��HashMap  ����ֱ����дjson  ���ߴ���javaʵ����
		
		
		return "";
	}
}
