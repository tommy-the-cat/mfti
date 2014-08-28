package mobi.mfti.prototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class MeterTutorialActivity extends Activity{
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meter_tutorial);	
	}
	
	public void whyMeterClicked(View v){
		// build alert to display EUI description
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// build alert dialog
	    builder
	    .setMessage(getString(R.string.whyMeterDescription_text))
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
