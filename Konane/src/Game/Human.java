package Game;

public class Human extends Player {

	public Human(String colorValue) {
		super(colorValue);
		playerType = "Human";
	}
	public String getPlayerType(){
		return playerType;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
}
