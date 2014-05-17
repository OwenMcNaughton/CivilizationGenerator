import java.awt.Color;
import java.util.Random;

public class Tile {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Nation owner;
	
	private double value;
	private int pop;
	
	private boolean visible;
	
	
	public Tile(int mouseX, int mouseY) {
		this.x = mouseX;
		this.y = mouseY;
		this.width = 10;
		this.height = 10;
		
		this.owner = null;
		
		Random gen = new Random();
		this.value = (double) gen.nextInt(300) / 100;
		
		this.pop = 0;
		
		this.visible = true;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Color getColor() {
		if(this.owner != null) {
			return this.owner.color;
		} else return Color.GRAY;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public String getOwnerStr() {
		if(this.owner != null) {
			return this.owner.name;
		} else return "Neutral";
	}
	
	public Nation getOwner() {
		if(this.owner != null) {
			return this.owner;
		} else return null;
	}
	
	public void setOwner(Nation n) {
		this.owner = n;
	}
	
	public void setValue(int v) {
		this.value = v;
	}
	
	public boolean isVisible() {
		if(this.visible) {
			return true;
		} else return false;
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public int getPop() {
		return this.pop;
	}
	
	public void setPop(int p) {
		this.pop = p;
	}
	
}
