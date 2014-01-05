package com.bensonche.batteryalert;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Populate levelSpinner
		Spinner levelSpinner = (Spinner) findViewById(R.id.levelSpinner);
		ArrayList<Integer> lvlList = new ArrayList<Integer>();

		for (int i = 5; i <= 50; i += 5)
			lvlList.add(i);

		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, lvlList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		levelSpinner.setAdapter(adapter);

		// Populate frequencySpinner
		Spinner frequencySpinner = (Spinner) findViewById(R.id.frequencySpinner);

		ArrayAdapter<CharSequence> freqAdapter = ArrayAdapter
				.createFromResource(this, R.array.frequencyArray,
						android.R.layout.simple_spinner_item);
		freqAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequencySpinner.setAdapter(freqAdapter);

		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);

		// Set preferences
		EditText txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);

		levelSpinner.setSelection(pref.getInt("batteryLevel", 3));
		frequencySpinner.setSelection(pref.getInt("frequency", 2));
		txtEmailAddress.setText(pref.getString("email", ""));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onOffClicked(View view) {
		EditText txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);

		if (((ToggleButton) view).isChecked())
			txtEmailAddress.setText("on");
		else
			txtEmailAddress.setText("off");

	}

}
