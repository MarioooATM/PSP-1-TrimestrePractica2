package vista;


import controlador.BuscarDatos;
import controlador.BuscarDatosHilos;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main (String[] args) throws InterruptedException {
        BuscarDatos bd= new BuscarDatos();
        Long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        bd.conectarBBDD();
        endTime = System.currentTimeMillis();
        System.out.println("Duración sin hilos: " + (endTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        contarRegistroHilos();
        endTime = System.currentTimeMillis();
        System.out.println("Duración con hilos: " + (endTime - startTime) + " ms");
    }
    public static void contarRegistroHilos() throws InterruptedException {
        BuscarDatos bd= new BuscarDatos();
        Semaphore semaforo = new Semaphore(1);
        ArrayList<Usuario> users = new ArrayList<Usuario>();
        int numRegistro = bd.contarRegistros();
        int rango= numRegistro/5;
        int resto = numRegistro%5;
        int numInicial=1;
        int numFinal=rango;
        int total=0;

        BuscarDatosHilos bdHilos = null;
        for(int x=1;x<=5;x++){
            if(resto>=1 && x == 5){
                numInicial+=rango;
                numFinal+=resto;
            }
            bdHilos = new BuscarDatosHilos(numInicial,numFinal,semaforo);
            bdHilos.start();
            numInicial+=rango;
            numFinal+=rango;
            users.add(bdHilos.getMonto());
        }

        bdHilos.join();
        Thread.sleep(250);
        for (int x = 0; x <= users.size()-1; x++) {
            total +=users.get(x).getSueldo();
        }
        System.out.println("Total sueldo empleados: "+total);
    }
}
