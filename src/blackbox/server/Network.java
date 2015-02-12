package blackbox.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Network {
	private ServerSocket serversocket;
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private HeartBeat heartbeat;
	private String hostname;
	public static final int PORT = 6066;
	public static final int TIMEOUT = 5*1000;
	public static final String NOP = "NOP";

	public Network() throws SocketTimeoutException, SocketException,IOException{
		serversocket = new ServerSocket();
		serversocket.setReuseAddress(true);
		serversocket.bind(new InetSocketAddress(PORT));
		socket = serversocket.accept();
		System.out.println("Connected to "+ socket.getRemoteSocketAddress());
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		heartbeat = new HeartBeat();
		hostname = socket.getLocalAddress().getHostName();
		Thread startHeart = new Thread(heartbeat);
		startHeart.start();
	}
	public void send(String msg)throws IOException {
		out.flush();
		out.writeUTF(msg);
	}
	
	public String get()throws IOException, EOFException {
		return in.readUTF();
	}
	public boolean isAlive() {
		return heartbeat.isConnected();
	}
	public String getHostname() {
		return hostname;
	}
	public void close()throws IOException {
		socket.close();
		serversocket.close();
		
	}
	private class HeartBeat extends Thread{
		private boolean isAlive;
		private static final int FREQUENCY = 1*1000;
		public HeartBeat() {
			isAlive = true;
		}
		public boolean isConnected(){
			return isAlive;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					
						send(Network.NOP);
						isAlive = true;
						
				}
				catch(SocketException e) {
					isAlive = false;
					break;
				}
				catch(IOException e) {
					isAlive = false;
					break;
					//e.printStackTrace();
				}
				try {
					sleep(FREQUENCY);
				}
				catch(InterruptedException e) {
					//e.printStackTrace();
				}
				
			}
		}
	}
}
