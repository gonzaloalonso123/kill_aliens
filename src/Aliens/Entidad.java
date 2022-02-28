package Aliens;

public class Entidad {

	int x;
	int y;
	int x_vel;
	int y_vel;
	String img;
	
	public Entidad(int x, int y, int x_vel, int y_vel, String img) {
		super();
		this.x = x;
		this.y = y;
		this.x_vel = x_vel;
		this.y_vel = y_vel;
		this.img = img;
	}
	
	public Entidad(int x, int y, int x_vel, int y_vel) {
		super();
		this.x = x;
		this.y = y;
		this.x_vel = x_vel;
		this.y_vel = y_vel;
	}
		
}
