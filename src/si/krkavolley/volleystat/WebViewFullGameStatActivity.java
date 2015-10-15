package si.krkavolley.volleystat;

import java.util.HashMap;
import java.util.List;

import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.os.Build;

public class WebViewFullGameStatActivity extends Activity {

	WebView myWebView;
	long gameId;
	String gameName;
	DatabaseHelper db;
	Context ctx;
	HashMap playerMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_full_game_stat);

		Bundle b = getIntent().getExtras();
		gameId = b.getLong("gameId");
		gameName = b.getString("gameName");

		ctx = getApplicationContext();
		db = new DatabaseHelper(ctx);
		playerMap = db.getAllPlayersHashMap();
		List<Stat> stats = db.getFullGameStats((int)gameId);
		
		myWebView = (WebView) findViewById(R.id.webViewFullGameStat);
		String htmlDocument = "<html><head><style>td{padding: 3px 7px;}</style></head><body><h1>"+gameName+"</h1>";
		for(int i = 0; i < stats.size(); i++){
			htmlDocument+= "<table>"
							+"<tr>"
							+"<td>Rec pos</td>"
							+"<td>Rec ideal</td>"
							//TODO design a display 
			htmlDocument+= stats.get(i).getAttack_0() + "<br/>";
		}
		
		htmlDocument += "</body></html>";
		myWebView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8",
				null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view_full_game_stat, menu);
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
