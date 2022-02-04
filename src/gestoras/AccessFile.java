package gestoras;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;

public class AccessFile {
    private Cipher encriptador;

    public void addClaveEncriptadaFichero(String nomFichero){
        try(FileInputStream fis = new FileInputStream(nomFichero)){
            FileOutputStream fos = new FileOutputStream(nomFichero+".encript");
            BufferedInputStream is = new BufferedInputStream(fis);
            BufferedOutputStream os = new BufferedOutputStream(fos);
            byte[]buff=new byte[encriptador.getBlockSize()];
            int bytesLeidos = is.read(buff);
            while(bytesLeidos!=-1){
                //mete el mensaje encriptado en el fichero update->encripta
                os.write(encriptador.update(buff,0,bytesLeidos));
                bytesLeidos=is.read(buff);
            }
            os.write(encriptador.doFinal());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
