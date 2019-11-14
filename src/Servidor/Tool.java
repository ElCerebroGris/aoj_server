/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Tools.Iniciar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Iterator;

public class Tool {

    public void createBat(String nome, String caminho) {
        File f = new File(caminho + "/" + nome + ".bat");
        try {
            f.createNewFile();
            Charset utf = StandardCharsets.UTF_8;
            try (BufferedWriter br = Files.newBufferedWriter(Paths.get(f.getAbsolutePath()), utf)) {
                String texto = "@echo off&&cls\n"
                        + "set /p pathName=" + caminho + ":%=%\n"
                        + "cd %pathName%\n"
                        + "REM set /p exec=Enter The Name of the executable you want to make:%=%\n"
                        + "set /p file=Enter The Name of the file you want to compile:%=%\n"
                        + "g++ -o %file% %file%.cpp\n"
                        + "%file%.exe";
                br.write(texto);
                br.flush();
                br.close();
            } catch (IOException e) {

            }
        } catch (Exception ex) {
        }
    }

    public void Escrever(String texto, String caminho) {
        Path p = Paths.get(caminho);
        Charset utf = StandardCharsets.UTF_8;
        try (BufferedWriter br = Files.newBufferedWriter(p, utf)) {
            br.write(texto);
            br.flush();
            br.close();
        } catch (IOException e) {

        }
    }

    public void iterarFiles() throws IOException {
        Iterator<Path> it = Files.newDirectoryStream(Paths.get(Iniciar.getUrl() + "Problemas/P_1/"))
                .iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void main(String[] args) {
        Calendar date = Calendar.getInstance();

    }

}
