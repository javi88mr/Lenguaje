/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lenguaje;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class Lenguaje {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("funciona");
        String nombreFichero;
        FileLock bloqueo;
        RandomAccessFile raf = null;

        if (args.length == 2) {
            try {
                int numLineas = Integer.parseInt(args[0]);
                String osName = System.getProperty("os.name");
                if (osName.toUpperCase().contains("WIN")) {
                    nombreFichero = args[1].replace("\\", "\\\\");
                } else {
                    nombreFichero = args[1];
                }

                File archivo = new File(nombreFichero);
                //Preparamos el acceso al fichero
                if (!archivo.exists()) { //Si no existe el fichero
                    archivo.createNewFile(); //Lo creamos
                }
                raf = new RandomAccessFile(archivo, "rwd"); //Abrimos el fichero
                bloqueo = raf.getChannel().lock();                                

                raf.seek(archivo.length()); //nos ponemos al final

                for (int i = 0; i < numLineas; i++) {
                    String linea = "";
                    int caracteres = 5;
                    for (int j = 0; j < caracteres; j++) {
                        int codigoAscii = (int) Math.floor(Math.random() * (122 - 97) + 97);
                        linea = linea + (char) codigoAscii;
                    }
                    raf.writeChars(linea + "\n");
                }
                
                bloqueo.release();
                bloqueo = null;                
                raf.close();
                
                
            } catch (IOException ex) {
                Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("El programa debe tener dos parametros");
        }

    }

}
