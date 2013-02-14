package com.dolson.attachfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dolson.attachfour.R;

public class Game extends Activity implements Runnable
{
	private Board board;
	private MenuInflater mi;
	Thread t;
	private long lastNano = 0;
	private long timeLeftMillis = 0;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        board = new Board(this);
        new Thread(board).start();
        //this.runOnUiThread(board);
        
        //set the players names along with the colors that they are
        TextView tv1 = (TextView)findViewById(R.id.player1);
        tv1.setText(board.getPlayer(0).getName());
        tv1 = (TextView)findViewById(R.id.player2);
        tv1.setText(board.getPlayer(1).getName());
        
        LinearLayout main = (LinearLayout)findViewById(R.id.board_view);
        main.addView(board);
        board.setOnClickListener(board);
        lastNano = System.nanoTime();
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	super.onCreateOptionsMenu(menu);
    	mi = this.getMenuInflater();
    	mi.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	super.onOptionsItemSelected(item);
    	switch (item.getItemId()) 
    	{
	        case R.id.new_game:
	            this.newGame();
	            return true;
	        case R.id.settings:
	        	//TODO: strategies for players (h vs h, h vs c, c vs c) - based on radiobuttons???
	        	Intent intent = new Intent(this, SettingsActivity.class);
	        	intent.putExtra("strat0", board.getPlayer(0).getStrat());
	        	intent.putExtra("strat1", board.getPlayer(1).getStrat());
	        	
	        	startActivityForResult(intent, 0);
	        	     	
	        	return true;
	        default:
	        	return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override 
    public void onSaveInstanceState(Bundle state)
    {
    	super.onSaveInstanceState(state);
    	
    	//Player info
    	state.putInt("strat0", board.getPlayer(0).getStrat());
    	state.putInt("strat1", board.getPlayer(1).getStrat());
    	state.putString("name0", board.getPlayer(0).getName());
    	state.putString("name1", board.getPlayer(1).getName());
    	
    	//board info
    	
    	/*try
		{
			t.wait();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    
    @Override
    public void onRestoreInstanceState(Bundle state)
    {
    	super.onRestoreInstanceState(state);
    	
    	//player info
    	board.getPlayer(0).setName(state.getString("name0"));
    	board.getPlayer(1).setName(state.getString("name1"));
    	board.getPlayer(0).setStrat(state.getInt("strat0"));
    	board.getPlayer(1).setStrat(state.getInt("strat1"));
    	
    	//board info
    	//t.notify();
    }
    
    @Override
    public void onActivityResult(int request, int result, Intent i)
    {
    	if (result == 0)
    	{
    		//clicked, so reset the board
    		if (i.getExtras().getBoolean("p0bool"))
    		{
    			board.getPlayer(0).setStrat(i.getExtras().getInt("p0int"));
    			if (i.getExtras().getInt("p0int") == 0)
    			{
    				CharSequence text = "I will have player0 enter in their name   ";
    				int duration = Toast.LENGTH_SHORT;
    				Toast toast = Toast.makeText(this, text, duration);
    				toast.show();
    			}
    		}
    		
    		if (i.getExtras().getBoolean("p1bool"))
    		{
    			board.getPlayer(1).setStrat(i.getExtras().getInt("p1int"));
    			if (i.getExtras().getInt("p1int") == 0)
    			{
    				CharSequence text = "I will have player1 enter in their name   ";
    				int duration = Toast.LENGTH_SHORT;
    				Toast toast = Toast.makeText(this, text, duration);
    				toast.show();
    			}
    		}
    		
    		// if they changed the settings for a player, then we should start a new game since a new player is playing
    		if (i.getExtras().getBoolean("p0bool") || i.getExtras().getBoolean("p1bool"))
    		{
    			this.newGame();
    		}
    	}	
    }
    
    private void newGame()
    {
		board.resetBoard();
		t = new Thread(this);
		t.start();
		board.invalidate();
		board.notifyPlayerTurn();
    }
    
    public Handler updateHandler = new Handler(){
        //** Gets called on every message that is received *//*
        // @Override
        public void handleMessage(Message msg) {
            //board.update();
        	System.out.println("This is in the handler");
            board.invalidate();
            super.handleMessage(msg);
        }
    };


	@Override
	public void run()
	{
		while (!board.isGameOver())
		{
			try
			{
				//try to maintain 60 frames per second
				timeLeftMillis = (16666666 - (System.nanoTime()-lastNano))/1000000;
				System.out.println("Sleeping for " + timeLeftMillis);
				if (timeLeftMillis > 0)
					Thread.sleep(timeLeftMillis);
				System.out.println("Finished sleeping " + System.nanoTime());
				lastNano = System.nanoTime();
			
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (board.getDropToken()) //ie: we are dropping a token so all we do is redraw til its dropped
			{
				System.out.println("trying to invalidate");
				Game.this.updateHandler.sendEmptyMessage(0);
				System.out.println("Sent to update Handler " + System.nanoTime());
			}
			
			if (board.getDroppedTokenSuccess()) //then we have finished dropping a token and we can finally notify the next player
			{
				board.setDroppedTokenSuccess(false);
				board.notifyPlayerTurn();
			}
			
		}
	}
}


