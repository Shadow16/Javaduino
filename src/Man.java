import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

@SuppressWarnings("serial")
public class Man extends JFrame implements ActionListener{

	JPanel panel;
	JLabel Lrojo, Lamarillo;
	JButton botonEncendido, botonApagado;
	JRadioButton radioRojo, radioAmarillo;
	
	private static final String TURN_AMARILLO_OFF = "0";
	private static final String TURN_AMARILLO_ON = "1";
	private static final String TURN_ROJO_OFF = "2";
	private static final String TURN_ROJO_ON = "3";
	
	// Variables de conexión
	private OutputStream output = null;
	SerialPort serialPort;
	private final String PUERTO = "COM3";
	
	private static final int TIMEOUT=2000; // Milisegundos
	
	private static final int DATA_RATE=9600;
	
	
	public Man(){
		setTitle("Proyecto Java con Arduino!!");
		setSize(300,400);
		setLocationRelativeTo(null);
		setResizable(false);
		componentes();
		inicializarConexion();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void componentes(){
		// PANEL
		panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		
		// RADIOBUTTON ROJO
		radioRojo = new JRadioButton("LED Rojo");
		radioRojo.setBounds(20,30,100,30);
		radioRojo.addActionListener(this);
		panel.add(radioRojo);
		
		// RADIOBUTTON AMARILLO
		radioAmarillo = new JRadioButton("LED Amarillo");
		radioAmarillo.setBounds(20,70,100,30);
		radioAmarillo.addActionListener(this);
		panel.add(radioAmarillo);
		
		// LABEL ROJO
		Lrojo = new JLabel("APAGADO");
		Lrojo.setBounds(40,150,100,30);
		panel.add(Lrojo);
		
		// LABEL AMARILLO
		Lamarillo = new JLabel("APAGADO");
		Lamarillo.setBounds(160,150,100,30);
		panel.add(Lamarillo);
		
		// BUTTON ENCENDIDO
		botonEncendido = new JButton("ENCENDER");
		botonEncendido.setBounds(20,200,100,30);
		botonEncendido.addActionListener(this);
		panel.add(botonEncendido);
		
		// BUTTON APAGADO
		botonApagado = new JButton("APAGAR");
		botonApagado.setBounds(140,200,100,30);
		botonApagado.addActionListener(this);
		panel.add(botonApagado);
	}
	
	public void inicializarConexion(){
		CommPortIdentifier puertoID = null;
		@SuppressWarnings("rawtypes")
		Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();
		
		while(puertoEnum.hasMoreElements()){
			CommPortIdentifier actualPuertoID = (CommPortIdentifier) puertoEnum.nextElement();
			if(PUERTO.equals(actualPuertoID.getName())){
				puertoID = actualPuertoID;
				break;
			}
		}
		
		if(puertoID == null){
			mostrarError("No se pudo conectar al puerto arduino");
			System.exit(ERROR);
		}
		
		try {
			serialPort = (SerialPort) puertoID.open(this.getClass().getName(), TIMEOUT);
			// Parametro puerto serie
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			output = serialPort.getOutputStream();
		} catch(Exception e){
			mostrarError(e.getMessage());
			System.exit(ERROR);
		}
	}
	
	private void enviarDatos(String datos){
		try {
			output.write(datos.getBytes());
		} catch(Exception e){
			mostrarError("Error al enviar datos!!");
			System.exit(ERROR);
		}	
	}
	
	public void mostrarError(String mensaje){
		JOptionPane.showMessageDialog(this, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(botonEncendido)){
			
			if(radioRojo.isSelected()){
				enviarDatos(TURN_ROJO_ON);
				Lrojo.setText("ENCENDIDO");
			}
			
			if(radioAmarillo.isSelected()){
				enviarDatos(TURN_AMARILLO_ON);
				Lamarillo.setText("ENCENDIDO");
			} 
		}
		
		if(e.getSource().equals(botonApagado)){
			
			if(radioRojo.isSelected()){
				enviarDatos(TURN_ROJO_OFF);
				Lrojo.setText("APAGADO");
			}
			
			if(radioAmarillo.isSelected()){
				enviarDatos(TURN_AMARILLO_OFF);
				Lamarillo.setText("APAGADO");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Man();
	}

}
