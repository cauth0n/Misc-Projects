package Game;

public class Move {

	private Tile tileFrom;
	private Tile tileTo;
	private Tile tileSelectionMove;

	public Move(Tile moveFrom, Tile moveTo) {
		this.tileFrom = moveFrom;
		this.tileTo = moveTo;
	}

	public Move(Tile tileSelectionMove) {
		this.tileSelectionMove = tileSelectionMove;
	}

	public Tile getTileFrom() {
		return tileFrom;
	}

	public Tile getTileTo() {
		return tileTo;
	}

	public Tile getTileSelectionMove() {
		return tileSelectionMove;
	}

	public void setTileTo(Tile tileTo) {
		this.tileTo = tileTo;
	}

	public void setTileFrom(Tile tileFrom) {
		this.tileFrom = tileFrom;
	}

	public void setTileSelectionMove(Tile tileSelectionMove) {
		this.tileSelectionMove = tileSelectionMove;
	}

}
