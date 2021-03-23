package model;

public class HighScore {
	private int punkte;
	private String name;
	public HighScore(String name, int punkte) {
		this.name=name;
		this.punkte=punkte;
	}

	public String getName() {
		return this.name;
	}
	public int getPunkte() {
		return this.punkte;
	}
}
