package br.UFSC.GRIMA.visual;

import java.awt.Frame;

public class WaitEvents extends WaitWindow {
	
	public WaitEvents(Frame owner, String msg) {
		super(owner);
		textProgress.setText(msg);
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}
	
//	public static void main(String args[])
//	{
//		WaitEvents w = new WaitEvents(null, "marcio");
//	}
}
