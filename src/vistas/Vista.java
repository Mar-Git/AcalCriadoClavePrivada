package vistas;

import java.util.Scanner;

public class Vista {


    public static int menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int respuesta=0;
        do{
            System.out.println("""
                    Seleccione la operacion que desea realizar
                    1.Generar clave
                    2.Encriptar fichero
                    3.Desencriptar fichero
                    0.Salir
                    """);
            respuesta = Integer.parseInt(sc.nextLine());
        }while(respuesta<0||respuesta>3);

        return respuesta;
    }

    public static String elegirOpcionAlgoritmo(){
        Scanner sc= new Scanner(System.in);
        int opcion=0;
        String[] tipoAlgoritmos= {"AES","DES","DESede"};
        do{
            System.out.println("""
                Seleccione el algoritmo:
                1.AES
                2.DES
                3.DESede
                """);
            opcion=Integer.parseInt(sc.nextLine());
        }while(opcion<1||opcion>3);

        return tipoAlgoritmos[opcion-1];
    }


    public static String introNombreFicheroGuardarClave(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero en el que desea guardar la clave");
        String nombreFicheroConClave = sc.nextLine();
        return nombreFicheroConClave;
    }
    public static String introNombreFicheroConClave(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero en el que se encuentra la clave");
        String nombreFicheroConClave = sc.nextLine();
        return nombreFicheroConClave;
    }
    public static String introNombreFicheroADesencriptar(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero que desea desencriptar");
        String nombreFicheroEncriptado = sc.next();
        return nombreFicheroEncriptado;
    }
    public static String introNombreFicheroConResultado(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero que contendra el resultado");
        String nombreFicheroDesencriptado = sc.next();
        return nombreFicheroDesencriptado;
    }
    public static String introNombreFicheroAEncriptar(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero que desea encriptar");
        String nombreFicheroEncriptado = sc.next();
        return nombreFicheroEncriptado;
    }

    public static void mensajeAtencionUsuario() {
        System.out.println("Debe introducir primero el algoritmo que desee utilizar");
    }
}

