package com.bensonche.batteryalert;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText txtBatteryLevel = (EditText)findViewById(R.id.txtBattLevel);
		EditText txtFrequency =(EditText)findViewById(R.id.txtFrequency);
		EditText txtEmailAddress =(EditText)findViewById(R.id.txtEmailAddress);
		
		txtBatteryLevel.setText("D");
		txtFrequency.setText("B");
		txtEmailAddress.setText("abc@gmail.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onOffClicked(View view)
	{
		EditText txtBatteryLevel = (EditText)findViewById(R.id.txtBattLevel);
		
		if(((ToggleButton) view).isChecked())
			txtBatteryLevel.setText("on");
		else
			txtBatteryLevel.setText("off");

	}

}
