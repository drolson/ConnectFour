package com.dolson.connectfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity
{
	private Board board;
	private MenuInflater mi;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        board = new Board(this); 
        
        //set the players names along with the colors that they are
        TextView tv1 = (TextView)findViewById(R.id.player1);
        tv1.setText(board.getPlayer(0).getName());
        tv1 = (TextView)findViewById(R.id.player2);
        tv1.setText(board.getPlayer(1).getName());
        
        LinearLayout main = (LinearLayout)findViewById(R.id.board_view);
        main.addView(board);
        board.setOnClickListener(board);
        
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
	        	//TODO: strategies for players (h vs h, h vs c, c vs c) - based on checkboxes???
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
    		
    		if (i.getExtras().getBoolean("p0bool") || i.getExtras().getBoolean("p1bool"))
    		{
    			this.newGame();
    		}
    	}	
    }
    
    private void newGame()
    {
		board.resetBoard();
		board.invalidate();
		board.notifyPlayerTurn();
    }
}
