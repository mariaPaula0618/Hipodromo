package InterfazHipodromo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import HipodromoServidor.ClienteHipodromo;

public class PanelCarrera extends JPanel{

	private ClienteHipodromo principal;
	private int[] posicion;
	private String caballo;

	public PanelCarrera(ClienteHipodromo clienteHipodromo) {
		principal=clienteHipodromo;	
		posicion= new int[] {0,0,0,0,0,0};
		caballo="";
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		Graphics2D p= (Graphics2D) g;
		p.setBackground(Color.BLACK);
		ImageIcon a= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(a.getImage(), posicion[0]+15, 0, this);
		p.drawString("A", 8, 30);
		if(caballo.equals("A")) {
			p.setColor(Color.red);
			p.drawOval(posicion[0]+20, 0, 20, 20);
			p.fillOval(posicion[0]+20, 0, 20, 20);
			p.setColor(Color.black);
		}
		ImageIcon b= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(b.getImage(), posicion[1]+15, 70, this);
		p.drawString("B", 8, 104);
		if(caballo.equals("B")) {
			p.setColor(Color.red);
			p.drawOval(posicion[1]+20, 70, 20, 20);
			p.fillOval(posicion[1]+20, 70, 20, 20);
			p.setColor(Color.black);
		}
		ImageIcon c= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(c.getImage(), posicion[2]+15, 140, this);
		p.drawString("C", 8, 174);
		if(caballo.equals("C")) {
			p.setColor(Color.red);
			p.drawOval(posicion[2]+20, 140, 20, 20);
			p.fillOval(posicion[2]+20,  140, 20, 20);
			p.setColor(Color.black);
		}
		ImageIcon d= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(d.getImage(), posicion[3]+15, 210, this);
		p.drawString("D", 8, 242);
		if(caballo.equals("D")) {
			p.setColor(Color.red);
			p.drawOval(posicion[3]+20, 210, 20, 20);
			p.fillOval(posicion[3]+20, 210, 20, 20);
			p.setColor(Color.black);
		}
		ImageIcon e= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(e.getImage(), posicion[4]+15, 280, this);
		p.drawString("E", 8, 313);
		if(caballo.equals("E")) {
			p.setColor(Color.red);
			p.drawOval(posicion[4]+20, 280, 20, 20);
			p.fillOval(posicion[4]+20, 280, 20, 20);
			p.setColor(Color.black);
		}
		ImageIcon f= new ImageIcon("./Data/caballo_2.gif");
		p.drawImage(f.getImage(), posicion[5]+15, 350, this);
		
		p.drawString("F", 8, 384);
		if(caballo.equals("F")) {
			p.setColor(Color.red);
			p.drawOval(posicion[5]+20, 350, 20, 20);
			p.fillOval(posicion[5]+20, 350, 20, 20);
			p.setColor(Color.black);
		}

	}

	public void ponerPosicion(int[] pos) {
		posicion=pos;
	}
	public void ponerNombre(String nombre) {
		caballo=nombre;
	}

}
