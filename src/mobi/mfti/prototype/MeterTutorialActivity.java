package mobi.mfti.prototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MeterTutorialActivity extends Activity{
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meter_tutorial);	
	}
	
	public void whyMeterClicked(View v){
		showAlert(getString(R.string.whyMeterDescription_text));
	}
	
	public void tutorialButtonClicked(View v){
		showAlert(getString(R.string.tutorial_text));
	}
	
	public void exampleButtonClicked(View v){
		startActivity(new Intent(getApplicationContext(), MeterExampleActivity.class));
	}
	
	public void hintsButtonClicked(View v){
		showAlert(getString(R.string.hints_text));
	}
	
	public void readUsageButtonClicked(View v){
		startActivity(new Intent(getApplicationContext(), ReadUsageActivity.class));
	}
	
	public void listenerButtonClicked(View v){
		startActivity(new Intent(getApplicationContext(), ListenerActivity.class));
	}
	
	public void showAlert(String msg){
		// build alert to display EUI description
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// build alert dialog
	    builder
	    .setMessage(msg)
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int which) 
	        { 
	        	dialog.dismiss(); 
	        }
	    });             
	    
	    // display dialog
	    AlertDialog alert = builder.create();
	    alert.show();
	}
}
