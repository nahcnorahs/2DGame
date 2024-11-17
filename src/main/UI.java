package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage keyImage, image;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT , is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
			
		}
		
		if (gp.gameState == gp.playState) {
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		
		
		
//		if(gameFinished == true) {
//			
//
//			g2.setFont(arial_40);
//			g2.setColor(Color.white);
//			
//			String text;
//			int textLength;
//			int x;
//			int y;
//			
//			text = "okk lets go you found the TREASURE";
//			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //returns length of the text above
//			x = gp.screenWidth/2 - textLength/2;
//			y = gp.screenHeight/2 - (gp.tileSize*3);
//			g2.drawString(text,x,y);
//			
//			
//			
//			text = "YAY U FINISHED";
//			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //returns length of the text above
//			x = gp.screenWidth/2 - textLength/2;
//			y = gp.screenHeight/2 + (gp.tileSize*2);
//			g2.drawString(text,x,y);
//			
//			gp.gameThread = null; //stops the thread aka game
//			
//			
//		}
//		else {
//			
//			g2.setFont(arial_40);
//			g2.setColor(Color.white);
//			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
//			g2.drawString("x  " + gp.player.hasKey, 74, 65);
//			
//			
//			//MESSAGE
//			if(messageOn == true) {
//				
//				g2.setFont(g2.getFont().deriveFont(30F));
//				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
//				
//				messageCounter++;
//				
//				if(messageCounter > 120) {
//					messageCounter = 0;
//					messageOn = false;
//				}
//			}
//		}
	
		
	}
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "BlueBoyAdventure";
		int x = getXForCenteredText(text);
		int y = gp.tileSize*3;
		
		//SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		//MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		text = "QUIT";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		//WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*3);
		int height = gp.tileSize*3;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		x+=gp.tileSize;
		y+=gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) { 
			g2.drawString(line, x, y);
			y += 40; 
		}
	
		
	}

	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255); //white
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

}