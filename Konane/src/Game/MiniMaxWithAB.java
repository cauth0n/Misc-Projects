package Game;

public class MiniMaxWithAB extends Player {

	public MiniMaxWithAB(String colorValue) {
		super(colorValue);
		playerType = "MiniWith";
	}
	
	@Override
	public String getPlayerType() {
		return playerType;
	}

	@Override
	public String getColorValue() {
		return colorValue;
	}

	@Override
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;

	}

	@Override
	public Move makeAMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move firstMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move secondMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStoredGameBoard(Tile[][] gameBoardCopy) {
		copyOfGameBoard = gameBoardCopy;
	}

}
