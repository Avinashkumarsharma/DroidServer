package blackbox.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class EngineDroid {
	private Blackbox gui;
	private Network link;
	private Keyboard keyboard;
	private Engine engine;
	public static final String CONNECTED = "Connected";
	public static final String DISCONNECTED = "Waiting for App to connect";
	public static final String RETRYING = "Retrying to connect";
	public static final String WAITING = "Waiting..";
	public static final String START = "START";
	public static final String STOP = "STOP";
	public EngineDroid(Blackbox gui) {
		this.gui = gui;
		keyboard = new Keyboard();
		link = null;
	}
	public final Network getLink(){
		return link;
	}
	public boolean isRunning(){
		try {
			return engine.isRunnig();
		}
		catch(NullPointerException e) {
			return false;
		}
	}
	public void stop() {
		if(link != null) {
			try {
				link.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void start() {
		engine = new Engine();
		engine.setRunning(true);
		Thread startEngine = new Thread(engine);
		startEngine.start();
	}
	
		class Engine extends Thread {
		private boolean running;
		public Engine() {
			running = false;
		}
		public void setRunning(boolean value) {
			running = value;
		}
		private void close() {
			System.out.println("Closing");
			gui.setStatus(DISCONNECTED);
			if(link != null) {
				try {
					link.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			link = null;
		}
		public boolean isRunnig() {
			return running;
		}
		@Override
		public void run() {
			gui.setStatus(DISCONNECTED);
			while(running) {
				try {
					if(link == null || !link.isAlive()) {
						gui.setButtonText(WAITING);
						gui.setButtonEnabled(false);
						link = new Network();
						gui.setButtonEnabled(true);
						gui.setButtonText(STOP);
						gui.setStatus(CONNECTED);
					}
				}
				catch(SocketTimeoutException e) {
					close();
					//e.printStackTrace();
					gui.setStatus(RETRYING);
				}
				catch(SocketException e) {
					close();
					//e.printStackTrace();
					gui.setStatus(RETRYING);
				}
				catch(IOException e) {
					close();
					//e.printStackTrace();
					gui.setStatus(RETRYING);
				}
				try {
					if(link != null && link.isAlive()) {
						String msg = link.get();
						System.out.println(msg);
						//int key = Integer.parseInt(msg);
						//keyboard.press(key);
					}
					else {
						close();
						gui.setButtonText(WAITING);
					}
				}
				catch (SocketException e) {
					close();
					//e.printStackTrace();
				}
				catch(EOFException e) {
					close();
					//e.printStackTrace();
				}
				catch(IOException e) {
					close();
					//e.printStackTrace();
				}
			}
		}
		
	}
}
