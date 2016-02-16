package br.UFSC.GRIMA.IO;

import java.util.ArrayList;

import javax.xml.datatype.XMLGregorianCalendar;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;
import br.UFSC.GRIMA.dataStructure.Device;

public class Agent {
	private String agentName;
	private String agentIP;
	private long bufferSize;
	private XMLGregorianCalendar creationTime;
	private ArrayList<Device> devices;
	private IOControl ioControl;
	private ArrayList<Camera> cameras;
///////////////////////////Constructor///////////////////////////////////////////////////////////
	public Agent(MTConnectStreamsType currentObject, MTConnectDevicesType probeObject, String name, String ip, IOControl ioControl) {
		setAgentIP(ip);
		setAgentName(name);
		setBufferSize(currentObject.getHeader().getBufferSize());
		setCreationTime(currentObject.getHeader().getCreationTime());
		setIoControl(ioControl);
		setCameras(new ArrayList<Camera>());
		setDevices(new ArrayList<Device>());
		for(int i = 0; i < currentObject.getStreams().getDeviceStream().size(); i++) {
			devices.add(new Device(currentObject.getStreams().getDeviceStream().get(i), probeObject.getDevices().getDevice().get(i), i, this));
		}
	}
////////////////////////////Methods//////////////////////////////////////////////////////////////////////
	public Device getDeviceByName(String name) {
		for (int i = 0; i < devices.size(); i++) {
			if (devices.get(i).getName().equals(name)) 
				return devices.get(i);
		}
		return null;
	}
////////////////////////Getters and Setters/////////////////////////////////////////////////////////////
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentIP() {
		return agentIP;
	}
	public void setAgentIP(String agentIP) {
		this.agentIP = agentIP;
	}
	public long getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(long bufferSize) {
		this.bufferSize = bufferSize;
	}
	public ArrayList<Device> getDevices() {
		return devices;
	}
	public void setDevices(ArrayList<Device> devices) {
		this.devices = devices;
	}
	public XMLGregorianCalendar getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(XMLGregorianCalendar creationTime) {
		this.creationTime = creationTime;
	}
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
	public ArrayList<Camera> getCameras() {
		return cameras;
	}
	public void setCameras(ArrayList<Camera> cameras) {
		this.cameras = cameras;
	}
}
