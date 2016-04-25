package org.xxhh.java300.jvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ProcessBuilderTest {

	public static void main(String[] args) {
		
		
	
	}
	
	public static void runTimeTest(){
		Process p;
		String cmd = "c:\\test\\test.bat";
		
		try{
			p = Runtime.getRuntime().exec(cmd);
			InputStream fis = p.getInputStream();
			
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void processBuilderTest() throws IOException{
		ProcessBuilder pb = new ProcessBuilder("java","-jar","Test3.jar");
		pb.directory(new File("F:\\dist"));
		Map<String,String> map = pb.environment();
		Process p = pb.start();
		System.exit(0);
	}
}
