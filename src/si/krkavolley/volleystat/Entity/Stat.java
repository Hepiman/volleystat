package si.krkavolley.volleystat.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Stat implements Parcelable {

	int id;
	int game_id;
	int player_id;
	int game_set;

	int reception_3, reception_2, reception_1, reception_0, reception_wa,
			reception_over;
	int attack_3, attack_2, attack_1, attack_0, attack_e, attack_ee, attack_b,
			attack_bb;
	int serve_3, serve_2, serve_1, serve_0, serve_wa, serve_over, serve_e;
	int block;

	public Stat() {

	}
	
	public void setId(int id){
		this.id = id;
	}

	public int getGame_id() {
		return game_id;
	}
	public void setBlock(int block){
		this.block = block;
	}
	public int getBlock(){
		return this.block;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getGame_set() {
		return game_set;
	}

	public void setGame_set(int game_set) {
		this.game_set = game_set;
	}

	public int getReception_3() {
		return reception_3;
	}

	public void setReception_3(int reception_3) {
		this.reception_3 = reception_3;
	}

	public int getReception_2() {
		return reception_2;
	}

	public void setReception_2(int reception_2) {
		this.reception_2 = reception_2;
	}

	public int getReception_1() {
		return reception_1;
	}

	public void setReception_1(int reception_1) {
		this.reception_1 = reception_1;
	}

	public int getReception_0() {
		return reception_0;
	}

	public void setReception_0(int reception_0) {
		this.reception_0 = reception_0;
	}

	public int getReception_wa() {
		return reception_wa;
	}

	public void setReception_wa(int reception_wa) {
		this.reception_wa = reception_wa;
	}

	public int getReception_over() {
		return reception_over;
	}

	public void setReception_over(int reception_over) {
		this.reception_over = reception_over;
	}

	public int getAttack_3() {
		return attack_3;
	}

	public void setAttack_3(int attack_3) {
		this.attack_3 = attack_3;
	}

	public int getAttack_2() {
		return attack_2;
	}

	public void setAttack_2(int attack_2) {
		this.attack_2 = attack_2;
	}

	public int getAttack_1() {
		return attack_1;
	}

	public void setAttack_1(int attack_1) {
		this.attack_1 = attack_1;
	}

	public int getAttack_0() {
		return attack_0;
	}

	public void setAttack_0(int attack_0) {
		this.attack_0 = attack_0;
	}

	public int getAttack_e() {
		return attack_e;
	}

	public void setAttack_e(int attack_e) {
		this.attack_e = attack_e;
	}

	public int getAttack_ee() {
		return attack_ee;
	}

	public void setAttack_ee(int attack_ee) {
		this.attack_ee = attack_ee;
	}

	public int getAttack_b() {
		return attack_b;
	}

	public void setAttack_b(int attack_b) {
		this.attack_b = attack_b;
	}

	public int getAttack_bb() {
		return attack_bb;
	}

	public void setAttack_bb(int attack_bb) {
		this.attack_bb = attack_bb;
	}

	public int getServe_3() {
		return serve_3;
	}

	public void setServe_3(int serve_3) {
		this.serve_3 = serve_3;
	}

	public int getServe_2() {
		return serve_2;
	}

	public void setServe_2(int serve_2) {
		this.serve_2 = serve_2;
	}

	public int getServe_1() {
		return serve_1;
	}

	public void setServe_1(int serve_1) {
		this.serve_1 = serve_1;
	}

	public int getServe_0() {
		return serve_0;
	}

	public void setServe_0(int serve_0) {
		this.serve_0 = serve_0;
	}

	public int getServe_wa() {
		return serve_wa;
	}

	public void setServe_wa(int serve_wa) {
		this.serve_wa = serve_wa;
	}

	public int getServe_over() {
		return serve_over;
	}

	public void setServe_over(int serve_over) {
		this.serve_over = serve_over;
	}

	public int getServe_e() {
		return serve_e;
	}

	public void setServe_e(int serve_e) {
		this.serve_e = serve_e;
	}

	public static Parcelable.Creator<Stat> getCreator() {
		return CREATOR;
	}

	public Stat(Parcel in) {
		this.player_id = in.readInt();
		this.game_set = in.readInt();
		this.reception_3 = in.readInt();
		this.reception_2 = in.readInt();
		this.reception_1 = in.readInt();
		this.reception_0 = in.readInt();
		this.reception_wa = in.readInt();
		this.reception_over = in.readInt();
		this.attack_3 = in.readInt();
		this.attack_2 = in.readInt();
		this.attack_1 = in.readInt();
		this.attack_0 = in.readInt();
		this.attack_e = in.readInt();
		this.attack_ee = in.readInt();
		this.attack_b = in.readInt();
		this.attack_bb = in.readInt();
		this.serve_3 = in.readInt();
		this.serve_2 = in.readInt();
		this.serve_1 = in.readInt();
		this.serve_0 = in.readInt();
		this.serve_wa = in.readInt();
		this.serve_over = in.readInt();
		this.serve_e = in.readInt();
		this.block = in.readInt();
	}

	public Stat(int game_id, int player_id, int game_set) {
		this.game_id = game_id;
		this.player_id = player_id;
		this.game_set = game_set;
		this.reception_3 = 0;
		this.reception_2 = 0;
		this.reception_1 = 0;
		this.reception_0 = 0;
		this.reception_wa = 0;
		this.reception_over = 0;
		this.attack_3 = 0;
		this.attack_2 = 0;
		this.attack_1 = 0;
		this.attack_0 = 0;
		this.attack_e = 0;
		this.attack_ee = 0;
		this.attack_b = 0;
		this.attack_bb = 0;
		this.serve_3 = 0;
		this.serve_2 = 0;
		this.serve_1 = 0;
		this.serve_0 = 0;
		this.serve_wa = 0;
		this.serve_over = 0;
		this.serve_e = 0;
		this.block = 0;
	}

	public Stat(int id, int game_id, int player_id, int game_set,
			int reception_3, int reception_2, int reception_1, int reception_0,
			int reception_wa, int reception_over, int attack_3, int attack_2,
			int attack_1, int attack_0, int attack_e, int attack_ee,
			int attack_b, int attack_bb, int serve_3, int serve_2, int serve_1,
			int serve_0, int serve_wa, int serve_over, int serve_e, int block) {
		super();
		this.id = id;
		this.game_id = game_id;
		this.player_id = player_id;
		this.game_set = game_set;
		this.reception_3 = reception_3;
		this.reception_2 = reception_2;
		this.reception_1 = reception_1;
		this.reception_0 = reception_0;
		this.reception_wa = reception_wa;
		this.reception_over = reception_over;
		this.attack_3 = attack_3;
		this.attack_2 = attack_2;
		this.attack_1 = attack_1;
		this.attack_0 = attack_0;
		this.attack_e = attack_e;
		this.attack_ee = attack_ee;
		this.attack_b = attack_b;
		this.attack_bb = attack_bb;
		this.serve_3 = serve_3;
		this.serve_2 = serve_2;
		this.serve_1 = serve_1;
		this.serve_0 = serve_0;
		this.serve_wa = serve_wa;
		this.serve_over = serve_over;
		this.serve_e = serve_e;
		this.block = block;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(game_id);
		dest.writeInt(player_id);
		dest.writeInt(game_set);
		dest.writeInt(reception_3);
		dest.writeInt(reception_2);
		dest.writeInt(reception_1);
		dest.writeInt(reception_0);
		dest.writeInt(reception_wa);
		dest.writeInt(reception_over);

		dest.writeInt(attack_3);
		dest.writeInt(attack_2);
		dest.writeInt(attack_1);
		dest.writeInt(attack_0);
		dest.writeInt(attack_e);
		dest.writeInt(attack_ee);
		dest.writeInt(attack_b);
		dest.writeInt(attack_bb);

		dest.writeInt(serve_3);
		dest.writeInt(serve_2);
		dest.writeInt(serve_1);
		dest.writeInt(serve_0);
		dest.writeInt(serve_wa);
		dest.writeInt(serve_over);
		dest.writeInt(serve_e);
		
		dest.writeInt(block);
	}

	public static final Parcelable.Creator<Stat> CREATOR = new Parcelable.Creator<Stat>() {
		public Stat createFromParcel(Parcel in) {

			return new Stat(in);
		}

		public Stat[] newArray(int size) {
			return new Stat[size];
		}
	};

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getReceptionsTotal(){
		return (this.reception_0+this.reception_1+this.reception_2+this.reception_3+this.reception_over+this.reception_wa);
	}
	public int getReceptionsPositive(){
		if(getReceptionsTotal()>0){
		return ((100*(this.reception_3+this.reception_2))/getReceptionsTotal());
		}else{
			return 0;
		}
	}
	public int getReceptionIdeal(){
		if(getReceptionsTotal()>0){
			return ((100*(this.reception_3))/getReceptionsTotal());
		}else{
			return 0;
		}
	}
	public int getReceptionEff(){
		if(getReceptionsTotal()>0){
			return (100*((this.reception_3+this.reception_2+this.reception_1)-(this.reception_0+this.reception_over+this.reception_wa)))/getReceptionsTotal();
		}else{
			return 0;
		}
	}
	public int getAttacksTotal(){
		return (this.attack_0+this.attack_1+this.attack_2+this.attack_3+this.attack_b+this.attack_bb+this.attack_e+this.attack_ee);
	}
	public int getAttackPoints(){
		return (this.attack_3+this.attack_2);
	}
	public int getAttackEff(){
		if(getAttacksTotal()>0){
			return (100*(getAttackPoints())/getAttacksTotal());
		}else{
			return 0;
		}
	}
	public int getServeTotal(){
		return (this.serve_0+this.serve_1+this.serve_2+this.serve_3+this.serve_e+this.serve_over+this.serve_wa);
	}
	public int getTotalPoints(){
		return (this.attack_3+this.attack_2+this.serve_wa+this.block);
	}

}
