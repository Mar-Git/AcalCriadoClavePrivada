package gestoras;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GestoraArchivosClaveAsimetrica {

    private static final String ALGORITMO_CLAVE_PUBLICA = "RSA";
    private static final String FICH_CLAVE_PUB = "clavepublica.der";
    private final String TYPE_FILE_ENCRYPT= ".encript";
    private final String TYPE_FILE_DESENCRYPT= ".desencript";


    public void encriptarMensajeRSA(String nombreFicheroEntrada, String nombreFicheroSalida, String nombreFichConClave) {
        String cadenaEnClaro="";
        byte clavePubCodif[];
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFicheroEntrada))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            cadenaEnClaro = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fisClavePub = new FileInputStream(FICH_CLAVE_PUB)) {
            clavePubCodif = fisClavePub.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: no existe fichero de clave pública %s\n.", FICH_CLAVE_PUB);
            return;
        } catch (IOException e) {
            System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", FICH_CLAVE_PUB);
            return;
        }

        KeyFactory keyFactory;
        try {

            keyFactory = KeyFactory.getInstance(ALGORITMO_CLAVE_PUBLICA);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(clavePubCodif);
            PublicKey clavePublica = keyFactory.generatePublic(publicKeySpec);

            byte[] mensajeEnClaro = cadenaEnClaro.getBytes("UTF-8");

            Cipher cifrado = Cipher.getInstance(ALGORITMO_CLAVE_PUBLICA);
            cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);

            byte[] mensajeCifrado = cifrado.doFinal(mensajeEnClaro);

            try(FileOutputStream fos = new FileOutputStream(nombreFicheroSalida + TYPE_FILE_ENCRYPT);
                BufferedOutputStream os = new BufferedOutputStream(fos)) {
                fos.write(Base64.getEncoder().encode(mensajeCifrado));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.printf("ERROR: no existe algoritmo de cifrado %s.\n.", ALGORITMO_CLAVE_PUBLICA);
        } catch (InvalidKeySpecException e) {
            System.out.println("ERROR: especificación de clave no válida.");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("Clave no válida.");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("Tamaño de bloque no válido.");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.out.println("Excepción con relleno.");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("Excepción con relleno.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("ERROR: codificación de caracteres UTF-8 no soportada.");
        }

    }

    public void desencriptadoRSA(String nombreFicheroEntrada, String nombreFicheroSalida, String nombreFichConClavePrivada){
        byte[] clavePrivCodif;
        try (FileInputStream fisClavePriv = new FileInputStream(nombreFichConClavePrivada)) {
            clavePrivCodif = fisClavePriv.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: no existe fichero de clave pública %s\n.", nombreFichConClavePrivada);
            return;
        } catch (IOException e) {
            System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", nombreFichConClavePrivada);
            return;
        }
//TODO falta la parte de coger el mensaje encriptado del fichero de entrada
        KeyFactory keyFactory;
        try {

            keyFactory = KeyFactory.getInstance(ALGORITMO_CLAVE_PUBLICA);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(clavePrivCodif);
            PrivateKey clavePrivada = keyFactory.generatePrivate(privateKeySpec);

            byte[] textoCifrado=null;
            byte[] mensajeCifrado = Base64.getDecoder().decode(textoCifrado);

            Cipher cifrado = Cipher.getInstance(ALGORITMO_CLAVE_PUBLICA);
            cifrado.init(Cipher.DECRYPT_MODE, clavePrivada);

            byte[] mensajeDescifrado = cifrado.doFinal(mensajeCifrado);
            try(FileOutputStream fos = new FileOutputStream(nombreFicheroSalida + TYPE_FILE_DESENCRYPT);
                BufferedOutputStream os = new BufferedOutputStream(fos)) {
                fos.write(mensajeDescifrado);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.printf("Texto descifrado:\n%s\n", new String(mensajeDescifrado, "UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            System.out.printf("ERROR: no existe algoritmo de cifrado %s.\n.", ALGORITMO_CLAVE_PUBLICA);
        } catch (InvalidKeySpecException e) {
            System.out.println("ERROR: especificación de clave no válida.");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("Clave no válida.");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("Tamaño de bloque no válido.");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.out.println("Excepción con relleno.");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("Excepción con relleno.");
            e.printStackTrace();
        }
    }
}
