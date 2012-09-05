package Game;

public abstract class Player {
	
	protected String colorValue;
	protected String playerType;
	
	public Player(String colorValue){
		this.colorValue = colorValue;
	}
	public abstract Move makeAMove();
	public abstract String getPlayerType();
	public abstract String getColorValue();
	public abstract void setColorValue(String colorValue);
}
