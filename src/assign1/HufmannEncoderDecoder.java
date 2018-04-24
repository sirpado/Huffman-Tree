package assign1;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

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

	public HufmannEncoderDecoder()
	{
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Compress(String[] input_names, String[] output_names)//o(nlogn)	
	{
		long startTime = System.currentTimeMillis();
		Path path = Paths.get(input_names[0]);
		try
		{
			byte[] arr = Files.readAllBytes(path);
			int freq[] = freqarr(arr);
			HuffmanNode root = HuffmanNode.BuildTree(freq);//o(nlogn)
			
			HashMap <Character,String > dictionary = new HashMap<Character,String>();
			root.buildDictionary(dictionary,"");//o(nlogn)
			
			//write the coded version to string
			String code =codedString(arr, dictionary);
			
			//actually write to the file
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
			char c = (char)arr[j];
			String s = dictionary.get(c);//o(1)
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
		Writer writer = null;
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(output_names[0]), "Ascii"));
			for (int i = 0; i < code.length(); i += 8)//o(n)
			{
				//incase code.length%8 !=0 we should make a sign so we know how to decompress it later
				if (code.substring(i).length() < 8)
				{
					String last = code.substring(i);
					while (last.length() < 8)
						last += '0';
					byte b_last[] = last.getBytes();
					String temp = new String(b_last,"Ascii");
					int new_val =  Integer.parseInt(temp,2);
					writer.write((char)new_val);
					writer.write(8-code.substring(i).length() *2);
					writer.write("wow");
					break;
				}
				else
				{
					byte [] b = code.substring(i,i+8).getBytes();
					String temp = new String(b,"Ascii");
					int new_val =  Integer.parseInt(temp,2);
					writer.write((char)new_val);
				}
			}
		} 
		catch (IOException e) {  e.printStackTrace();}
		finally
		{
			try
			{writer.close();} catch (Exception e) {e.printStackTrace();}
		}
	} 

	@Override
	public void Decompress(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub

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
