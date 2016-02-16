package br.UFSC.GRIMA.IO;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClientCamera {
	private String ip;
	private ArrayList<Camera> cameras;
	private IOControl ioControl;
/////////////////////////////////////Constructor//////////////////////////////////////////////////////////////
	public ClientCamera(String ip, IOControl ioControl) {
		setIp(ip);
		setIoControl(ioControl);
		setCameras(new ArrayList<Camera>());
		Socket socket;
		try {
			socket = new Socket(ip, 12345);
			Scanner scanner = new Scanner(socket.getInputStream());
			int porta = 20000;
			while (scanner.hasNextLine()) {
				cameras.add(new Camera(scanner.nextLine(), porta, this));
				porta = porta - 10;
			}
			cameras.remove(cameras.size() - 1);
			scanner.close();
			socket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Was not possible the connection with the WEBCAM server!", "Error", JOptionPane.ERROR_MESSAGE);
			setCameras(null);
		}
	}
//////////////////////////////////////Getters and Setters///////////////////////////////////////////////////////////
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public ArrayList<Camera> getCameras() {
		return cameras;
	}
	public void setCameras(ArrayList<Camera> cameras) {
		this.cameras = cameras;
	}
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
}
