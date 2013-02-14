package com.dolson.attachfour;


public class IntermediateStrategy implements Strategy
{
	Board board;
	Player p;
	private final int MAX = 100;
	private final int MIN = -1*MAX;
	//int [][] tb = new int[6][7];
	int [][] b = new int[6][7];
	int myID;
	int otherID;
	int main;
	int lastRow = 0;
	int lastCol = 0;
	int index;
	final int debug = 0;
	private final int mainDepth = 3;
	
	public IntermediateStrategy(Player p)
	{
		this.p = p;
		board = p.getBoard();
		myID = p.getID();
		otherID = (myID+1)%2;
	}
	
	@Override
	public void move()
	{
		
	}

	private void addPiece(int col, int player)
	{
		for (int i = b.length-1; i >= 0; i--)
		{
			if (b[i][col] == -1)
			{
				b[i][col] = player;
				lastRow = i;
				lastCol = col;
				break;
			}
		}
	}
	
	private void removePiece(int col)
	{
		for (int i = 0; i < b.length; i++)
		{
			if (b[i][col] != -1)
			{
				b[i][col] = -1;
				lastRow = 0;
				lastCol = 0;
				break;
			}
		}
	}
	
	private int minimax(int depth, int player)
	{
		if (depth <= 0) //invalid call to function
			return -1;
			
		int index = 3;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				
				addPiece(i, player);
				int value = maximize(depth-1, player+1);
				if (value > max)
				{
					index = i;
					max = value;
				}
				removePiece(i);
			}
		}
		
		return index;
	}
	
	private int maximize(int depth, int player)
	{
		if (depth == 0 || checkWinner()[0] > 0) //if depth == 0 or leaf node
			return (depth+1)*winner((player+1)%2);
					
		//int index = 3;
		int max = 0;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				//max = 0;
				addPiece(i, player%2);
				int value = minimize(depth-1, player+1);
				//if (value < max)
				//{
				//	System.out.println("we found a MIN value " + value);
				//	max = value;
				//}
				max += value;
				removePiece(i);
			}
		}
	
		return max;
	}
	
	private int minimize(int depth, int player)
	{
		if (depth == 0 || checkWinner()[0] > 0) //if depth == 0 or leaf node
			return (depth+1)*winner((player+1)%2);
		
		//int index = 3;
		int min = 0;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				addPiece(i, player%2);
				int value = maximize(depth-1,player+1);
				if (value > min)
				{
					//index = i;
					min = value;
				}
				min += value;
				removePiece(i);
			}
		}
		
		return min;
	}
	
	/*private int minimax(int depth, int player)
	{
		if (depth <= 0) //invalid call to function
			return -1;
			
		int index = 3;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				
				addPiece(i, player);
				if (debug == 1)
				System.out.println(depth + " trying a piece at: " + i);
				int value = maximize(depth-1, player+1);
				if (value > max)
				{
					index = i;
					if (debug == 1)
					System.out.println("found a good index value: " + index);
					max = value;
				}
				removePiece(i);
			}
			if (debug == 1)
			System.out.println("depth: " + depth + "     gives us this score: " + max + "     for this index: " + i);
		}
		
		return index;
	}
	
	private int maximize(int depth, int player)
	{
		if (depth == 0 || checkWinner()[0] > 0) //if depth == 0 or leaf node
			return winner((player+1)%2);
					
		//int index = 3;
		int max = Integer.MAX_VALUE;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				//max = 0;
				addPiece(i, player%2);
				int value = minimize(depth-1,player+1);
				if (value < max)
				{
					max = value;
				}
				removePiece(i);
			}
		}
	
		return max;
	}
	
	private int minimize(int depth, int player)
	{
		if (depth == 0 || checkWinner()[0] > 0) //if depth == 0 or leaf node
			return winner((player+1)%2);
		
		//int index = 3;
		int min = 0;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				addPiece(i, player%2);
				
				removePiece(i);
			}
		}
		
		return min;
	}*/
	
	private int winner(int player)
	{
		int [] winner = checkWinner();
		if (winner[0] > 0) //make sure that there was a 4 in a row
		{
			if (myID != player && player == winner[1]) //block the win
			{
				return MIN;
			}
			else if (myID == player && player == winner[1]) //take the win
			{
				return MAX;
			}
			//return MAX;
		}	
		//System.out.println();
		return 0;
	}

	private int[] checkWinner()
	{
		int[] t = {-1,-1};
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b[i].length; j++)
			{
				if (i == 0 || j == 0 || i == b.length-1)
				{
					int [] winner = board.checkWinner(i, j, 4, b, false);
					if (winner[0] > 0)
						return winner;
				}
			}
		}
		return t;
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		int move;
		
		//do 
		//{
			int [][] tb = board.getBoard();
			for (int i = 0; i < tb.length; i++)
			{
				for (int j = 0; j < tb[i].length; j++)
				{
					if (tb[i][j] == -1)
						b[i][j] = -1;
					else if (tb[i][j] == 0)
						b[i][j] = 0;
					else if (tb[i][j] == 1)
						b[i][j] = 1;
				}
			}
			//System.out.println("Trying a new area");
			//move = minimax(b, mainDepth, myID);
			//move = index;
			move = minimax(mainDepth, myID);
			
		//} while (board.setSpace(move) == 1);
			if (board.setSpace(move) == 1)
				System.exit(0);
	}
}
