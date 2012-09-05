package Game;

public class Move {
	
	
	private int[] moveFrom;
	private int[] moveTo;
	
	public Move(int[] moveFrom, int[] moveTo)
	{
		this.moveFrom = moveFrom;
		this.moveTo = moveTo;
	}

	public int getRowFrom() {
		return moveFrom[0];
	}
	
	public int getColFrom() {
		return moveFrom[1];
	}

	public void setMoveFrom(int[] moveFrom) {
		this.moveFrom = moveFrom;
	}

	public int getRowTo() {
		return moveTo[0];
	}
	
	public int getColTo() {
		return moveTo[1];
	}
	
	public void setMoveTo(int[] moveTo) {
		this.moveTo = moveTo;
	}

}
