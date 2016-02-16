package br.UFSC.GRIMA.IO;

import java.util.ArrayList;

public class SaveExecution implements Runnable {
	private DataBaseService dataBaseService;
	private LoadExecution loadExecution;
	private ArrayList<String> buffer;
	private long loopTime;
	private Thread thread;
////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public SaveExecution (LoadExecution loadExecution) {
		setLoadExecution(loadExecution);
		setBuffer(new ArrayList<String>());
		setThread(new Thread(this, "Load Execution"));
		setLoopTime(System.currentTimeMillis());
		thread.start();
	}
///////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////
	public DataBaseService getDataBaseService() {
		return dataBaseService;
	}
	public void setDataBaseService(DataBaseService dataBaseService) {
		this.dataBaseService = dataBaseService;
	}
	public LoadExecution getLoadExecution() {
		return loadExecution;
	}
	public void setLoadExecution(LoadExecution loadExecution) {
		this.loadExecution = loadExecution;
	}
	public ArrayList<String> getBuffer() {
		return buffer;
	}
	public void setBuffer(ArrayList<String> buffer) {
		this.buffer = buffer;
	}
	public long getLoopTime() {
		return loopTime;
	}
	public void setLoopTime(long loopTime) {
		this.loopTime = loopTime;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
////////////////////////Run Tasks/////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if ((getLoopTime() - System.currentTimeMillis()) < 800) {
				try {
				Thread.sleep(800);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
			setLoopTime(System.currentTimeMillis());
		}
	}
	

}
