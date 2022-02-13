package gestoras;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class GestoraClaves {
    private static final String ALGORITMO_GEN_NUM_ALEAT = "SHA1PRNG";

    public void generarArchivoClave(String nombreArchivoSalida, String algoritmo) {
        try {
            SecretKey clave = getSecretKey(algoritmo);
            System.out.printf("Formato de clave: %s\n", clave.getFormat());
            SecretKeyFactory keySpecFactory=null;
            if(algoritmo!="AES") {
                keySpecFactory = SecretKeyFactory.getInstance(algoritmo);
            }
            byte[] valorClave = getBytes(nombreArchivoSalida, algoritmo, clave, keySpecFactory);
            try (FileOutputStream fos = new FileOutputStream(nombreArchivoSalida)) {
                fos.write(valorClave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algoritmo de generación de claves no soportado");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public SecretKey getSecretKey(String algoritmo) throws NoSuchAlgorithmException {
        KeyGenerator genClaves = KeyGenerator.getInstance(algoritmo);
        SecureRandom srand = SecureRandom.getInstance(ALGORITMO_GEN_NUM_ALEAT);
        genClaves.init(srand);
        SecretKey clave = genClaves.generateKey();
        return clave;
    }

    private byte[] getBytes(String nombreArchivoSalida, String algoritmo, SecretKey clave, SecretKeyFactory keySpecFactory) throws InvalidKeySpecException {
        byte[] valorClave = null;
        switch (algoritmo) {
            case "DESede" -> {
                DESedeKeySpec keySpec = (DESedeKeySpec) keySpecFactory.getKeySpec(clave, DESedeKeySpec.class);
                valorClave = keySpec.getKey();
            }
            case "DES" -> {
                DESKeySpec keySpec = (DESKeySpec) keySpecFactory.getKeySpec(clave, DESKeySpec.class);
                valorClave = keySpec.getKey();
            }
            case "AES" -> {
                SecretKeySpec keySpec= new SecretKeySpec(clave.getEncoded(), algoritmo);
//                    PBEKeySpec keyspec = (PBEKeySpec) keySpec.getKeySpec(key, PBEKeySpec.class);
                valorClave = keySpec.getEncoded();
            }
        }
        System.out.printf("Longitud de clave: %d bits\n", valorClave.length * 8);
        System.out.printf("Valor de la clave: [%s] en fichero %s\n",
                valorHexadecimal(valorClave), nombreArchivoSalida);
        return valorClave;
    }

    private StringBuilder valorHexadecimal(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%x", b));
        }
        return result;
    }

}
