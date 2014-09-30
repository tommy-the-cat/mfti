package mobi.mfti.prototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//  added to remove annoying warnings
@SuppressWarnings("unused")
public class MainActivity extends Activity{

	
	private TextView tipTextView,
					 findEUITextView;
	private EditText kwhEditText, 
					 pplEditText, 
					 sqrftEditText;
	
	Random r = new Random();	
	int lineIWant = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		tipTextView     = (TextView)findViewById(R.id.tipTextView);
		kwhEditText     = (EditText)findViewById(R.id.kwhEditText);
		pplEditText     = (EditText)findViewById(R.id.pplEditText);
		sqrftEditText   = (EditText)findViewById(R.id.sqrftEditText);
		findEUITextView = (TextView)findViewById(R.id.findEUITextView);
		


		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getTip()
	//  ----------------------------------
	//  Purpose: Retrieves a random tip
	//	     from tips.txt located
	//           in the assets folder.
	//  Note:    tips.txt will most likely 
	//           be replaced by an SQLite
	//			 database at some point.
	//  ----------------------------------
	{
		AssetManager assetManager = getAssets();
		
		// when get tip is called, the lineIWant
		// variable gets populated with a random
		// number between 0 - 25.
		int lineIWant = r.nextInt(25);
		tipTextView.setText("");
		try{
			
			InputStream input = assetManager.open("tips.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
	        String line = null;
	        
	        try{
	        	int count = 0;
	        	while((line = br.readLine())!=null)	            {
	                
	        		if (count == lineIWant){
	        			tipTextView.setText(line);
	        			break;
	        		}
	                count++;
	            }
	        	
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void getTipClicked(View v)
	//  ---------------------------------
	//  Purpose: called when the 
	//           getTip Button is 
	//	     is clicked. Simply 
	//           the getTip method.
	//  ---------------------------------
	{
		
		//Toast.makeText(getApplicationContext(), "getTipClicked called", Toast.LENGTH_SHORT).show();		
		getTip();
		
	}
	
	@SuppressLint("DefaultLocale")
	public void calcEUIButtonClicked(View v){
		
		try {
			
			double kwh   = 0,
				   ppl   = 0,
				   sqrft = 0,
				   eui   = 0;
			
			// call getUserInput to get values
			// from the EditText's
			kwh   = getUserInput(kwhEditText);
			ppl   = getUserInput(pplEditText);
			sqrft = getUserInput(sqrftEditText);
	
			// convert kilowatt hours to BTU's
			kwh *= 3.412;
			
			// calculate EUI
			eui = kwh / (sqrft * ppl);
			
			// display EUI
			findEUITextView.setText("Your EUI for the month is: " + String.format("%.3f", eui));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
			//  the following line should remain
			//  commented out unless debugging.
			
			//tipTextView.setText(e.toString());
		}
	}
	public void clearEUIButtonClicked(View v){
		
		// empty all fields and set focus
		// to the KWH EditText
		kwhEditText.setText("");
		pplEditText.setText("");
		sqrftEditText.setText("");		
		kwhEditText.requestFocus();
		
		// revert the label
		findEUITextView.setText(getString(R.string.findEUI_text));
	}
	
	public void euiInfoClicked(View v)
	//  ---------------------------------
	//  Purpose: displays an AlertDialog 
	//           with EUI information
	//  ---------------------------------
	{
		
		// build alert to display EUI description
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// build alert dialog
	    builder
	    .setMessage(getString(R.string.eui_description))
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
	
	
	private double getUserInput(EditText eT)
	//  ---------------------------------
	//  Purpose: gets the value from an 
	//           Edit text and returns 
	//           the value as a double.
	//  ---------------------------------	
	{
		double value = 0;
		
		value = Double.parseDouble(eT.getText().toString());
		
		return value;
	}

	public void tutorialButtonClicked(View v){
		
		//Toast.makeText(getApplicationContext(), "tutorialButtonClicked called", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(getApplicationContext(), MeterTutorialActivity.class));
		
	}
	
    

}
