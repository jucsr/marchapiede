package br.UFSC.GRIMA.IO;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;

public class CurrentThread implements Runnable {
	private boolean terminated;
	private Agent target;
	private LoadExecution loadExecution;
	private Thread thread;
	private MTConnectStreamsType result;
//////////////////////////constructor///////////////////////////////////////////////
	public CurrentThread (Agent target, LoadExecution loadExecution) {
		setTerminated(false);
		setTarget(target);
		setLoadExecution(loadExecution);
		setThread(new Thread(this, "Current Thread"));
		thread.start();
	}
/////////////////////////Getters and Setters////////////////////////////////////////
	public boolean isTerminated() {
		return terminated;
	}
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	public Agent getTarget() {
		return target;
	}
	public void setTarget(Agent target) {
		this.target = target;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public MTConnectStreamsType getResult() {
		return result;
	}
	public void setResult(MTConnectStreamsType result) {
		this.result = result;
	}
	public LoadExecution getLoadExecution() {
		return loadExecution;
	}
	public void setLoadExecution(LoadExecution loadExecution) {
		this.loadExecution = loadExecution;
	}

//////////////////////run Tasks//////////////////////////////////////////////////////////	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try	{
			JAXBContext jc = JAXBContext.newInstance(MTConnectStreamsType.class);
			Unmarshaller u = jc.createUnmarshaller();
			URL url = new URL(target.getAgentIP() + "/current" );
			JAXBElement<MTConnectStreamsType> element =(JAXBElement<MTConnectStreamsType>)u.unmarshal(url);
			setResult(element.getValue());
			setTerminated(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			setTerminated(true);
		}
	}
	
}
