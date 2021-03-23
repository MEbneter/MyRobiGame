package model;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
/**
 * 
 * @author manuel
 * hier kommt das geschoss her.
 */
public class Kugel {
	private PShape kugel;
	private PApplet myWindow;
	private PVector position = new PVector(0,0);
	private PVector richtung = new PVector(0,0);
	private int bounce = 0;
	private int bounceCount= 4;
/**
 * Konstruktor für die Kugel
 * @param xPos
 * @param yPos
 * @param myWindow
 */
	public Kugel (float xPos, float yPos, PApplet myWindow) {
		this.myWindow = myWindow;
		this.position.x = xPos;
		this.position.y = yPos;
		  /*
		   * einmaliges zeichnen der "kugel"
		   */
		  kugel = myWindow.createShape();
		  kugel.beginShape();
		  kugel.fill(20,250,255);//farbe der figur
		  kugel.stroke(255,180,0);
		  kugel.strokeWeight((float) 1);
		  kugel.strokeJoin(myWindow.ROUND);
		  kugel.vertex(4, -16);
		  kugel.vertex(2, -6);
		  kugel.vertex(16, 4);
		  kugel.vertex(6, 2);
		  kugel.vertex(-4, 16);
		  kugel.vertex(-2, 6);
		  kugel.vertex(-16, -4);
		  kugel.vertex(-6, -2);
		  kugel.scale((float) 1);
		  kugel.endShape(myWindow.CLOSE);
	}
	/**
	 * zeichnen der "kugel"
	 */
	public void drawKugel () {
		if (bounce < bounceCount) {
			if (position.x < 0 || position.x > myWindow.width) {
				this.richtung.x *= -1;
				this.bounce +=1;
			} else if (position.y < 0 || position.y > myWindow.height) {
				this.richtung.y *= -1;
				this.bounce +=1;
			}
			kugel.rotate(myWindow.PI/30);	//natürlich dreht sich das ding "handtuch nicht vergessen"
			myWindow.shape(kugel, this.position.x, this.position.y);
			myWindow.circle(this.position.x, this.position.y, 8);
			position.x = position.x + richtung.x*5;
			position.y = position.y + richtung.y*5;
		} else {
			this.position.x=-800;
			this.position.y=-800;
		}
	}
	/*
	 * flugrichtung x und y übergeben
	 */
	private void moveRichtung() {
		// wer mag sie nicht unsere Souls-like Games :P in bischen hart die option
				
	}
	public void setBCount (int b) {
		this.bounceCount = b;
	}
	public void setRichtung (PVector r){
		richtung.x=r.x;
		richtung.y=r.y;
	}
    /*
     * gets für pos und richtung
     */
	public float getXPos() {
		return this.position.x;
	}
	public float getYPos() {
		return this.position.y;
	}
	public float getRichtungX() {
		return this.richtung.x;
	}
	public float getRichtungY() {
		return this.richtung.y;
	}
	public PVector getVector() {
		return position;
	}
}
