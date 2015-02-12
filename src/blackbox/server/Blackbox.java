package blackbox.server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Blackbox {
	private JFrame base;
	private EngineDroid engine;
	private CButton connect;
	private JLabel host;
	private JLabel status;
	private static final String SERVER_NAME = "Blackbox";
	public static final String START = "Start";
	private static final String HOST_NAME = "Host name";
	public static final String CONNECTED = "Connected";
	public static final String DISCONNECTED = "Disconnected";
	public static final String TIMEOUT = "Timed out, start again";
	public static final String STOP = "Stop";
	public static final String WAITING = "Waiting for connection";
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	public Blackbox() {
			prepareGUI();
			
	}
	private void prepareGUI(){
		base = new JFrame(SERVER_NAME);
		base.setResizable(false);
		base.setLayout(null);
		base.setSize(WIDTH, HEIGHT);
		base.setLocationRelativeTo(null);
		base.setIconImage(new ImageIcon("icon.png").getImage());
		base.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
		});
		Color bgColor = new Color(76, 76, 76);
		base.getContentPane().setBackground(bgColor);
		connect = new CButton(START, new Color(52, 52, 52), new Color(189, 255, 63));
		connect.setBounds(WIDTH/2 - 75, HEIGHT/2, 150, 50);
		connect.setFocusPainted(false);
		base.add(connect);
		try{
			String hostname = InetAddress.getLocalHost().getHostName(); 
			host = new JLabel(hostname);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setBackground(null);
		panel.setBounds(0,0, WIDTH, HEIGHT/2);
		host.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 35));
		host.setForeground(new Color(213, 59, 12));
		JLabel hostname = new JLabel(HOST_NAME);
		hostname.setBounds(170 ,-15,100, 50);
		panel.add(host, SwingConstants.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
		panel.setBackground(bgColor.darker());
		hostname.setForeground(Color.GRAY);
		status = new JLabel(DISCONNECTED);
		status.setForeground(Color.WHITE);
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(bgColor.brighter());
		statusPanel.setBounds(0, HEIGHT - 50, WIDTH, 70);
		statusPanel.add(status);
		base.add(statusPanel);
		base.add(hostname);
		base.add(panel);
		engine = new EngineDroid(this);
		prepairBindings();
	}
	//All the setters
	public void setHostname(String host){
		this.host.setText(host);
	}
	public void setStatus(String status){
		this.status.setText(status);
	}
	public void setButtonText(String value){
		connect.setText(value);
	}
	public void setButtonEnabled(boolean val){
		connect.setEnabled(val);
	}
	public void resetButton(){
		setStatus(Blackbox.DISCONNECTED);
		setButtonText(Blackbox.START);
		setButtonEnabled(true);
	}
	public void startServer(){
		base.setVisible(true);
	}
	
	private void prepairBindings(){
		connect.setActionCommand(connect.getText());
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				onClickConnect(event);
			}
		} );
	}
	private void onClickConnect(ActionEvent event){
		if(engine.isRunning()){
				engine.stop();
				resetButton();
		}
		else{
			System.out.println("Starting");
			engine.start();
			setButtonText(STOP);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Blackbox server = new Blackbox();
				server.startServer();
				
			}
		});
	}
}


class CButton extends JButton {

	private static final long serialVersionUID = 1L;
	private Color background;
	private Color pressed;
	public CButton() {
		super();
		setFocusPainted(false);
		setForeground(Color.WHITE);
	}
	public CButton(String value){
		super(value);
		background = Color.BLACK;
	}
	public CButton(String value, Color background,Color pressed){
		super(value);
		this.background = background;
		this.pressed = pressed;
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		setFocusPainted(false);
		setBorderPainted(false);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(getModel().isPressed()){
			g.setColor(pressed);
		}
		else if(getModel().isRollover()){
			g.setColor(background.darker());
		}
		else{
			g.setPaintMode();
			g.setColor(background);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
