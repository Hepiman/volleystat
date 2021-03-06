package si.krkavolley.volleystat.Entity;
import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable{

	int id;
	String name;
	String date;
	String description;
	String score;
	String score_sets="0:0 0:0 0:0 0:0 0:0";
	
	public Game(){
		
	}
	
	@Override
	public String toString(){
		String txt = this.name + " (" + this.score + ")" + ", " + this.date;
		return txt;
		
	}
	
	public Game(String name, String description, String date, String score, String score_sets){
		this.name = name;
		this.description = description;
		this.date = date;
		this.score = score;
		this.score_sets = score_sets;
	}
	
	public Game(int id, String name, String description, String date, String score, String score_sets){
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.score = score;
		this.score_sets = score_sets;
	}
	
	public Game(Parcel in) {
		this.name = in.readString();
		this.description = in.readString();
		this.date = in.readString();
		this.score = in.readString();
		this.score_sets = in.readString();
	}

	// setters
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public void setScoreSets(String score_sets){
		this.score_sets = score_sets;
	}
	

	//getters
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	public String getScoreSets(){
		return this.score_sets;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(date);
		dest.writeString(score);
		dest.writeString(score_sets);
	}
	
	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>(){
		public Game createFromParcel(Parcel in){
			
			return new Game(in);
		}
		
		public Game[] newArray(int size){
			return new Game[size];
		}
	};

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

