package si.krkavolley.volleystat;

import si.krkavolley.volleystat.Entity.Game;
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
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class EditGameActivity extends Activity {

	EditText editName, editDesc, editDate;
	Button btnSave;
	DatabaseHelper db;
	long gameId;
	String gameName, gameDesc, gameDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		db = new DatabaseHelper(getApplicationContext());
		Bundle b = getIntent().getExtras();
		gameId = b.getLong("gameId");
		gameName = b.getString("gameName");
		gameDesc = b.getString("gameDesc");
		gameDate = b.getString("gameName");
		
		editName = (EditText) findViewById(R.id.createGame_name);
		editDesc = (EditText) findViewById(R.id.createGame_desc);
		editDate = (EditText) findViewById(R.id.createGame_date);
		btnSave = (Button) findViewById(R.id.button_save_new_game);
		
		editName.setText(gameName);
		editDesc.setText(gameDesc);
		editName.setText(gameDate);
		
		btnSave.setText("Save");
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game g = new Game();
				g.setId((int)gameId);
				g.setDate(editDate.getText().toString());
				g.setName(editName.getText().toString());
				g.setDescription(editDesc.getText().toString());
				db.updateGame(g);
				Toast.makeText(getApplicationContext(), "Game " + gameId + " updated.", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_game, menu);
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
