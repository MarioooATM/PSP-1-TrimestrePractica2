package vista;


import controlador.BuscarDatos;
import controlador.BuscarDatosHilos;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main (String[] args) throws InterruptedException {
        int opcion=0;
        BuscarDatos bd= new BuscarDatos();
        Long startTime = System.currentTimeMillis();
        ;
        Scanner sc = new Scanner(System.in);
        while(opcion!=9) {
            System.out.println("Selecciona una opcion\n 1 Sumar todos los salarios sin hilos\n 2 Sumar todos los salarios con hilos\n 9 Salir");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    bd.conectarBBDD();
                    break;
                case 2:
                    contarRegistroHilos();
                    break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Duración: " + (endTime - startTime) + " ms");
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
                System.out.println(numInicial+"Init");
                System.out.println(numFinal+"Final");
            }
            System.out.println("rango "+x);
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
        System.out.println("total ******"+total);
    }
}
