package model;

import java.util.Random;



/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {
	
	/* Include whatever instance variables you think are useful. */

	private Random random; // A random generator to generate different colors  
	private int score = 0; // The score of the player
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random = random;
	}

	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	public boolean isGameOver() {
		for(int i = 0 ; i < board[0].length;i++) {
			if(board[board.length-1][i]!=BoardCell.EMPTY){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {
		return score;
	}

	
	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do 
	 * the following:
	 * 
	 * 1. Shift the existing rows down by one position.
	 * 2. Insert a row of random BoardCell objects at the top
	 * of the board. The row will be filled from left to right with cells 
	 * obtained by calling BoardCell.getNonEmptyRandomBoardCell().  (The Random
	 * number generator passed to the constructor of this class should be
	 * passed as the argument to this method call.)
	 */
	public void nextAnimationStep() {
		int emptyRow = theFirstEmptyRow();
		if(emptyRow == 0) {
			for(int i = 0; i < board[0].length;i++) {
				int index = random.nextInt(5);
				board[0][i] = BoardCell.values()[index % 4];
			}
		} else if(emptyRow + 1 <= getRows()){
			for (int i = emptyRow; i > 0; i--) {
				for (int j = 0; j < board[0].length; j++) {
					board[i][j] = board[i-1][j];
				}
			}
			for (int i = 0; i < board[0].length; i++) {
				int index = random.nextInt(5);
				board[0][i] = BoardCell.values()[index % 4];
			}
		}
		if(isGameOver()) {
			return;
		}
	}
	
	// This method returns the index of first empty row traversing from the last row 
	private int theFirstEmptyRow() {
		for(int i = board.length-1; i >= 0; i--) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j]!=BoardCell.EMPTY){
					return i+1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * This method is called when the user clicks a cell on the board.
	 * If the selected cell is not empty, it will be set to BoardCell.EMPTY, 
	 * along with any adjacent cells that are the same color as this one.  
	 * (This includes the cells above, below, to the left, to the right, and 
	 * all in all four diagonal directions.)
	 * 
	 * If any rows on the board become empty as a result of the removal of 
	 * cells then those rows will "collapse", meaning that all non-empty 
	 * rows beneath the collapsing row will shift upward. 
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for 
	 * invalid row or "Invalid column index" for invalid column.  We check 
	 * for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (rowIndex < 0 || rowIndex > board.length - 1) {
			throw new IllegalArgumentException("Invalid row index");
		}
		if (colIndex < 0 || colIndex > board[0].length - 1) {
			throw new IllegalArgumentException("Invalid column index");
		}
		
		if (board[rowIndex][colIndex] != BoardCell.EMPTY) {
			if (colIndex < board[0].length - 1 && colIndex >= 0) {
				if (board[rowIndex][colIndex] == board[rowIndex][colIndex + 1]) {
					board[rowIndex][colIndex + 1] = BoardCell.EMPTY;
					score++;
				}
			}
			if (colIndex <= board[0].length - 1 && colIndex > 0) {
				if (board[rowIndex][colIndex] == board[rowIndex][colIndex - 1]) {
					board[rowIndex][colIndex - 1] = BoardCell.EMPTY;
					score++;
				}
			}
			if (rowIndex <= board.length - 1 && rowIndex > 0) {
				if (board[rowIndex][colIndex] == board[rowIndex - 1][colIndex]) {
					board[rowIndex - 1][colIndex] = BoardCell.EMPTY;
					score++;
				}
				if (colIndex <= board[0].length - 1 && colIndex > 0) {
					if (board[rowIndex][colIndex] == board[rowIndex - 1][colIndex - 1]) {
						board[rowIndex - 1][colIndex - 1] = BoardCell.EMPTY;
						score++;
					}
				}
				if (colIndex < board[0].length - 1 && colIndex >= 0) {
					if (board[rowIndex][colIndex] == board[rowIndex - 1][colIndex + 1]) {
						board[rowIndex - 1][colIndex + 1] = BoardCell.EMPTY;
						score++;
					}
				}
			}
			if (rowIndex < board.length - 1 && rowIndex >= 0) {
				if (board[rowIndex][colIndex] == board[rowIndex + 1][colIndex]) {
					board[rowIndex + 1][colIndex] = BoardCell.EMPTY;
					score++;
				}
				if (colIndex < board[0].length - 1 && colIndex >= 0) {
					if (board[rowIndex][colIndex] == board[rowIndex + 1][colIndex + 1]) {
						board[rowIndex + 1][colIndex + 1] = BoardCell.EMPTY;
						score++;
					}
				}
				if (colIndex <= board[0].length - 1 && colIndex > 0) {
					if (board[rowIndex][colIndex] == board[rowIndex + 1][colIndex - 1]) {
						board[rowIndex + 1][colIndex - 1] = BoardCell.EMPTY;
						score++;
					}
				}
			}
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			score++;
		}

		int i;
		for (i = 0; i < board.length; i++) {
			if (lineIsEmpty(i)) {
				break;
			}
		}
		if (i >= board.length - 1) {
			return;
		}
		for (int j = i + 1; j < board.length; j++) {
			if (!lineIsEmpty(j)) {
				for (int k = 0; k < board[0].length; k++) {
					board[i][k] = board[j][k];
					board[j][k] = BoardCell.EMPTY;
				}
				i++;

			}
		}
	}

	// This method judge whether the line is empty
	private boolean lineIsEmpty(int row) {
		for (int j = 0; j < board[0].length; j++) {
			if (board[row][j] != BoardCell.EMPTY) {
				return false;
			}
		}
		return true;
	}
}