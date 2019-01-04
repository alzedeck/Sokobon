
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            SOKOBAN
// 
// Semester:         CS 200 Fall 2018
//
// Author:          Amber Zedeck
// Email:            zedeck@wisc.edu
// CS Login:         zedeck
// Lecturer's Name:  Professor Renault
// Lab Section:    	 Lec 002
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, roommates 
// strangers, etc do.
//
// Persons:          CS Drop in tutoring,
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Scanner;

import java.util.Arrays;
import java.util.Random;

public class Sokoban {

	/**
	 * Prompts the user for a value by displaying prompt. Note: This method should
	 * not add a new line to the output of prompt.
	 *
	 * After prompting the user, the method will consume an entire line of input
	 * while reading an int. If the value read is between min and max (inclusive),
	 * that value is returned. Otherwise, "Invalid value." terminated by a new line
	 * is output to the console and the user is prompted again.
	 *
	 * @param sc     The Scanner instance to read from System.in.
	 * @param prompt The name of the value for which the user is prompted.
	 * @param min    The minimum acceptable int value (inclusive).
	 * @param max    The maximum acceptable int value (inclusive).
	 * @return Returns the value read from the user.
	 */
	public static int promptInt(Scanner sc, String prompt, int min, int max) {
		System.out.print(prompt);
		int userValue = 0; // level input by user
		boolean valid = false;
		while (!valid) {
			if (sc.hasNextInt()) { // if there is an int
				userValue = sc.nextInt(); // if the scanner sees an integer, set userValue to that int
				
				if (userValue >= min && userValue <= max) { // if it the integer is valid (within max and min)
					valid = true; // change value of boolean
				}

			} else { // if there is no int input by user
				String wrong = sc.nextLine(); // what is input by user
				System.out.println("Invalid value.");
				System.out.print(prompt);

			}
		}

		sc.nextLine();
		return userValue;
	}

	/**
	 * Prompts the user for a char value by displaying prompt. Note: This method
	 * should not be a new line to the output of prompt.
	 *
	 * After prompting the user, the method will read an entire line of input and
	 * return the first non-whitespace character converted to lower case.
	 *
	 * @param sc     The Scanner instance to read from System.in
	 * @param prompt The user prompt.
	 * @return Returns the first non-whitespace character (in lower case) read from
	 *         the user. If there are no non-whitespace characters read, the null
	 *         character is returned.
	 */
	public static char promptChar(Scanner sc, String prompt) {

		String userChar; // input of user (y/n)
		char letter = ' ';
		System.out.print(prompt);
		userChar = sc.nextLine();
		userChar = userChar.toLowerCase();
		for (int i = 0; i < userChar.length(); i++) { // loop that reads the whole line from user
			userChar.charAt(i);
			if (userChar.charAt(i) != ' ') { // if the first char is not a whitespace
				letter = userChar.charAt(i); // the first non-whitespace char is assigned userChar to to letter

			}

		}

		return letter;

	}

	/**
	 * Prompts the user for a string value by displaying prompt. Note: This method
	 * should not be a new line to the output of prompt.
	 *
	 * After prompting the user, the method will read an entire line of input,
	 * remove any leading and trailing whitespace, and return the input converted to
	 * lower case.
	 *
	 * @param sc     The Scanner instance to read from System.in
	 * @param prompt The user prompt.
	 * @return Returns the string entered by the user, converted to lower case with
	 *         leading and trailing whitespace removed.
	 */
	public static String promptString(Scanner sc, String prompt) {
		String userString; // input from user
		System.out.print(prompt);
		userString = sc.nextLine();
		userString = userString.replaceAll("\\s+", ""); // removes spaces
		userString = userString.toLowerCase(); // converts string to lower case
		return userString;

	}

