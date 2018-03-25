package assign1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

		String org_text = null;
		String byte_val;
		int freq[] = new int [256];
		try {
			while ((org_text = br.readLine()) != null)
			{
				for (int i = 0; i < org_text.length();i += 8)
				{
					byte_val = org_text.substring(i, i+8);
					int ch = Integer.parseInt(byte_val, 2);
					freq[ch]++;
				}
				
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	


	HuffmanNode root = new HuffmanNode(0, '`');
	root = root.BuildTree(freq);
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
