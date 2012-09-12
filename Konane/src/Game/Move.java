package Game;

public class Move {
	
	
	private int[] moveFrom;
	private int[] moveTo;
	private int[] selectionMove;
	
	public Move(int[] moveFrom, int[] moveTo)
	{
		this.moveFrom = moveFrom;
		this.moveTo = moveTo;
	}
	public Move(int[] select)
	{
		this.selectionMove = select;
	}
	

	public int getSelectionMoveRow() {
		return selectionMove[0];
	}
	public int getSelectionMoveCol(){
		return selectionMove[1];
	}
	
	public void setSelectionMove(int[] selectionMove) {
		this.selectionMove = selectionMove;
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
