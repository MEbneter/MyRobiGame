package controller;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import model.Food;
import model.HighScore;
import model.Kugel;
import model.Menu;
import model.Robi;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * the stepstone to your carrier as game developer
 * @author sven
 * @author manuel ebneter
 * ich hab für meine kleine richtungsberechnung ewigkeiten benötigt um auf die lösung zu kommen :D hat graue haare aber auch spass gemacht.
 */
public class RobiGameController extends PApplet{

	private Robi playerRobi;
	private ArrayList<Food> foodItems;
	private ArrayList<Kugel> kugeln = new ArrayList<Kugel>();
	private ArrayList<Robi> kiEnemies;
	private PVector foodPos;
	private PVector robiPos = new PVector(150,250);
	private PVector richtung = new PVector(0,0);
	private PVector mMaus = new PVector(mouseX,mouseY);
	private PVector mStart;
	private PVector mScore;
	private Menu menu;
	private Timer tim;
	private TimerTask tTask;
	private Random r = new Random();
	private int gameStatus = 0;
	private String playerName;
	private HighScore playerScore;
	private boolean topTen = true;
	private HighScoreController score = new HighScoreController();
	

	/**
	 * creates and runs the entire app
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("controller.RobiGameController");
	}
/**
 * (non-Javadoc)
 * @see processing.core.PApplet#settings()
 * hier bestimmen wir die Fenstergrösse
 */
	public void settings() {
		size(800, 600);
	}

	/**
	 * still abused to create a game session 
	 * 
	 */
	public void setup() {
		mStart= new PVector(width/2,height/2);
		mScore= new PVector(width/2,height/2+120);
		menu= new Menu(this);
		playerRobi = new Robi(robiPos , 0xFFFF0000, this);
		//create food items and store them away
		foodItems = new ArrayList<Food>();
		for (int i=0; i < 10; i++){
			foodPos= new PVector (random(width), random(height));
			foodItems.add(new Food (foodPos , 15, this));
		}
		/**
		 * erstellt unsere Gegner
		 */
		kiEnemies = new ArrayList<Robi>();
		PVector danger;
		for (int i=0; i<4 ; i++) {
			danger = new PVector (r.nextInt(width),r.nextInt(height));
			Robi enemy = new Robi(danger , 0xFF0000FF, this);
			kiEnemies.add(enemy);
			
		}
		/**
		 * timer hier wird die gamelogik abgefragt
		 */
		tim = new Timer();
		tTask = new TimerTask() {
			@Override
			public void run() { // this method runs complete game logic 
				handleGameLogic();
			}			
		};
		tim.scheduleAtFixedRate(tTask,new Date(), 100);
	}

