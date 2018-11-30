package InterfazHipodromo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import HipodromoServidor.ClienteHipodromo;

public class PanelApuestas extends JPanel implements ActionListener{
	
	
	private JLabel lbApuesta;
	private JTextField txtApuesta;
	private JButton btApostar;
	private JComboBox<String> caballo;
	private ClienteHipodromo principal;
	private JLabel lbCaballo;
	
	private String nombreCaballo;
	private String apuestCaballo;

	public PanelApuestas(ClienteHipodromo clienteHipodromo) {
		principal=clienteHipodromo;
		
		
		lbApuesta= new JLabel("Ingrese su apuesta");
		txtApuesta= new JTextField();
		btApostar=  new JButton("Apostar");
		btApostar.setActionCommand("apostar");
		btApostar.addActionListener(this);
		lbCaballo= new JLabel("Seleccione un caballo");
		caballo= new JComboBox<>(new String[] {"A","B","C","D","E","F"});
		
		
		setLayout(new GridLayout(1, 5));
		add(lbCaballo);
		add(caballo);
		add(lbApuesta);
		add(txtApuesta);
		add(btApostar);
		
	}

	public void desabilidarCaballo() {
		obtenerDatos();
	
		caballo.setEnabled(false);
		
	}
	public void obtenerDatos() {
		nombreCaballo= (String)caballo.getSelectedItem();
		apuestCaballo= txtApuesta.getText();
		
	}
	public String nombreCaballo() {
		return nombreCaballo;
	}
	public String apuestaCaballo() {
		return apuestCaballo;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	String comando= e.getActionCommand();
	if(comando.equals("apostar")) {
		principal.apostarCliente();
		btApostar.setEnabled(false);
		txtApuesta.setEditable(false);
	}
		
	}
}
