package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KeyHandler implements KeyListener { //listener interface for keyboard input
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); //returns # of the key that was pressed
		BufferedImage image = null;
		
		//TITLE
		if(gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_W) { 
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			
			if (code == KeyEvent.VK_S) { 
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
					gp.lowerVolume();
				}
				if(gp.ui.commandNum == 1) {
					gp.playMusic(5);
					new Thread(new Runnable() {
						 @Override
					        public void run() {
					            try {
					                Thread.sleep(5000); // Wait for 5 seconds
					                System.exit(0); // Exit the game after the delay
					            } catch (InterruptedException e) {
					                e.printStackTrace();
					            }
					        }
					    }).start();
					
				
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);;
				}
			}
		}
		//PLAY
		if(gp.gameState == gp.playState) {
			
			if (code == KeyEvent.VK_W) { //if user pressed W
				upPressed = true;
			}
			
			if (code == KeyEvent.VK_S) { //if user pressed 
				downPressed = true;
			}
			if (code == KeyEvent.VK_A) { //if user pressed 
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D) { //if user pressed W
				rightPressed = true;
			}
			if (code == KeyEvent.VK_P) { //pause
				gp.gameState = gp.pauseState;
			}
			if (code == KeyEvent.VK_ENTER) { 
				enterPressed = true;
			}
		}
		
		//PAUSE
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) { //pause
				gp.gameState = gp.playState;
			}
		}
		
		//DIALOGUE
		else if (gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) { //if user pressed W
			upPressed = false;
		}
		
		if (code == KeyEvent.VK_S) { //if user pressed 
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) { //if user pressed 
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) { //if user pressed W
			rightPressed = false;
		}
		
		
	} 

}
