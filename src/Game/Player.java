package Game;

import java.util.ArrayList;

public abstract class Player {

	protected String black = "Black";
	protected String white = "White";
	protected String empty = "Empty";
	protected String colorValue;
	protected String playerType;
	protected Tile[][] copyOfGameBoard;
	protected ArrayList<Move> setOfAllBlackMoves;
	protected ArrayList<Move> setOfAllWhiteMoves;

	public Player(String colorValue) {
		this.colorValue = colorValue;
	}

	public abstract void updateStoredGameBoard(Tile[][] gameBoardCopy, ArrayList<Move> copyOfMoves, String color);

	public abstract Move makeAMove();

	public abstract String getPlayerType();

	public abstract String getColorValue();

	public abstract void setColorValue(String colorValue);

	public abstract Move firstMove(ArrayList<Move> setOfAllStartingMoves);

	public abstract Move secondMove(ArrayList<Move> setOfAllStartingMoves);
}