	/**
	 * Initializes the game board to a given level. You can assume that the level at
	 * lvl has been successfully verified by the checkLevel method and that pos is
	 * an array of length 2.
	 *
	 * 1 - The game board should be created row-by-row. a - For each row, copy the
	 * values from the corresponding row in the 2-d array contained at index lvl in
	 * levels. b - When the worker is located, it's position should be recorded in
	 * the pos parameter. 2 - For each goal described in the array at index lvl of
	 * goals, convert the character at the goal coordinate to: -
	 * Config.WORK_GOAL_CHAR if it contains the worker - Config.BOX_GOAL_CHAR if it
	 * contains a box - Config.GOAL_CHAR otherwise
	 * 
	 * @param lvl    The index of the level to load.
	 * @param levels The array containing the levels.
	 * @param goals  The parallel array to levels, containing the goals for the
	 *               levels.
	 * @param pos    The starting pos of the worker. A length 2 array, where index 0
	 *               is the row and index 1 is the column.
	 * @return A two dimension array representing the initial configuration for the
	 *         given level.
	 */
	public static char[][] initBoard(int lvl, char[][][] levels, int[][] goals, int[] pos) {

		int i; // int for loop
		int j; // int for loop

		char[][] map = new char[levels[lvl].length][]; // array initialized to the length of the array of the level

		for (i = 0; i < levels[lvl].length; i++) { // loop that goes through array and assigns values to each space
			map[i] = new char[levels[lvl][i].length];
			for (j = 0; j < levels[lvl][i].length; j++) {
				map[i][j] = levels[lvl][i][j]; // inside level
				if (map[i][j] == Config.WORKER_CHAR) { // if at this point in the level there is a worker
					pos[0] = i;
					pos[1] = j; // assign worker i j coordinates

				}
			}
		}

		for (i = 0; i < goals[lvl].length; i += 2) { // loop that sorts through every other element in goals array
			int row = goals[lvl][i]; // assigns first element in lvl to the row
			int col = goals[lvl][i + 1]; // assigns next element in lvl to column
			if (levels[lvl][row][col] == Config.WORKER_CHAR) { // if that specified coordinate in goal is worker on the
																// board
				map[row][col] = Config.WORK_GOAL_CHAR; // assign to worker goal
			} else if (levels[lvl][row][col] == Config.BOX_CHAR) { // if that specified coordinate in level in goal
																	// array is a box
				map[row][col] = Config.BOX_GOAL_CHAR;// assign to box goal

			} else { // if specified space in goals array is empty on the board
				map[row][col] = Config.GOAL_CHAR; // assign to a goal
			}

		
		}

		return map;
	}

