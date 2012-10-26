package Game;

public class Tile {

	private String colorValue;
	private int rowPlacement;
	private int colPlacement;
	private int cellWorth;

	public Tile(String colorValue, int rowPlacement, int colPlacement, int boardSize) {
		this.colorValue = colorValue;
		this.rowPlacement = rowPlacement;
		this.colPlacement = colPlacement;

		if (rowPlacement == 0 || rowPlacement == (boardSize - 1)) {
			if (colPlacement == 0 || colPlacement == (boardSize - 1)) {
				cellWorth = 10;
			} else if (colPlacement == 1 || colPlacement == (boardSize - 2)) {
				cellWorth = 7;
			} else {
				cellWorth = 5;
			}
		} else if (rowPlacement == 1 || rowPlacement == (boardSize - 2)) {
			if (colPlacement == 0 || colPlacement == (boardSize - 1)) {
				cellWorth = 7;
			} else if (colPlacement == 1 || colPlacement == (boardSize - 2)) {
				cellWorth = 5;
			} else {
				cellWorth = 3;
			}
		} else {
			if (colPlacement == 0 || colPlacement == (boardSize - 1)) {
				cellWorth = 5;
			} else if (colPlacement == 1 || colPlacement == (boardSize - 2)) {
				cellWorth = 3;
			} else {
				cellWorth = 0;
			}
		}
	}

	public int getCellWorth() {
		return cellWorth;
	}

	public int getRowPlacement() {
		return rowPlacement;
	}

	public int getColPlacement() {
		return colPlacement;
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

}
