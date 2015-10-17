package si.krkavolley.volleystat.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OpponentError implements Parcelable {

	int id, gameId, err_1, err_2, err_3, err_4, err_5;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getErr_1() {
		return err_1;
	}

	public void setErr_1(int err_1) {
		this.err_1 = err_1;
	}

	public int getErr_2() {
		return err_2;
	}

	public void setErr_2(int err_2) {
		this.err_2 = err_2;
	}

	public int getErr_3() {
		return err_3;
	}

	public void setErr_3(int err_3) {
		this.err_3 = err_3;
	}

	public int getErr_4() {
		return err_4;
	}

	public void setErr_4(int err_4) {
		this.err_4 = err_4;
	}

	public int getErr_5() {
		return err_5;
	}

	public void setErr_5(int err_5) {
		this.err_5 = err_5;
	}

	public OpponentError() {

	}

	public OpponentError(int gameId) {
		this.gameId = gameId;
	}

	public OpponentError(int gameId, int err_1, int err_2, int err_3,
			int err_4, int err_5) {
		this.gameId = gameId;
		this.err_1 = err_1;
		this.err_2 = err_2;
		this.err_3 = err_3;
		this.err_4 = err_4;
		this.err_5 = err_5;
	}

	public OpponentError(int id, int gameId, int err_1, int err_2, int err_3,
			int err_4, int err_5) {
		this.id = id;
		this.gameId = gameId;
		this.err_1 = err_1;
		this.err_2 = err_2;
		this.err_3 = err_3;
		this.err_4 = err_4;
		this.err_5 = err_5;
	}

	public OpponentError(Parcel in) {
		this.id = in.readInt();
		this.gameId = in.readInt();
		this.err_1 = in.readInt();
		this.err_2 = in.readInt();
		this.err_3 = in.readInt();
		this.err_4 = in.readInt();
		this.err_5 = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(gameId);
		dest.writeInt(err_1);
		dest.writeInt(err_2);
		dest.writeInt(err_3);
		dest.writeInt(err_4);
		dest.writeInt(err_5);
	}

	public static final Parcelable.Creator<OpponentError> CREATOR = new Parcelable.Creator<OpponentError>() {
		public OpponentError createFromParcel(Parcel in) {

			return new OpponentError(in);
		}

		public OpponentError[] newArray(int size) {
			return new OpponentError[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
