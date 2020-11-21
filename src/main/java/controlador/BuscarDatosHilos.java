package controlador;

import modelo.Usuario;

import java.sql.*;
import java.util.concurrent.Semaphore;

public class BuscarDatosHilos extends Thread{
    private int numInicial;
    private int numFinal;
    private Semaphore semaforo;
    public int monto;
    Usuario user = new Usuario();
    public BuscarDatosHilos(int numInicial,int numFinal,Semaphore semaforo){
        this.numInicial= numInicial;
        this.numFinal= numFinal;
        this.semaforo= semaforo;
    }
    public void run(){
        conectarBBDD();
    }

    public void conectarBBDD() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            System.out.println(numInicial+ "inicial");
            System.out.println(numFinal+ "final");
            ResultSet registro = consulta.executeQuery("SELECT INGRESOS FROM Empleados WHERE id>="+numInicial+"&& id<="+numFinal);
            for (int x = numInicial; x <= numFinal; x++) {
                registro.next();
                monto += registro.getInt("INGRESOS");
                System.out.println(monto+" Ha sido sumado por "+Thread.currentThread().getName());
            }
            System.out.println(monto + "******");
            conexion.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        user.setSueldo(monto);
    }
    public Usuario getMonto(){
        return user;
    }

}
