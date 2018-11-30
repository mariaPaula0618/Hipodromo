package InterfazHipodromo;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelBanner extends JPanel {
	public final static String RUTA_BANNER= "./Data/hipodromo.jpg";
	
	   
	
	
	//CONSTRUCTOR
	
	public PanelBanner (){
		JLabel imagen = new JLabel( );
        ImageIcon icono = new ImageIcon( RUTA_BANNER );
        // La agrega a la etiqueta
        imagen = new JLabel( "" );
        imagen.setIcon( icono );
        add( imagen );

        setBackground( Color.WHITE);
	}
}
