package assign1;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
	public void Compress(String[] input_names, String[] output_names)
	{
		Writer writer = null;
		Path path = Paths.get(input_names[0]);
		int freq[] = new int [256];
		//reads the file as bytes and count how many times the bite has appeared during the code
		try {
			byte[] arr = Files.readAllBytes(path);
			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i] < 0)
					freq[arr[i]+256]++;
				else
					freq[(arr[i])] ++;
			}
			
			HuffmanNode root = HuffmanNode.BuildTree(freq);
			
			HashMap <Character,String > dictionary = new HashMap<Character,String>();
			root.buildDictionary(dictionary,"");
			//write the coded version to the file
			try 
			{
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(output_names[1]), "utf-8"));
			    String code =null;
			    for (int j = 0; j < arr.length; j++)
			    {
			    	char c = (char)arr[j];
			    	 String s = dictionary.get(c);
			    	if (s == null)
			    		s = dictionary.get((char)(c + 256));
			    	//writer.write(s);
			    	if (code == null)
			    		code = s;
			    	else
			    		code +=s;
			    }
			    for (int i = 0; i < code.length(); i += 8)
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
			    	byte [] b = code.substring(i,i+8).getBytes();
			    	String temp = new String(b,"Ascii");
			    	int new_val =  Integer.parseInt(temp,2);
			    	writer.write((char)new_val);
			    }
			} 
			catch (IOException e) {  e.printStackTrace();}
			finally
			{
			   try
			   {writer.close();} catch (Exception e) {e.printStackTrace();}
			}
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void Decompress(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
