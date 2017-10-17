package pers.pinvon.packet;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;

import pers.pinvon.tool.CallBack;
import pers.pinvon.tool.OperateFile;

public class PacketAnalysis {
	private HashMap<String, Integer> srcIP = new HashMap<String, Integer>();
	private HashMap<String, Integer> desIP = new HashMap<String, Integer>();
	private HashMap<String, Integer> srcPort = new HashMap<String, Integer>();
	private HashMap<String, Integer> desPort = new HashMap<String, Integer>();
	private double size = 0.0f;
	
	public PacketAnalysis(){
		this.size = 0;
		srcIP.clear();
		desIP.clear();
		srcPort.clear();
		desPort.clear();
	}
	
	public void count(String filePath){
		OperateFile.readFile(filePath, new CallBack(){
			public void doSomething(BufferedReader reader){
				dealRow(reader);
			}
		});
	}
	
	private void dealRow(BufferedReader reader){
		String str = null;
		try{
			while((str = reader.readLine()) != null){
				String[] row = str.split(",");
				countEveryValue(srcIP, row[0]);
				countEveryValue(desIP, row[1]);
				
				if(!row[2].equals(' ')){
					countEveryValue(srcPort, row[2]);
					countEveryValue(desPort, row[3]);
				}
				this.size += 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void countEveryValue(HashMap<String, Integer> hashMap, String str){
		if(!hashMap.containsKey(str)){
			hashMap.put(str, new Integer(1));
		}else{
			int k = hashMap.get(str).intValue()+1;
			hashMap.put(str, new Integer(k));
		}
	}
	
	public HashMap<String, Integer> getSrcIPList(){
		return srcIP;
	}
	
	public HashMap<String, Integer> getDesIPList(){
		return desIP;
	}
	
	public HashMap<String, Integer> getSrcPortList(){
		return srcPort;
	}
	
	public HashMap<String, Integer> getDesPortList(){
		return desPort;
	}
	
	public double getSize(){
		return this.size;
	}
	
	public double calcEntropy(HashMap<String, Integer> hashMap){
		Iterator<String> it = hashMap.keySet().iterator();
		String str = null;
		double p = 0.0f;
		double entropy = 0.0f;
		while(it.hasNext()){
			str = (String)it.next();
			p = (double)hashMap.get(str) / this.size;
			entropy += p * Math.log(p) / Math.log(2);
		}
		return -entropy;
	}
	
	public void calcEntropy(){
		if(!getSrcIPList().isEmpty()){
			double srcIPEntropy = calcEntropy(getSrcIPList());
			double desIPEntropy = calcEntropy(getDesIPList());
			double srcPortEntropy = calcEntropy(getSrcPortList());
			double desPortEntropy = calcEntropy(getDesPortList());
			System.out.println(srcIPEntropy);
			System.out.println(desIPEntropy);
			System.out.println(srcPortEntropy);
			System.out.println(desPortEntropy);
		}
	}
	
	
	
	
	public static void main(String[] args){
		PacketAnalysis a = new PacketAnalysis();
		a.count("data/result_0.txt");
		a.calcEntropy();
		
		/*
		HashMap<String, Integer> hashMap = a.getSrcIPList();
		Iterator<String> it = hashMap.keySet().iterator();
		while(it.hasNext()){
			String str = (String)it.next();
			System.out.println(str);
			System.out.println(hashMap.get(str));
		}*/
	}
}
