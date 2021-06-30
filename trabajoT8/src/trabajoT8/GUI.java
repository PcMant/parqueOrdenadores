package trabajoT8;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JPanel;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

/**
 * * Esta clase contiene una interfaz gráfica que además interactua con una base de datos.
 * Interfaz gráfica generada en eclipse usando el plugin WindowBuilder 1.9.5
 * 
 * @author Juan Molina
 * 
 *
 */
public class GUI {
	
	//Instancias de los elementos de la interfaz
	private JFrame frmParqueDeOrdenadores;
	private JTextField textField_Marca;
	private JTextField textField_Modelo;
	private JTextField textField_Procesador;
	private JTextField textField_TipoMemoria;
	private JTextField textField_CantidadMemoria;
	private JTextField textField_Ubicacion;
	private JTextField textField_NumeroSerie;
	private JTextField textField_bIdInterno;
	private JTextField textField_bUbicacion;
	private JTextField textField_bTipoMemoria;
	private JTextField textField_BIdInterno;
	private JTextField textField_AMarca;
	private JTextField textField_AModelo;
	private JTextField textField_AProcesador;
	private JTextField textField_ATipoMemoria;
	private JTextField textField_ACantidadMemoria;
	private JTextField textField_AUbicacion;
	private JTextField textField_ANumeroSerie;
	private JTextField textField_AIdInterno;
	private static JTable table_1;
	private JTextField textField_NAIdInterno;
	
	//Variables de conteo para añadir las filas en la Jtable table_1 necesarias
	/** Contador para contar las filas con datos */
	private int filasDatos = 0;
	/** Contador para filas vacias, util para terminar de rellenar la ventana */
	private int filasVacias = 0;

	//Metodos utiles para las sentencias
	
	/**
	 * Método creado para eliminar espacios de String para solventar problemas
	 * con los espacios campos vacios y convierte texto a mayúsculas.
	 * Ofrece la opción de sustituir campos vacios a null.
	 * 
	 * @param contenido Texto tipo String
	 * @param Valor boleano que de ponerlo en true aplica la conversión de un campo vacio a null
	 * @return retorna String una vez procesado
	 */
	private String fueraEspacios(String contenido, boolean nul) {
		String texto = contenido.toUpperCase();
		
		//Comprueba si esta vacio o si empieza por espcacio
		if(texto.isEmpty() || texto.equals("^ ")) {
			//Quitar espacios, tabuladores y retornos
			texto = texto.replaceAll("\\s","");
		}
		
		
		//Quitar espacios del principio y del final
		texto = texto.trim();
		
		//Modifica a null si se especifica que se quiere sustituir valor vacio por NULL pero en modo String
		//Se comprueba la variable null y que texto este vacio
		if(nul && texto.isEmpty()) {
			texto = null;
		}
		
		return texto;
	}
		
	//Métodos utiles para listar datos en la JTable table_1
	
