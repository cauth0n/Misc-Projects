package Game;

import java.util.ArrayList;
import java.util.Random;

public class MiniMaxWithoutAB extends Player {
	private int treeDepth;
	private boolean player1;	// 1 for black, 0 for white
	private int boardSize;
	private int counter = 1;

	public MiniMaxWithoutAB(String colorValue, int treeDepth) {
		super(colorValue);
		playerType = "MiniWithout";
		this.treeDepth = treeDepth;

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
	public Move firstMove(ArrayList<Move> setOfAllStartingMoves) {
		player1 = true;
		Random r = new Random();
		int rand = r.nextInt(setOfAllStartingMoves.size());
		return setOfAllStartingMoves.get(rand);
	}

	@Override
	public Move secondMove(ArrayList<Move> setOfAllStartingMoves) {
		player1 = false;
		Random r = new Random();
		int rand = r.nextInt(setOfAllStartingMoves.size());
		return setOfAllStartingMoves.get(rand);
	}

	@Override
	public Move makeAMove() {
		return minimax(copyOfGameBoard);
	}

	public Move minimax(Tile[][] gameState) {
		Tile[][] nextState = gameState;
		int x = minValue(nextState);

		printBoard(nextState);

		for (Move moves : action(gameState)) {
			boolean valid = true;
			Tile[][] checkState = nextState.clone();
			undoMove(checkState, moves);

			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (!checkState[i][j].getColorValue().equals(nextState[i][j].getColorValue())) {
						valid = false;
					}
				}
			}
			if (valid) {
				return moves;
			}
		}

