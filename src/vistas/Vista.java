package vistas;

import java.util.Scanner;

public class Vista {

    Scanner sc= new Scanner(System.in);

    public static void menuPrincipal(){
        System.out.println("Introduzca el nombre del fichero en el que desea generar la clave");
        System.out.println("""
                Seleccione el algoritmo para generar la clave
                1.AES
                2.DES
                3.DESede
                """);
    }
}
