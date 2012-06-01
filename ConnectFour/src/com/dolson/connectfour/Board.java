package com.dolson.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Board extends View implements OnClickListener
{
	private int[][] board;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int placeChecker = -1;
	private float diameter;
	private int space = -1;
	private int oldSpace;
	private final float dropSpeed = (float)0.25;
	private float yValue;
	private boolean dropToken = false;
	private boolean isWinner = false;
	private int playerTurn = 0;
		
	Player p1;
	Player p2;
	
	public Board(Context context)
	{
		super(context);
		board = new int[6][7];
			for (int i = 0; i < board.length; i++)
				for (int j = 0; j < board[0].length; j++)
					board[i][j] = -1;
			
		p1 = new Player("test1", 0, 0, this);
		p2 = new Player("test2", 1, 1, this);
		this.notifyPlayerTurn();
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);


	    int smallestWidth;
	    int orientedHeight;
	    if (this.getWidth() < this.getHeight())
	    {
	    	smallestWidth = this.getWidth();
	    	orientedHeight = this.getHeight();
	    }
	    else
	    {
	    	smallestWidth = this.getHeight();
	    	orientedHeight = this.getWidth();
	    }
	    
	    diameter = smallestWidth/7;
	    
	    float radius = diameter/2;
	   
	    float spacing = (smallestWidth - (diameter)*(float)7)/6;
	   
	    
	    for (int row = 1; row <= 6; row++)
	    {
	    	for (int i = 0; i < 7; i++)
	    	{
	    		// if board = 1 > red
	    		//if board = 2 > black
	    		//if board = 0 > white
	    		if (board[6-row][i] == -1)
	    			paint.setColor(Color.WHITE);
	    		else if (board[6-row][i] == 0)
	    			paint.setColor(Color.RED);
	    		else if (board[6-row][i] == 1)
	    			paint.setColor(Color.BLACK);
	    		
	    		canvas.drawCircle((i*diameter)+(radius)+spacing, (orientedHeight-(diameter*row))+radius, radius, paint);
	    		
	    	}
	    }
	    
	    //this will draw the checker that hasnt been dropped but will be when button released
	    if (playerTurn == 0)
    		paint.setColor(Color.RED);
    	else
    		paint.setColor(Color.BLACK);
	    
	    if (placeChecker != -1)
	    {
	    	canvas.drawCircle((placeChecker*diameter)+(radius)+spacing, (orientedHeight-(diameter*7))+radius, radius, paint);
	    }
	    
	    if (dropToken)
	    {
	    	canvas.drawCircle((space*diameter)+(radius)+spacing, (orientedHeight-(diameter*yValue))+radius, radius, paint);
	    	
	    	
	    	if (yValue <= (board.length-findYValue(space)))
	    	{
	    		insert(space, playerTurn);
	    		if (!isWinner)
	    		{
	    			playerTurn = (playerTurn+1)%2;
	    			dropToken = false;
	    			this.notifyPlayerTurn();
	    		}
	    		
	    	}
	    	yValue = yValue - dropSpeed;
	    	this.invalidate();
	    } 
	}
	

	private void insert(int col, int player)
	{
		boolean placedToken = false;
		int row = -1;
		for (int i = board.length-1; i >= 0; i--)
		{
			if (board[i][col] == -1) //then empty space
			{
				board[i][col] = player;
				row = i;
				placedToken = true;
				break;
			}
		}
		//if it wasnt placed, then the slot was full so need to try another slot
		if (!placedToken)
		{
			//do a toast message
		}
		//otherwise, check to see if anybody won
		else
		{
			checkWinner(row, col);
		}
			
	}
	
	/*
	 * 0 - Nobody won yet
	 * 1 - Player one won
	 * 2 - Player 2 won
	 */
	protected boolean checkWinner(int row, int col)
	{
		int tempRow = row;
		int tempCol = col;
		
		//check horizontal
		int p = -1;
		int count = 0;
		for (int j = 0; j < board[row].length; j++)
		{
			if (board[row][j] != -1)
			{
				if (board[row][j] == p)
					count++;
				else
				{
					p = board[row][j];
					count = 1;
				}
			}
			else
			{
				p = -1;
				count = 0;
			}
			//we have a winner
			if (count == 4)
			{
				CharSequence text = "Winner! Winner! Winner!   " + this.getPlayer(playerTurn).getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(super.getContext(), text, duration);
				toast.show();
				isWinner = true;
			}
		}
		
		
		//check vertical
		count = 0;
		p = -1;
		for (int j = board.length-1; j >= 0; j--)
		{
			if (board[j][col] == -1)
				break;
			else
			{
				if (board[j][col] == p)
					count++;
				else
				{
					p = board[j][col];
					count = 1;
				}
			}
			
			//check to see if we have 4 in a row
			if (count == 4)
			{
				CharSequence text = "Winner! Winner! Winner!   " + this.getPlayer(playerTurn).getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(super.getContext(), text, duration);
				toast.show();
				isWinner = true;
			}
		}
		
		
		//check forwardslash
		count = 0;
		p = -1;
		while (tempRow < board.length && tempCol >= 0)
		{
			tempRow++;
			tempCol--;
		}
		tempRow--;
		tempCol++;

		//now we are at the most bottom left place as possible with that piece placement
		
		while (tempRow >= 0 && tempCol < board[0].length)
		{
			if (board[tempRow][tempCol] == -1)
			{
				count = 0;
				p = -1;
			}
			else
			{
				if (board[tempRow][tempCol] == p)
					count++;
				else
				{
					p = board[tempRow][tempCol];
					count = 1;
				}
			}
			
			//check to see if we have a winner
			if (count == 4)
			{
				CharSequence text = "Winner! Winner! Winner!   " + this.getPlayer(playerTurn).getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(super.getContext(), text, duration);
				toast.show();
				isWinner = true;
			}
			
			tempRow--;
			tempCol++;
		}
		
		//check backslash
		count = 0;
		p = -1;
		tempRow = row;
		tempCol = col;
		
		while (tempRow > 0 && tempCol > 0)
		{
			tempRow--;
			tempCol--;
		}

		while (tempRow < board.length && tempCol < board[0].length)
		{
			if (board[tempRow][tempCol] == -1)
			{
				count = 0;
				p = -1;
			}
			else
			{
				if (board[tempRow][tempCol] == p)
					count++;
				else
				{
					p = board[tempRow][tempCol];
					count = 1;
				}
			}
			
			//check to see if we have a winner
			if (count == 4)
			{
				CharSequence text = "Winner! Winner! Winner!   " + this.getPlayer(playerTurn).getName();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(super.getContext(), text, duration);
				toast.show();
				isWinner = true;
			}
			
			tempRow++;
			tempCol++;
		}
		
		
		return isWinner;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[0].length; col++)
			{
				s += "|" + board[row][col];
			}
			
			s+="|\n";
		}
		return s;
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		//if a token is already being dropped, dont let the person change while its dropping
		//also if there is a winner, don't let there be any more input
		//also if the computer is playing, we dont get any input
		if (!dropToken && !isWinner && this.getPlayer(playerTurn).isHuman())
		{
			int xloc = (int)me.getX();
			space = (int)(xloc/diameter);	
			
			int eventaction = me.getAction();
			switch (eventaction)
			{
			case MotionEvent.ACTION_DOWN:
				placeChecker = space;
				break;
			case MotionEvent.ACTION_MOVE:
				if (oldSpace != space)
					placeChecker = space;
				break;
			case MotionEvent.ACTION_UP:
				placeChecker = -1;
				dropToken = true;
				yValue = 7;
				break;
			}
			
			oldSpace = space;
			this.invalidate();
		}
		return true;
	}
	
	private int findYValue(int col)
	{
		for (int i = board.length-1; i >= 0; i--)
		{
			if (board[i][col] == -1) //then empty space
			{
				return i;
			}
		}
		return -1;
	}
	protected Player getPlayer(int id)
	{
		if (p1.getID() == id)
			return p1;
		else if (p2.getID() == id)
			return p2;
		else
			return null;
	}

	protected void setSpace(int i)
	{
		if (board[0][i] != -1)
		{
			CharSequence text = "Winner! Winner! Winner!   " + this.getPlayer(playerTurn).getName();
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(super.getContext(), text, duration);
			toast.show();
			
			this.notifyPlayerTurn();
		}
		else
		{
			oldSpace = i;
			space = i;
			dropToken = true;
			yValue = (float)7.25;
		}
	}

	private void notifyPlayerTurn()
	{
		this.getPlayer(playerTurn).notifyPlayerTurn();
	}
	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}
	
}
