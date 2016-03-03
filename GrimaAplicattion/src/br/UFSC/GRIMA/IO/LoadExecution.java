package br.UFSC.GRIMA.IO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;
import br.UFSC.GRIMA.dataStructure.*;

public class LoadExecution implements Runnable {
	private IOControl ioControl;
	private ArrayList<Variable> variableList;
	private ArrayList<Variable> dataSaveList;
	private ArrayList<Agent> agentList;
	private SaveExecution saveExecution;
	private Thread thread;
	private long loopTime;
	private String lastError;
	private boolean connected;
////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public LoadExecution(IOControl ioControl) {
		setIoControl(ioControl);
		setVariableList(new ArrayList<Variable>());
		setDataSaveList(new ArrayList<Variable>());
		setAgentList(new ArrayList<Agent>());
		setThread(new Thread(this, "Load Execution"));
		setLoopTime(System.currentTimeMillis());
		thread.start();
		
		setSaveExecution(new SaveExecution(this));	
	}
/////////////////////////Methods//////////////////////////////////////////////////////////////////////////
	private JAXBElement<MTConnectDevicesType> extracted(Unmarshaller u, URL url) throws JAXBException 
	{
		return (JAXBElement<MTConnectDevicesType>)u.unmarshal(url);
	}
	public MTConnectDevicesType probeRequest(String ip) {
		try {
			JAXBContext jc = JAXBContext.newInstance(MTConnectDevicesType.class);
			Unmarshaller u = jc.createUnmarshaller();
			URL url = new URL(ip + "/probe");
			JAXBElement<MTConnectDevicesType> element = extracted(u, url);
			return element.getValue();
		}
		catch (Exception e) {
			setLastError(new String(e.toString()));
			return null;
		}
	}
	public MTConnectStreamsType currentRequest(String ip) {
		try	{
			JAXBContext jc = JAXBContext.newInstance(MTConnectStreamsType.class);
			Unmarshaller u = jc.createUnmarshaller();
			URL url = new URL(ip + "/current" );
			JAXBElement<MTConnectStreamsType> element =(JAXBElement<MTConnectStreamsType>)u.unmarshal(url);
			return element.getValue();
		}
		catch (Exception e) {
			setLastError(new String(e.toString()));
			return null;
		}
	}
	public void addToVariableList(Variable variable) {
		if (!agentList.contains(variable.getComponent().getDevice().getAgent())) 
			agentList.add(variable.getComponent().getDevice().getAgent());
		if (!variableList.contains(variable)) {
			if (variable.getDataSerie() == null) {
				if(variable.getName() != null) 
					variable.setDataSerie(new TimeSeries(variable.getName()));
				else
					variable.setDataSerie(new TimeSeries(variable.getDataItemID()));
			}
			variable.getDataSerie().addChangeListener(variable);
			variable.getDataSerie().setNotify(false);
			variableList.add(variable);
		}
	}
	public boolean removeFromVariableList(Variable variable, boolean notify) { ///////////////////////////lembrar de adicionar o valor nulo no final da lista
		if (ioControl.getController().getPanelMonitoringSystem().isVariableMonitored(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The variable is currently used in one of the created Monitoring Panels, to remove from the monitoring you need to destroy the panels.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (ioControl.getController().getDeviceMonitoringSystem().getSelectedVariables().contains(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The variable is currently added to the Device Monitoring Segment, please remove it from this segment first.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (dataSaveList.contains(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The system is saving this variable in the current DataBase, please remove the variable from that list first.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			variableList.remove(variable);
			boolean agentUsedInOtherVariable = false;
			for(int i = 0; i < variableList.size(); i++) {
				if(variableList.get(i).getComponent().getDevice().getAgent().equals(variable.getComponent().getDevice().getAgent())) {
					agentUsedInOtherVariable = true;
				}
			}
			if(!agentUsedInOtherVariable) 
				agentList.remove(variable.getComponent().getDevice().getAgent());
			return true;
		}
	}
	public boolean addToSaveList(Variable variable, boolean notify) {
		if(saveExecution.getDataBaseService() != null) {
			dataSaveList.add(variable);
			return true;
		}
		else {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The system has no DataBase repository logged to save the data.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public void removeFromSaveList(Variable variable) {
		dataSaveList.remove(variable);
	}
///////////////////////Getters and Setters///////////////////////////////////////////////////////////////
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
	public ArrayList<Variable> getDataSaveList() {
		return dataSaveList;
	}
	public void setDataSaveList(ArrayList<Variable> dataSaveList) {
		this.dataSaveList = dataSaveList;
	}
	public ArrayList<Variable> getVariableList() {
		return variableList;
	}
	public void setVariableList(ArrayList<Variable> variableList) {
		this.variableList = variableList;
	}
	public ArrayList<Agent> getAgentList() {
		return agentList;
	}
	public void setAgentList(ArrayList<Agent> agentList) {
		this.agentList = agentList;
	}
	public SaveExecution getSaveExecution() {
		return saveExecution;
	}
	public void setSaveExecution(SaveExecution saveExecution) {
		this.saveExecution = saveExecution;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public long getLoopTime() {
		return loopTime;
	}
	public void setLoopTime(long loopTime) {
		this.loopTime = loopTime;
	}
	public String getLastError() {
		return lastError;
	}
	public void setLastError(String lastError) {
		this.lastError = lastError;
	}
////////////////////////Run Tasks/////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (!variableList.isEmpty()) {
				ArrayList<CurrentThread> threads = new ArrayList<CurrentThread>();
				for (int i = 0; i < agentList.size(); i++) {
					threads.add(new CurrentThread(agentList.get(i), this));
				}
				boolean terminated = false;
				while (!terminated) {
					terminated = true;
					for(int i = 0; i < threads.size(); i++) 
						terminated = terminated && threads.get(i).isTerminated();
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {}
				}
				try {
					Millisecond referenceTime = new Millisecond(threads.get(0).getResult().getHeader().getCreationTime().toGregorianCalendar().getTime());
					ioControl.getController().getMainInterface().setCurrentTime(referenceTime.toString());
				}catch(Exception e){}
				for(int i = 0; i < threads.size(); i++) {
					if(threads.get(i).getResult() != null)
						threads.get(i).getTarget().setCreationTime(threads.get(i).getResult().getHeader().getCreationTime());
				}
				for(int i = 0; i < variableList.size(); i++) {
					MTConnectStreamsType currentObject = null;
					for (int j = 0; j < threads.size(); j++) {
						if (variableList.get(i).getComponent().getDevice().getAgent().equals(threads.get(j).getTarget())) {
							currentObject = threads.get(j).getResult();
							break;
						}
					}
					///////criar mecanismo que informa erro de comunicacao, cuidar para fazer isso para o agente e nao para cada agente da variavel na lista
					if (currentObject != null) {
						String value = null;
						XMLGregorianCalendar time = null;
						int devPosition = variableList.get(i).getComponent().getDevice().getAgentPosition();
						int comPosition = variableList.get(i).getComponent().getAgentPosition();
						int varPosition = variableList.get(i).getAgentPosition();
						if (variableList.get(i).getDivision() == 'S') {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getSamples().getSample().get(varPosition).getValue().getValue();
							time  = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getSamples().getSample().get(varPosition).getValue().getTimestamp();
						}
						else if (variableList.get(i).getDivision() == 'E') {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getEvents().getEvent().get(varPosition).getValue().getValue();
							time = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getEvents().getEvent().get(varPosition).getValue().getTimestamp();
						}
						else {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getCondition().getCondition().get(varPosition).getName().getLocalPart();
							time = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getCondition().getCondition().get(varPosition).getValue().getTimestamp();
						}
						time.setTimezone(currentObject.getHeader().getCreationTime().getTimezone());
						XMLGregorianCalendar iniTime = (XMLGregorianCalendar) currentObject.getHeader().getCreationTime().clone();
						int second = iniTime.getSecond() - variableList.get(i).getTimeRange()[2];
						int minute = iniTime.getMinute() - variableList.get(i).getTimeRange()[1];
						int hour = iniTime.getHour() - variableList.get(i).getTimeRange()[0];
						int day = iniTime.getDay();
						int month = iniTime.getMonth();
						int year = iniTime.getYear();
						if (second < 0) {
							second = second + 60;
							minute--;
						}
						if (minute < 0) {
							minute = minute + 60;
							hour--;
						}
						if (hour < 0) {
							hour = hour + 24;
							day--;
						}
						if (day < 1) {
							XMLGregorianCalendar correction = iniTime;
							try {
								correction.setMonth(iniTime.getMonth() - 1);
							}
							catch (IllegalArgumentException e) {
								correction.setYear(iniTime.getYear() - 1);
								correction.setMonth(1);
							}
							day = day + correction.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
							month--;
						}
						if (month < 1) {
							month = month + 12;
							year--;
						}
						Millisecond creationTime = new Millisecond(iniTime.toGregorianCalendar().getTime());    ////////////////////creationTime
						iniTime.setTime(hour, minute, second);
						iniTime.setDay(day);
						iniTime.setMonth(month);
						iniTime.setYear(year);
						Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());     /////////////////////timeStamp init time value
						Millisecond timestamp = new Millisecond(time.toGregorianCalendar().getTime());			/////////////////////register time value
						boolean tolerable = (Math.abs(timestamp.getFirstMillisecond() - creationTime.getFirstMillisecond()) <= 1000);
						if(variableList.get(i).getType() == 'n') {
							if(!value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).typeIdentification(value);
							}
						}
						if(variableList.get(i).getType() == '1') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().addOrUpdate(creationTime, null);
							}
							else {
								try {
									double numValue = ((Double)(Double.parseDouble(value.replace(',', '.')))).doubleValue();
									if(variableList.get(i).getDataSerie().isEmpty()) {
										if((timestamp.compareTo(inicialTime) > 0) && (timestamp.compareTo(creationTime) < 0))
											variableList.get(i).getDataSerie().addOrUpdate(timestamp, numValue);
										else if(timestamp.compareTo(inicialTime) < 0) {
											variableList.get(i).getDataSerie().addOrUpdate(inicialTime, numValue);
										}
										variableList.get(i).getDataSerie().setNotify(true);
										variableList.get(i).getDataSerie().setNotify(false);
									}
									if(tolerable) 
										variableList.get(i).getDataSerie().addOrUpdate(timestamp, numValue);
									else
										variableList.get(i).getDataSerie().addOrUpdate(creationTime, numValue);
								}
								catch (Exception e) {
									variableList.get(i).setVariableToIrregular();
									e.printStackTrace();
								}
							}
						}
						else if(variableList.get(i).getType() == 'c') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().addOrUpdate(creationTime, null);
							}
							else  {
								if(variableList.get(i).getDataSerie().isEmpty()) {
									if((timestamp.compareTo(inicialTime) > 0) && (timestamp.compareTo(creationTime) < 0))
										variableList.get(i).getDataSerie().addOrUpdate(timestamp, variableList.get(i).getCategoryPosition(value));
									else if(timestamp.compareTo(inicialTime) < 0) {
										variableList.get(i).getDataSerie().addOrUpdate(inicialTime, variableList.get(i).getCategoryPosition(value));
									}
									variableList.get(i).getDataSerie().setNotify(true);
									variableList.get(i).getDataSerie().setNotify(false);
								}
								if(tolerable)
									variableList.get(i).getDataSerie().addOrUpdate(timestamp, variableList.get(i).getCategoryPosition(value));
								else
									variableList.get(i).getDataSerie().addOrUpdate(creationTime, variableList.get(i).getCategoryPosition(value));
								if (variableList.get(i).getCategoryStrings().size() > 10) {
									variableList.get(i).setVariableToIrregular();
								}
							}
						}
						else if(variableList.get(i).getType() == 'i') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().clear();
								variableList.get(i).getDataSerie().add(creationTime, 0);
								variableList.get(i).getCategoryStrings().clear();
								variableList.get(i).getCategoryStrings().add(null);
							}
							else {
								variableList.get(i).getDataSerie().clear();
								variableList.get(i).getDataSerie().add(creationTime, 0);
								variableList.get(i).getCategoryStrings().clear();
								variableList.get(i).getCategoryStrings().add(value);
							}
						}
						///////////////Discart medium value/////////////////
						TimeSeries serie = variableList.get(i).getDataSerie();
						if (serie.getItemCount() > 3) {
							if((serie.getValue(serie.getItemCount() - 1) == null)&&(serie.getValue(serie.getItemCount() - 2) == null)&&(serie.getValue(serie.getItemCount() - 3) == null) && (serie.getValue(serie.getItemCount() - 4) == null))
								serie.delete(serie.getItemCount()-2, serie.getItemCount()-2); /// deleta o penúltimo registro
							else if (serie.getValue(serie.getItemCount() - 1) != null) {
								if (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 2)) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 3))) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 4))))
									serie.delete(serie.getItemCount()-2, serie.getItemCount()-2); /// deleta o penúltimo registro
							}
						}
						//////////////discart old values////////////////////////
						if (((variableList.get(i).getType() == '1') || (variableList.get(i).getType() == 'c'))&& (serie.getItemCount() > 1)) {
							for (int j = 0; j < serie.getItemCount() - 1;j++) {
								if (inicialTime.compareTo(serie.getTimePeriod(j)) <= 0) {
									break;
								}
								else if (inicialTime.compareTo(serie.getTimePeriod(j+1)) < 0) {
									if ((variableList.get(i).getType() == '1') && (serie.getValue(j) != null) && (serie.getValue(j + 1) != null)) {
										//faz uma aproximacao linear em t0
										double y1 = serie.getValue(j).doubleValue();
										double y2 = serie.getValue(j+1).doubleValue();
										long x0 = inicialTime.getLastMillisecond();
										long x1 = serie.getTimePeriod(j).getLastMillisecond();
										long x2 = serie.getTimePeriod(j+1).getLastMillisecond();
										double a =  (double) ((y2 - y1 )/(x2 - x1));
										double b = (double) (y1 - a*x1);
										double yn = a*x0 + b;
										serie.addOrUpdate(inicialTime, yn);
									}
									else {
										serie.addOrUpdate(inicialTime, serie.getValue(j));
									}
									serie.delete(0, j);
								}
							}
							//////////////////////ajusta valores na categoryStrings
							if (variableList.get(i).getType() == 'c') {
								boolean[] existence = new boolean[variableList.get(i).getCategoryStrings().size()];
								for (int j = 0; j < variableList.get(i).getDataSerie().getItemCount(); j++) {
									existence[variableList.get(i).getDataSerie().getValue(j).intValue()] = true;
								}
								boolean noChange = true;
								for (int j = 0; j < existence.length; j++) {
									noChange = noChange && existence[j];
								}
								if (!noChange) {
									int[] correction = new int[existence.length];
									ArrayList<String>newCategoryStrings = new ArrayList<String>();
									correction[0] = 0;
									for(int j = 0; j < existence.length; j++) {
										if (j != 0)
											correction[j] = correction[j - 1];
										if(existence[j] == true) 
											newCategoryStrings.add(variableList.get(i).getCategoryStrings().get(j));
										else {
											correction[j]++;
										}
											
									}
									variableList.get(i).setCategoryStrings(newCategoryStrings);
									for(int j = 0; j < variableList.get(i).getDataSerie().getItemCount(); j++) 
										variableList.get(i).getDataSerie().update(j, variableList.get(i).getDataSerie().getValue(j).intValue() - correction[variableList.get(i).getDataSerie().getValue(j).intValue()]);
								}
							}
						}
						variableList.get(i).getDataSerie().setNotify(true);
						variableList.get(i).getDataSerie().setNotify(false);
					}
					else {
						variableList.get(i).getDataSerie().addOrUpdate(new Millisecond(variableList.get(i).getComponent().getDevice().getAgent().getCreationTime().toGregorianCalendar().getTime()).next(), null);
					}
				}
			}
			long loop = System.currentTimeMillis() - getLoopTime();
			ioControl.getController().getMainInterface().setLoopTime(loop);
			if (loop < 200) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setLoopTime(System.currentTimeMillis());
		}
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	
}
