package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JOptionPane;

public class ClienteTemp {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket cliente = new Socket("150.162.27.130",12345);
		
		BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		String msg = entrada.readLine().toString();
		JOptionPane.showMessageDialog(null,"Data recebida do servidor:" + msg.toString());
		
		
		PrintStream saidaC = new PrintStream(cliente.getOutputStream());
		saidaC.flush();
		saidaC.println("Tchau");
		saidaC.close();
		entrada.close();
		
		System.out.println("Conexão encerrada");
	}

}
