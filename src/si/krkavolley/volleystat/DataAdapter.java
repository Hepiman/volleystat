package si.krkavolley.volleystat;

import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Player;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

class DataAdapter extends BaseAdapter {
	ArrayList<Player> list;
	Context context;

	DataAdapter(Context c, ArrayList<Player> listOfExercises,
			AssetManager assetManager, boolean[] selectedExercises) {
		list = listOfExercises;
		context = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
