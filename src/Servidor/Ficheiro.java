/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Tools.Iniciar;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zamba
 */
public class Ficheiro {

    public static String GerarMain(long pid, String codigo, long tempo, String linguagem, int textN)
            throws IOException {
        File f = null;
        if (linguagem.equals("C++")) {
            f = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/main.cpp");
        } else {
            f = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/main." + linguagem);
        }

        try {
            f.createNewFile();
            PrintWriter p = new PrintWriter(f);
            p.append(codigo);
            p.flush();
            p.close();
            String res = Compilador.getResultado(pid, tempo, linguagem, textN);
            return res;
        } catch (Exception ex) {
            return "Erro no servidor";
        } finally {
            f.delete();
            File user = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/outUser.txt");
            if (linguagem.equals("Java")) {
                File classe = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/Main.class");
                classe.delete();
            }
            user.delete();
        }
    }

    public static String lerCodigo(String url) {
        String codigo = "";
        try {
            File f = new File(url);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                codigo = codigo.concat("\n");
                codigo = codigo.concat(s.nextLine());
            }

            return codigo;
        } catch (Exception ex) {
            return "Erro ao ler arquivo";
        }
    }

    public static int listarTextNFiles(long pid) {
        int quant = 0;
        Path p = Paths.get(Iniciar.getUrl()+"Problemas/P_" + pid);
        Iterator<Path> it;
        try {
            it = Files.newDirectoryStream(p).iterator();
            while (it.hasNext()) {
                String s = it.next().getFileName().toString();
                if(s.startsWith("output")){
                    ++quant;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Ficheiro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quant;
    }

}
