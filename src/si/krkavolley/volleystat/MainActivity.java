package si.krkavolley.volleystat;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import si.krkavolley.volleystat.Entity.*;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	Context context;
	Button buttonPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = getApplicationContext();
		buttonPlayers = (Button) findViewById(R.id.menu_button_players);
		
		buttonPlayers.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	Intent i;
	        	i = new Intent(context, PlayersActivity.class);
	        	startActivity(i);
	        };
	    });
	       
		
		
		
//        List<String> statTypes = new ArrayList<String>();
//        statTypes.add("sprejem");
//        statTypes.add("napad");
//        statTypes.add("servis");
//        ArrayAdapter<String> statTypesAdapter = new ArrayAdapter<String>(
//                this, 
//                android.R.layout.simple_list_item_single_choice,
//                statTypes );
//        statTypeList = (ListView) findViewById(R.id.statTypeList);
//        statTypeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        statTypeList.setAdapter(statTypesAdapter);
		
		
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.main, container, false);
			return rootView;
		}
	}

}
