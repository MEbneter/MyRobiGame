package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import model.HighScore;
/**
 * verwaltet und speichert die heghsocores
 * @author manue
 *
 */

public class HighScoreController {
	ArrayList<HighScore> scores;

	/**
	 * kleiner testramen
	 * @param arg
	 */
	public static void main(String[] arg) {
		HighScoreController testController = new HighScoreController();
	
		testController.readHighscores();
		testController.sortScores();
		testController.printHighscores();	
		//testController.addScore(new HighScore("heiri", 200)); // versuchsweise spieler dem array Hinufügen
		testController.writeScores();
		
	}

	public void printHighscores() {
		for (HighScore hs : scores) {
			System.out.println(hs.getName() +" : "+ hs.getPunkte());
		}
	}
	public void sortScores()  {
		// kurze schreibweise mit lambda ausdruck
		scores.sort((a,b) -> b.getPunkte()-a.getPunkte());
		/* lange schreibweise
		scores.sort(new Comparator<HighScore>() {
		 public int compare(HighScore a, HighScore b) {
			 return b.getPunkte()-a.getPunkte();
		 }
		});*/
	}
	/*
	 * hier kann man eine neue score in die arrayList hinzufügen.
	 */
	public void addScore(HighScore x) {
		scores.add(x);
	}
	/*
	 * hier schreiben wir die Arraylist wieder in das File
	 */
	public void writeScores() {
		PrintWriter prnt = null;
		try {
			prnt = new PrintWriter(System.getProperty("user.home") + "\\highscore.lst");
			// For-each loop zum abklappern der einzelnen HighScore objekte
			for (HighScore score : scores) {
				prnt.write(score.getName() + ";" + score.getPunkte() + System.lineSeparator());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			prnt.flush(); // puffer leeren und ins file schreiben
			prnt.close(); // Systemresourcen freigeben
		}
	}
	/**
	 * liest highscores aus der datei
	 */
	public void readHighscores() {

		scores = new ArrayList<>();
		BufferedReader bfr = null;
		File file;
		try {
			file = new File(System.getProperty("user.home") + "\\highscore.lst");
			file.createNewFile();  // erstellt highscore.lst fals nicht vorhanden
			FileReader reader = new FileReader(file);
			bfr = new BufferedReader(reader);
			while (bfr.ready()) { //liest solange er lesen kann und gibt jede zeile aus
				String line = bfr.readLine();
				//System.out.println(line);
				scores.add(parseHighscore(line));  //schreibe das read in die arrayListe
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			try {
				bfr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HighScore parseHighscore(String line) {
		HighScore result = null;
		String[] parts =line.split(";"); // splittet den string beim semikolon
		String name = parts[0];
		int punkte = Integer.parseInt(parts[1]);
		result = new HighScore(name, punkte);
		return result;
	}

	public ArrayList<HighScore> getHighScore() {
		return scores;
	}
}
