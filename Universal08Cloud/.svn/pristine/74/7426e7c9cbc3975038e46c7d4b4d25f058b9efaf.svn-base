package br.UFSC.GRIMA.util;

import java.net.*;
import java.io.*;

public class MTConnectHelloWorld
{
	public static void main(String [] args) throws Exception
	{
//		System.out.println("Your Fisrt Hello World Program" + "\n");
		
//		URL MTConnect = new URL("http://agent.MTConnect.org/probe");
		URL MTConnect = new URL("http://agent.mtconnect.org/current?path=//Axes//Linear//DataItem[@subType='ACTUAL']");
		BufferedReader in = new BufferedReader(new InputStreamReader(MTConnect.openStream()));
		String inputLine;
		while((inputLine = in.readLine()) != null)
		{
			System.out.println(inputLine);
		}
			in.close();
//			System.out.println("\n" + "XML from probe command printed above");
	}
}
