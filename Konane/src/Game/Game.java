package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	/*
	 * String colors declared as final because they will be assigned to Tile spots.
	 */
	protected final String black = "Black";
	protected final String white = "White";
	protected final String empty = "Empty";

	/*
	 * Players in our game. 
	 */
	private Player p1;
	private Player p2;
	private Player winner;

	/*
	 * Game information. gameBoard is the copy of our current game, from server-side. Note that it can only be editted from this side,
	 * but copies can be made of it and manipulated.
	 */
	private int boardSize;
	private Tile[][] gameBoard;
	
	/*
	 * ArrayList of moves at any state
	 */
	private ArrayList<Move> setOfAllPossibleStartingMoves = new ArrayList<Move>();
	private ArrayList<Move> setOfAllPossibleBlackMoves = new ArrayList<Move>();
	private ArrayList<Move> setOfAllPossibleWhiteMoves = new ArrayList<Move>();

	/**
	 * Constructor -- calls init() which handles game sequencing.
	 * 
	 * @param boardSize defines the size of the gameBoard that will be played on.
	 */
	public Game(int boardSize) {
		this.boardSize = boardSize;
		gameBoard = new Tile[boardSize][boardSize];
		init();
	}

	/**
	 * Initializer, builds all game information needed
	 */
	public void init() {
		setUpPlayers();
		initializeGame();
		buildFirstMoveSet();
		secondMove(firstMove());
		printBoard();
		loopManager();
	}

	/**
	 * Recursive method that continues adding moves to an arrayList passed by reference until the boardLimits are reached.
	 * 
	 * @param row
	 * @param col
	 * @param initialMove
	 * @param movesToReturn
	 * @return
	 */
	public boolean isJumpingLeftLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn) {

		if (((col - 2) < 0) || (gameBoard[row][col - 1].getColorValue().equals(empty)) || (!gameBoard[row][col - 2].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameBoard[row][col - 2]);
			movesToReturn.add(nextJump);
			return isJumpingLeftLegal(row, col - 2, initialMove, movesToReturn);
		}
	}

	public boolean isJumpingRightLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn) {

		if (((col + 2) > (boardSize - 1)) || (gameBoard[row][col + 1].getColorValue().equals(empty))
				|| (!gameBoard[row][col + 2].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameBoard[row][col + 2]);
			movesToReturn.add(nextJump);
			return isJumpingRightLegal(row, col + 2, initialMove, movesToReturn);
		}
	}

	public boolean isJumpingUpLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn) {

		if (((row - 2) < 0) || (gameBoard[row - 1][col].getColorValue().equals(empty)) || (!gameBoard[row - 2][col].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameBoard[row - 2][col]);
			movesToReturn.add(nextJump);
			return isJumpingUpLegal(row - 2, col, initialMove, movesToReturn);
		}
	}

	public boolean isJumpingDownLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn) {

		if (((row + 2) > (boardSize - 1)) || (gameBoard[row + 1][col].getColorValue().equals(empty))
				|| (!gameBoard[row + 2][col].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameBoard[row + 2][col]);
			movesToReturn.add(nextJump);
			return isJumpingDownLegal(row + 2, col, initialMove, movesToReturn);
		}
	}

	public void constructWhiteMoveSet() {
		for (Tile[] arr : gameBoard) {
			for (Tile tile : arr) {
				if (tile.getColorValue().equals(white)) {
					Move immediateLeft = new Move(tile, null);
					if (isLeftMove(tile.getRowPlacement(), tile.getColPlacement(), immediateLeft)) {
						ArrayList<Move> allLeftMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateLeft);
						if (isJumpingLeftLegal(immediateLeft.getTileTo().getRowPlacement(), immediateLeft.getTileTo().getColPlacement(),
								immediateLeft, allLeftMoves)) {
							for (Move move : allLeftMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateRight = new Move(tile, null);
					if (isRightMove(tile.getRowPlacement(), tile.getColPlacement(), immediateRight)) {
						ArrayList<Move> allRightMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateRight);
						if (isJumpingLeftLegal(immediateRight.getTileTo().getRowPlacement(), immediateRight.getTileTo().getColPlacement(),
								immediateLeft, allRightMoves)) {
							for (Move move : allRightMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateUp = new Move(tile, null);
					if (isUpMove(tile.getRowPlacement(), tile.getColPlacement(), immediateUp)) {
						ArrayList<Move> allUpMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateUp);
						if (isJumpingUpLegal(immediateUp.getTileTo().getRowPlacement(), immediateUp.getTileTo().getColPlacement(), immediateUp,
								allUpMoves)) {
							for (Move move : allUpMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateDown = new Move(tile, null);
					if (isDownMove(tile.getRowPlacement(), tile.getColPlacement(), immediateDown)) {
						ArrayList<Move> allDownMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateDown);
						if (isJumpingDownLegal(immediateDown.getTileTo().getRowPlacement(), immediateDown.getTileTo().getColPlacement(),
								immediateDown, allDownMoves)) {
							for (Move move : allDownMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
				}
			}
		}
	}

	public void constructBlackMoveSet() {
		for (Tile[] arr : gameBoard) {
			for (Tile tile : arr) {
				if (tile.getColorValue().equals(black)) {
					Move immediateLeft = new Move(tile, null);
					if (isLeftMove(tile.getRowPlacement(), tile.getColPlacement(), immediateLeft)) {
						ArrayList<Move> allLeftMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateLeft);
						if (isJumpingLeftLegal(immediateLeft.getTileTo().getRowPlacement(), immediateLeft.getTileTo().getColPlacement(),
								immediateLeft, allLeftMoves)) {
							for (Move move : allLeftMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateRight = new Move(tile, null);
					if (isRightMove(tile.getRowPlacement(), tile.getColPlacement(), immediateRight)) {
						ArrayList<Move> allRightMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateRight);
						if (isJumpingLeftLegal(immediateRight.getTileTo().getRowPlacement(), immediateRight.getTileTo().getColPlacement(),
								immediateLeft, allRightMoves)) {
							for (Move move : allRightMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateUp = new Move(tile, null);
					if (isUpMove(tile.getRowPlacement(), tile.getColPlacement(), immediateUp)) {
						ArrayList<Move> allUpMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateUp);
						if (isJumpingUpLegal(immediateUp.getTileTo().getRowPlacement(), immediateUp.getTileTo().getColPlacement(), immediateUp,
								allUpMoves)) {
							for (Move move : allUpMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateDown = new Move(tile, null);
					if (isDownMove(tile.getRowPlacement(), tile.getColPlacement(), immediateDown)) {
						ArrayList<Move> allDownMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateDown);
						if (isJumpingDownLegal(immediateDown.getTileTo().getRowPlacement(), immediateDown.getTileTo().getColPlacement(),
								immediateDown, allDownMoves)) {
							for (Move move : allDownMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
				}
			}
		}
	}

	public void buildFirstMoveSet() {
		Move UL = new Move(gameBoard[0][0]);
		Move LL = new Move(gameBoard[boardSize - 1][0]);
		Move UR = new Move(gameBoard[0][boardSize - 1]);
		Move LR = new Move(gameBoard[boardSize - 1][boardSize - 1]);
		Move MUL = new Move(gameBoard[(boardSize / 2) - 1][(boardSize / 2) - 1]);
		Move MUR = new Move(gameBoard[(boardSize / 2) - 1][(boardSize / 2)]);
		Move MLL = new Move(gameBoard[(boardSize / 2)][(boardSize / 2) - 1]);
		Move MLR = new Move(gameBoard[(boardSize / 2)][(boardSize / 2)]);
		setOfAllPossibleStartingMoves.add(UL);
		setOfAllPossibleStartingMoves.add(LL);
		setOfAllPossibleStartingMoves.add(UR);
		setOfAllPossibleStartingMoves.add(LR);
		setOfAllPossibleStartingMoves.add(MUL);
		setOfAllPossibleStartingMoves.add(MLL);
		setOfAllPossibleStartingMoves.add(MUR);
		setOfAllPossibleStartingMoves.add(MLR);
	}

	public void printMoves(ArrayList<Move> moves) {
		for (Move move : moves) {
			System.out.println("From [" + move.getTileFrom().getRowPlacement() + ", " + move.getTileFrom().getColPlacement() + "]");
			System.out.println("To [" + move.getTileTo().getRowPlacement() + ", " + move.getTileTo().getColPlacement() + "]");
		}
	}

	/**
	 * Main Loop Manager of the Game.
	 * 
	 * P1 is offered a turn, and after that turn, the board is checked to see if it is a win. Then,
	 * if no win happened, P2 is offered a turn. Then, the board is checked again.
	 */
	public void loopManager() {

		boolean isGameFinished = false;

		while (!isGameFinished) {
			Move p1ValueToUpdate = new Move(null, null);
			Move p2ValueToUpdate = new Move(null, null);
			boolean p1Move = false;
			printMove("p1");

			while (!p1Move) {
				constructBlackMoveSet();
				System.out.println("Player 1 has " + setOfAllPossibleBlackMoves.size() + " moves left.");
				printMoves(setOfAllPossibleBlackMoves);
				if (setOfAllPossibleBlackMoves.isEmpty()) {
					winner = p2;
					isGameFinished = true;
					break;
				}

				p1ValueToUpdate = p1.makeAMove();
				if (p1.getPlayerType().equals("Human")) {
					for (Move move : setOfAllPossibleBlackMoves) {
						if (isSameMove(p1ValueToUpdate, move)) {
							p1Move = true;
							setOfAllPossibleBlackMoves.clear();
							break;
						}
					}
					System.out.println("Bad move, p1");
				} else {
					p1Move = true;
					setOfAllPossibleBlackMoves.clear();
				}
			}
			if (isGameFinished) {
				break;
			}
			applyUpdate(p1ValueToUpdate);

			boolean p2Move = false;
			printMove("p2");
			while (!p2Move) {
				constructWhiteMoveSet();
				System.out.println("Player 2 has " + setOfAllPossibleWhiteMoves.size() + " moves left.");
				printMoves(setOfAllPossibleWhiteMoves);

				if (setOfAllPossibleWhiteMoves.isEmpty()) {
					winner = p1;
					isGameFinished = true;
					break;
				}

				p2ValueToUpdate = p2.makeAMove();
				if (p1.getPlayerType().equals("Human")) {
					for (Move move : setOfAllPossibleWhiteMoves) {
						if (isSameMove(p2ValueToUpdate, move)) {
							p2Move = true;
							setOfAllPossibleWhiteMoves.clear();
							break;
						}
					}
					System.out.println("Bad move, p2");
				} else {
					p2Move = true;
					setOfAllPossibleWhiteMoves.clear();
				}
			}
			if (isGameFinished) {
				break;
			}
			applyUpdate(p2ValueToUpdate);
		}
		printWinner();
	}

	public void printWinner() {
		if (winner.getColorValue().equals(black)) {
			System.out.println("Player 1 wins!!!!");
		} else {
			System.out.println("Player 2 wins!!!!");
		}
	}

	public boolean isSameMove(Move one, Move two) {
		if (one.getTileFrom().getRowPlacement() == two.getTileFrom().getRowPlacement()
				&& one.getTileFrom().getColPlacement() == two.getTileFrom().getColPlacement()
				&& one.getTileTo().getRowPlacement() == two.getTileTo().getRowPlacement()
				&& one.getTileTo().getColPlacement() == two.getTileTo().getColPlacement()) {
			return true;
		}
		return false;

	}

	/**
	 * Checks if a move exists left of the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isLeftMove(int row, int column, Move moveToReturn) {
		if (column == 0 || column == 1) {	// can't check left with that few spots
			return false;
		} else {
			if (gameBoard[row][column - 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row][column - 2].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameBoard[row][column - 2]);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists right of the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isRightMove(int row, int column, Move moveToReturn) {
		if (column == (boardSize - 1) || column == (boardSize - 2)) {	// can't check right with that
																		// few spots
			return false;
		} else {
			if (gameBoard[row][column + 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row][column + 2].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameBoard[row][column + 2]);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists above the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isUpMove(int row, int column, Move moveToReturn) {
		if (row == 0 || row == 1) {	// can't check up with that few spots
			return false;
		} else {
			if (gameBoard[row - 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row - 2][column].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameBoard[row - 2][column]);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists below the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isDownMove(int row, int column, Move moveToReturn) {
		if (row == (boardSize - 1) || row == (boardSize - 2)) {	// can't check up with that few
																// spots
			return false;
		} else {
			if (gameBoard[row + 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row + 2][column].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameBoard[row + 2][column]);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * ALERT ALERT ALERT --- Not sure if a piece is removed or not after it is done jumping. I know
	 * the pieces any piece jumps over are removed. But is the piece that does the jumping removed?
	 * The only change would be the last variable. [move.getRowTo()][move.getColTo()]
	 * 
	 * Assuming here that it is NOT removed. That it stays. ---
	 * 
	 * Applies update to global gameBoard. Move is already established as legal.
	 * 
	 * @param move
	 *            Last move made
	 * @param color
	 *            Color making the last move
	 */
	public void applyUpdate(Move move) {

		int iterator;
		move.getTileFrom().setColorValue(empty);

		if (isMoveHorizontal(move)) {
			iterator = move.getTileFrom().getColPlacement();

			while (iterator > move.getTileTo().getColPlacement()) {
				gameBoard[move.getTileFrom().getRowPlacement()][iterator].setColorValue(empty);
				iterator--;
			}

			while (iterator < move.getTileTo().getColPlacement()) {
				gameBoard[move.getTileFrom().getRowPlacement()][iterator].setColorValue(empty);
				iterator++;
			}
		} else {
			iterator = move.getTileFrom().getRowPlacement();

			while (iterator > move.getTileTo().getRowPlacement()) {
				gameBoard[iterator][move.getTileFrom().getColPlacement()].setColorValue(empty);
				iterator--;
			}

			while (iterator < move.getTileTo().getRowPlacement()) {
				gameBoard[iterator][move.getTileFrom().getColPlacement()].setColorValue(empty);
				iterator++;
			}
		}
		if (((move.getTileTo().getRowPlacement() + move.getTileTo().getColPlacement()) % 2) == 0) {
			move.getTileTo().setColorValue(black);
		} else {
			move.getTileTo().setColorValue(white);
		}
		p1.updateStoredGameBoard(gameBoard, setOfAllPossibleBlackMoves, black);
		p2.updateStoredGameBoard(gameBoard, setOfAllPossibleWhiteMoves, white);
	}

	/**
	 * Checks to see if move made was horizontal or vertical (Can only be one).
	 * 
	 * 
	 * @param move
	 *            move we are checking for.
	 * 
	 * 
	 * @return horizontal if rows match vertical if columns match
	 */
	public boolean isMoveHorizontal(Move move) {
		if (move.getTileFrom().getRowPlacement() == move.getTileTo().getRowPlacement()) {
			return true;
		}
		return false;
	}

	public boolean isSameTile(Tile one, Tile two) {
		boolean valid = false;
		if (one.getRowPlacement() == two.getRowPlacement() && one.getColPlacement() == two.getColPlacement()) {
			valid = true;
		}
		return valid;
	}

	public void buildSecondMoveSet(Move firstMove) {
		if (firstMove.getTileSelectionMove().getRowPlacement() == 0) {
			if (firstMove.getTileSelectionMove().getColPlacement() == 0) {
				Move right = new Move(gameBoard[0][1]);
				Move down = new Move(gameBoard[1][0]);
				setOfAllPossibleStartingMoves.add(right);
				setOfAllPossibleStartingMoves.add(down);
			} else {
				Move left = new Move(gameBoard[0][boardSize - 2]);
				Move down = new Move(gameBoard[1][boardSize - 1]);
				setOfAllPossibleStartingMoves.add(left);
				setOfAllPossibleStartingMoves.add(down);
			}
		} else if (firstMove.getTileSelectionMove().getRowPlacement() == boardSize - 1) {
			if (firstMove.getTileSelectionMove().getColPlacement() == 0) {
				Move right = new Move(gameBoard[boardSize - 1][1]);
				Move up = new Move(gameBoard[boardSize - 2][0]);
				setOfAllPossibleStartingMoves.add(right);
				setOfAllPossibleStartingMoves.add(up);
			} else {
				Move left = new Move(gameBoard[boardSize - 1][boardSize - 2]);
				Move up = new Move(gameBoard[boardSize - 2][boardSize - 1]);
				setOfAllPossibleStartingMoves.add(left);
				setOfAllPossibleStartingMoves.add(up);
			}
		} else {
			Move left = new Move(
					gameBoard[firstMove.getTileSelectionMove().getRowPlacement()][firstMove.getTileSelectionMove().getColPlacement() - 1]);
			Move right = new Move(
					gameBoard[firstMove.getTileSelectionMove().getRowPlacement()][firstMove.getTileSelectionMove().getColPlacement() + 1]);
			Move up = new Move(gameBoard[firstMove.getTileSelectionMove().getRowPlacement() - 1][firstMove.getTileSelectionMove().getColPlacement()]);
			Move down = new Move(
					gameBoard[firstMove.getTileSelectionMove().getRowPlacement() + 1][firstMove.getTileSelectionMove().getColPlacement()]);
			setOfAllPossibleStartingMoves.add(left);
			setOfAllPossibleStartingMoves.add(up);
			setOfAllPossibleStartingMoves.add(right);
			setOfAllPossibleStartingMoves.add(down);

		}
	}

	/**
	 * Second move of game. Must be white, and adjacent to the first move.
	 * 
	 * 
	 * @param initialMove
	 *            First move made by other player
	 */
	public void secondMove(Move initialMove) {
		boolean valid = false;
		buildSecondMoveSet(initialMove);
		p2.updateStoredGameBoard(gameBoard, setOfAllPossibleWhiteMoves, white);
		Move secondMove;
		if (p2.getPlayerType().equals("Human")) {
			do {
				System.out.println("Wrong second move! Try again p2!");
				secondMove = p2.secondMove(setOfAllPossibleStartingMoves);
				for (Move iter : setOfAllPossibleStartingMoves) {
					if (isSameTile(secondMove.getTileSelectionMove(), iter.getTileSelectionMove())) {
						valid = true;
						break;
					}
				}

			} while (!valid);
		} else {
			secondMove = p2.secondMove(setOfAllPossibleStartingMoves);
		}
		setOfAllPossibleStartingMoves.clear();
		secondMove.getTileSelectionMove().setColorValue(empty);
	}

	/**
	 * Simply show player the board.
	 * 
	 * 
	 * @param player
	 *            Player that sees the board next
	 */
	public void printMove(String player) {
		System.out.format("Your move, %s.\nThe board looks like this:\n\n", player);
		printBoard();
	}

	/**
	 * First Move. This has a unique moveset.
	 * 
	 * 
	 * @return first move choice. Needed for validity of second move.
	 */
	public Move firstMove() {
		boolean valid = false;
		p1.updateStoredGameBoard(gameBoard, setOfAllPossibleBlackMoves, black);
		Move firstMove = null;
		if (p1.getPlayerType().equals("Human")) {
			do {
				System.out.println("Bad starting move. Try again P1.");
				firstMove = p1.firstMove(setOfAllPossibleStartingMoves);
				for (Move iter : setOfAllPossibleStartingMoves) {
					if (isSameTile(firstMove.getTileSelectionMove(), iter.getTileSelectionMove())) {
						valid = true;
						break;
					}
				}

			} while (!valid);
		} else {
			firstMove = p1.firstMove(setOfAllPossibleStartingMoves);
		}
		setOfAllPossibleStartingMoves.clear();
		firstMove.getTileSelectionMove().setColorValue(empty);
		return firstMove;
	}

	/**
	 * Prints menu for new players to choose from.
	 */
	public void printPlayerMenu() {
		System.out.println("1: Human Player");
		System.out.println("2: MiniMax without alpha/beta pruning");
		System.out.println("3: MiniMax with alpah/beta pruning");
	}

	/**
	 * Builds a player at runtime, based on user choice. Returns a boolean representing the validity
	 * of the choices.
	 * 
	 * Bad choices mean go back to chooser again.
	 * 
	 * color means black or white -- first player is black. second player is white.
	 * 
	 * 
	 * @param player
	 *            Player object (p1 or p2) to chose from
	 * @param playerChoice
	 *            Human, minimax w/o ab pruning, or minimax with ab pruning.
	 * @param color
	 *            which color player will be
	 * 
	 * 
	 * @return true if valid choice is made false if invalid choice is made
	 */
	public Player playerBuilder(int playerChoice, String color) {
		Player retVal = null;

		switch (playerChoice) {
		case 1:
			retVal = new Human(color);
			break;
		case 2:
			int treeDepth = promptTreeDepth();
			retVal = new MiniMaxWithoutAB(color, treeDepth);
			break;
		case 3:
			int treeDepth3 = promptTreeDepth();
			retVal = new MiniMaxWithAB(color, treeDepth3);
			break;
		}

		return retVal;
	}

	public int promptTreeDepth() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the depth of the tree for this AI player.");
		return in.nextInt();
	}

	/**
	 * Sets up 2 players, querying them for info until 2 players of the 3 player choices are met
	 */
	public void setUpPlayers() {
		p1 = null;
		p2 = null;
		int choice;
		Scanner scan = new Scanner(System.in);
		while (p1 == null) {
			System.out.println("Select player 1 -- PLAYER 1 WILL BE BLACK AND GO FIRST");
			printPlayerMenu();
			choice = scan.nextInt();

			p1 = playerBuilder(choice, black);
			if (p1 == null) {
				System.out.println("Invalid selection. Please try again.");
			}
		}
		while (p2 == null) {
			System.out.println("Select player 2 -- PLAYER 2 WILL BE WHITE AND GO SECOND");
			printPlayerMenu();
			choice = scan.nextInt();

			p2 = playerBuilder(choice, white);
			if (p2 == null) {
				System.out.println("Invalid selection. Please try again.");
			}
		}
	}

	/**
	 * Initialize Game -- Black has a piece in the Upper left hand corner, and every other spot
	 * thereafter.
	 * 
	 * Logic below based on the fact that in a square array, every other spot is either an even
	 * number, or odd.
	 */
	public void initializeGame() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if ((i + j) % 2 == 0) {
					gameBoard[i][j] = new Tile(black, i, j, boardSize);
				} else {
					gameBoard[i][j] = new Tile(white, i, j, boardSize);
				}
			}
		}
	}

	/**
	 * Returns a dashed line the same size as the game board.
	 * 
	 * Used for printing lines after every row.
	 * 
	 * 
	 * @return formatted line representing length of board as dashes "--"
	 */
	public String printBoardLine() {
		String line = "";
		for (int i = 0; i < ((boardSize * 4) + 1); i++) {
			line += "-";
		}
		return line;
	}

	/**
	 * Print the game board. Prints by iterating through entire array, checking if black or white,
	 * and printing
	 */
	public void printBoard() {

		System.out.println(printBoardLine());
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print("|");
				if (gameBoard[i][j].getColorValue().equals(black)) {
					System.out.print(" b ");
				} else if (gameBoard[i][j].getColorValue().equals(white)) {
					System.out.print(" w ");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println("|");
			System.out.println(printBoardLine());
		}
	}

	/**
	 * gets a copy of the current gameboard. Players use this as a copy for them to work on.
	 * 
	 * @return current gameboard.
	 */
	public Tile[][] getGameBoard() {
		return gameBoard;
	}

}
