package blackbox.server;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
public class Mouse {
	private int x, y, sensitivity;
	private int screenWidth, screenHeight;
	private Robot mouse;
	private static final int RELEASE_DELAY = 200;
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	public static final int UP = -1;
	public static final int DOWN = 1;
	public static final int LEFT_CLICK = 0;
	public static final int RIGHT_CLICK = 3; 
	public Mouse() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)dim.getWidth();
		screenHeight = (int)dim.getHeight();
		x = screenWidth/2;
		y = screenHeight/2;
		sensitivity = 5;
		initBotMouse();
	}
	public Mouse(int sensitivity) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)dim.getWidth();
		screenHeight = (int)dim.getHeight();
		x = screenWidth/2;
		y = screenHeight/2;
		this.sensitivity = sensitivity;
		initBotMouse();
	}
	public Mouse(int sensitivity, int curX, int curY) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)dim.getWidth();
		screenHeight = (int)dim.getHeight();
		x = curY;
		y = curX;
		this.sensitivity = sensitivity;
		initBotMouse();
	}
	private void initBotMouse() {
		try {
			mouse = new Robot();
		}
		catch (AWTException e) {
			e.printStackTrace();
		}
		mouse.mouseMove(x, y);
	}
	public void postDelay(int ms){
		mouse.delay(ms);
	}
	public void leftClick(){
		mouse.mousePress(InputEvent.BUTTON1_MASK);
	}
	public void releaseLeftClick(){
		postDelay(200);
		mouse.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public void rightClick(){
		mouse.mousePress(InputEvent.BUTTON3_MASK);
		mouse.delay(RELEASE_DELAY);
		releaseRightClick();
	}
	public void releaseRightClick(){
		mouse.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	public void move(int dirX, int dirY){
		y += dirY*sensitivity;
		x += dirX*sensitivity;
		mouse.mouseMove(x, y);
	}
	public void moveTo(int x, int y) {
		mouse.mouseMove(x, y);
	}
	
}