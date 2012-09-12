package Game;

public abstract class Player {
	
	protected String black = "Black";
	protected String white = "White";
	protected String colorValue;
	protected String playerType;
	protected Tile[][] copyOfGameBoard;
	
	public Player(String colorValue){
		this.colorValue = colorValue;
	}
	public abstract void updateStoredGameBoard(Tile[][] gameBoardCopy);
	public abstract Move makeAMove();
	public abstract String getPlayerType();
	public abstract String getColorValue();
	public abstract void setColorValue(String colorValue);
	public abstract Move firstMove();
	public abstract Move secondMove();
}
