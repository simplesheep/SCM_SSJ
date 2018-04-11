package simple.tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadWord {
	public static void main(String[] args) throws Exception {
		
		
		List<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader("E:\\word.txt"));
		
		String word = reader.readLine();
		while(word != null) {
			list.add(word);
			word = reader.readLine();
		}
		reader.close();
		String pattern1 = "<col>";
		String pattern2 = "</col>";
		Pattern pattern_1 = Pattern.compile(pattern1);
		Pattern pattern_2 = Pattern.compile(pattern2);
		for(String string : list) {
			
			System.out.println(string);
			//ÇÐ¸î
			Matcher matcher = pattern_1.matcher(string);
			Matcher matcher2 = pattern_2.matcher(string);
			int start = 0;
			int end = 0;
			while(matcher.find()) {
				//System.out.print(matcher.start() + 5 +"-");
				start = matcher.start() + 5;
				matcher2.find();
				//System.out.print(matcher2.start());
				end = matcher2.start();
				System.out.print(string.substring(start, end));
				System.out.println();
			}
			
		}
		
		
		
	}
}
