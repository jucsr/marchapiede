package threads;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TrataClients extends Thread 
{
	public ServerSocket server;
	private Socket client;
	public TrataClients(ServerSocket server) throws SocketException
	{
		this.server = server;
		this.server.setSoTimeout(1000);
	}
	@SuppressWarnings("deprecation")
	public void run()
	{
		while(!this.isInterrupted())
		{
			conexao();
		}
		if(this.isInterrupted())
		{
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		}
	}
	
	
	public void conexao()
	{
			try 
			{			
				client = server.accept();
				System.out.println("Cliente Thread Conectado: "+ client.getInetAddress().getHostAddress());
				PrintStream saida = new PrintStream(client.getOutputStream());
				saida.flush();
				saida.println("ocupped");
				client.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
	            System.out.println(e.getMessage());
			}	       
	}	
}
