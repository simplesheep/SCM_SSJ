package simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonContronller {
	
	@ResponseBody//加一个该注解
	@RequestMapping("/getJson.json")//将扩展名改为json
	public String getJson() {

		String json = "{\"name\":\"simple\"}"; //  name  simple
		return json;
	}

}
