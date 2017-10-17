package pers.pinvon.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OperateFile {
	public static void readFile(String filePath, CallBack call){
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(filePath));
			call.doSomething(reader);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void writeFile(String filePath){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(filePath));
			/*
			for(ProtocolData data : content){
				writer.write(data.getDesIP() + "," + data.getDesIP() + "," + data.getSrcPort() + "," + data.getDesPort());
				writer.newLine();
			}
			*/				
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(writer != null)
				try{
					writer.close();
				}catch(IOException e){
					e.printStackTrace();
				}

		}
	}
}
