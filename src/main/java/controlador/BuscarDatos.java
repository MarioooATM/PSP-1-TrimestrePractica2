package controlador;

import modelo.Usuario;

import java.sql.*;

public class BuscarDatos {
    public void conectarBBDD() {
        int monto=0;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            ResultSet registro = consulta.executeQuery("SELECT INGRESOS FROM Empleados");
            while(registro.next()){
                monto += registro.getInt("INGRESOS");
            }
            conexion.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(monto);
    }
    public int contarRegistros(){
        int numRegistros=0;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            ResultSet registro = consulta.executeQuery("SELECT count(*) FROM EMPLEADOS");
            registro.next();
            numRegistros= registro.getInt("count(*)");
            conexion.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRegistros;
    }
}
