package main;

import gestoras.GestoraArchivosClaveAsimetrica;

public class MainPrueba {

    private static GestoraArchivosClaveAsimetrica gEncriptarAsim=new GestoraArchivosClaveAsimetrica();
    private static String archivoEntrada="pruebaClavePublic";
    private static String archivoSalida="encriptado.encript";
    private static String clavePublic ="clavepublica.der";
    private static String mensajeDesencriptado ="mensajeDesenc";
    private static String clavePrivada ="claveprivada.pkcs8";

    public static void main(String[] args) {

        //gEncriptarAsim.encriptarMensajeRSA(archivoEntrada,archivoSalida,clavePublic);
        gEncriptarAsim.desencriptadoRSA(archivoSalida,mensajeDesencriptado,clavePrivada);
    }
}
