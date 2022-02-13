package gestoras;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class GestoraArchivos {

    private final String TYPE_FILE_ENCRYPT= ".encript";
    private final String TYPE_FILE_DESENCRYPT= ".desencript";

    public void encriptarArchivo(String nombreFicheroEntrada, String nombreFicheroSalida, String nombreFichConClave, String algoritmo){
        byte valorClave[]=null;
        valorClave = getBytes(nombreFichConClave, valorClave);
        try {
            Cipher encriptador = getCipher(algoritmo, valorClave, Cipher.ENCRYPT_MODE);
            try (FileInputStream fis = new FileInputStream(nombreFicheroEntrada);
                 FileOutputStream fos = new FileOutputStream(nombreFicheroSalida + TYPE_FILE_ENCRYPT);
                 BufferedInputStream is = new BufferedInputStream(fis);
                 BufferedOutputStream os = new BufferedOutputStream(fos)) {
                byte[] buff = new byte[encriptador.getBlockSize()];
                int bytesLeidos = is.read(buff);
                while(bytesLeidos != -1) {
                    os.write(encriptador.update(buff,0,bytesLeidos));
                    bytesLeidos=is.read(buff);
                }
                os.write(encriptador.doFinal());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desencriptarArchivo(String nombreFicheroEntrada, String nombreFicheroSalida, String nombreFichConClave, String algoritmo){
        byte valorClave[]=null;
        valorClave = getBytes(nombreFichConClave, valorClave);
        try {
            Cipher desencriptador = getCipher(algoritmo, valorClave, Cipher.DECRYPT_MODE);
            try (FileInputStream fis = new FileInputStream(nombreFicheroEntrada);
                 FileOutputStream fos = new FileOutputStream(nombreFicheroSalida + TYPE_FILE_DESENCRYPT);
                 BufferedInputStream is = new BufferedInputStream(fis);
                 BufferedOutputStream os = new BufferedOutputStream(fos)) {
                byte[] buff = new byte[desencriptador.getBlockSize()];
                int bytesLeidos = is.read(buff);
                while(bytesLeidos != -1) {
                    os.write(desencriptador.update(buff,0,bytesLeidos));
                    bytesLeidos=is.read(buff);
                }
                os.write(desencriptador.doFinal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Cipher getCipher(String algoritmo, byte[] valorClave, int encryptMode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(valorClave, algoritmo);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algoritmo);
        SecretKey clave = keyFactory.generateSecret(keySpec);
        Cipher encriptador;
        if (algoritmo.equals("AES"))
            encriptador = Cipher.getInstance("AES/CBC/PKCS5Padding");
        else{
            encriptador = Cipher.getInstance(algoritmo);
        }

        encriptador.init(encryptMode, clave);
        return encriptador;
    }

    private byte[] getBytes(String nombreFichConClave, byte[] valorClave) {
        try (FileInputStream fisClave = new FileInputStream(nombreFichConClave)) {
            valorClave = fisClave.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: no existe fichero de clave %s\n.", nombreFichConClave);
        } catch (IOException e) {
            System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", nombreFichConClave);
        }
        return valorClave;
    }



}
