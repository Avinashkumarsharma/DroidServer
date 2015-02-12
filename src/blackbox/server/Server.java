package blackbox.server;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

public class Server extends Thread {
	private ServerBlackBox server;
	private Blackbox serverGui;
	private boolean running;
	private Mouse mouse;
	private Keyboard keyboard;
	public Server(Blackbox serverGui) {
		this.serverGui = serverGui; 
		mouse = new Mouse();
		keyboard = new Keyboard();
		running = false;
		try {
			server = new ServerBlackBox();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void setRunning(boolean value){
		running = value;
	}
	public boolean isConnected(){
		boolean status;
		try{
			status =  server.isConnected();
		}
		catch(NullPointerException e){
			return false;
		}
		return status;
	}
	
	public void close(){
		running = false;
		try {
			server.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		serverGui.setStatus(Blackbox.DISCONNECTED);
	}
	@Override
	public void run() {
		try{
			serverGui.setStatus(Blackbox.WAITING);
			serverGui.setButtonEnabled(false);
			server.connect();
			serverGui.setButtonEnabled(true);
			serverGui.setButtonText(Blackbox.STOP);
			serverGui.setStatus(Blackbox.CONNECTED);
		}
		catch(SocketTimeoutException e){
			running = false;
			serverGui.resetButton();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		while(running) {
			try{
				String msg = server.get();
				if(msg.trim().equalsIgnoreCase(Mouse.RIGHT_CLICK+"")){
					System.out.println("RIGHT CLICK");
					mouse.rightClick();
				}
				else if(msg.trim().equalsIgnoreCase(Mouse.LEFT_CLICK+"")){
					System.out.println("LEFT CLICK");
					mouse.leftClick();
					mouse.releaseLeftClick();
				}
				if(msg != null && msg.length() > 0) {
					try {
						
						int keycode = Integer.parseInt(msg);
						keyboard.press(keycode);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				msg = "";
				
			}catch(SocketException e){
				close();
				break;
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
		System.out.println("Exiting Server");
	}
}
