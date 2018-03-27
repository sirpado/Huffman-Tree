package assign1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
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
		
		File file = new File(input_names[0]);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Writer writer = null;
		String org_text = null;
		String text = null;
		
		int freq[] = new int [256];
		try {
			while ((org_text = br.readLine()) != null)
			{
				org_text = org_text.replaceAll("\\s+", "");
				if (text == null)
					text = org_text;
				else
					text = text + org_text;
			}
			byte [] arr = new BigInteger(text,2).toByteArray();
			for (int i = 0; i < arr.length; i++)
			{
				freq[(arr[i])] ++;
			}
			
			HuffmanNode root = HuffmanNode.BuildTree(freq);
			
			HashMap <Character,String > dictionery = new HashMap<Character,String>();
			root.buildDictionery(dictionery,"");
			
			try 
			{
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(output_names[1]), "utf-8"));
			    for (int j = 0; j < arr.length; j++)
			    {
			    	char c = (char)arr[j];
			    	String s = dictionery.get(c);
			    	writer.write(s);
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
