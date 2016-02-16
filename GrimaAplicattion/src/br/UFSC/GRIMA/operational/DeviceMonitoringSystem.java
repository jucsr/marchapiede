package br.UFSC.GRIMA.operational;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jfree.data.time.Millisecond;

import br.UFSC.GRIMA.IO.Camera;
import br.UFSC.GRIMA.IO.MainExecution;
import br.UFSC.GRIMA.dataStructure.Component;
import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;

public class DeviceMonitoringSystem {
	private MainExecution controller;
	private int[] timeRange;
	private boolean pause;
	private Device selectedDevice;
	private ArrayList<Component>selectedComponents;
	private ArrayList<Variable>selectedVariables;
	private Camera selectedCamera;
	////////////FrameComponents///
	private JCheckBox deviceCheckBox;
	private JCheckBox cameraCheckbox;
	private JToggleButton playPause;
///////////////////////////////////Constructor///////////////////////////////////////////////////////////////////
	public DeviceMonitoringSystem(MainExecution mainExecution) {
		setController(mainExecution);
		setTimeRange(mainExecution.getDefaultTimeRange());
		setPause(false);
		setSelectedComponents(new ArrayList<Component>());
		setSelectedVariables(new ArrayList<Variable>());
	}
//////////////////////////////////Getters and Setters////////////////////////////////////////////////////////////
	public MainExecution getController() {
		return controller;
	}
	public void setController(MainExecution controller) {
		this.controller = controller;
	}
	
	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public ArrayList<Variable> getSelectedVariables() {
		return selectedVariables;
	}

	public void setSelectedVariables(ArrayList<Variable> selectedVariables) {
		this.selectedVariables = selectedVariables;
	}

	public ArrayList<Component> getSelectedComponents() {
		return selectedComponents;
	}

	public void setSelectedComponents(ArrayList<Component> selectedComponents) {
		this.selectedComponents = selectedComponents;
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public Camera getSelectedCamera() {
		return selectedCamera;
	}

	public void setSelectedCamera(Camera selectedCamera, JPanel container) {///////////////reformar apos setar os bagulho das camera
		if(this.selectedCamera != null)
			this.selectedCamera.destroyVideoPanel();
		this.selectedCamera = selectedCamera;
		if(selectedCamera != null) {
			selectedCamera.adjustVideoPanel();
			container.add(selectedCamera.getVideoPanel(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
		}
	}
	public void setSelectedCamera(Camera selectedCamera) {///////////////reformar apos setar os bagulho das camera
		this.selectedCamera = selectedCamera;
	}

	public int[] getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(int[] timeRange) {
		this.timeRange = timeRange;
	}
	public JCheckBox getCameraCheckbox() {
		return cameraCheckbox;
	}
	public void setCameraCheckbox(JCheckBox cameraCheckbox) {
		this.cameraCheckbox = cameraCheckbox;
	}
	public JToggleButton getPlayPause() {
		return playPause;
	}
	public void setPlayPause(JToggleButton playPause) {
		this.playPause = playPause;
	}
	public JCheckBox getDeviceCheckBox() {
		return deviceCheckBox;
	}
	public void setDeviceCheckBox(JCheckBox deviceCheckBox) {
		this.deviceCheckBox = deviceCheckBox;
	}
	
}
