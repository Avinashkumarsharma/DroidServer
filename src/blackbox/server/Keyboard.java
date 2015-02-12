package blackbox.server;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Keyboard {
	private int delay;
	private Robot keyboard;
	public static final int PRESS = 0;
	public static final int RELEASE = 500;
	private static final int BINDING_NONE = 1000;
	public Keyboard(){
		delay = 50;
		try {
			keyboard = new Robot();
		}
		catch(AWTException e){
			e.printStackTrace();
		}
	}
	public void setDelay(int ms){
		this.delay = ms;
	}
	public void type(String s)
	  {
	    byte[] bytes = s.getBytes();
	    for (byte b : bytes)
	    {
	      int code = b;
	      keyboard.delay(delay);
	      keyboard.keyPress(code);
	      keyboard.keyRelease(code);
	    }
	  }
	public void postDelay(int ms){
		keyboard.delay(ms);
	}
	public void press(int keycode){
		if(keycode >= BINDING_NONE)
			return;
		if(keycode >=RELEASE){
			keycode -= RELEASE;
			keyboard.keyRelease(keycode);
			//System.out.println("Released");
		}
		else{
			//System.out.println("Pressed ");
			keyboard.keyPress(keycode);
		}
	}
	public static void main(String[] args) {
		System.out.println("Shift = "+KeyEvent.VK_SHIFT);
		System.out.println("Enter = "+KeyEvent.VK_ENTER);
		System.out.println("Esc = "+KeyEvent.VK_ESCAPE);
		System.out.println("Pgdown = "+KeyEvent.VK_PAGE_DOWN);
		System.out.println("PgUp = "+KeyEvent.VK_PAGE_UP);
		System.out.println("Up = "+KeyEvent.VK_UP);
		System.out.println("Right = "+KeyEvent.VK_RIGHT);
		System.out.println("Down = "+KeyEvent.VK_DOWN);
		System.out.println("LEft = "+KeyEvent.VK_LEFT);
		System.out.println("Tab = "+KeyEvent.VK_TAB);
		System.out.println("CapsLock = "+KeyEvent.VK_CAPS_LOCK);
		System.out.println("Control = "+KeyEvent.VK_CONTROL);
		System.out.println("ALT = "+KeyEvent.VK_ALT);
		System.out.println("1 = "+KeyEvent.VK_1);
		System.out.println("2 = "+KeyEvent.VK_2);
		System.out.println("3 = "+KeyEvent.VK_3);
		System.out.println("9 = "+KeyEvent.VK_9);
		
	}
}
