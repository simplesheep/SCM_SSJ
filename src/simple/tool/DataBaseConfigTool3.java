package simple.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseConfigTool3 {

	
	
		private static final Properties PROPERTIES = new Properties();
	
	
		//私有构造器
		private DataBaseConfigTool3() {
			
			//初始化配置文件
			InputStream inStream = DataBaseConfigTool3.class.getClassLoader().getResourceAsStream("database.properties");
			System.out.println(inStream);
			try {
				PROPERTIES.load(inStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//定义该类对象的一个引用
		private static DataBaseConfigTool3 baseConfigTool;
		
		//定义一个静态内部类  帮助我们实例化外部类对象
		private static class Config{
			
			private static final DataBaseConfigTool3 BASE_CONFIG_TOOL3 = new DataBaseConfigTool3();
			
			
		}
	
		//提供静态方法返回该类实例  静态内部类模式：延迟加载 + 线程安全
		public static DataBaseConfigTool3 getInstance() {
		
			
			baseConfigTool = Config.BASE_CONFIG_TOOL3;
			
			return baseConfigTool;
			
		}
		
		
		
		//提供一个方法 返回properties文件中的值  根据key
		public String getProperty(String key) {
			
			
			return PROPERTIES.getProperty(key);
			
		}
	
	
}
