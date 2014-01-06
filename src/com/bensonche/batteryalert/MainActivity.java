package com.bensonche.batteryalert;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			SharedPreferences pref = MainActivity.this.getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			
			String prefName = "";
			
			switch(parent.getId()) {
				case R.id.frequencySpinner:
					prefName = "frequency";
					break;
				case R.id.levelSpinner:
					prefName = "batteryLevel";
					break;
			}
			
			editor.putInt(prefName, position);
			
			editor.commit();
		}
		
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	private TextWatcher EditTextListener = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			SharedPreferences pref = MainActivity.this.getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			
			editor.putString("email", s.toString());
			
			editor.commit();
		}

		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}

		public void onTextChanged(CharSequence s, int start, int before, int count) {}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Populate levelSpinner
		Spinner levelSpinner = (Spinner) findViewById(R.id.levelSpinner);
		ArrayList<Integer> lvlList = new ArrayList<Integer>();

		for (int i = 5; i <= 50; i += 5)
			lvlList.add(i);

		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, lvlList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		levelSpinner.setAdapter(adapter);
		levelSpinner.setOnItemSelectedListener(SpinnerListener);

		// Populate frequencySpinner
		Spinner frequencySpinner = (Spinner) findViewById(R.id.frequencySpinner);
		ArrayAdapter<CharSequence> freqAdapter = ArrayAdapter.createFromResource(this, R.array.frequencyArray,
						android.R.layout.simple_spinner_item);
		freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequencySpinner.setAdapter(freqAdapter);
		frequencySpinner.setOnItemSelectedListener(SpinnerListener);
		
		// Set Listener
		EditText txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
		txtEmailAddress.addTextChangedListener(EditTextListener);

		// Set preferences
		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
		
		ToggleButton onOffToggle = (ToggleButton) findViewById(R.id.OnOffToggle);

		levelSpinner.setSelection(pref.getInt("batteryLevel", 3));
		frequencySpinner.setSelection(pref.getInt("frequency", 2));
		txtEmailAddress.setText(pref.getString("email", ""));
		onOffToggle.setChecked(pref.getBoolean("OnOff", false));
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public void onOffClicked(View view) {
		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		boolean checked =  ((ToggleButton) view).isChecked();
		editor.putBoolean("OnOff", checked);
		
		editor.commit();
	}
	
	public void TestEmail(View view) {
		String email = ((EditText) findViewById(R.id.txtEmailAddress)).getText().toString();
		
		new TestEmailTask().execute(email, "%Test email%");
	}
	
	private class TestEmailTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Email.sendEmail(urls[0], urls[1]);
			
			return "";
		}
		
		@Override
		protected void onPostExecute(String result) {
			
		}
	}
}
