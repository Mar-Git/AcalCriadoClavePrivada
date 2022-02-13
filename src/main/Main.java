package main;

import gestoras.*;
import vistas.Vista;

public class Main {
    public static GestoraArchivos gArchivos= new GestoraArchivos();
    public static GestoraClaves gClave = new GestoraClaves();

    public static void main(String[] args) {
        String algoritmo="";
        String ficheroClave="";
        int opcion=0;
        do {
            opcion = Vista.menuPrincipal();
            switch (opcion) {
                case 1 -> {
                    algoritmo = Vista.elegirOpcionAlgoritmo();
                    ficheroClave = Vista.introNombreFicheroGuardarClave();
                    gClave.generarArchivoClave(ficheroClave, algoritmo);
                }
                case 2 -> {
                    if (algoritmo == "") {
                        algoritmo = Vista.elegirOpcionAlgoritmo();
                    }
                    encriptarFichero(algoritmo);
                }
                case 3 -> {
                    if (algoritmo == "") {
                        algoritmo = Vista.elegirOpcionAlgoritmo();
                    }
                    desencriptarClaveFichero(algoritmo);
                }
            }
        } while (opcion != 0);
    }

    private static void desencriptarClaveFichero(String algoritmo) {
        String fichClave=Vista.introNombreFicheroConClave();
        String fichSalida=Vista.introNombreFicheroConResultado();
        String fichEntrada=Vista.introNombreFicheroADesencriptar();
        gArchivos.desencriptarArchivo(fichEntrada,fichSalida,fichClave,algoritmo);
    }

    private static void encriptarFichero(String algoritmo) {
        String fichClave=Vista.introNombreFicheroConClave();
        String fichSalida=Vista.introNombreFicheroConResultado();
        String fichEntrada=Vista.introNombreFicheroAEncriptar();
        gArchivos.encriptarArchivo(fichEntrada,fichSalida,fichClave,algoritmo);
    }
}