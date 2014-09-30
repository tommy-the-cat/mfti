package mobi.mfti.prototype;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListenerActivity extends Activity {
    
	//  string array that holds the months of the year
	//  to be used on the X axis of the chart
	private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };
	
	private static final int REQUEST_CODE = 1234;
    
    ArrayList<String> matches;
    private TextView speechTextView;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listener);	
		
		Button speakButton = (Button) findViewById(R.id.speakButton);
        speechTextView = (TextView) findViewById(R.id.speechTextView);
        
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
	}
	
	public void speakButtonClicked(View v) { startVoiceRecognitionActivity(); }
	
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a number...");
        startActivityForResult(intent, REQUEST_CODE);
    }
    
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {

            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            speechTextView.append(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void resetButtonClicked(View v) { speechTextView.setText(""); }
    
    public void submitButtonClicked(View v) { openChart(); };
    
    private void openChart(){
        int[] x = { 1,2,3,4,5,6,7,8 };
        int[] energyUse = { 2000,2500,2700,3000,2800,3500,3700,3800};
 
        // Creating an  XYSeries for Energy
        XYSeries energySeries = new XYSeries("Energy Use");
        // Adding data to Energy Series
        for(int i=0;i<x.length;i++){
        	energySeries.add(x[i], energyUse[i]);
        }
 
        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Energy Series to the dataset
        dataset.addSeries(energySeries);
        
        // Creating XYSeriesRenderer to customize energySeries
        XYSeriesRenderer energyRenderer = new XYSeriesRenderer();
        energyRenderer.setColor(Color.WHITE);
        energyRenderer.setPointStyle(PointStyle.CIRCLE);
        energyRenderer.setFillPoints(true);
        energyRenderer.setLineWidth(2);
        energyRenderer.setDisplayChartValues(true);
        
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Energy Usage");
        multiRenderer.setXTitle("Year 2013");
        multiRenderer.setYTitle("Energy Used (kwh)");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }
 
        // Adding energyRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(energyRenderer);
 
        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
 
        // Start Activity
        startActivity(intent);
    }
    
}