package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CharToBin {

	public static void main(String[] args) {
		//Convert chars to binary 
		/*
		char[] c={203,156,210,152};
		String str="";
		for(int i=0;i<c.length;i++){
		System.out.println((int)c[i]);
		str = str +Integer.toBinaryString(c[i]);}
		System.out.println(str);
		*/
		String str="";
		try {
			InputStream in = new FileInputStream("c:\\data\\1.txt");
			int data = in.read();
			while(data != -1) {
			  //do something with data...
				str=str+Integer.toBinaryString(data);
			  
			  data = in.read();}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
		String tstr="";
		
			tstr=str.substring(0, 7);
			int c=Integer.parseInt(tstr,2);
			
			System.out.println(c);
			System.out.print((char)c);
		
		
		
		
	}

}
