package main;

import gestoras.GestoraArchivosClaveAsimetrica;

public class MainPrueba {

    private static GestoraArchivosClaveAsimetrica gEncriptarAsim=new GestoraArchivosClaveAsimetrica();
    private static String archivoEntrada="pruebaClavePublic";
    private static String archivoSalida="encriptado";
    private static String clavePublic ="clavepublica.der";

    public static void main(String[] args) {

        //gEncriptarAsim.encriptarMensajeRSA(archivoEntrada,archivoSalida,clavePublic);

    }
}
