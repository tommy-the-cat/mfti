package mobi.mfti.prototype;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListenerActivity extends Activity {
	
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
}