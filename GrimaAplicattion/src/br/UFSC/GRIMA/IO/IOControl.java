package br.UFSC.GRIMA.IO;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;

public class IOControl {
	private MainExecution controller;
	private ArrayList<Agent> agents;
	private ArrayList<ClientCamera> clientCameras;
	private LoadExecution loadExecution;
	private DataBaseService dataBaseService;
	
	public IOControl(MainExecution mainExecution) {
		setController(mainExecution);
		setAgents(new ArrayList<Agent>());
		setClientCameras(new ArrayList<ClientCamera>());
		setLoadExecution(new LoadExecution(this));
	}
///////////////////////////Methods/////////////////////////////////////////////////////////////
	public Agent getAgentByName(String name) {
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getAgentName().equals(name)) {
				return agents.get(i);
			}
		}
		return null;
	}
	public Agent getAgentByIP(String name) {
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getAgentIP().equals(name)) {
				return agents.get(i);
			}
		}
		return null;
	}
	public ClientCamera getClientByIP(String ip) {
		for(int i = 0; i < clientCameras.size(); i++) {
			if(clientCameras.get(i).getIp().equals(ip))
				return clientCameras.get(i);
		}
		return null;
	}
	public String addAgent (String name, String ip) {
		MTConnectDevicesType probeObject = loadExecution.probeRequest(ip);
		if (probeObject == null) 
			return loadExecution.getLastError();
		else {
			controller.setAddProgress(50);
			MTConnectStreamsType currentObject = loadExecution.currentRequest(ip);
			if (currentObject == null)
				return loadExecution.getLastError();
			else {
				controller.setAddProgress(90);
				agents.add(new Agent(currentObject, probeObject, name, ip, this));
				
			}
		}
		
		return null;
	}
	public boolean addClientCamera(String name) {
		if(getClientByIP(name) != null) {
			JOptionPane.showMessageDialog(null, "This ip is already added in the application.", "Error in IP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		clientCameras.add(new ClientCamera(name, this));
		if(clientCameras.get(clientCameras.size() - 1).getCameras() == null) {
			clientCameras.remove(clientCameras.size() - 1);
			return false;
		}
		return true;
	}
/////////////////////////////Getters and Setters////////////////////////////////////////////
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	public MainExecution getController() {
		return controller;
	}
	public void setController(MainExecution controller) {
		this.controller = controller;
	}
	public LoadExecution getLoadExecution() {
		return loadExecution;
	}
	public void setLoadExecution(LoadExecution loadExecution) {
		this.loadExecution = loadExecution;
	}
	public DataBaseService getDataBaseService() {
		return dataBaseService;
	}
	public void setDataBaseService(DataBaseService dataBaseService) {
		this.dataBaseService = dataBaseService;
	}
	public ArrayList<ClientCamera> getClientCameras() {
		return clientCameras;
	}
	public void setClientCameras(ArrayList<ClientCamera> clientCameras) {
		this.clientCameras = clientCameras;
	}
}
