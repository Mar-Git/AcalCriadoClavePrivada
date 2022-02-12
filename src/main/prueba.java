package main;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class prueba {

    private static final String ALGORITMO_CLAVE_SIMETRICA = "DESede";  // 3DES
    private static final String ALGORITMO_GEN_NUM_ALEAT = "SHA1PRNG";
    private static final String NOM_FICH_CLAVE = "clave.raw";

    public static void main(String[] args) {

        try {
            KeyGenerator genClaves = KeyGenerator.getInstance(ALGORITMO_CLAVE_SIMETRICA);

            SecureRandom srand = SecureRandom.getInstance(ALGORITMO_GEN_NUM_ALEAT);
            genClaves.init(srand);

            SecretKey clave = genClaves.generateKey();
            System.out.printf("Formato de clave: %s\n", clave.getFormat());


            SecretKeyFactory keySpecFactory = SecretKeyFactory.getInstance(ALGORITMO_CLAVE_SIMETRICA);

            DESedeKeySpec keySpec = (DESedeKeySpec) keySpecFactory.getKeySpec(clave, DESedeKeySpec.class);

            byte[] valorClave = keySpec.getKey();

//      byte[] valorClave = clave.getEncoded();

            System.out.printf("Longitud de clave: %d bits\n", valorClave.length*8);
            System.out.printf("Valor de la clave: [%s] en fichero %s",
                    valorHexadecimal(valorClave), NOM_FICH_CLAVE);

            try(FileOutputStream fos = new FileOutputStream(NOM_FICH_CLAVE)) {
                fos.write(valorClave);
            } catch (IOException e) {
                System.out.println("Error de E/S escribiendo clave en fichero");
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algoritmo de generación de claves no soportado");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    static String valorHexadecimal(byte[] bytes) {
        String result = "";
        for (byte b : bytes) {
            result += String.format(String.format("%x", b));
        }
        return result;
    }

}

