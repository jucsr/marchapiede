package br.UFSC.GRIMA.IO;

import java.sql.Connection;

public class DataBaseService {
	private IOControl ioControl;
	private Connection connection;
	private String DBip;
	
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getDBip() {
		return DBip;
	}
	public void setDBip(String dBip) {
		DBip = dBip;
	}

}
