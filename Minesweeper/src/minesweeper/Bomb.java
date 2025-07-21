package minesweeper;

public class Bomb extends MineTile {
   private String icon;
	public Bomb(int r, int c) {
		super(r, c);
		setI("B");
		// TODO Auto-generated constructor stub
	}
	public String getI() {
		return icon;
	}
	public void setI(String icon) {
		this.icon = icon;
	}

}
