package com.dolson.connectfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            board = new Board(this);
            //TODO: also have to reset the players based on settings
            return true;
        case R.id.settings:
        	//TODO: strategies for players (h vs h, h vs c, c vs c) - based on checkboxes???
        	this.setIntent(new Intent(this, SettingsActivity.class));
        	
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
    	}
    }
}
