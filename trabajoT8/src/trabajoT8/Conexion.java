
package trabajoT8;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

/**
 * Clase encargada de las conexiones con la base de datos
 * 
 * @author Juan Molina
 * 
 */
public class Conexion {
	
	public Connection con; //Instancio la variable de tipo Connection como con.
	
	/**
	 * M�todo que inicia una conexi�n con la base de datos, en caso de error avisa.
	 * 
	 * @return Retorna la conexi�n
	 */
	public Connection conectar() {
		//Utiliza protejo la sentencia de conexi�n y arrojo un error en caso de no conexi�n.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			/* Para probarlo en un mysql local sustituir mysql.lan por localhos y 32813 por el puerto que uses
			 por defecto es el 3306 y sustituir "user" por su usuario y "usuario123456" por su contrase�a*/
			con = DriverManager.getConnection("jdbc:mysql://mysql.lan:32813/prog","user","usuario123456");
			//JOptionPane.showMessageDialog(null, "Conectado correctamente.");
		}catch(Exception e) {
			// Todo: handle exception
			JOptionPane.showMessageDialog(null, "Error al conectarse a la BD.");
		}
		
		return con; //Retorna la conexi�n
	}
	
	

}
