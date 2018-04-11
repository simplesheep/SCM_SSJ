package simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	
	@InitBinder
	public void converterStringToDate2(WebDataBinder binder) {
		System.out.println("====类型转换器生效");
		//注册支持的类型转换
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		
	}
	
}
