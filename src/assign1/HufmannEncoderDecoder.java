package assign1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
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
			System.out.println("analyzing freq//"); //DEBUG
			int freq[] = freqarr(arr);//Create new frequency array fro all possible bytes (255)
			this.root = HuffmanNode.BuildTree(freq);//o(nlogn)
			
			//Build the tree (recursive)
			System.out.println("building tree..");//DEBUG
			
			HashMap <Character,String > dictionary = new HashMap<Character,String>();
			root.buildDictionary(dictionary,"");//o(nlogn)
			
			//write the coded version to string
			String code =codedString(arr, dictionary);
			
			//actually write to the file
			System.out.println("Code Length: " + code.length()); //DEBUG
			
			writeStringToFile(code, output_names);
		}
		catch (IOException e) {  e.printStackTrace();}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("That took " + ((endTime - startTime)/1000) + " seconds");
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
			System.out.println("running.. letter: " + j + " of " + arr.length);//DEBUG
			char c = (char)arr[j];
			String s = dictionary.get(c);//o(1) get the value of the letter and put into code
			System.out.println("letter: " + c + " code: " + s);//DEBUG
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
		//reads the file as bytes and count how many times the bite has appeared during the code
		for (int i = 0; i < arr.length; i++)//o(n)
		{
			if (arr[i] < 0)
				freq[arr[i]+256]++;
			else
				freq[(arr[i])] ++;
		}
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

		 for(int i=0;i<code.length();i=i+8)
		 {
			tstr=code.substring(i, i+8);
			int bin=Integer.parseInt(tstr,2);
			
			byte[] barr= {(byte)bin};
			System.out.println("bin: "+bin);
				fos.write(barr);
			}
		 System.out.println(zeroCounter);
		 System.out.println((byte)zeroCounter);
		 fos.write((byte)zeroCounter);
		}
			catch(Exception e){e.printStackTrace();}
		}

		
		/*Writer writer = null;
		System.out.println("writing to file..");//DEBUG
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output_names[0]), "Ascii"));
			/*
			int bytes=(code.length())/8;
			int eolB=bytes*8;//End of last byte
			int remainder=0; // number of 0s to add to complete a byte
			if (code.length()%8!=0){
				remainder=8-(code.length()-eolB);
				System.out.println("adding "+remainder+" 0s..");//DEBUG
				for(int i=0;i<remainder;i++) {
					code+='0';
				}
			}
			System.out.println("code:" + code);//DEBUG
				String tstr=new String();
				for(int i=0;i<code.length();i=i+8){
					tstr=code.substring(i, i+8);
					writer.write((char)Integer.parseInt(tstr,2));
					System.out.println("maybe: "+ (char)Integer.parseInt(tstr,2));
					System.out.println("maybe2: "+ tstr);
					}
				
				writer.write(new Integer(remainder).toString());
			
				for (int i = 0; i < code.length(); i += 8)//o(n)
			{
				System.out.println("Checking 8s starting from " +i + "of : " + code.length());
				//incase code.length%8 !=0 we should make a sign so we know how to decompress it later
				

				if (code.substring(i).length() < 8)
				{
					System.out.println("Code %8 !=0 ! ");//DEBUG
					String last = code.substring(i);
					System.out.println("last length : " + last.length());
					int oCounter=0;
					while (last.length() < 8) { //example: last is 3 bits
						last += '0';//adding 5 0's
						oCounter++;
						System.out.println("adding 0"); //DEBUG
					}
					byte b_last[] = last.getBytes();//b_last is 
					String temp = new String(b_last,"Ascii");
					System.out.println("temp string: " + temp);//DEBUG
					int new_val =  Integer.parseInt(temp,2);
					System.out.println("new val: " + new_val);//DEBUG
					System.out.println((char)new_val);
					writer.write((char)new_val);
					writer.write(new Integer(oCounter).toString());
					//writer.write("eof");
					break;
				}
				else
				{
					
					byte [] b = code.substring(i,i+8).getBytes();
					String temp = new String(b,"Ascii");
					System.out.println(temp);//DEBUG
					int new_val =  Integer.parseInt(temp,2);
					System.out.println(new_val);//DEBUG 
					
				
			
		} }}
		catch (IOException e) {  e.printStackTrace();}
		finally
		{
			try
			{writer.close();} 
			catch (Exception e) {e.printStackTrace();}
		}*/

	
	@Override
	public void Decompress(String[] input_names, String[] output_names)
	{
		long startTime = System.currentTimeMillis();
		System.out.println("starting Decompression");//DEBUG
		String binStr="";//BinaryString
		Queue<Character> binQ = new LinkedList<Character>();
		try {
			FileInputStream in = new FileInputStream(input_names[0]);
			//TODO: add a way to read the tree
			//in.read(reciver);
			int data = in.read();//Read chars from the coded file
			int leftover0s=0;
			while(data != -1) {
				byte temp=(byte)in.read();
				//String temp=Integer.toBinaryString((byte)data);
				String s1 = String.format("%8s", Integer.toBinaryString(temp & 0xFF)).replace(' ', '0');
				if(s1.length()<8) s1=String.format("%8s", s1).replace(' ', '0');
				System.out.println(s1);
				
				//System.out.println(temp);
				binStr=binStr+temp;  
				data = in.read();
				}
			leftover0s=(binStr.charAt(binStr.length()-1))-48;//Find how many 0s we added (ASCII so -48)
		      for(int i=0; i<binStr.length()-leftover0s-1; i++){
		          binQ.add(binStr.charAt(i));
		       }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Writer writer;
		try {
			writer = new FileWriter(output_names[0]);
			writer.write(decode(binQ));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
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
		System.out.println(binQ.peek());//DEBUG
		if(binQ.poll()=='0'){//PROBLEM: Gets to null
			if(root.getLeft()==null) return root.get_char();
			else return decodeRec(root.getLeft(),binQ);
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
		// TODO Auto-generated method stub
		return null;
	}

}