	/**
	 * Método que agrega los resultados a una JTable table_1 de todos los datos
	 */
	private void listarTodo(){
		//Para datos
		//Para mostrar en la tabla
		
				//Inicio conexión
				Conexion con = new Conexion();
				Connection conexion = con.conectar();
				//variable que guarda la sentencia a ejecutar
				String sql = "SELECT * FROM ordenador";
				//La variable st de tipo Statement sirve para enviar sentencias
				Statement st;
				//Instancio el objeto model, el cual es un modelo a aplicar en tablas Jtable
				DefaultTableModel model = new DefaultTableModel();
				//Se añaden las columnas necesarias
				model.addColumn("Id");
				model.addColumn("Marca");
				model.addColumn("Modelo");
				model.addColumn("Procesador");
				model.addColumn("Tipo RAM");
				model.addColumn("Memoria");
				model.addColumn("Ubicacion");
				model.addColumn("S/N");
				//aplica los cambios a table_1
				table_1.setModel(model);
				//determina la cantidad de columnas
				String[] dato = new String[8];
				//Renicio los contadores
				filasDatos = 0;
				filasVacias = 0;
				
				try {
					st = conexion.createStatement();
					//Ejecuta la sentencia sql
					ResultSet result = st.executeQuery(sql);
					
					//El bucle recorre todos los resultados
					//Añadiendo las filas con datos a la tabla
					while(result.next()) {
						dato[0]=result.getString(1);
						dato[1]=result.getString(2);
						dato[2]=result.getString(3);
						dato[3]=result.getString(4);
						dato[4]=result.getString(5);
						dato[5]=result.getString(6);
						dato[6]=result.getString(7);
						dato[7]=result.getString(8);
						//System.out.println(result.getString(1));
						model.addRow(dato);
						filasDatos++;
					}
					
					//cierra la conexión
					conexion.close();
					//pasa los valores de un contador a otro
					filasVacias = filasDatos;
					
					if(filasDatos<13) {
						//Añade el resto de filas en vacio para rellenar la ventana
						for(int i=0; i<=12-filasDatos; i++) {
							dato[0]="";
							dato[1]="";
							dato[2]="";
							dato[3]="";
							dato[4]="";
							dato[5]="";
							dato[6]="";
							dato[7]="";
							model.addRow(dato);
							filasVacias++;
						}
						
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
				}
	}
	
	
	/**
	 * Esta clase añade a la JTable table_1 los resultados en función de la
	 * columna indicada y el parámtro indicado.
	 * 
	 * @param columna Columna correspondiente dentro de la tabla ordenador
	 * @param parametro Dato en función a buscar
	 */
	private void listarSegun1param(String columna, String parametro) {
		//Para datos
		//Para mostrar en la tabla
				//Inicio una conexión con la base de datos
				Conexion con = new Conexion();
				Connection conexion = con.conectar();
				
				//Guardo la sentencia a ejecutar en una variable
				String sql = "SELECT * FROM ordenador WHERE "+columna+"="+"'"+parametro+"'";
				//La variable st es para preparar sentencias
				Statement st;
				//Instancio en un objeto un modelo de tabla
				DefaultTableModel model = new DefaultTableModel();
				//Establezco las columnas
				model.addColumn("Id");
				model.addColumn("Marca");
				model.addColumn("Modelo");
				model.addColumn("Procesador");
				model.addColumn("Tipo RAM");
				model.addColumn("Memoria");
				model.addColumn("Ubicacion");
				model.addColumn("S/N");
				//le aplico el modelo a la tabla
				table_1.setModel(model);
				//Este string sirve para determinar la cantidad de columnas
				String[] dato = new String[8];
				//Reinicio los contadores a 0
				filasDatos = 0;
				filasVacias = 0;
				
				try {
					
					st = conexion.createStatement();
					
					ResultSet result = st.executeQuery(sql);
					
					//Va añadiendo filas con datos, el bucle recorre todos los resultados obenidos
					while(result.next()) {
						dato[0]=result.getString(1);
						dato[1]=result.getString(2);
						dato[2]=result.getString(3);
						dato[3]=result.getString(4);
						dato[4]=result.getString(5);
						dato[5]=result.getString(6);
						dato[6]=result.getString(7);
						dato[7]=result.getString(8);
						//System.out.println(result.getString(1));
						model.addRow(dato);
						filasDatos++;
					}
					
				//Cierro la conexión con la base de datos
					conexion.close();
					
					filasVacias = filasDatos;
					//Para añadir las columnas restantes para rellenar la ventana
					if(filasDatos<13) {
						
						for(int i=0; i<=12-filasDatos; i++) {
							dato[0]="";
							dato[1]="";
							dato[2]="";
							dato[3]="";
							dato[4]="";
							dato[5]="";
							dato[6]="";
							dato[7]="";
							model.addRow(dato);
							filasVacias++;
						}
						
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
					//Llena la ventana de filas vacias
					for(int i=0; i<=12; i++) {
						dato[0]="";
						dato[1]="";
						dato[2]="";
						dato[3]="";
						dato[4]="";
						dato[5]="";
						dato[6]="";
						dato[7]="";
						model.addRow(dato);	
					}	
				}
	}
	
	/**
	 * Muestra resultados en la JTable table_1 en función de las columnas
	 * indicadas y su dato indicado como parámetros.
	 * 
	 * @param columna1 Primera columna a especificar de la tabla ordenador
	 * @param parametro1 Primer dato a especificar del parámetro columna1
	 * @param columna2 Segunda columna a especificar de la tabla ordenador
	 * @param parametro2 Segundo dato a especificar del parámetro columna2
	 */
	private void listarSegun2param(String columna1, String parametro1, String columna2, String parametro2) {
		//Para datos
		//Para mostrar en la tabla
				//Inicio conexión con la base de datos
				Conexion con = new Conexion();
				Connection conexion = con.conectar();
				
				//Almaceno en un string la sentencia que voy a ejecutar
				String sql = "SELECT * FROM ordenador WHERE "+columna1+"="+"'"+parametro1+"'"+" AND "+columna2+"="+"'"+parametro2+"'";
				//La variable st es para preparar sentencias
				Statement st;
				//Instancia un objeto de modelo de tabla
				DefaultTableModel model = new DefaultTableModel();
				//Va añadiendo las columnas
				model.addColumn("Id");
				model.addColumn("Marca");
				model.addColumn("Modelo");
				model.addColumn("Procesador");
				model.addColumn("Tipo RAM");
				model.addColumn("Memoria");
				model.addColumn("Ubicacion");
				model.addColumn("S/N");
				//Aplica configuración de tabla
				table_1.setModel(model);
				
				//Este string me sirve para el número de columnas
				String[] dato = new String[8];
				
				//Resetear los contadores
				filasDatos = 0;
				filasVacias = 0;
				
				try {
					st = conexion.createStatement();
					
					ResultSet result = st.executeQuery(sql);
					
					while(result.next()) { //Esto no para hasta no recorrer todos los resultados
						dato[0]=result.getString(1);
						dato[1]=result.getString(2);
						dato[2]=result.getString(3);
						dato[3]=result.getString(4);
						dato[4]=result.getString(5);
						dato[5]=result.getString(6);
						dato[6]=result.getString(7);
						dato[7]=result.getString(8);
						//System.out.println(result.getString(1));
						model.addRow(dato); //Añade filas con los datos a la tabla
						filasDatos++; //Cuenta los datos
					}
					
					
					conexion.close(); //cierra la conexión
					
					filasVacias = filasDatos;
					
					if(filasDatos<13) {
						
						for(int i=0; i<=12-filasDatos; i++) {
							dato[0]="";
							dato[1]="";
							dato[2]="";
							dato[3]="";
							dato[4]="";
							dato[5]="";
							dato[6]="";
							dato[7]="";
							model.addRow(dato);
							filasVacias++;
						}
						
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
					
					for(int i=0; i<=12; i++) {
						dato[0]="";
						dato[1]="";
						dato[2]="";
						dato[3]="";
						dato[4]="";
						dato[5]="";
						dato[6]="";
						dato[7]="";
						model.addRow(dato);
						
					}	
				}
	}
	
	/**
	 * Muestra resultados en la JTable table_1 en función de las columnas
	 * indicadas y su dato indicado como parámetros.
	 * 
	 * @param columna1 Primera columna a especificar de la tabla ordenador
	 * @param parametro1 Primer dato a especificar del parámetro columna1
	 * @param columna2 Segunda columna a especificar de la tabla ordenador
	 * @param parametro2 Segundo dato a especificar del parámetro columna2
	 * @param columna3 Tercera columna especificar de la tabla ordenador
	 * @param parametro3 Segundo dato a especificar del parámetro columna 3
	 */
	private void listarSegun3param(String columna1, String parametro1, String columna2, String parametro2, String columna3, String parametro3) {
		//Para datos
		//Para mostrar en la tabla
				//Conexión con la base de datos
				Conexion con = new Conexion();
				Connection conexion = con.conectar();
				
				//GUarda en la variable la sentencia a ejecutar
				String sql = "SELECT * FROM ordenador WHERE "+columna1+"="+"'"+parametro1+"'"+" AND "+columna2+"="+"'"+parametro2+"'"+" AND "+columna3+"="+"'"+parametro3+"'";
				//La variable st es para preparar sentencias sql
				Statement st;
				//Intancia en un objeto un modelo de tabla
				DefaultTableModel model = new DefaultTableModel();
				//Añade columnas al modelo
				model.addColumn("Id");
				model.addColumn("Marca");
				model.addColumn("Modelo");
				model.addColumn("Procesador");
				model.addColumn("Tipo RAM");
				model.addColumn("Memoria");
				model.addColumn("Ubicacion");
				model.addColumn("S/N");
				//Aplica el modelo a table 1
				table_1.setModel(model);
				//Este string me sirve para determinar la cantidad de columnas
				String[] dato = new String[8];
				//Reinicio los contadores
				filasDatos = 0;
				filasVacias = 0;
				
				try {
					st = conexion.createStatement();
					
					ResultSet result = st.executeQuery(sql);
					
					//Este bucle recorre todas las columnas 
					while(result.next()) {
						dato[0]=result.getString(1);
						dato[1]=result.getString(2);
						dato[2]=result.getString(3);
						dato[3]=result.getString(4);
						dato[4]=result.getString(5);
						dato[5]=result.getString(6);
						dato[6]=result.getString(7);
						dato[7]=result.getString(8);
						//System.out.println(result.getString(1));
						model.addRow(dato);
						filasDatos++;
					}
					
					//Cierra la conexión
					conexion.close();
					//Pasa el valor de un contador a otro
					filasVacias = filasDatos;
					//Este bloque se encarga de rellenar la pantalla poniendo las filas restantes vacias
					if(filasDatos<13) {
						
						for(int i=0; i<=12-filasDatos; i++) {
							dato[0]="";
							dato[1]="";
							dato[2]="";
							dato[3]="";
							dato[4]="";
							dato[5]="";
							dato[6]="";
							dato[7]="";
							model.addRow(dato);
							filasVacias++;
						}
						
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
					//En caso de error rellena la ventana
					for(int i=0; i<=12; i++) {
						dato[0]="";
						dato[1]="";
						dato[2]="";
						dato[3]="";
						dato[4]="";
						dato[5]="";
						dato[6]="";
						dato[7]="";
						model.addRow(dato);	
					}
				}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmParqueDeOrdenadores.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmParqueDeOrdenadores = new JFrame();
		frmParqueDeOrdenadores.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/trabajoT8/icono.jpg")));
		frmParqueDeOrdenadores.setTitle("Parque de ordenadores");
		frmParqueDeOrdenadores.setResizable(false);
		frmParqueDeOrdenadores.setBounds(100, 100, 605, 405);
		frmParqueDeOrdenadores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmParqueDeOrdenadores.getContentPane().setLayout(null);
		frmParqueDeOrdenadores.setLocationRelativeTo(null); //Lo puse para centrar ventana
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 599, 376);
		frmParqueDeOrdenadores.getContentPane().add(tabbedPane);
		
		JPanel panel_deRegistro = new JPanel();
		tabbedPane.addTab("Registro de un equipo", null, panel_deRegistro, null);
		panel_deRegistro.setLayout(null);
		
		JPanel panel_Registro = new JPanel();
		panel_Registro.setBorder(new TitledBorder(null, "Registro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Registro.setBounds(33, 30, 524, 292);
		panel_deRegistro.add(panel_Registro);
		panel_Registro.setLayout(null);
		
		JLabel lblNewLabel_Marca = new JLabel("Marca");
		lblNewLabel_Marca.setBounds(38, 37, 46, 14);
		panel_Registro.add(lblNewLabel_Marca);
		
		JLabel lblNewLabel_Modelo = new JLabel("Modelo");
		lblNewLabel_Modelo.setBounds(38, 62, 46, 14);
		panel_Registro.add(lblNewLabel_Modelo);
		
		JLabel lblNewLabel_Procesador = new JLabel("Procesador");
		lblNewLabel_Procesador.setBounds(38, 87, 150, 14);
		panel_Registro.add(lblNewLabel_Procesador);
		
		JLabel lblNewLabel_TipoMemoria = new JLabel("Tipo de RAM");
		lblNewLabel_TipoMemoria.setBounds(38, 112, 150, 14);
		panel_Registro.add(lblNewLabel_TipoMemoria);
		
		JLabel lblNewLabel_CantidadMemoria = new JLabel("Cantidad de memoria RAM");
		lblNewLabel_CantidadMemoria.setBounds(38, 137, 150, 14);
		panel_Registro.add(lblNewLabel_CantidadMemoria);
		
		JLabel lblNewLabel_Ubicacion = new JLabel("Ubicaci\u00F3n");
		lblNewLabel_Ubicacion.setBounds(38, 165, 150, 14);
		panel_Registro.add(lblNewLabel_Ubicacion);
		
		JLabel lblNewLabel_NumeroSerie = new JLabel("N\u00FAmero de serie");
		lblNewLabel_NumeroSerie.setBounds(38, 190, 150, 14);
		panel_Registro.add(lblNewLabel_NumeroSerie);
		
		textField_Marca = new JTextField();
		textField_Marca.setBounds(198, 34, 295, 20);
		panel_Registro.add(textField_Marca);
		textField_Marca.setColumns(10);
		
		textField_Modelo = new JTextField();
		textField_Modelo.setBounds(198, 59, 295, 20);
		panel_Registro.add(textField_Modelo);
		textField_Modelo.setColumns(10);
		
		textField_Procesador = new JTextField();
		textField_Procesador.setBounds(198, 84, 295, 20);
		panel_Registro.add(textField_Procesador);
		textField_Procesador.setColumns(10);
		
		textField_TipoMemoria = new JTextField();
		textField_TipoMemoria.setBounds(198, 109, 295, 20);
		panel_Registro.add(textField_TipoMemoria);
		textField_TipoMemoria.setColumns(10);
		
		textField_CantidadMemoria = new JTextField();
		textField_CantidadMemoria.setBounds(198, 134, 295, 20);
		panel_Registro.add(textField_CantidadMemoria);
		textField_CantidadMemoria.setColumns(10);
		
		textField_Ubicacion = new JTextField();
		textField_Ubicacion.setBounds(198, 162, 295, 20);
		panel_Registro.add(textField_Ubicacion);
		textField_Ubicacion.setColumns(10);
		
		textField_NumeroSerie = new JTextField();
		textField_NumeroSerie.setBounds(198, 187, 295, 20);
		panel_Registro.add(textField_NumeroSerie);
		textField_NumeroSerie.setColumns(10);
		
		JButton btnNewButton_Registrar = new JButton("Registrar");
		btnNewButton_Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Inicio una conexión con la base de datos instanciando el objeto con de la clase Conexion
				Conexion con = new Conexion();
				Connection conexion = con.conectar(); //Ejecuto el método conectar instanciado como conexion
				
				try {
					//Guardo en la variable la sentencia sql que voy a conectar
					String query = "INSERT INTO ordenador (Marca,Modelo,Procesador,TipoMemoria,CantidadMemoria,Ubicacion,NumeroSerie) values(?,?,?,?,?,?,?)";
					//Preparo la sentencia
					PreparedStatement statement = conexion.prepareStatement(query);
					
					statement.setString(1, fueraEspacios(textField_Marca.getText(),true));
					statement.setString(2, fueraEspacios(textField_Modelo.getText(),true));
					statement.setString(3, fueraEspacios(textField_Procesador.getText(),true));
					statement.setString(4, fueraEspacios(textField_TipoMemoria.getText(),true));
					statement.setString(5, fueraEspacios(textField_CantidadMemoria.getText(),true));
					statement.setString(6, fueraEspacios(textField_Ubicacion.getText(),true));
					statement.setString(7, fueraEspacios(textField_NumeroSerie.getText(),true));
					//Actualizo la base de datos, ejecutando dicha sentencia
					statement.executeUpdate();
					//Cierro la conexión
					conexion.close();
					//Aviso de que se hizo la sentencia de manera correcta
					JOptionPane.showMessageDialog(null, "Ordenador registrado correctamente!");
					
				}catch (Exception e) {
					//Aviso en caso de error por consola
					System.out.println("Ooops! error al conectarse a la base de datos");
					e.printStackTrace();
				}
				
			}
		});
		
		
		
		
		btnNewButton_Registrar.setBounds(225, 239, 89, 23);
		panel_Registro.add(btnNewButton_Registrar);
		
		JPanel panel_Listado = new JPanel();
		tabbedPane.addTab("Listado de equipos", null, panel_Listado, null);
		panel_Listado.setLayout(null);
		
		JScrollPane scrollPane_paraLaTabla = new JScrollPane();
		scrollPane_paraLaTabla.setBounds(0, 113, 594, 235);
		panel_Listado.add(scrollPane_paraLaTabla);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Marca", "Modelo", "Prodesador", "Tipo RAM", "Memoria", "Ubicaci\u00F3n", "S/N"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(52);
		table_1.setToolTipText("");
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_paraLaTabla.setViewportView(table_1);
		listarTodo();
		
		JLabel lblNewLabel_bIdInterno = new JLabel("ID");
		lblNewLabel_bIdInterno.setBounds(10, 11, 46, 14);
		panel_Listado.add(lblNewLabel_bIdInterno);
		
		JLabel lblNewLabel_bUbicacion = new JLabel("Ubicaci\u00F3n");
		lblNewLabel_bUbicacion.setBounds(10, 36, 111, 14);
		panel_Listado.add(lblNewLabel_bUbicacion);
		
		JLabel lblNewLabel_bTipoMemoria = new JLabel("Tipo de memoria");
		lblNewLabel_bTipoMemoria.setBounds(10, 61, 111, 14);
		panel_Listado.add(lblNewLabel_bTipoMemoria);
		
		textField_bIdInterno = new JTextField();
		textField_bIdInterno.setBounds(125, 8, 232, 20);
		panel_Listado.add(textField_bIdInterno);
		textField_bIdInterno.setColumns(10);
		
		textField_bUbicacion = new JTextField();
		textField_bUbicacion.setBounds(125, 33, 232, 20);
		panel_Listado.add(textField_bUbicacion);
		textField_bUbicacion.setColumns(10);
		
		textField_bTipoMemoria = new JTextField();
		textField_bTipoMemoria.setBounds(125, 58, 232, 20);
		panel_Listado.add(textField_bTipoMemoria);
		textField_bTipoMemoria.setColumns(10);
		
		//Este elemento se encarga del conteo de los resultados de mostrarlo por un elemento JLabel
		JLabel lblNewLabel_Contador = new JLabel("Total de registros: "+filasDatos);
		panel_Listado.add(lblNewLabel_Contador);
		lblNewLabel_Contador.setBounds(10, 88, 213, 14);
		
		JButton btnNewButton_Buscar = new JButton("Buscar/Actualizar resultados");
		btnNewButton_Buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Instrucciones de evento click en boton Buscar
				
						/*Estas estructuras if else lo que hacen es comprobar si los campos del formulario
						 * de busqueda tienen contenido o si están vacias y en consecuencia de ello se listara
						 * en la JTable unos datos u otros.
						 */
						if(textField_bIdInterno.getText().isEmpty()==false&&(textField_bUbicacion.getText().isEmpty() || textField_bTipoMemoria.getText().isEmpty())) {
							
							if(textField_bIdInterno.getText().isEmpty()==false && textField_bUbicacion.getText().isEmpty()==false && textField_bTipoMemoria.getText().isEmpty()) {
								//Listar por IdInterno y Ubicacion de la tabla ordenador
								System.out.println("Esto deberá listar por Id y Ubicacion");
								listarSegun2param("IdInterno",textField_bIdInterno.getText(),"Ubicacion",textField_bUbicacion.getText());

							}else if(textField_bIdInterno.getText().isEmpty()==false && textField_bTipoMemoria.getText().isEmpty()==false && textField_bUbicacion.getText().isEmpty()) {
								//Listar por IdInterno y TipoMemoria de la tabla ordenador
								System.out.println("Esto deberá listar por Id y Tipo de Memoria");
								listarSegun2param("IdInterno",textField_bIdInterno.getText(),"TipoMemoria",textField_bTipoMemoria.getText());
								
							}else if(textField_bUbicacion.getText().isEmpty() && textField_bTipoMemoria.getText().isEmpty()) {
								//Listar por IdInterno de la tabla ordenador
								System.out.println("Esto deberá listar por id");
								listarSegun1param("IdInterno",textField_bIdInterno.getText());
							}
							
						}else if(textField_bUbicacion.getText().isEmpty()==false&&(textField_bIdInterno.getText().isEmpty() || textField_bTipoMemoria.getText().isEmpty())) {
							
							if(textField_bUbicacion.getText().isEmpty()==false && textField_bTipoMemoria.getText().isEmpty()==false && textField_bIdInterno.getText().isEmpty()) {
								//Listar por Ubicacion y TipoMemoria de la tabla ordenador
								System.out.println("Esto deberá listar por Ubicación y Tipo de Memoria");
								listarSegun2param("Ubicacion",textField_bUbicacion.getText(),"TipoMemoria",textField_bTipoMemoria.getText());

							}else if(textField_bIdInterno.getText().isEmpty() && textField_bTipoMemoria.getText().isEmpty()) {
								//Listar por Ubicacion de la tabla ordenador
								System.out.println("Esto deberá listar por ubicacion");
								listarSegun1param("Ubicacion",textField_bUbicacion.getText());
							}
						}else if(textField_bTipoMemoria.getText().isEmpty()==false&&(textField_bIdInterno.getText().isEmpty() || textField_bUbicacion.getText().isEmpty())) {
							if(textField_bIdInterno.getText().isEmpty() && textField_bUbicacion.getText().isEmpty()) {
								//Listar por tipo de memoria de la tabla ordenador
								System.out.println("Esto deberá listar por tipo de memoria");
								listarSegun1param("TipoMemoria",textField_bTipoMemoria.getText());
							}
						}else if(textField_bTipoMemoria.getText().isEmpty()==false && textField_bIdInterno.getText().isEmpty()==false && textField_bUbicacion.getText().isEmpty()==false){
							//Listar por IdInterno, TipoMemoria y Ubicacion
							System.out.println("Esto deberá listar por IdInterno, TipoMemoria y Ubicacion");
							listarSegun3param("IdInterno",textField_bIdInterno.getText(),"TipoMemoria",textField_bTipoMemoria.getText(),"Ubicacion",textField_bUbicacion.getText());
							
						}else if(textField_bTipoMemoria.getText().isEmpty()&&textField_bIdInterno.getText().isEmpty()&&textField_bUbicacion.getText().isEmpty()){
							//Lista todos los datos de la tabla ordenador
							System.out.println("Esto deberá listar todos los datos");
							listarTodo();
						}
						
						//Actualiza el contador tras cada ejecución de SELECT MySQL
						lblNewLabel_Contador.setText("Total de registros: "+filasDatos);
				
			}
		});
		btnNewButton_Buscar.setBounds(383, 32, 201, 23);
		panel_Listado.add(btnNewButton_Buscar);
		
