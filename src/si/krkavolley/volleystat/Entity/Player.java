package si.krkavolley.volleystat.Entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable{

	int id;
	String name;
	String description;
	
	public Player(){
		
	}
	
	public Player(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public Player(int id, String name, String description){
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Player(Parcel in) {
		this.name = in.readString();
		this.description = in.readString();
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
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
	}
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>(){
		public Player createFromParcel(Parcel in){
			
			return new Player(in);
		}
		
		public Player[] newArray(int size){
			return new Player[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
