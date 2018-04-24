package assign1;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1. 	ID# XXXXXXXXX
 * Student 1. 	ID# XXXXXXXXX
 */

// Uncomment if you wish to use FileOutputStream and FileInputStream for file access.

import base.compressor;

public class HufmannEncoderDecoder implements compressor
{
	public HuffmanNode root=null;
	public HufmannEncoderDecoder()
	{
		
	}
	@Override
	public void Compress(String[] input_names, String[] output_names)//o(nlogn)	
	{
		long startTime = System.currentTimeMillis();
		System.out.println("starting..");//DEBUG
		Path path = Paths.get(input_names[0]);//Get path from 1st array slot
		try
		{
			byte[] arr = Files.readAllBytes(path);//Use imported class to read bytes to an array
			int freq[] = freqarr(arr);//Create new frequency array fro all possible bytes (255)
			this.root = HuffmanNode.BuildTree(freq);//o(nlogn)
			
			//Build the tree (recursive)
			
			long startTimeD=System.currentTimeMillis();
			HashMap <Character,String > dictionary = new HashMap<Character,String>();
			root.buildDictionary(dictionary,"");//o(nlogn)
			long endTimeD=System.currentTimeMillis();
			System.out.println("Building the Dictionary took " + ((endTimeD - startTimeD)/1000) + " seconds");
			
			//write the coded version to string
			long startTimeC=System.currentTimeMillis();
			String code =codedString(arr, dictionary);
			long endTimeC=System.currentTimeMillis();
			System.out.println("Building the code string took " + ((endTimeC - startTimeC)/1000) + " seconds");
			
			//actually write to the file
			System.out.println("Code Length: " + code.length()); //DEBUG
			
			writeStringToFile(code, output_names);
		}
		catch (IOException e) {  e.printStackTrace();}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Comp took " + ((endTime - startTime)/1000) + " seconds");
	}
	
	/*
	 * @param byte array
	 * @param HashMap
	 * @return String
	 * returns a string of the coded version of the file
	 */
	public String codedString(byte [] arr,HashMap <Character,String > dictionary)
	{
		String code = null;
		for (int j = 0; j < arr.length; j++)//o(n)
		{
			char c = (char)arr[j];
			String s = dictionary.get(c);//o(1) get the value of the letter and put into code
			if (s == null)
				s = dictionary.get((char)(c + 256));
			if (code == null)//will happen once
				code = s;
			else
				code +=s;
		}
		return code;
		
	}
	/*
	 * @param: byte array
	 * @return: int array
	 * creates frequency array for all bytes
	 */
	public int[] freqarr(byte[] arr)
	{
		int freq[] = new int [256];
		long startTimeF=System.currentTimeMillis();
		//reads the file as bytes and count how many times the bite has appeared during the code
		for (int i = 0; i < arr.length; i++)//o(n)
		{
			if (arr[i] < 0)
				freq[arr[i]+256]++;
			else
				freq[(arr[i])] ++;
		}
		long endTimeF=System.currentTimeMillis();
		System.out.println("Analyzing freq took: " + (endTimeF-startTimeF)/1000 + " seconds.");
		return freq;
	}

	/*
	 * @param coded String 
	 * @param output path
	 * @return null
	 * Writes coded string to compressed file
	 */
	public void writeStringToFile(String code, String[] output_names)
	{
		int zeroCounter = 0;
		while (code.length() % 8 != 0)
		{
			code += '0';
			zeroCounter++;
		}
		String tstr;
		 try
		{
		 FileOutputStream fos = new FileOutputStream(output_names[0]);
		 fos.write((byte)zeroCounter);
		 for(int i=0;i<code.length();i=i+8)
		 {
			tstr=code.substring(i, i+8);
			int bin=Integer.parseInt(tstr,2);
			
			byte[] barr= {(byte)bin};
				fos.write(barr);
			}

		 
		}
			catch(Exception e){e.printStackTrace();}
		}
	
	@Override
	public void Decompress(String[] input_names, String[] output_names)
	{
		long startTimed = System.currentTimeMillis();
		System.out.println("starting Decompression");//DEBUG
		String binStr="";//BinaryString
		Queue<Character> binQ = new LinkedList<Character>();
		try {
			FileInputStream in = new FileInputStream(input_names[0]);
			//TODO: add a way to read the tree

			int data = in.read();//Read chars from the coded file
			int leftover0s=0;
			leftover0s=data;//Find how many 0s we added
			while(data != -1) {
				String temp=Integer.toBinaryString(data);
				if(temp.length()<8) temp=String.format("%8s", temp).replace(' ', '0');
				
				binStr=binStr+temp;  
				data = in.read();
				}
			in.close();
			
		      for(int i=8; i<binStr.length()-leftover0s; i++){
		          binQ.add(binStr.charAt(i));
		       }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Writer writer;
		try {
			writer = new FileWriter(output_names[0]);
			writer.write(decode(binQ));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

long endTimed = System.currentTimeMillis();
		
		System.out.println("Decomp took " + ((endTimed - startTimed)/1000) + " seconds");
		
	}
	
	public String decode(Queue<Character> binQ){
		String decodedStr="";
		while(!binQ.isEmpty()){
			decodedStr=decodedStr + decodeRec(this.root,binQ);
		}
		return decodedStr;
	}
	public char decodeRec(HuffmanNode root,Queue<Character> binQ){
		if(root.getLeft()==null && root.getRight()==null){
			return root.get_char();
		}
		if(binQ.poll()=='0'){//PROBLEM: Gets to null
			return decodeRec(root.getLeft(),binQ);
		}
		
		return decodeRec(root.getRight(),binQ);
	}
	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names)
	{
		long startTime = System.currentTimeMillis();
		Path path = Paths.get(input_names[0]);
			byte[] arr = null;
			try
			{
				arr = Files.readAllBytes(path);
			} 
			catch (IOException e) {e.printStackTrace();}
			int freq[] = freqarr(arr);
			HuffmanNode root = HuffmanNode.BuildTree(freq);//o(nlogn)
			
			HashMap <Character,String > dictionary = new HashMap<Character,String>();
			
			root.buildDictionary(dictionary,"");//o(nlogn)
			
			//write the coded version to string
			String code =codedString(arr, dictionary);
			
			//actually write to the file
			writeStringToFile(code, output_names);
		long endTime = System.currentTimeMillis();

		System.out.println("That took " + ((endTime - startTime)/1000) + " seconds");
		return (code.getBytes());
	}

	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names)
	{
		return null;
	}

}
