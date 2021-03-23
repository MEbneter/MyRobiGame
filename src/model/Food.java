package model;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * missing javadoc hurts. not only in exams.
 * @author sven
 *
 */
public class Food {
	private PVector position;
	private int punktewert;
	private int myColor = 0xFFFFAA00; 
	public int size;
	PApplet myWindow;
/**
 * Der konstruktor der Food's
 * @param pos		PVector
 * @param size		int
 * @param myWindow	PApplet
 */
	public Food(PVector pos, int size, PApplet myWindow){
		this.size = size;
		this.myWindow = myWindow; 
		this.punktewert = (int) myWindow.random(4, 9);
		position = pos;
	}
	/**
	 * zeichnen des Food
	 */
	public void draw(){
		myWindow.fill(myColor);
		myWindow.ellipse(position.x,position.y,size,size);
		myWindow.fill(0xFF55AA00);
		myWindow.ellipse(position.x,position.y,size/2,size/2);    
	}
	/*
	 * get für Punktewert des Food's
	 */
	public int getPunkte() {
		return this.punktewert;
	}
	/*
	 * get zur abfrage der positionen einzeln und als vector
	 */
	public float getXPos() {
		return this.position.x;
	}
	public float getYPos() {
		return this.position.y;
	}
	public PVector getVector() {
		return position;
	}
	
}
