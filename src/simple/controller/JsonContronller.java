package simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonContronller {
	
	@ResponseBody//��һ����ע��
	@RequestMapping("/getJson.json")//����չ����Ϊjson
	public String getJson() {

		String json = "{\"name\":\"simple\"}"; //  name  simple
		return json;
	}

}
