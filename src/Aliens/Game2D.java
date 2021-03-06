package Aliens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game2D extends JPanel implements ActionListener {

	final int WIDTH = 1000;
	final int HEIGHT = 600;
	
	double x_velocity_1 = 1;
	double x_velocity_2 = 1;
	double y_velocity_1 = 1;
	double y_velocity_2 = 1;

	Action upAction;
	Action downAction;
	Action leftAction;
	Action rightAction;
	
	int y_1 = 100;
	int x_1 = 100;
	int y_2 = 60;
	int x_2 = 300;
	
	Timer timer;
	Pj laser;
	
	ArrayList<BolaFuego> bolas_laser;
	ArrayList<Pj> aliens;
	
	Image alienImage;
	Image laserImage;
	Image bolaImage;
	Image background;

	int puntuacion;
	double nivel = 1; 
	boolean gameOver = false;
	int balas;
	int contadorBalas;
	
	Game2D()
	{	
		addKeyListener(new KeyP());
        setFocusable(true);
        requestFocusInWindow();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));	
		
		bolas_laser = new ArrayList<BolaFuego>();
		aliens = new ArrayList<Pj>();
		
		loadImages();
	
		laser = new Pj(0, 500, 0, -3, 100);
		
		timer = new Timer(10, this);
		timer.start();
	}
	
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;	
		
		g2D.drawImage(background, 0,0,null);
		
		g2D.drawImage(laserImage, laser.x, laser.y, null);
		// g2D.drawImage(laserImage, laser.x + 70, laser.y, - 70, 70, null);
		
		for(Pj alien : aliens)
			g2D.drawImage(alienImage, alien.x, alien.y, null);
		
		g2D.setFont(new Font("Ink free", Font.BOLD, 70));
		g2D.setColor(Color.white);
		
		for(int i = 0; i < balas; i++)
		{
			g2D.drawImage(bolaImage, (10 + (i*20)), 550, null);
		}
		
		if(!gameOver)
			g2D.drawString("PUNTUACION: " + puntuacion, 250, 80);
		else
			g2D.drawString("HAS MUERTO: " + puntuacion, 250, 80);
		
		bolas_laser.forEach((b) -> g2D.drawImage(bolaImage, b.x, b.y, null));
	}

	public void generarAlienigena(double nivel)
	{
		nivel /= 100;
		Random r = new Random();
		if(r.nextDouble() < nivel)
		{
			int x = r.nextInt(500) + 300;
			int y = r.nextInt(500);
			int y_vel = 1;
			int x_vel = 1;
			if(r.nextBoolean())
			{
				x_vel *= -1;
			}
			if(r.nextBoolean())
			{
				y_vel *= -1;
			}
			
			aliens.add(new Pj(x,y,x_vel,y_vel,100));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		actualizarLocAliens();
		actualizarLocLaser();
		actualizarLocBolas();
		checkColision();
		checkPlayerColision();
		generarAlienigena(nivel);
		balas();
		repaint();
	}
	
	public void checkPlayerColision()
	{
		aliens.forEach((alien) ->  {
			if (alien.y > laser.y && alien.y < laser.y + 70 && alien.x < 70)
			{
				gameOver();
			}
		});
	}
	
	public void gameOver()
	{
		gameOver = true;
		timer.stop();
		repaint();
	}
	
	public void actualizarLocBolas()
	{
		for(BolaFuego bola_laser : bolas_laser)
		{
			bola_laser.x += bola_laser.x_vel;
		}
	}
	
	public void actualizarLocLaser()
	{
		if(laser.y >= HEIGHT - 70 || laser.y <= 0)
			laser.y_vel *= -1;
		
		laser.y += laser.y_vel;
	}
	
	public void actualizarLocAliens()
	{
		for(Pj alien : aliens)	
		{
			if(alien.x >= WIDTH - 70 || alien.x <= 0)
				alien.x_vel *= -1;
			if(alien.y >= HEIGHT - 70 || alien.y <= 0)
				alien.y_vel *= -1;
			
			alien.y += alien.y_vel;
			alien.x += alien.x_vel;
		}
	}
	
	public void checkColision()
	{
		bolas_laser.forEach((b) -> {
			for (Pj alien : aliens)
			{
				if(b.x > alien.x && b.x < alien.x + 70 && b.y > alien.y && b.y < alien.y + 70)
				{
					alien.muerto = true;
					puntuacion ++;
					if(puntuacion % 50 == 0)
						nivel++;
				}
			}
		});
		
	
		aliens.removeIf(alien -> (alien.muerto));
	}
	
	public void balas()
	{
		contadorBalas++;
		if(contadorBalas % 100 == 0 && balas < 3)
		{
			balas++;
		}
	}
	
	public class KeyP extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyPressed(e);
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				laser.y_vel = -3;
				break;
			case KeyEvent.VK_DOWN:
				laser.y_vel = 3;
				break;
			case KeyEvent.VK_SPACE:
				if(balas > 0)
				{
					bolas_laser.add(new BolaFuego(45, laser.y, 20, 0, "/Images/bola_laser.png"));
					balas--;
				}
				break;
			}	
		}
	}
	
	public void loadImages()
	{
		background = new ImageIcon(getClass().getResource("/Images/espacio.jpeg")).getImage();
		alienImage = new ImageIcon(getClass().getResource("/Images/alien_1.png")).getImage();
		laserImage = new ImageIcon(getClass().getResource( "/Images/pistola_laser.png")).getImage();
		bolaImage = new ImageIcon(getClass().getResource("/Images/bola_laser.png")).getImage();
	}
}
