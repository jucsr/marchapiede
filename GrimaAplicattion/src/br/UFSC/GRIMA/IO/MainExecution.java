package br.UFSC.GRIMA.IO;


import java.util.ArrayList;

import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.operational.DeviceMonitoringSystem;
import br.UFSC.GRIMA.operational.PanelMonitoringSystem;
import br.UFSC.GRIMA.visual.MainInterface;

import javax.swing.JApplet;

public class MainExecution extends JApplet{
	private MainInterface mainInterface;
	private DeviceMonitoringSystem deviceMonitoringSystem;
	private PanelMonitoringSystem panelMonitoringSystem;
	private IOControl ioControl;
	private int[] defaultTimeRange;
	private ArrayList<Variable> newMonitoringPanelList;
	private int addProgress;
	
	
///////////////////////////Constructor//////////////////////////////////////////////////////////	
	public MainExecution() {
		int[] range = {1, 0, 0}; 
		setDefaultTimeRange(range);;
		setDeviceMonitoringSystem(new DeviceMonitoringSystem(this));
		setPanelMonitoringSystem(new PanelMonitoringSystem(this));
		setMainInterface(new MainInterface(this));
		setIoControl(new IOControl(this));
		setNewMonitoringPanelList(new ArrayList<Variable>());
	}
////////////////////////////////////Methods/////////////////////////////////////////////////////////////
	public String addAgent (String ip) {
		setAddProgress(5);
		if (ioControl.getAgentByIP(ip) != null)
			return (new String("The ip given is already in use in another Agent in the Application."));
		setAddProgress(10);
		String  str =  ioControl.addAgent(ip);
		mainInterface.setMenuDeviceMonitor();
		return str;
	}
	public ArrayList<Device> getAllDevices() {
		ArrayList<Device> devices = new ArrayList<Device>();
		for(int i = 0; i < ioControl.getAgents().size(); i++) {
			for (int j = 0; j < ioControl.getAgents().get(i).getDevices().size(); j++) {
				devices.add(ioControl.getAgents().get(i).getDevices().get(j));
			}
		}
		return devices;
	}
	public ArrayList<Camera> getAllCameras() {
		ArrayList<Camera> cameras = new ArrayList<Camera>();
		for(int i = 0; i < ioControl.getClientCameras().size(); i++) {
			for(int j = 0; j < ioControl.getClientCameras().get(i).getCameras().size(); j++) 
				cameras.add(ioControl.getClientCameras().get(i).getCameras().get(j));
		}
		return cameras;
	}
	public ArrayList<Agent> getAllAgents() {
		return ioControl.getAgents();
	}
	public boolean addClientCamera(String ip) {
		return ioControl.addClientCamera(ip);
	}
	
////////////////////Getters and Setters/////////////////////////////////////////////////////
	public IOControl getIoControl() {
		return ioControl;
	}

	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}

	public MainInterface getMainInterface() {
		return mainInterface;
	}

	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}

	public DeviceMonitoringSystem getDeviceMonitoringSystem() {
		return deviceMonitoringSystem;
	}

	public void setDeviceMonitoringSystem(DeviceMonitoringSystem deviceMonitoringSystem) {
		this.deviceMonitoringSystem = deviceMonitoringSystem;
	}

	public PanelMonitoringSystem getPanelMonitoringSystem() {
		return panelMonitoringSystem;
	}

	public void setPanelMonitoringSystem(PanelMonitoringSystem panelMonitoringSystem) {
		this.panelMonitoringSystem = panelMonitoringSystem;
	}
	
	public ArrayList<Variable> getNewMonitoringPanelList() {
		return newMonitoringPanelList;
	}
	public void setNewMonitoringPanelList(ArrayList<Variable> newMonitoringPanelList) {
		this.newMonitoringPanelList = newMonitoringPanelList;
	}
	public int[] getDefaultTimeRange() {
		return defaultTimeRange;
	}
	public void setDefaultTimeRange(int[] defaultTimeRange) {
		this.defaultTimeRange = defaultTimeRange;
	}
	public int getAddProgress() {
		return addProgress;
	}
	public void setAddProgress(int addProgress) {
		this.addProgress = addProgress;
	}
	
}
