package Aliens;

public class Pj extends Entidad{

	int hp = 100;
	boolean muerto;

	public Pj(int x, int y, int x_vel, int y_vel, int hp, String img) {
		super(x,y,x_vel,y_vel,img);
		this.hp = hp;
	}
	public Pj(int x, int y, int x_vel, int y_vel, int hp) {
		super(x,y,x_vel,y_vel);
		this.hp = hp;
	}
}
