package com.dolson.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class Board extends View implements OnClickListener
{
	private int[][] board;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int placeChecker = -1;
	private float diameter;
	private int space = -1;
	private int oldSpace;
	private int turn = 0;
	private final float dropSpeed = (float)0.25;
	private float yValue;
	private boolean dropToken = false;
	
	
	public Board(Context context)
	{
		super(context);
		board = new int[6][7];
			for (int i = 0; i < board.length; i++)
				for (int j = 0; j < board[0].length; j++)
					board[i][j] = -1;
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    //System.out.println("thius is an updated view");
	    //int smallestWidth = Math.min(this.getWidth(), this.getHeight());
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
	    //System.out.println(this.getWidth());
	    float radius = diameter/2;
	    //System.out.println(this.getHeight());
	    float spacing = (smallestWidth - (diameter)*(float)7)/6;
	    //System.out.println(spacing);
	    
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
	    if (turn == 0)
    		paint.setColor(Color.RED);
    	else
    		paint.setColor(Color.BLACK);
	    
	    if (placeChecker != -1)
	    {
	    	/*if (turn == 0)
	    		paint.setColor(Color.RED);
	    	else
	    		paint.setColor(Color.BLACK);*/
	    	
	    	
	    	canvas.drawCircle((placeChecker*diameter)+(radius)+spacing, (orientedHeight-(diameter*7))+radius, radius, paint);
	    }
	    
	    if (dropToken)
	    {
	    	
	    	//while (dropToken)
	    	//{
	    		//System.out.println(placeChecker + "       " + yValue);
	    		canvas.drawCircle((space*diameter)+(radius)+spacing, (orientedHeight-(diameter*yValue))+radius, radius, paint);
	    		
	    		System.out.println("Drop speed:  " + yValue + "     " + (board.length-1-findYValue(space)));
	    		
	    		if (yValue <= (board.length-findYValue(space)))
	    		{
	    			insert(space, turn);
	    			turn = (turn+1)%2;
	    			dropToken = false;
	    		}
	    		yValue = yValue - dropSpeed;
	    		this.invalidate();
	    	//}
	    }
	    
	    
	}
	

	private void insert(int col, int player)
	{
		boolean placedToken = false;
		for (int i = board.length-1; i >= 0; i--)
		{
			if (board[i][col] == -1) //then empty space
			{
				board[i][col] = player;
				placedToken = true;
				break;
			}
		}
		//if it wasnt placed, then the slot was full so need to try another slot
		if (!placedToken)
		{
			//do a toast message
		}
	}
	
	/*
	 * 0 - Nobody won yet
	 * 1 - Player one won
	 * 2 - Player 2 won
	 */
	protected int checkWinner()
	{
		//check horizontal
		//check vertical
		//check forwardslash
		//check backslash
		
		return 0;
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
			
			s+="|/n";
		}
		return s;
	}

	
	
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		//if a token is already being dropped, dont let the person change while its dropping
		if (!dropToken)
		{
			int xloc = (int)me.getX();
			space = (int)(xloc/diameter);
			System.out.println("space: " + space);	
			
			int eventaction = me.getAction();
			switch (eventaction)
			{
			case MotionEvent.ACTION_DOWN:
				System.out.println("there was a press down");
				placeChecker = space;
				break;
			case MotionEvent.ACTION_MOVE:
				System.out.println("there was some movement");
				//recalc, but dont have to do anything but draw if it chages the space
				if (oldSpace != space)
					placeChecker = space;
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("we released the pressing");
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

	/*private void placeChecker()
	{
		for (int i = 5; i <= 0; i++)
		{
			if (board[space][i] == -1)
				board[space][i] = turn;
		}
	}*/
	
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
	
	protected boolean gameOver()
	{
		
		
		return false;
	}
	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
