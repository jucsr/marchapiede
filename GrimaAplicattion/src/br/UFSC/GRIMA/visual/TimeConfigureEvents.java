package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class TimeConfigureEvents extends TimeConfigureWindow implements ActionListener {
	private int[] timeRange;
	private JFrame ower;
	public TimeConfigureEvents(JFrame ower, int[] timeRange) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(hourField)) {
			
		}
	}
	
}
