package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable { //inherits JPanel class aka has all functions of JPanel
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile 
	final int scale = 3; 
	
	public int tileSize = originalTileSize * scale; //48x48 tile
	public final  int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; //threads can be stopped and started
	
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;

	
	//ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; //10 slots for objects
	public Entity npc[] = new Entity[10];
	
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //sets the size of the class (JPanel)
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true = all the drawings from this component will be done in an offscreen painting buffer aka improve rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true); //can be 'focused' to receive key input
		
	}
	
	public void setupGame() {
		
		aSetter.setObject(); //want to be called before game starts
		aSetter.setNPC();
		//playMusic(0);
		gameState = titleState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this); //this = gamePanel class to the thread constructor = instantiate the thread
		gameThread.start(); //will call run()
		
	}
	
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval; //how much time has passed divided by the drawInterval and added to the delta
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) { //when delta reaches drawInterval we update and repaint and then reset the delta
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	public void update() {
		
		if(gameState == playState) {
			player.update();
		}
		if (gameState == pauseState) {
			
		}
		
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].update();
			}
		}
		
	}
	
	public void paintComponent(Graphics g) { //standard method in JPanel
		
		super.paintComponent(g); //super = parent class = JPanel
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			//TILE
			tileM.draw(g2);		
			//OBJECT
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			
			//NPC
			for(int i = 0; i < npc.length; i++) {
				
				if(npc[i] != null) {
					npc[i].draw(g2);
				}
			}
			//PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);
		}
		
	
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) { //sound effects
		
		se.setFile(i);
		se.play();
	
	}
	
	public void lowerVolume() {
		music.lowerVolume();
	}

}
