package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	KeyHandler keyH;	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp); //calling constructor of the superclass and passing gp
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23; //player starting position
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));


			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() { //gets called 60x per second
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.collisionChecker.checkTile(this);
			
			//CHECK OBJ COLLISION
			int objIndex = gp.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.collisionChecker.checkEntity(this,gp.npc);
			interactNPC(npcIndex);
			
			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				case "up":
					worldY -= speed; //playerY = playerY - playerSpeed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++; //it increases counter by 1 by each call
			if (spriteCounter > 14) { //if it hits 10 it changes sprite so player image changes every 10 frames
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
		}
		
	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null; //makes it disappear
				gp.ui.showMessage("you obtained a key!!");
				break;
			case "Door":
				gp.playSE(3);
				if(hasKey > 0) {
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("you unlocked the door");
				}
				else {
					gp.ui.showMessage("oop awks you need a key");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed += 3;
				gp.obj[i] = null;
				gp.ui.showMessage("OKKK SPEEEEDY");
				break;
			case "Pokemon":
				speed -= 3;
				gp.obj[i] = null;
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				gp.lowerVolume();
				new Thread(new Runnable() {
					 @Override
				        public void run() {
				            try {
				                Thread.sleep(5000); // Wait for 5 seconds
								gp.gameState = gp.titleState;
				            } catch (InterruptedException e) {
				                e.printStackTrace();
				            }
				        }
				    }).start();
				break;
			}
		}
	}
	
	public void interactNPC(int i) {
		
		if (i != 999) {
			
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
			
			
		}
		gp.keyH.enterPressed = false;
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // null for imageObserver
	}

}
