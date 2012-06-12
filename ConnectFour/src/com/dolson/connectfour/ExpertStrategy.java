package com.dolson.connectfour;

public class ExpertStrategy implements Strategy
{
	Board board;
	Player p;
	private final int MAX = Integer.MAX_VALUE;
	private final int MIN = MAX*-1;
	//int [][] tb = new int[6][7];
	int [][] b = new int[6][7];
	int myID;
	int otherID;
	int main;
	int lastRow = 0;
	int lastCol = 0;
	int index;
	private final int mainDepth = 2;
	
	public ExpertStrategy(Player p)
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
	
	/*private int minimax(int [][] tb, int depth)
	{
		//low level copy of the board, so we dont actually edit the board
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
		
		if (depth <= 0) //invalid call to the function
		{
			return -1;
		}
		
		int max = MIN;
		int index = 0; //start at center column
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, myID);
				int strength = maxNode(depth-1, max);
				System.out.println("strength for " + i + " is: " + strength);
				if (strength > max)
				{
					index = i;
					max = strength;
					System.out.println("chaning the index value");
				}
				removePiece(i);
			}
		}
		
		return index;
	}
	
	private int maxNode(int depth, int parentMax)
	{
		//System.out.println("min");
		if (depth <= 0)
		{
			return winner(otherID);
		}
		//int min = MIN;
		int max = MIN;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, otherID);
				int strength = minNode(depth-1, max);
				if (strength > parentMax)
				{
					removePiece(i);
					max = strength;
				}
				if(strength > max)
                {
                    max = strength;
                }
				removePiece(i);
			}
		}
		return max;
	}
	
	private int minNode(int depth, int parentMin)
	{
		//System.out.println("max");
		if (depth <= 0)
		{
			return winner(myID);
		}
		//int max = MAX;
		int min = MAX;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, myID);
				int strength = maxNode(depth-1, min);
				if (strength < parentMin)
				{
					removePiece(i);
					min = strength;
				}
				if(strength < min)
                {
                    min = strength;
                }
				removePiece(i);
			}
		}
		return min;
	}*/
	
	/*private int minimax(int[][] tb, int depth, int player)
	{
		if (board.checkWinner(lastRow, lastCol, 4, b, false)[0] > 0 || depth <= 0)
		{
			System.out.println("WE ARE AT DEPTH 0");
			int win = winner ((player+1)%2);
			System.out.println("       winning score is: " + win);
			return win;
		}
		int max = MIN;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				System.out.println("trying move in " + i + "   " + depth);
				this.addPiece(i, player%2);
				int temp = Math.max(max, -1*minimax(b, depth -1, player+1));
				System.out.println("possible value is " + temp + "    vs max: " + max);
				if (temp > max)// && depth == mainDepth)
				{
					index = i;
					max = temp;
					System.out.println("              so far best index is " + index);
				}
				removePiece(i);
			}
		}
		return max;	
	}*/
	
	private int minimax(int depth, int player)
	{
		if (depth <= 0) //invalid call to function
			return -1;
			
		int index = 3;
		int max = MAX;
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				int value = minimize(depth-1, player+1);
				if (value < max)
				{
					index = i;
					max = value;
				}
			}
		}
		return maximize(depth, player);
	}
	
	private int maximize(int depth, int player)
	{
		if (depth == 0 || checkWinner()[0] > 0) //if depth == 0 or leaf node
			return winner((player+1)%2);
					
		int index = 3;
		int max = MIN;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				addPiece(i, player%2);
				int value = minimize(depth-1, player+1);
				if (value > max)
				{
					index = i;
					value = max;
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
		
		int index = 3;
		int min = MAX;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then this could be a valid move
			{
				addPiece(i, player%2);
				int value = maximize(depth-1,player+1);
				if (value < min)
				{
					index = i;
					min = value;
				}
				removePiece(i);
			}
		}
		
		
		
		return min;
	}
	
	private int winner(int player)
	{
		int [] winner = checkWinner();
		if (winner[0] > 0) //make sure that there was a 4 in a row
		{
			if (myID != player && player == winner[1]) //block the win
			{
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				return MIN;
			}
			else if (myID == player && player == winner[1]) //take the win
			{
				System.out.println("possible winner*************************************");
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
			System.out.println("trying expert move " + move);
			
		//} while (board.setSpace(move) == 1);
			if (board.setSpace(move) == 1)
				System.exit(0);
		
		System.out.println("exiting out of settings a piece");
	}
}