		JPanel panel_deBorrado = new JPanel();
		tabbedPane.addTab("Borrado de un registro", null, panel_deBorrado, null);
		panel_deBorrado.setLayout(null);
		
		JPanel panel_Borrar = new JPanel();
		panel_Borrar.setLayout(null);
		panel_Borrar.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Borrado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_Borrar.setBounds(32, 28, 524, 292);
		panel_deBorrado.add(panel_Borrar);
		
		JLabel lblId_BIdInterno = new JLabel("Id interno");
		lblId_BIdInterno.setBounds(58, 116, 94, 14);
		panel_Borrar.add(lblId_BIdInterno);
		
		textField_BIdInterno = new JTextField();
		textField_BIdInterno.setColumns(10);
		textField_BIdInterno.setBounds(173, 113, 295, 20);
		panel_Borrar.add(textField_BIdInterno);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Intrucciones para el evento de borrado
				Conexion con = new Conexion(); //Instancio el objeto con de la clase Conexion
				Connection conexion = con.conectar(); //Ejecuto el método conectar instanciandolo como conexion
				
				try {
					//Guardo la sentencia en una variable
					String query = "DELETE FROM ordenador WHERE IdInterno=?";
					//Preparo la sentencia
					PreparedStatement statement = conexion.prepareStatement(query);
					//El unico campo comodin dentro de la sentencia
					statement.setString(1, fueraEspacios(textField_BIdInterno.getText(),false));
					//Actualizo la base de datos y ejecuto
					statement.executeUpdate();
					//Cierro la conexión
					conexion.close();
					//Aviso de ha sido borrado el registro 
					JOptionPane.showMessageDialog(null, "Ordenador borrado correctamente!");
					
				}catch (Exception e) {
					//Error en caso de fallo
					System.out.println("Ooops! error al conectarse a la base de datos");
					e.printStackTrace();
				}
				
			}
		});
		btnBorrar.setBounds(231, 144, 89, 23);
		panel_Borrar.add(btnBorrar);
		
		JPanel panel_deActualizar = new JPanel();
		tabbedPane.addTab("Actualizar un registro", null, panel_deActualizar, null);
		panel_deActualizar.setLayout(null);
		
		JPanel panel_Actualizar = new JPanel();
		panel_Actualizar.setLayout(null);
		panel_Actualizar.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Actualizar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_Actualizar.setBounds(29, 27, 524, 292);
		panel_deActualizar.add(panel_Actualizar);
		
		JLabel lblNewLabel_AMarca = new JLabel("Marca");
		lblNewLabel_AMarca.setBounds(38, 78, 46, 14);
		panel_Actualizar.add(lblNewLabel_AMarca);
		
		JLabel lblNewLabel_AModelo = new JLabel("Modelo");
		lblNewLabel_AModelo.setBounds(38, 103, 46, 14);
		panel_Actualizar.add(lblNewLabel_AModelo);
		
		JLabel lblNewLabel_AProcesador = new JLabel("Procesador");
		lblNewLabel_AProcesador.setBounds(38, 128, 150, 14);
		panel_Actualizar.add(lblNewLabel_AProcesador);
		
		JLabel lblNewLabel_ATipoMemoria = new JLabel("Tipo de RAM");
		lblNewLabel_ATipoMemoria.setBounds(38, 153, 150, 14);
		panel_Actualizar.add(lblNewLabel_ATipoMemoria);
		
		JLabel lblNewLabel_ACantidadMemoria = new JLabel("Cantidad de memoria RAM");
		lblNewLabel_ACantidadMemoria.setBounds(38, 178, 150, 14);
		panel_Actualizar.add(lblNewLabel_ACantidadMemoria);
		
		JLabel lblNewLabel_AUbicacion = new JLabel("Ubicaci\u00F3n");
		lblNewLabel_AUbicacion.setBounds(38, 205, 150, 14);
		panel_Actualizar.add(lblNewLabel_AUbicacion);
		
		JLabel lblNewLabel_ANumeroSerie = new JLabel("N\u00FAmero de serie");
		lblNewLabel_ANumeroSerie.setBounds(38, 230, 150, 14);
		panel_Actualizar.add(lblNewLabel_ANumeroSerie);
		
		textField_AMarca = new JTextField();
		textField_AMarca.setColumns(10);
		textField_AMarca.setBounds(198, 75, 295, 20);
		panel_Actualizar.add(textField_AMarca);
		
		textField_AModelo = new JTextField();
		textField_AModelo.setColumns(10);
		textField_AModelo.setBounds(198, 100, 295, 20);
		panel_Actualizar.add(textField_AModelo);
		
		textField_AProcesador = new JTextField();
		textField_AProcesador.setColumns(10);
		textField_AProcesador.setBounds(198, 125, 295, 20);
		panel_Actualizar.add(textField_AProcesador);
		
		textField_ATipoMemoria = new JTextField();
		textField_ATipoMemoria.setColumns(10);
		textField_ATipoMemoria.setBounds(198, 150, 295, 20);
		panel_Actualizar.add(textField_ATipoMemoria);
		
		textField_ACantidadMemoria = new JTextField();
		textField_ACantidadMemoria.setColumns(10);
		textField_ACantidadMemoria.setBounds(198, 175, 295, 20);
		panel_Actualizar.add(textField_ACantidadMemoria);
		
		textField_AUbicacion = new JTextField();
		textField_AUbicacion.setColumns(10);
		textField_AUbicacion.setBounds(198, 202, 295, 20);
		panel_Actualizar.add(textField_AUbicacion);
		
		textField_ANumeroSerie = new JTextField();
		textField_ANumeroSerie.setColumns(10);
		textField_ANumeroSerie.setBounds(198, 227, 295, 20);
		panel_Actualizar.add(textField_ANumeroSerie);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sentencias del evento actualizar
				Conexion con = new Conexion(); //Instancio el objeto con de la clase Conexion
				Connection conexion = con.conectar(); //Ejecuto el método conectar instanciado como conexion
				
				try {
					//Guardo en un String la sentencia
					String query = "UPDATE ordenador SET IdInterno=?, Marca=?, Modelo=?, Procesador=?, TipoMemoria=?, CantidadMemoria=?, Ubicacion=?, NumeroSerie=? WHERE IdInterno=?";
					//Preparo la sentencia
					PreparedStatement statement = conexion.prepareStatement(query);
					
					statement.setInt(1, Integer.parseInt(fueraEspacios(textField_NAIdInterno.getText(),true)));
					statement.setString(2, fueraEspacios(textField_AMarca.getText(),true));
					statement.setString(3, fueraEspacios(textField_AModelo.getText(),true));
					statement.setString(4, fueraEspacios(textField_AProcesador.getText(),true));
					statement.setString(5, fueraEspacios(textField_ATipoMemoria.getText(),true));
					statement.setString(6, fueraEspacios(textField_ACantidadMemoria.getText(),true));
					statement.setString(7, fueraEspacios(textField_AUbicacion.getText(),true));
					statement.setString(8, fueraEspacios(textField_ANumeroSerie.getText(),true));
					statement.setInt(9, Integer.parseInt(fueraEspacios(textField_AIdInterno.getText(),true)));
					//Actualizo la base de datos ejecutando la sentencia
					statement.executeUpdate();
					//Cierro la conexión
					conexion.close();
					//Se notifica al usuario que el registro se ha actualizado correctamente
					JOptionPane.showMessageDialog(null, "Ordenador actualizado correctamente!");
					
				}catch (Exception a) {
					//Se saca por consola los errores
					System.out.println("Ooops! error al conectarse a la base de datos");
					a.printStackTrace();
				}
				
				
				
			}
		});
		btnActualizar.setBounds(221, 258, 102, 23);
		panel_Actualizar.add(btnActualizar);
		
		JLabel lblNewLabel_AIdInterno = new JLabel("Id interno");
		lblNewLabel_AIdInterno.setBounds(38, 29, 150, 14);
		panel_Actualizar.add(lblNewLabel_AIdInterno);
		
		textField_AIdInterno = new JTextField();
		textField_AIdInterno.setBounds(198, 21, 295, 20);
		panel_Actualizar.add(textField_AIdInterno);
		textField_AIdInterno.setColumns(10);
		
		JLabel lblNewLabel_NAIdInterno = new JLabel("Nuevo Id interno");
		lblNewLabel_NAIdInterno.setBounds(38, 54, 150, 14);
		panel_Actualizar.add(lblNewLabel_NAIdInterno);
		
		textField_NAIdInterno = new JTextField();
		textField_NAIdInterno.setBounds(198, 49, 295, 20);
		panel_Actualizar.add(textField_NAIdInterno);
		textField_NAIdInterno.setColumns(10);	
	}
}