/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Tools.Iniciar;
import java.io.BufferedReader;
import java.io.File;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.nio.file.FileSystem;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author Zamba
 */
public class Compilador {

    static boolean codigo = false;

    public static String compile(long pid, String lingagem) {
        System.out.println("Compilando..." + lingagem);
        ProcessBuilder p;
        boolean compilado = true;
        if (lingagem.equals("java")) {
            p = new ProcessBuilder("javac", "Main.java");
        } else if (lingagem.equals("c")) {
            //Linux p = new ProcessBuilder("gcc", "-std=c++0x", "-w", "-o", "Main", "Main.c");
            //p = new ProcessBuilder("gcc", "-o", "Main", "Main.c");
            p = new ProcessBuilder("gcc", "Main.c");
        } else {
            //p = new ProcessBuilder("g++", "-std=c++0x", "-w", "-o", "Main", "Main.cpp");
            p = new ProcessBuilder("g++", "Main.cpp");
        }

        p.directory(new File(Iniciar.getUrl() + "Problemas/P_" + pid));
        p.redirectErrorStream(true);

        try {
            Process pp = p.start();
            InputStream is = pp.getInputStream();
            String temp;
            try (BufferedReader b = new BufferedReader(new InputStreamReader(is))) {
                while ((temp = b.readLine()) != null) {
                    compilado = false;
                    System.out.println("Ola " + temp);
                }
                pp.waitFor();
            }
            if (!compilado) {
                is.close();
                codigo = false;
                return "Compilation error";
            }
            is.close();
            codigo = true;
            return "COMPILADO COM SUCESSO";

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
            System.out.println("" + e);
            return "Compilation error";
        }

    }

    public static String execute(long pid, String linguagem, String n, long tempo) {
        System.out.println("Executando...");
        ProcessBuilder p;
        if (linguagem.equals("java")) {
            p = new ProcessBuilder("java", "Main");
        } else if (linguagem.equals("c")) {
            p = new ProcessBuilder("gcc", "-o", "Main.exe");
        } else {
            p = new ProcessBuilder("g++", "-o", "Main.exe");
        }
        p.directory(new File(Iniciar.getUrl() + "Problemas/P_" + pid + "/"));
        File in = new File(n);
        p.redirectInput(in);
        if (in.exists()) {
            System.out.println("Input file " + in.getAbsolutePath());
        }
        p.redirectErrorStream(true);
        System.out.println("Current directory " + in.getParent());
        File out = new File(Iniciar.getUrl() + "Problemas/P_" + pid + "/outUser.txt");

        p.redirectOutput(out);
        if (out.exists()) {
            System.out.println("Output file generated " + out.getAbsolutePath());
        }

        try {
            Process pp = p.start();
            if (!pp.waitFor(tempo, TimeUnit.MILLISECONDS)) {
                return "Time Limited Exceded";
            }

            /*Texte*/
            String[] results = IOUtils.toString(pp.getInputStream()).split(",");

            //System.out.println("MEMORY = "+(Long.valueOf(results[3])*1024));
            int exitCode = pp.exitValue();
            System.out.println("Exit value = " + pp.exitValue());
            if (exitCode == -1) {
                return "Runtime error";
            }

            System.out.println("Execução terminada");
            return "Ok";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro a executar";
        }

    }

    public static String getResultado(long pid, long tempo, String linguagem, int textN) {
        String resultado;
        resultado = compile(pid, linguagem);
        System.out.println(resultado);
        if (codigo) {
            File file_input = new File(Iniciar.getUrl() + "Problemas/P_" + pid
                    + "/input" + textN + ".txt");
            resultado = execute(pid, linguagem, file_input.getAbsolutePath(), tempo);
            if (linguagem.equals("java")) {
                File f = new File(Iniciar.getUrl() + "Problemas/P_" + pid + "/Main.class");
                f.delete();
            } else {
                File f = new File(Iniciar.getUrl() + "Problemas/P_" + pid + "/Main.exe");
                f.delete();
            }
            if (resultado.equals("Ok")) {
                resultado = MatchFiles.Match(pid, textN);
            }
            System.out.println("Resultado: " + resultado);
        }
        return resultado;
    }
}
