package simple.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

	// 自定义一个日期转换格式
	private static SimpleDateFormat[] dateFormats = new SimpleDateFormat[3];

	static {

		dateFormats[0] = new SimpleDateFormat("yyyy-MM-dd");
		dateFormats[1] = new SimpleDateFormat("yyyy/MM/dd");
		dateFormats[2] = new SimpleDateFormat("yyyy.MM.dd");

	}

	@Override
	public Date convert(String str) {

		Date date = null;
		for (SimpleDateFormat dateFormat : dateFormats) {

			try {
				date = dateFormat.parse(str);
				return date; // 转换成功返回
			} catch (ParseException e) {

				e.printStackTrace();
				continue;// 转化失败 继续
			}

		}

		return date;
	}

}
