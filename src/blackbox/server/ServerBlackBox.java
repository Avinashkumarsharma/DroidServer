package blackbox.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerBlackBox {
	private ServerSocket serverSocket;
	private Socket server;
	private String host;
	DataOutputStream out;
	DataInputStream in;
	final static int PORT = 6066;
	final static int TIMEOUT = 60000;
	
	
	public ServerBlackBox()throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(TIMEOUT);
	}
	
	
	public boolean connect()throws SocketTimeoutException, IOException {
			System.out.println("Waiting for client on port " +
		            serverSocket.getLocalPort() + "...");
			if(!serverSocket.isBound()){
			}
			if(server == null || !isConnected())
			server = serverSocket.accept();
			server.setKeepAlive(true);
			System.out.println("Connected to "+ server.getRemoteSocketAddress());
			in = new DataInputStream(server.getInputStream());
			out = new DataOutputStream(server.getOutputStream());
			host = server.getLocalAddress().getHostName();
		return server.isConnected();
	}
	
	public String getHostName(){
		return host;
	}
	public boolean isOpened(){
		return !server.isClosed();
	}
	
	public void close()throws IOException{
		//serverSocket.close();
		server.close();
	}
	
	public boolean isConnected(){
		boolean status;
		try{
			 status = server.isClosed();
		}
		catch(NullPointerException e){
			return false;
		}
		return !status;
	}
	public String get()throws IOException{
		String msg = "";
		try{
			msg = in.readUTF();
		}
		catch(EOFException e){
			return null;
		}
		
		return msg;
	}
	public void send(String msg)throws IOException{
		out.flush();
		out.writeUTF(msg);
	}
}
