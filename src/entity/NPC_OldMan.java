package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
		
	}
	
	public void getImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/npc/oldman_right_2.png"));


			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDialogue() {
		
		dialogues[0] = "get out of my way you old fart";
		dialogues[1] = "what do you want from me? treasure u say?";
		dialogues[2] = "can't help you out there, but my grandson Harry might";
		dialogues[3] = "now go away";
	}
	
	public void setAction() { //AI movement for npc
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1; //picks a number from 1-100
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 & i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
	
	}
	
	public void speak() {
		
		super.speak();
	}
	
	
	
	
}
