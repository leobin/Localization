package locationaware.wifi.unused.evaluationtools;

import static locationaware.wifi.dataminingtools.DebugTools.debug;

public class Position {
	char location;
	int cell;
	static int[][][] board;
	static double[] cellSize;
	
	public Position(char location, int cell) {
		this.location = location;
		this.cell = cell;
		
		cellSize = new double[4];
		board = new int[4][][];
		//Location A
		{
			cellSize[0] = 1.6;
			board[0] = fillBoard(3, 4);
		}
		
		//Location B
		{
			cellSize[1] = 1.2;
			board[1] = fillBoard(10, 1);
		}
		
		//Location C
		{
			cellSize[2] = 1.2;
			board[2] = fillBoard(10, 1);
		}
		
		//Location D
		{
			cellSize[3] = 0.8;
			board[3] = fillBoard(5, 9);
		}
	}
	private int[][] fillBoard(int nrows, int ncols) {
		int[][] res = new int[nrows][ncols];
		int id = 0;
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				res[i][j] = ++id;
			}
		}
		return res;
	}
	public double getDistanceTo(Position expectedPosition) {
		if (location != expectedPosition.location)
			return Double.POSITIVE_INFINITY;
		int at = location - 'A';
		int[] my = findNumber(cell, board[at]);
		int[] expected = findNumber(expectedPosition.cell, board[at]);
		return cellSize[at] * Math.hypot(my[0] - expected[0], my[1] - expected[1]);	
	}
	private int[] findNumber(int num, int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == num) 
					return new int[] {i, j};
			}
		}
		return null;
	}
	public String toString() {
		return location + ", " + cell;
	}
	
	public int getCell() {
		return cell;
	}
	
	public char getName() {
		return location;
	}
	
	public static Position getPosition(String string) {
		String[] a = string.split("_");
		return new Position(a[2].charAt(0), Integer.parseInt(a[3]));
	}
	public static Position getPositionXP(String string) {
		try {
			return new Position(string.charAt(0), Integer.parseInt(string.substring(1)));	
		} catch (Exception e) {
			debug(string);
			return null;
		}
	}

}