	/**
	 * this method does all drawing
	 * die cases bestimmen die Verschiedenen game Phasen /menu /ingame /gamover
	 */
	public void draw(){
		mMaus = new PVector(mouseX,mouseY);
		switch (gameStatus) {
		case 0:
			//menuScreen();
			menu.drawMenu(mMaus, mStart, mScore);
			startOptionen();
		break;
		case 1:
			background(0x202020);
			textAlign(CENTER);
			textSize(30);
			fill(0xFF99FFAA);
			text("Punkte: "+playerRobi.getRobiPunkte() ,width/2, 30);
			playerRobi.draw();
			//iterate over all foot items
			for (Kugel kugel : kugeln) {
				kugel.drawKugel();
			}
			for (int i=0; i <foodItems.size(); i++){
				Food tFood = foodItems.get(i); 
				tFood.draw(); //draw i-th food object
			}
			for (int i=0; i <kiEnemies.size(); i++) {
				Robi tEnemy = kiEnemies.get(i);
				tEnemy.draw();
			}
		break;
		case 2:
			//gameOverScreen();
			menu.drawGameOver(playerRobi.getRobiPunkte(), playerName);
			resetGame();
		break;
		case 3:
			// HighscoreScreen
			
			menu.drawScore(mMaus, score.getHighScore());
			startOptionen();
		break;
		}
	}	
	/**
	 * this method runs complete game logic
	 */
	private void handleGameLogic() {
		playerRobi.move();
		detectFoodCollisions(playerRobi);
		/**
		 * jeder gegner kriegt eine Chance zu schiessen wenn der Player Punkte macht (die mögen nicht wenn man ihnen die Blumen wegisst)
		 */
		for (int i = 0; i < kiEnemies.size(); i++) {
			Robi tEnemie = kiEnemies.get(i);
			if (playerRobi.getRobiPunkte() > playerRobi.getRobiPunkteAlt() && random(99)>80) {
				kugeln.add(new Kugel(tEnemie.getXPos(), tEnemie.getYPos(),this));
				
				for (Kugel kugel : kugeln) {
					setRichtung(playerRobi, kugel);
					if (kugel.getRichtungX() == 0 && kugel.getRichtungY() == 0) {
						kugel.setRichtung(richtung);
					}
				}
			}
			
			/**
			 * Schauen ob der Spieler von einer Kugel getroffen wird
			 */
			for (Kugel kugel : kugeln) {
				if (getDistance(kugel.getVector(),playerRobi.getVector()) < playerRobi.getRobiWidth()/2+10) {
					
					if (gameStatus !=2) {
						score.readHighscores();
						playerScore = new HighScore (playerName,playerRobi.getRobiPunkte());
						score.sortScores();
						int tCount = 0;
						for (HighScore tScore : score.getHighScore()) {
							if (playerRobi.getRobiPunkte() > tScore.getPunkte() && tCount < 10) {
								topTen = true;
							}else {
								topTen = false;
							}
							tCount++;
						}
						if (topTen) {
							score.addScore(playerScore);
						}
						score.writeScores();
					}
					gameStatus=2;
				}
			}

			/**
			 * Hier sucht sich die KI wo der am nähesten gelegene Food ist
			 */
			double minDist = Double.MAX_VALUE;
			Food nearestFood = null;
			for (Food f : foodItems) { // for-each schleife
				double d = getDistance(tEnemie.getVector(), f.getVector());
				if (d <= minDist) {
					minDist = d;
					nearestFood = f;
				}
			}
			/**
			 * entsprechend der Position des ermittelten food wird sich in dessen richtung bewegt
			 */
			if (PApplet.abs(tEnemie.getXPos() - nearestFood.getXPos()) > PApplet.abs(tEnemie.getYPos() - nearestFood.getYPos())) {
				if (tEnemie.getXPos() - nearestFood.getXPos() > 0) {
					tEnemie.moveLeft();
				} else {
					tEnemie.moveRight();
				}
			}else {
				if (tEnemie.getYPos() - nearestFood.getYPos() > 0) {
						tEnemie.moveUp();
				} else {
						tEnemie.moveDown();
				}
			}	
			tEnemie.move();
			detectFoodCollisions(tEnemie);
		}
		playerRobi.setRobiPunkteAlt(playerRobi.getRobiPunkte());
	}
	/**
	 * startet das spiel
	 */
	public void startOptionen() {
		
		
		//option start
			if(mousePressed && gameStatus==3 && mMaus.y>height-40) {
	    		mousePressed = false;
	    		gameStatus=0;
	    	}
			if(mousePressed && PVector.dist(mStart,mMaus)<55 && gameStatus==0) {
	    		gameStatus=1;
	    		playerName = JOptionPane.showInputDialog("Name hier eingeben: ");
	    		mousePressed = false;
	    	}
	    	if(mousePressed && PVector.dist(mScore,mMaus)<55 && gameStatus==0) {
	    		// file lesen , array dem score menu übergeben
	    		mousePressed = false;
	    		score.readHighscores();
				score.sortScores();
	    		gameStatus=3;
	    	}
	    	
	    	
		
	}
	/**
    * mit Enter das spiel Zurücksetzten und in den Menu Screen wechseln
    */
	public void resetGame () {
		if (key == ENTER) {
	    	gameStatus = 0;
	    	playerRobi.setXPos((int)random(width));
	    	playerRobi.setYPos((int)random(height));
	    	playerRobi.setRobiPunkte(0);
	    	playerRobi.setRobiPunkteAlt(0);
	    	playerRobi.resizeRobi();
	    	kugeln.clear();
	    	keyCode =0;
	    }
	}
	/**
	 * gucken ob jemand ein Food-Item frisst
	 */
	private void detectFoodCollisions(Robi v) {
		for (int i=0; i < foodItems.size(); i++) {
			Food tFood = foodItems.get(i);
			if (checkCollision(v,tFood) && gameStatus == 1){
				if (v == playerRobi ) {
					v.grow();
					playerRobi.addRobiPunkte(tFood.getPunkte());
				} else {
					for (Robi enemy : kiEnemies) {
						enemy.addRobiPunkte(tFood.getPunkte());
					}
				}
				foodPos= new PVector (random(width), random(height));
				foodItems.set(i, new Food(foodPos, 15, this));
			} 	
		}		
	}
/**
 * 	(non-Javadoc)
 * @see processing.core.PApplet#keyPressed()
 */
	public void keyPressed(){
		switch (keyCode){
		case UP: playerRobi.moveUp(); break;
		case DOWN: playerRobi.moveDown(); break;
		case LEFT: playerRobi.moveLeft(); break;
		case RIGHT: playerRobi.moveRight(); break;
		}
	}
/**
 * @param v = zielcord
 * @param e = startcord
 * Zuerst dachte ich das klappt mit v.sub(e).normalize(); , leider aber nicht oder ich hab irgendwo einen denkfehler gemacht.
 * Die Richtung zum Ziehl wird in einem PVector gespeichert - Zahlen zwischen 0 und 1.
 * Berechnet den Multiplikator für die X- und Y-Bewegung damit sich die Kugel Richtung Ziel bewegt.
 */
	public void setRichtung(Robi v, Kugel e) {
		richtung.x = (v.getXPos()-e.getXPos()) / (PApplet.abs(v.getXPos()-e.getXPos())+PApplet.abs(v.getYPos()-e.getYPos()));
		richtung.y = (v.getYPos()-e.getYPos()) / (PApplet.abs(v.getXPos()-e.getXPos())+PApplet.abs(v.getYPos()-e.getYPos()));
	}
	/**
	 * Was macht echt diese Funktion? Und wie macht sie das? 
	 * Geht das auch Ã¼bersichtlicher?
	 * Jetzt Besser?
	 */
	public boolean checkCollision(Robi v, Food f){
		return PVector.dist(v.getVector(), f.getVector()) < (v.getRobiWidth()/2+f.size/2);
	}
	public float getDistance(PVector v, PVector f) {
		return v.dist(f);
	}
}
