package com.dolson.connectfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import com.dolson.attachfour.R;

public class SettingsActivity extends Activity
{
	Intent i = new Intent();
	RadioButton r00;
	RadioButton r01;
	RadioButton r02;
	RadioButton r03;
	RadioButton r10;
	RadioButton r11;
	RadioButton r12;
	RadioButton r13;
	SettingsActivity a = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		 
		i.putExtra("p0bool", false);
		i.putExtra("p1bool", false);
		
		Bundle extras = this.getIntent().getExtras();
		 
		if (extras != null)
		{
			int strat0 = extras.getInt("strat0");
			int strat1 = extras.getInt("strat1");
			
			this.createButton();
			
			//first player
			if (strat0 == 0)
			{
				r00.setChecked(true);
			}
			else if (strat0 == 1)
			{
				r01.setChecked(true);
			}
			else if (strat0 == 2)
			{
				r02.setChecked(true);
			}
			else if (strat0 == 3)
			{
				r03.setChecked(true);
			}
			
			//second player
			if (strat1 == 0)
			{
				r10.setChecked(true);
			}
			else if (strat1 == 1)
			{
				r11.setChecked(true);
			}
			else if (strat1 == 2)
			{
				r12.setChecked(true);
			}
			else if (strat1 == 3)
			{
				r13.setChecked(true);
			}
		}
		
		this.setResult(0, i);
	}
	
	 public void createButton()
	 {
		 r00 = (RadioButton)this.findViewById(R.id.radio00);
		 r00.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p0bool", true);i.putExtra("p0int", 0);}});
		 
		 r01 = (RadioButton)this.findViewById(R.id.radio01);
		 r01.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p0bool", true);i.putExtra("p0int", 1);}});
		 
		 r02= (RadioButton)this.findViewById(R.id.radio02);
		 r02.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p0bool", true);i.putExtra("p0int", 2);}});
		 
		 r03 = (RadioButton)this.findViewById(R.id.radio03);
		 r03.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p0bool", true);i.putExtra("p0int", 3);}});
		 
		 r10 = (RadioButton)this.findViewById(R.id.radio10);
		 r10.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p1bool", true);i.putExtra("p1int", 0);}});
		 
		 r11 = (RadioButton)this.findViewById(R.id.radio11);
		 r11.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p1bool", true);i.putExtra("p1int", 1);}});
		 
		 r12 = (RadioButton)this.findViewById(R.id.radio12);
		 r12.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p1bool", true);i.putExtra("p1int", 2);}});
		 
		 r13 = (RadioButton)this.findViewById(R.id.radio13);
		 r13.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){i.putExtra("p1bool", true);i.putExtra("p1int", 3);}});
	 }
}