	/**
	 * Prints out the game board.
	 * 
	 * 1 - Since the game board does not contain the outer walls, print out a
	 * sequence of Config.WALL_CHAR with a length equal to that of the first row of
	 * board, plus the outer wall to the left and the right. 2 - For each row in
	 * board, print out a Config.WALL_CHAR, followed by the contents of the row,
	 * followed by a Config.WALL_CHAR. 3 - Finally, print out a sequence of
	 * Config.WALL_CHAR with a length equal to that of the last row of board, plus
	 * the outer wall to the left and the right.
	 *
	 * Note: each row printed out should be terminated by a new line.
	 *
	 * @param board The board to print.
	 */
	public static void printBoard(char[][] board) {
		int firstRow = board[0].length + 2; // adds walls to first row that is longer than length of board at 0
		for (int i = 0; i < firstRow; i++) { // loop that creates the wall char as border on top
			System.out.print(Config.WALL_CHAR);
		}
		System.out.println();
		for (int i = 0; i < board.length; i++) { // loop that cretes the wall char on sides
			System.out.print(Config.WALL_CHAR);
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);

			}
			System.out.print(Config.WALL_CHAR);
			System.out.println();
		}
		int lastRow = board[board.length - 1].length + 2; // adds last row of walls to bottom
		for (int i = 0; i < lastRow; i++) {
			System.out.print(Config.WALL_CHAR);
		}

		System.out.println();
	}

	/**
	 * Runs a given level through some basic sanity checks.
	 *
	 * This method performs the following tests (in order): 1 - lvl >= 0 2 - lvl is
	 * a valid index in levels, that the 2-d array at index lvl exists and that it
	 * contains at least 1 row. 3 - lvl is a valid index in goals, the 1-d array at
	 * index lvl exists and that it contains an even number of cells. 4 - the number
	 * of boxes is more than 0. 5 - the number of boxes equals the number of goals.
	 * 6 - the coordinate of each goal is valid for the given lvl and does not
	 * correspond to a wall cell. 7 - the number of workers is exactly 1. 8 - check
	 * for duplicate goals.
	 *
	 * @param lvl    The index of the level to load.
	 * @param levels The array containing the levels.
	 * @param goals  The parallel array to levels, containing the goals for the
	 *               levels.
	 * @return 1 if all tests pass. Otherwise if test: - Test 1 fails: 0 - Test 2
	 *         fails: -1 - Test 3 fails: -2 - Test 4 fails: -3 - Test 5 fails: -4 -
	 *         Test 6 fails: -5 - Test 7 fails: -6 - Test 8 fails: -7
	 * 
	 */
	public static int checkLevel(int lvl, char[][][] levels, int[][] goals) {
		// test1 --checks if level is less than or equal to zero
		if (lvl < 0) {
			return 0;

		}
		// test2 -- checks that a valid index in levels, that the 2-d array at index lvl
		// exists and that it contains at least 1 row.
		if ((levels.length <= lvl) || levels[lvl] == null || levels[lvl][0] == null) { // lvl number level exists
			return -1;
		}
		// test3--lvl is a valid index in goals, the 1-d array at index lvl exists and
		// that it contains an even number of cells.
		if ((goals.length <= lvl) || goals[lvl] == null || goals.length % 2 == 1) { // lvl number level exists
			return -2;
		}
		// test4--the number of boxes is more than 0
		boolean box = false;
		for (int i = 0; i < levels[lvl].length; i++) {
			for (int j = 0; j < levels[lvl][i].length; j++) { // j accesses column(in this case its boxes) while i
				// accesses row
				if (levels[lvl][i][j] == Config.BOX_CHAR) {
					box = true;
					break;

				}
			}

		}
		if (box == false) {
			return -3;

		}
		// test5--the number of boxes equals the number of goals.
		int numBox = 0;

		for (int i = 0; i < levels[lvl].length; i++) {
			for (int j = 0; j < levels[lvl][i].length; j++) { // j accesses column(in this case its boxes) while i
				// accesses row
				if (levels[lvl][i][j] == Config.BOX_CHAR) {
					numBox++;
				}

			}

		}
		if (numBox != goals[lvl].length / 2) {
			return -4;
		}

		// test6--the coordinate of each goal is valid for the given lvl and does not
		// correspond to a wall cell.
		for (int i = 0; i < goals[lvl].length; i += 2) {
			int x = goals[lvl][i]; // checking within the goal and not arbitrary values
			int y = goals[lvl][i + 1];
			if (x < 0 || x >= levels[lvl].length || y < 0 || y >= levels[lvl][x].length
					|| levels[lvl][x][y] == Config.WALL_CHAR) {
				return -5;

			}
		}
		// test7--the number of workers is exactly 1.

		int numWorker = 0;
		for (int i = 0; i < levels[lvl].length; i++) {
			for (int j = 0; j < levels[lvl][i].length; j++) { // j accesses column(in this case its boxes) while i
				if (levels[lvl][i][j] == Config.WORKER_CHAR) {
					numWorker++;
				} // accesses row

			}
		}
		if (numWorker != 1) {
			System.out.print(numWorker);

			return -6;

		}
		// Test 8 -- check for duplicate goals.
		for (int i = 0; i < goals[lvl].length - 1; i += 2) {
			for (int j = i + 2; j < goals[lvl].length - 1; j += 2) {
				if (goals[lvl][i] == goals[lvl][j] && goals[lvl][i + 1] == goals[lvl][j + 1]) {
					return -7;
				}
			}
		}

		return 1; // if all check, return 1
	}

	/**
	 * This method builds an int array with 2 cells, representing a movement vector,
	 * based on the String parameter.
	 *
	 * The rules to create the length 2 int array are as follows: - The 1st
	 * character of the String represents the direction. - The remaining characters
	 * (if there are any) are interpreted as integer and represent the magnitude or
	 * the number of steps to take.
	 *
	 * The cell at index 0 represents movement in the rows. Hence, a negative value
	 * represents moving up the rows and a positive value represents moving down the
	 * rows.
	 *
	 * The cell at index 1 represents movement in the columns. Hence, a negative
	 * value represents moving left in the columns and a positive value represents
	 * moving right in the columns.
	 *
	 * If the first character of moveStr does not match on of Config.UP_CHAR,
	 * Config.DOWN_CHAR, Config.LEFT_CHAR, or Config.RIGHT_CHAR, then return an
	 * array with 0 in both cells.
	 *
	 * If there are no characters after the first character of moveStr or the
	 * characters cannot be interpreted as an int, set the magnitude of the movement
	 * to 1.
	 *
	 * Hint: Use Scanner to parse the magnitude.
	 *
	 * Some examples: - If the parameter moveStr is "81": An array {-1, 0} would
	 * represent moving up by one character. - If the parameter moveStr is "65": An
	 * array {0, 5} would represent moving right by 5 characters.
	 *
	 * @param moveStr The string to parse.
	 * @return The calculated movement vector as a 2 cell int array.
	 */
	public static int[] calcDelta(String moveStr) {
		Scanner scnr = new Scanner(moveStr.substring(1)); // user input 23, equating to an downward movement of 3

		char direction; // up,down,left,right according to specific character
		int magnitude = 1; // how many moves n a certain direction
		int movementInRows = 0; // movement side to side
		int movementInCols = 0; // movement up or down

		if (scnr.hasNextInt()) { // if there is an integer
			magnitude = scnr.nextInt(); // assign integer to magnitude
		}

		direction = moveStr.charAt(0);

		switch (direction) { // checks different cases of characters and assigns movements accordingly
		case Config.UP_CHAR: // if it is up
			movementInCols = -magnitude; // up
			break;
		case Config.DOWN_CHAR:
			movementInCols = magnitude; // down
			break;
		case Config.LEFT_CHAR:
			movementInRows = -magnitude; // left
			break;
		case Config.RIGHT_CHAR:
			movementInRows = magnitude; // right
			break;
		default:
			break;

		}

		return new int[] { movementInCols, movementInRows }; // - or + x many times
	}

	/**
	 * This method checks that moving from one position to another position is a
	 * valid move.
	 *
	 * To validate the move, the method should (in order) check: 1 - that pos is
	 * valid. 2 - that the character at pos in board is in the valid array. 3 - that
	 * the delta is valid. 4 - that the new position is valid and not a wall
	 * character. 5 - that the new position is not a box character For what makes
	 * each test invalid, see the return details below.
	 *
	 * @param board The current board.
	 * @param pos   The position to move from. A length 2 array, where index 0 is
	 *              the row and index 1 is the column.
	 * @param delta The move distance. A length 2 array, where index 0 is the change
	 *              in row and index 1 is the change in column.
	 * @param valid A character array containing the valid characters for the cell
	 *              at pos. ex: pos not on wall char and box not on box char
	 * @return 1 if the move is valid. Otherwise: -1 : if pos is null, not length 2,
	 *         or not on the board. -2 : if the character at pos is not valid (not
	 *         in the valid array). -3 : if delta is null or not length 2. -4 : if
	 *         the new position is off the board or a wall character -5 : if the new
	 *         position is a box character
	 */
	public static int checkDelta(char[][] board, int[] pos, int[] delta, char[] valid) {

		// test 1 - the pos is valid

		// row check
		if (pos == null || pos.length != 2 || pos[0] < 0 || pos[0] >= board.length) {
			return -1;

		}
		int row = pos[0];
		int col = pos[1];
		// col check
		if (col < 0 || col > board[row].length) {
			return -1;
		}

		// test 2- character at pos is valid

		boolean validChar = false;

		for (int i = 0; i < valid.length; i++) {
			if (board[row][col] == valid[i]) {

				validChar = true;

			}
		}
		if (validChar == false) {
			return -2;
		}

		// Test3
		if (delta == null || delta.length != 2) {
			return -3;
		}

		// tTest 4
		int newRow = row + delta[0]; // x after delta
		int newCol = col + delta[1]; // y after delta

		// Counds check for newPos
		// Rowcheck
		if (newRow >= board.length || newRow < 0) {
			return -4;
		}
		// Col check
		if (newCol >= board[newRow].length || newCol < 0) {
			return -4;
		}
		if (board[newRow][newCol] == Config.WALL_CHAR) {
			return -4;
		}
		// Test 5
		if (board[newRow][newCol] == Config.BOX_CHAR || board[newRow][newCol] == Config.BOX_GOAL_CHAR) {
			return -5;
		}
		return 1; // if all check, return 1
	}

	/**
	 * Changes a character on the board to one of two characters (opt1 or opt2),
	 * depending on the value of the cell.
	 *
	 * Check the cell at position pos. If the character is val, change it to opt1.
	 * Otherwise, change it to opt2.
	 *
	 * @param board The current board.
	 * @param pos   The position to change. A length 2 array, where index 0 is the
	 *              row and index 1 is the column.
	 * @param val   The value to check for in the board.
	 * @param opt1  The character to change to if the value is val.
	 * @param opt2  The character to change to if the value is not val.
	 */
	public static void togglePos(char[][] board, int[] pos, char val, char opt1, char opt2) {

		int row = pos[0]; // x value of pos is 0
		int col = pos[1];// y value of pos is 1
		if (board[row][col] == val) { // if the position is equal to val
			board[row][col] = opt1; // change it to opt1

		} else { // if not
			board[row][col] = opt2; // change it to opt2
		}

	}

	/**
	 * Moves a box on the board.
	 *
	 * Step 1: Use your checkDelta method to check that the move is valid. Recall
	 * that there are 2 characters that can represent a box. Step 2: Use your
	 * togglePos method to correctly change the character at the new position to the
	 * appropriate box character. Step 3: Again use your togglePos method to
	 * correctly change the character at pos to the the appropriate character
	 * without a box.
	 *
	 * @param board The current board.
	 * @param pos   The position to change. A length 2 array, where index 0 is the
	 *              row and index 1 is the column.
	 * @param delta The move distance. A length 2 array, where index 0 is the change
	 *              in row and index 1 is the change in column.
	 * @return The return value of checkDelta if less than 1. Otherwise 1.
	 */
	public static int shiftBox(char[][] board, int[] pos, int[] delta) {
		char[] valid = { Config.BOX_CHAR, Config.BOX_GOAL_CHAR, }; // valid characters
		int resultCheckDelta = checkDelta(board, pos, delta, valid); // assigning the result of checkDelta to a variable

		if (resultCheckDelta < 1) {
			return resultCheckDelta;
		}

		int newPos[] = new int[2];

		if (resultCheckDelta == 1) {
			newPos[0] = pos[0] + delta[0]; // new position from adding change
			newPos[1] = pos[1] + delta[1]; // new position from adding change
			togglePos(board, newPos, Config.GOAL_CHAR, Config.BOX_GOAL_CHAR, Config.BOX_CHAR); // if the new position is at a goal, change it to box goal, if not change it to a box
																								
			togglePos(board, pos, Config.BOX_GOAL_CHAR, Config.GOAL_CHAR, Config.EMPTY_CHAR); // if the original position was a box goal, change it to a goal, if not change it to empty																					
		}

		return 1; // if all check, return 1
	}

	/**
	 * Processes a move of the worker step-by-step.
	 *
	 * Go through the delta step-by-step, calling doMove for each step. That is, if
	 * the delta is {0, -3}, your method should call doMove three times with an
	 * argument of {0, -1} for the delta parameter of doMove. Or, if the delta is
	 * {6, 0}, it would call the doMove six times with an argument of {1, 0} for the
	 * delta parameter of the doMove method.
	 *
	 * During the processing of the move, if ever a call to doMove returns a value
	 * less than 1, your method should stop processing and return that value.
	 *
	 * Note: You can assume that one of the cells of delta will be 0.
	 *
	 * @param board The current board.
	 * @param pos   The position to change. A length 2 array, where index 0 is the
	 *              row and index 1 is the column.
	 * @param delta The move distance. A length 2 array, where index 0 is the change
	 *              in row and index 1 is the change in column.
	 * @return If both of the cells of delta are 0, return 0. If the call to doMove
	 *         returns a value less than 1, return that value. Otherwise, return 1.
	 */
	public static int processMove(char[][] board, int[] pos, int[] delta) {

		int magnitude = delta[1]; // how many times the the worker should move
		int direction = delta[0]; // which direction the worker should move
		int numberLoops = Math.abs(magnitude) + Math.abs(direction); //
		int doTheMove; // var for calling doMove

		if (direction == 0 && magnitude == 0) { // if both elements in delta are 0
			return 0;
		}

		int[] newDelta = new int[2]; // array for change in position
		if (direction == 0) { // if delta at element 0 is zero(ex: {0,1}), execute following
			if (magnitude < 0) { // if delta at element zero is zero and delta at element 1 is negative(ex:
									// {0,-1})
				newDelta[1] = -1; //
			} else { // if delta at 1 is positive (ex: {0,1})
				newDelta[1] = 1;
			}

		} else { // if delta at element zero is not zero, execute following
			if (direction < 0) { // if delta at element 0 is (ex: {-1,1})
				newDelta[0] = -1; // (ex: {-1,0})
			} else { // if not
				newDelta[0] = 1; // (ex: {1,0})
			}
		}
		for (int i = 0; i < numberLoops; i++) { // loop to move the worker numberLoops times
			doTheMove = doMove(board, pos, newDelta);
			if (doTheMove < 1) { // if doMove is not valid
				return doTheMove;
			}
		}

		return 1; // if all check, return 1
	}

	/**
	 * Moves the worker on the board.
	 *
	 * Step 1: Use your checkDelta method to check that the move is valid. Recall
	 * that there are 2 characters that can represent the worker. Step 2: If
	 * checkDelta returns -5, use your shiftBox method to move the box by delta
	 * before moving the worker. Step 3: Use your togglePos method to correctly
	 * change the character at the new position to the appropriate worker character.
	 * Step 4: Again use your togglePos method to correctly change the character at
	 * pos to the the appropriate character without a worker. Step 5: Update the
	 * position of the worker in pos.
	 *
	 * @param board The current board.
	 * @param pos   The position to change. A length 2 array, where index 0 is the
	 *              row and index 1 is the column.
	 * @param delta The move distance. A length 2 array, where index 0 is the change
	 *              in row and index 1 is the change in column.
	 * @return If checkDelta returns a value less than 1 that is not -5, return that
	 *         value. If checkDelta returns -5 and shiftBox returns a value less
	 *         than 0, return 0. Otherwise, return 1.
	 */
	public static int doMove(char[][] board, int[] pos, int[] delta) {
		char[] valid = { Config.WORKER_CHAR, Config.WORK_GOAL_CHAR }; // valid characters
		int resultCheckDelta = checkDelta(board, pos, delta, valid); // result of calling checkDelta
		int shiftBoxReturn = 0; // return of shiftBox

		int[] newPos = new int[2];
		newPos[0] = pos[0] + delta[0]; // new position with delta
		newPos[1] = pos[1] + delta[1]; // new position with delta
		if (resultCheckDelta == -5) {
			shiftBoxReturn = shiftBox(board, newPos, delta);
			if (shiftBoxReturn < 0) {
				return 0;
			}
		}

		if (resultCheckDelta < 1 && resultCheckDelta != -5) {
			return resultCheckDelta;

		}
		togglePos(board, newPos, Config.GOAL_CHAR, Config.WORK_GOAL_CHAR, Config.WORKER_CHAR); // if the new position is at a goal, change character to work goal, if not,continue as worker																							
		togglePos(board, pos, Config.WORK_GOAL_CHAR, Config.GOAL_CHAR, Config.EMPTY_CHAR); // if the old position (worker has now moved) was a worker goal then assign to a goal, if not,it was empty
																							
		pos[0] = newPos[0]; // change position [0] to new position
		pos[1] = newPos[1]; // change position [1] to new position
		return 1;
	}

	/**
	 * Checks all the cells in board and ensures that there are no goals that are
	 * not covered by boxes.
	 *
	 * @param board The current board.
	 * @return true if all the goals are covered by boxes. Otherwise, false.
	 */
	public static boolean checkWin(char[][] board) {
		int i; // int for loop
		int j; // int for loop

		for (i = 0; i < board.length; i++) { // loop for checking if the boxes are covered
			for (j = 0; j < board[i].length; j++) {
				if (board[i][j] == Config.GOAL_CHAR) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This is the main method for the Sokoban game. It consists of the main game
	 * and play again loops with calls to the various supporting methods. The
	 * details of the main method for each milestone can be found in the BP1 -
	 * Sokoban write-up on the CS 200 webpage:
	 * https://cs200-www.cs.wisc.edu/wp/programs/
	 *
	 * For all milestones, you will need to create a Scanner object to read from
	 * System.in that you will pass to the helper methods.
	 *
	 * For milestone 3, you will need to create a Random object using Config.SEED as
	 * the seed. This object should be create at the beginning of the method,
	 * outside of any loops.
	 *
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // scanner with input from user
		Random random = new Random(Config.SEED); // random level
		char[][] currentLevel = null; // config for given level
		int[] position = new int[2]; // Array for where the worker is
		String userResponse = "";
		int[] userMove = new int[2]; // move input by user
		int max = Config.LEVELS.length - 1; // maximum level able to input
		int checkLevel; // int for calling check level

		System.out.println("Welcome to Sokoban!");
		do {

			int lvl = promptInt(sc, "Choose a level between 0 and " + max + ": ", -1, 1);

			if (lvl == -1) {
				lvl = random.nextInt(Config.LEVELS.length);
			}

			checkLevel = checkLevel(lvl, Config.LEVELS, Config.GOALS);

			if (checkLevel != 1) {
				System.out.println("Error loading level!");
			}
			if (checkLevel(lvl, Config.LEVELS, Config.GOALS) != 1) {
				if (checkLevel == 0) {
					System.out.println("Level ​lvl​ must be 0 or greater!");
				} else if (checkLevel == -1) {
					System.out.println("Error with Config.LEVELS");
				} else if (checkLevel == -2) {
					System.out.println("Error with Config.GOALS");
				} else if (checkLevel == -3) {
					System.out.println("Level ​lvl​ does not contain any boxes.");
				} else if (checkLevel == -4) {
					System.out.println("Level ​lvl​ does not have the same number of boxes as goals");
				} else if (checkLevel == -5) {
					System.out.println("Level ​lvl​ has a goal location that is a wall.");
				} else if (checkLevel == -6) {
					System.out.println("Level ​lvl​ has 0 or more than 1 worker(s).");
				} else if (checkLevel == -7) {
					System.out.println("Level ​lvl​ contains duplicate goals.");
				} else {
					System.out.println("Unknown Error");
				}

			} else {
				int numMoves = 0;
				System.out.println("Sokoban Level " + lvl);
				currentLevel = initBoard(lvl, Config.LEVELS, Config.GOALS, position);

				while (!userResponse.equals('q')) {
					printBoard(currentLevel);
					userResponse = promptString(sc, ": ");
					if (userResponse.length() == 0) {
						continue;
					}

					else if (userResponse.charAt(0) == (Config.QUIT_CHAR)) {

						break;
					} else {
						userMove = calcDelta(userResponse); // array that will be up/down or left/right
						processMove(currentLevel, position, userMove);

						if (userMove[0] != 0) { // if first element is not zero then the second element must be zero
							numMoves += Math.abs(userMove[0]); // numMoves = number of moves in first element
						} else {
							numMoves += Math.abs(userMove[1]);
						}
					}
					if (checkWin(currentLevel) == true) {
						System.out.println("Congratulations! You won in " + numMoves + " moves!");

						printBoard(currentLevel); // prints winning board
						break;
					}

				}

			}

		} while (promptChar(sc, "Play again? (y/n) ") == 'y');

		System.out.println("Thanks for playing!");

	}
}
