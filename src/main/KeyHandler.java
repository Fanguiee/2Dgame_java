package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	//debug
	public boolean checkDrawTime = false;
		
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_W:
			upPressed = true;
			break;
		case KeyEvent.VK_S:
			downPressed = true;
			break;
		case KeyEvent.VK_A:
			leftPressed = true;
			break;
		case KeyEvent.VK_D:
			rightPressed = true;
			break;
		//debug
		case KeyEvent.VK_T:
			checkDrawTime = !checkDrawTime;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_W:
			upPressed = false;
			break;
		case KeyEvent.VK_S:
			downPressed = false;
			break;
		case KeyEvent.VK_A:
			leftPressed = false;
			break;
		case KeyEvent.VK_D:
			rightPressed = false;
			break;
		}
	}

}
