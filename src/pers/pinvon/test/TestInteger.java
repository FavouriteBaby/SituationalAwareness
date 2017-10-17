package pers.pinvon.test;

import java.util.HashMap;

import pers.pinvon.hash.SimHash;
import pers.pinvon.packet.PacketAnalysis;

public class TestInteger {
	public static void main(String[] args){
		PacketAnalysis objPA = new PacketAnalysis();
		StringBuilder srcIP = new StringBuilder("");
		StringBuilder desIP = new StringBuilder("");
		StringBuilder srcPort = new StringBuilder("");
		StringBuilder desPort = new StringBuilder("");
		String path = "data/result_2.txt";
		objPA.count(path);
		HashMap<String, Integer> srcIPHashMap = objPA.getSrcIPList();
		HashMap<String, Integer> desIPHashMap = objPA.getDesIPList();
		HashMap<String, Integer> srcPortHashMap = objPA.getSrcPortList();
		HashMap<String, Integer> desPortHashMap = objPA.getDesPortList();
		
		double srcIPEntropy = objPA.calcEntropy(srcIPHashMap);
		double desIPEntropy = objPA.calcEntropy(desIPHashMap);
		double srcPortEntropy = objPA.calcEntropy(srcPortHashMap);
		double desPortEntropy = objPA.calcEntropy(desPortHashMap);
		
		final double scale = 2.48f;
		
		srcIP.append(" " + String.format("%.1f", srcIPEntropy/scale));
		desIP.append(" " + String.format("%.1f", desIPEntropy/scale));
		srcPort.append(" " + String.format("%.1f", srcPortEntropy/scale));
		desPort.append(" " + String.format("%.1f", desPortEntropy/scale));
		
		String sim = srcIP.toString() + " " + desIP.toString() + " " + srcPort.toString() + " " + desPort.toString(); 
		
		SimHash simHash = new SimHash(sim, 128);
		System.out.println(simHash.getStrSimHash().toString(2));
		System.out.println(simHash.getStrSimHash() + " " + simHash.getStrSimHash().bitLength());
		
	}
}
