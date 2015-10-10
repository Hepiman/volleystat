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

public class CreateGameActivity extends Activity {

	EditText gName, gDate, gDesc;
	Button btnSave;
	DatabaseHelper db;
	String name, date, desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		db = new DatabaseHelper(getApplicationContext());

		gName = (EditText) findViewById(R.id.createGame_name);

		gDate = (EditText) findViewById(R.id.createGame_date);

		gDesc = (EditText) findViewById(R.id.createGame_desc);

		btnSave = (Button) findViewById(R.id.button_save_new_game);
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = gName.getText().toString();
				date = gDate.getText().toString();
				desc = gDesc.getText().toString();
				Game game = new Game();
				game.setName(name);
				game.setDate(date);
				;
				game.setDescription(desc);
				game.setScore("0-0");
				db.createGame(game);
				Toast.makeText(getApplicationContext(),
						"Game " + name + " saved!", Toast.LENGTH_SHORT).show();

				gName.setText("");
				gDate.setText("");
				gDesc.setText("");
			}
		});

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game, menu);
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