		return null;

	}

	public int maxValue(Tile[][] state) {

		if (terminalTest(state, true) || counter == treeDepth) {
			return utilityFunction(state, true);
		}
		counter++;

		ArrayList<Move> possibleMoves = constructWhiteMoveSet(state);
		int max = Integer.MIN_VALUE;
		for (Move possibleMove : possibleMoves) {
			max = Math.max(max, minValue(result(state, possibleMove)));
			state = undoMove(state, possibleMove);
		}
		return max;
	}

	public int minValue(Tile[][] state) {
		if (terminalTest(state, false) || counter == treeDepth) {
			return utilityFunction(state, false);
		}
		counter++;
		ArrayList<Move> possibleMoves = constructBlackMoveSet(state);

		int min = Integer.MAX_VALUE;
		for (Move possibleMove : possibleMoves) {
			min = Math.min(min, maxValue(result(state, possibleMove)));
			state = undoMove(state, possibleMove);	// /omg I literally spent 10 hours on this
													// problem
			// (pass by reference issues with a game state
			// object)
		}
		return min;
	}

	public Tile[][] undoMove(Tile[][] state, Move move) {
		Tile[][] stateToReturn = state;
		int iterator;
		if ((move.getTileTo().getRowPlacement() + move.getTileTo().getColPlacement()) % 2 == 0) {
			if (isMoveHorizontal(move)) {
				iterator = move.getTileTo().getColPlacement();
				stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(empty);
				while (iterator <= move.getTileFrom().getColPlacement()) {

					if ((move.getTileTo().getRowPlacement() + iterator) % 2 == 1) {
						stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(white);
					}
					iterator++;
				}
				iterator = move.getTileTo().getColPlacement();
				while (iterator >= move.getTileFrom().getColPlacement()) {

					if ((move.getTileTo().getRowPlacement() + iterator) % 2 == 1) {
						stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(white);
					}
					iterator--;
				}
				stateToReturn[move.getTileFrom().getRowPlacement()][move.getTileFrom().getColPlacement()].setColorValue(black);
			} else {
				iterator = move.getTileTo().getRowPlacement();
				stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(empty);
				while (iterator <= move.getTileFrom().getRowPlacement()) {

					if ((move.getTileTo().getColPlacement() + iterator) % 2 == 1) {
						stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(white);
					}
					iterator++;
				}
				iterator = move.getTileTo().getRowPlacement();
				while (iterator >= move.getTileFrom().getRowPlacement()) {

					if ((move.getTileTo().getColPlacement() + iterator) % 2 == 1) {
						stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(white);
					}
					iterator--;
				}
				stateToReturn[move.getTileFrom().getRowPlacement()][move.getTileFrom().getColPlacement()].setColorValue(black);
			}
		} else {
			if (isMoveHorizontal(move)) {
				iterator = move.getTileTo().getColPlacement();
				stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(empty);
				while (iterator <= move.getTileFrom().getColPlacement()) {

					if ((move.getTileTo().getRowPlacement() + iterator) % 2 == 0) {
						stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(black);
					}
					iterator++;
				}
				iterator = move.getTileTo().getColPlacement();
				while (iterator >= move.getTileFrom().getColPlacement()) {

					if ((move.getTileTo().getRowPlacement() + iterator) % 2 == 0) {
						stateToReturn[move.getTileTo().getRowPlacement()][iterator].setColorValue(black);
					}
					iterator--;
				}
				stateToReturn[move.getTileFrom().getRowPlacement()][move.getTileFrom().getColPlacement()].setColorValue(white);
			} else {
				iterator = move.getTileTo().getRowPlacement();
				stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(empty);
				while (iterator <= move.getTileFrom().getRowPlacement()) {

					if ((move.getTileTo().getColPlacement() + iterator) % 2 == 0) {
						stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(black);
					}
					iterator++;
				}
				iterator = move.getTileTo().getRowPlacement();
				while (iterator >= move.getTileFrom().getRowPlacement()) {

					if ((move.getTileTo().getColPlacement() + iterator) % 2 == 0) {
						stateToReturn[iterator][move.getTileTo().getColPlacement()].setColorValue(black);
					}
					iterator--;
				}
				stateToReturn[move.getTileFrom().getRowPlacement()][move.getTileFrom().getColPlacement()].setColorValue(white);
			}

		}
		return stateToReturn;
	}

	public boolean terminalTest(Tile[][] gameState, boolean player) {	// 1 FOR BLACK PLAYER, 0 FOR
		// WHITE
		if (player) {
			if (constructBlackMoveSet(gameState).isEmpty()) {
				return true;
			}
		} else {
			if (constructWhiteMoveSet(gameState).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Move> action(Tile[][] gameState) {
		if (player1) {
			return constructBlackMoveSet(gameState);
		} else {
			return constructWhiteMoveSet(gameState);
		}
	}

	public int utilityFunction(Tile[][] gameState, boolean player) {
		int heuristicValueOfNode = 0;
		if (player) {
			for (Tile[] tiles : gameState) {
				for (Tile tile : tiles) {
					if (tile.getColorValue().equals("Black")) {
						heuristicValueOfNode += tile.getCellWorth();
					}
				}
			}
			heuristicValueOfNode += (constructBlackMoveSet(gameState).size()) * 3;
		} else {
			for (Tile[] tiles : gameState) {
				for (Tile tile : tiles) {
					if (tile.getColorValue().equals("White")) {
						heuristicValueOfNode += tile.getCellWorth();
					}
				}
			}
			heuristicValueOfNode += (constructWhiteMoveSet(gameState).size()) * 3;
		}
		return heuristicValueOfNode;
	}

	@Override
	public void updateStoredGameBoard(Tile[][] gameBoardCopy, ArrayList<Move> copyOfMoves, String color) {
		copyOfGameBoard = gameBoardCopy;
		boardSize = gameBoardCopy.length;
		if (color.equals("Black")) {
			setOfAllBlackMoves = copyOfMoves;
		} else {
			setOfAllWhiteMoves = copyOfMoves;
		}
	}

	public Tile[][] result(Tile[][] startingState, Move moveToApply) {

		int iterator;
		startingState[moveToApply.getTileFrom().getRowPlacement()][moveToApply.getTileFrom().getColPlacement()].setColorValue(empty);
		// moveToApply.getTileFrom().setColorValue(empty);

		if (isMoveHorizontal(moveToApply)) {
			iterator = moveToApply.getTileFrom().getColPlacement();

			while (iterator > moveToApply.getTileTo().getColPlacement()) {
				startingState[moveToApply.getTileFrom().getRowPlacement()][iterator].setColorValue(empty);
				iterator--;
			}

			while (iterator < moveToApply.getTileTo().getColPlacement()) {
				startingState[moveToApply.getTileFrom().getRowPlacement()][iterator].setColorValue(empty);
				iterator++;
			}
		} else {
			iterator = moveToApply.getTileFrom().getRowPlacement();

			while (iterator > moveToApply.getTileTo().getRowPlacement()) {
				startingState[iterator][moveToApply.getTileFrom().getColPlacement()].setColorValue(empty);
				iterator--;
			}

			while (iterator < moveToApply.getTileTo().getRowPlacement()) {
				startingState[iterator][moveToApply.getTileFrom().getColPlacement()].setColorValue(empty);
				iterator++;
			}
		}
		if (((moveToApply.getTileTo().getRowPlacement() + moveToApply.getTileTo().getColPlacement()) % 2) == 0) {
			startingState[moveToApply.getTileTo().getRowPlacement()][moveToApply.getTileTo().getColPlacement()].setColorValue(black);
			// moveToApply.getTileTo().setColorValue(black);
		} else {
			startingState[moveToApply.getTileTo().getRowPlacement()][moveToApply.getTileTo().getColPlacement()].setColorValue(white);
			// moveToApply.getTileTo().setColorValue(white);
		}

		return startingState;
	}

	public boolean isMoveHorizontal(Move move) {
		if (move.getTileFrom().getRowPlacement() == move.getTileTo().getRowPlacement()) {
			return true;
		}
		return false;
	}

	public boolean isJumpingLeftLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn, Tile[][] gameState) {

		if (((col - 2) < 0) || (gameState[row][col - 1].getColorValue().equals(empty)) || (!gameState[row][col - 2].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameState[row][col - 2]);
			movesToReturn.add(nextJump);
			return isJumpingLeftLegal(row, col - 2, initialMove, movesToReturn, gameState);
		}
	}

	public boolean isJumpingRightLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn, Tile[][] gameState) {

		if (((col + 2) > (boardSize - 1)) || (gameState[row][col + 1].getColorValue().equals(empty))
				|| (!gameState[row][col + 2].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameState[row][col + 2]);
			movesToReturn.add(nextJump);
			return isJumpingRightLegal(row, col + 2, initialMove, movesToReturn, gameState);
		}
	}

	public boolean isJumpingUpLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn, Tile[][] gameState) {

		if (((row - 2) < 0) || (gameState[row - 1][col].getColorValue().equals(empty)) || (!gameState[row - 2][col].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameState[row - 2][col]);
			movesToReturn.add(nextJump);
			return isJumpingUpLegal(row - 2, col, initialMove, movesToReturn, gameState);
		}
	}

	public boolean isJumpingDownLegal(int row, int col, Move initialMove, ArrayList<Move> movesToReturn, Tile[][] gameState) {

		if (((row + 2) > (boardSize - 1)) || (gameState[row + 1][col].getColorValue().equals(empty))
				|| (!gameState[row + 2][col].getColorValue().equals(empty))) {
			return true;
		} else {
			Move nextJump = new Move(initialMove.getTileFrom(), gameState[row + 2][col]);
			movesToReturn.add(nextJump);
			return isJumpingDownLegal(row + 2, col, initialMove, movesToReturn, gameState);
		}
	}

	public ArrayList<Move> constructWhiteMoveSet(Tile[][] gameState) {
		ArrayList<Move> setOfAllPossibleWhiteMoves = new ArrayList<Move>(15);
		for (Tile[] arr : gameState) {
			for (Tile tile : arr) {
				if (tile.getColorValue().equals(white)) {
					Move immediateLeft = new Move(tile, null);
					if (isLeftMove(tile.getRowPlacement(), tile.getColPlacement(), immediateLeft, gameState)) {
						ArrayList<Move> allLeftMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateLeft);
						if (isJumpingLeftLegal(immediateLeft.getTileTo().getRowPlacement(), immediateLeft.getTileTo().getColPlacement(),
								immediateLeft, allLeftMoves, gameState)) {
							for (Move move : allLeftMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateRight = new Move(tile, null);
					if (isRightMove(tile.getRowPlacement(), tile.getColPlacement(), immediateRight, gameState)) {
						ArrayList<Move> allRightMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateRight);
						if (isJumpingLeftLegal(immediateRight.getTileTo().getRowPlacement(), immediateRight.getTileTo().getColPlacement(),
								immediateLeft, allRightMoves, gameState)) {
							for (Move move : allRightMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateUp = new Move(tile, null);
					if (isUpMove(tile.getRowPlacement(), tile.getColPlacement(), immediateUp, gameState)) {
						ArrayList<Move> allUpMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateUp);
						if (isJumpingUpLegal(immediateUp.getTileTo().getRowPlacement(), immediateUp.getTileTo().getColPlacement(), immediateUp,
								allUpMoves, gameState)) {
							for (Move move : allUpMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
					Move immediateDown = new Move(tile, null);
					if (isDownMove(tile.getRowPlacement(), tile.getColPlacement(), immediateDown, gameState)) {
						ArrayList<Move> allDownMoves = new ArrayList<Move>();
						setOfAllPossibleWhiteMoves.add(immediateDown);
						if (isJumpingDownLegal(immediateDown.getTileTo().getRowPlacement(), immediateDown.getTileTo().getColPlacement(),
								immediateDown, allDownMoves, gameState)) {
							for (Move move : allDownMoves) {
								setOfAllPossibleWhiteMoves.add(move);
							}
						}
					}
				}
			}
		}
		return setOfAllPossibleWhiteMoves;
	}

	public ArrayList<Move> constructBlackMoveSet(Tile[][] gameState) {
		ArrayList<Move> setOfAllPossibleBlackMoves = new ArrayList<Move>(15);
		for (Tile[] arr : gameState) {
			for (Tile tile : arr) {
				if (tile.getColorValue().equals(black)) {
					Move immediateLeft = new Move(tile, null);
					if (isLeftMove(tile.getRowPlacement(), tile.getColPlacement(), immediateLeft, gameState)) {
						ArrayList<Move> allLeftMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateLeft);
						if (isJumpingLeftLegal(immediateLeft.getTileTo().getRowPlacement(), immediateLeft.getTileTo().getColPlacement(),
								immediateLeft, allLeftMoves, gameState)) {
							for (Move move : allLeftMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateRight = new Move(tile, null);
					if (isRightMove(tile.getRowPlacement(), tile.getColPlacement(), immediateRight, gameState)) {
						ArrayList<Move> allRightMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateRight);
						if (isJumpingLeftLegal(immediateRight.getTileTo().getRowPlacement(), immediateRight.getTileTo().getColPlacement(),
								immediateLeft, allRightMoves, gameState)) {
							for (Move move : allRightMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateUp = new Move(tile, null);
					if (isUpMove(tile.getRowPlacement(), tile.getColPlacement(), immediateUp, gameState)) {
						ArrayList<Move> allUpMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateUp);
						if (isJumpingUpLegal(immediateUp.getTileTo().getRowPlacement(), immediateUp.getTileTo().getColPlacement(), immediateUp,
								allUpMoves, gameState)) {
							for (Move move : allUpMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
					Move immediateDown = new Move(tile, null);
					if (isDownMove(tile.getRowPlacement(), tile.getColPlacement(), immediateDown, gameState)) {
						ArrayList<Move> allDownMoves = new ArrayList<Move>();
						setOfAllPossibleBlackMoves.add(immediateDown);
						if (isJumpingDownLegal(immediateDown.getTileTo().getRowPlacement(), immediateDown.getTileTo().getColPlacement(),
								immediateDown, allDownMoves, gameState)) {
							for (Move move : allDownMoves) {
								setOfAllPossibleBlackMoves.add(move);
							}
						}
					}
				}
			}
		}
		return setOfAllPossibleBlackMoves;
	}

	public boolean isLeftMove(int row, int column, Move moveToReturn, Tile[][] gameState) {
		if (column == 0 || column == 1) {	// can't check left with that few spots
			return false;
		} else {
			if (gameState[row][column - 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameState[row][column - 2].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameState[row][column - 2]);
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
	public boolean isRightMove(int row, int column, Move moveToReturn, Tile[][] gameState) {
		if (column == (boardSize - 1) || column == (boardSize - 2)) {	// can't check right with that
			// few spots
			return false;
		} else {
			if (gameState[row][column + 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameState[row][column + 2].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameState[row][column + 2]);
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
	public boolean isUpMove(int row, int column, Move moveToReturn, Tile[][] gameState) {
		if (row == 0 || row == 1) {	// can't check up with that few spots
			return false;
		} else {
			if (gameState[row - 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameState[row - 2][column].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameState[row - 2][column]);
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
	public boolean isDownMove(int row, int column, Move moveToReturn, Tile[][] gameState) {
		if (row == (boardSize - 1) || row == (boardSize - 2)) {	// can't check up with that few
			// spots
			return false;
		} else {
			if (gameState[row + 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameState[row + 2][column].getColorValue().equals(empty)) {
					moveToReturn.setTileTo(gameState[row + 2][column]);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public String printBoardLine() {
		int boardSize = copyOfGameBoard.length;
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
	public void printBoard(Tile[][] gameState) {
		int boardSize = gameState.length;
		System.out.println(printBoardLine());
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print("|");
				if (gameState[i][j].getColorValue().equals(black)) {
					System.out.print(" b ");
				} else if (gameState[i][j].getColorValue().equals(white)) {
					System.out.print(" w ");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println("|");
			System.out.println(printBoardLine());
		}
	}

	public void printMoves(ArrayList<Move> moves) {
		for (Move move : moves) {
			System.out.println("From [" + move.getTileFrom().getRowPlacement() + ", " + move.getTileFrom().getColPlacement() + "]");
			System.out.println("To [" + move.getTileTo().getRowPlacement() + ", " + move.getTileTo().getColPlacement() + "]");
		}
	}
}