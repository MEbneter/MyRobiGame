package model;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * missing javadoc hurts. not only in exams.
 * @author sven
 * 
 */
public class Robi {
	private int color;
	private int robiWidth = 20;
	private int robiHeight = 20;
	private int robiPunkte = 0;
	private int robiPunkteAlt = 0;
	private PApplet myWindow;
	private PVector position;
	
	enum DIRECTION { NORTH, EAST, SOUTH,WEST };
	private DIRECTION direction = DIRECTION.EAST;
	
	/**
	 * Konstruktor zum initialiseren der Startwerte der Attribute
	 * @param pos		= Positionen x und y
	 * @param color		= Farbe des Robi
	 * @param myWindow	= PApplet
	 */
	public Robi(PVector pos, int color, PApplet myWindow){
		position = pos;
		this.color = color;
		this.myWindow = myWindow;
	}
	/**
	    Funktion zum Zeichnen eines "Viechs"
	 */
	public void draw(){
		myWindow.rectMode(PApplet.CENTER);
		myWindow.fill(this.color);
		myWindow.rect(this.position.x ,this.position.y,robiWidth, robiHeight);
		myWindow.fill(0xFFFFFFFF);
		myWindow.noStroke();
		myWindow.ellipse(position.x -robiWidth/4f,position.y,robiHeight*0.7f,robiHeight*0.7f);
		myWindow.ellipse(position.x +robiWidth/4f,position.y,robiHeight*0.7f,robiHeight*0.7f);
		myWindow.fill(0);
		myWindow.ellipse(position.x -robiWidth/4f+1,position.y-2,robiHeight*0.4f,robiHeight*0.4f);
		myWindow.ellipse(position.x +robiWidth/4f-1,position.y-2,robiHeight*0.4f,robiHeight*0.4f); 
	}
	/**
	 * methoden für die bewegungen des objekts
	 */
	public void move() {
		switch (direction) {
			case NORTH: position.y = Math.max(0, position.y - 11); break;
			case SOUTH: position.y = Math.min(myWindow.height, position.y + 11); break;
			case EAST: position.x  = Math.min(myWindow.width, position.x  + 11); break;
			case WEST : position.x  = Math.max(0, position.x  - 11); break;
		}
	}

	public void moveUp(){
		this.direction = DIRECTION.NORTH;
	}

	public void moveDown(){
		this.direction = DIRECTION.SOUTH;
	}

	public void moveLeft(){
		this.direction = DIRECTION.WEST;
	}

	public void moveRight(){
		this.direction = DIRECTION.EAST;
	}

	/**
	 * lets the View grow slightly
	 */
	public void grow(){
		robiWidth+=2;
		robiHeight+=2;
	}
	/*
	 * get und set der positionen einzeln und als vector
	 */
	public float getXPos() {
		return position.x;
	}
	public float getYPos() {
		return position.y;
	}

	public void setXPos(int x) {
		this.position.x=x;
	}
	public void setYPos(int y) {
		this.position.y=y;
	}
	public PVector getVector() {
		return position;
	}
	/*
	 * hier wird die grösse des Robi zurückgesetzt
	 */
	public void resizeRobi() {
		this.robiHeight=20;
		this.robiWidth=20;
	}
	/*
	 * robiWith wird abgefragt
	 */
	public int getRobiWidth() {
		return this.robiWidth;
	}
	/*
	 * methoden für Punkte Zählen abfragen und stetzen
	 */
	public int getRobiPunkteAlt() {
		return robiPunkteAlt;
	}

	public void setRobiPunkteAlt(int punkteAlt) {
		this.robiPunkteAlt = punkteAlt;
	}

	public int getRobiPunkte() {
		return robiPunkte;
	}

	public void setRobiPunkte(int robiPunkte) {
		this.robiPunkte = robiPunkte;
	}

	public void addRobiPunkte(int punkte) {
		this.robiPunkte += punkte;
	}
}

