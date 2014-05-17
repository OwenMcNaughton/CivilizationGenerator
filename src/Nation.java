import java.awt.Color;

public class Nation {
	
	public String name;
	public Color color;
	public int population;
	public int techLevel;
	public double stability;
	public double growth;
	
	public Nation(String n, Color c) {
		this.name = n;
		this.color = c;
		this.techLevel = 1;
		this.stability = 1.0;
		this.growth = 0.0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getPop() {
		return this.population;
	}
	
	public void setPop(int p) {
		this.population = p;
	}
	
	public void addPop(int p) {
		this.population += p;
	}
	
	public void setTech(int t) {
		this.techLevel = t;
	}
	
	public int getTech() {
		return this.techLevel;
	}
	
	public void setStability(double s) {
		this.stability = s;
	}
	
	public double getStability() {
		return this.stability;
	}
	
	public void setGrowth(double g) {
		this.growth = g;
	}
	
	public double getGrowth() {
		return this.growth;
	}
}
