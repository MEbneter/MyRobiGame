package model;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
/**
 * 
 * @author manuel
 *Klasse für Menu Screens
 */
public class Menu {
	private PApplet myWindow;
/**
 * 
 * @param myWindow
 */
public Menu(PApplet myWindow) {
	this.myWindow=myWindow;
}
/**
 * gameover sceen
 * @param punkte = übergabe der punkte zur anzeige.
 */
public void drawGameOver(int punkte, String name) {
	myWindow.background(0);
	myWindow.textAlign(myWindow.CENTER);
	myWindow.textSize(50);
	myWindow.fill(255,0,0);
	myWindow.text("GAME OVER", myWindow.width/2, myWindow.height/2-100);
	myWindow.textSize(20);
	myWindow.fill(255,0,255);
	myWindow.text("Gratuliere "+name+" du hast "+ punkte + " Punkte erreicht!", myWindow.width/2, myWindow.height/2+60);
	myWindow.fill(150,150,150);
	myWindow.text("ENTER fürs Menu", myWindow.width/2, myWindow.height/2+40);
}
/**
 * menu screen
 * @param maus		übergabe der maus coord als PVector
 * @param mStart	übergabe der button coord. als PVector
 */
public void drawMenu(PVector maus, PVector mStart, PVector mScore) {
	maus.x=myWindow.mouseX;
	maus.y=myWindow.mouseY;
	myWindow.background(0);
	myWindow.rectMode(myWindow.CENTER);
	myWindow.fill(200, 200, 200, 50);
	if (PVector.dist(mStart,maus)<60){
		myWindow.fill(200, 200, 200, 40);
	}
	myWindow.rect(mStart.x,mStart.y,300,100,20);
	myWindow.rect(mScore.x,mScore.y,300,100,20);
	myWindow.textAlign(myWindow.CENTER);
	myWindow.textSize(70);
	myWindow.fill(0,255,255);
	myWindow.text("Robi-Game" ,myWindow.width/2,100);
	myWindow.stroke(200);
	myWindow.line(myWindow.width/4, 110, myWindow.width/4*3, 110);
	myWindow.textSize(20);
	myWindow.text("Sammle Blümchen für Punkte und \n lass dich nicht abschiessen!" ,myWindow.width/2,135);
	myWindow.fill(255,150,50);
    if (PVector.dist(mStart,maus)<60){
    	myWindow.fill(255,200,50);
    	
	}
    myWindow.textSize(50);
    myWindow.text("Start", mStart.x, mStart.y+20);
    myWindow.fill(255,150,50);
    if (PVector.dist(mScore,maus)<60){
    	myWindow.fill(255,200,50);
    	
	}
    myWindow.text("Highscores", mScore.x, mScore.y+20);
}
public void drawScore(PVector maus, ArrayList<HighScore> scores) {
	myWindow.background(0);
	myWindow.textAlign(myWindow.CENTER);
	myWindow.stroke(200);
	myWindow.textSize(60);
	myWindow.fill(255,100,200);
	myWindow.text("Scoreboard", myWindow.width/2, 70);
	myWindow.fill(255,200,100);
	myWindow.textSize(35);
	myWindow.text("Spieler: ",myWindow.width/2-100, 110);
	myWindow.text("Punkte: ",myWindow.width/2+120, 110);
	if (maus.y > myWindow.height-41) {
		myWindow.fill(200,200,150);		
	}else {
		myWindow.fill(150,150,100);
	}
	myWindow.text("Zurück", myWindow.width/2, myWindow.height-15);
	myWindow.stroke(200);
	myWindow.line(myWindow.width/10*4, myWindow.height-50, myWindow.width/10*6, myWindow.height-50);
	/*
	 * schleife zum ausgeben des Array Inhalts
	 */
	int platz =1;
	int y =150;
	for (HighScore score : scores) {
		if (platz<11) {
		myWindow.fill(255,200,100);
		myWindow.text("-"+platz+"-",myWindow.width/2-240, y);
		myWindow.fill(155,210,250);
		myWindow.text(score.getName(),myWindow.width/2-100, y);
		myWindow.text("  :  ",myWindow.width/2+20, y);
		myWindow.text(""+score.getPunkte(),myWindow.width/2+120, y);
		}
		y+=40;
		platz+=1;
	}
}
}
