package prueba_examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class SegundoTest {

    public static void main(String[] args) {
        try {
            // 1. LANZAR PROCESO Y ESPERAR CÓDIGO DE RETORNO (waitFor)
            // (Si usas args del main: new ProcessBuilder(args))
            ProcessBuilder pb1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1");
            Process p1 = pb1.start();
            int exitCode = p1.waitFor(); // 0 = éxito, != 0 = error
            System.out.println("Exit code del proceso 1: " + exitCode);

            // 2. LEER LA SALIDA DEL PROCESO CON BUFFEREDREADER
            // getInputStream() captura el stdout generado por el subproceso
            Process p2 = new ProcessBuilder("cmd", "/c", "echo", "Hola desde el proceso").start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("Leído: " + linea);
                }
            }
            p2.waitFor();

            // 3. REDIRIGIR SALIDA Y ERRORES DIRECTAMENTE A FICHEROS
            ProcessBuilder pb3 = new ProcessBuilder("cmd", "/c", "dir");
            pb3.redirectOutput(new File("salida.txt"));  // Equivale a >
            pb3.redirectError(new File("errores.txt"));  // Equivale a 2>
            Process p3 = pb3.start();
            p3.waitFor();
            System.out.println("Comando 'dir' ejecutado y guardado en archivos.");

        } catch (Exception e) {
            // Engloba IOException e InterruptedException
            e.printStackTrace();
        }
    }
}