package si.krkavolley.volleystat;

import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class StatisticsActivity extends Activity {

	DatabaseHelper db;
	Button btn_0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		db = new DatabaseHelper(getApplicationContext());

		btn_0 = (Button)findViewById(R.id.statistics_btn_0);
		btn_0.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//db.writeServe(1, 1, 1, 1);
				Stat stat = db.getPlayerStats(1, 1);
				Toast.makeText(getApplicationContext(), "Serve 0: " + stat.getServe_0(), Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
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

}
