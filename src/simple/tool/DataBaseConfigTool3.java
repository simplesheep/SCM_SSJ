package simple.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseConfigTool3 {

	
	
		private static final Properties PROPERTIES = new Properties();
	
	
		//˽�й�����
		private DataBaseConfigTool3() {
			
			//��ʼ�������ļ�
			InputStream inStream = DataBaseConfigTool3.class.getClassLoader().getResourceAsStream("database.properties");
			System.out.println(inStream);
			try {
				PROPERTIES.load(inStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//�����������һ������
		private static DataBaseConfigTool3 baseConfigTool;
		
		//����һ����̬�ڲ���  ��������ʵ�����ⲿ�����
		private static class Config{
			
			private static final DataBaseConfigTool3 BASE_CONFIG_TOOL3 = new DataBaseConfigTool3();
			
			
		}
	
		//�ṩ��̬�������ظ���ʵ��  ��̬�ڲ���ģʽ���ӳټ��� + �̰߳�ȫ
		public static DataBaseConfigTool3 getInstance() {
		
			
			baseConfigTool = Config.BASE_CONFIG_TOOL3;
			
			return baseConfigTool;
			
		}
		
		
		
		//�ṩһ������ ����properties�ļ��е�ֵ  ����key
		public String getProperty(String key) {
			
			
			return PROPERTIES.getProperty(key);
			
		}
	
	
}
