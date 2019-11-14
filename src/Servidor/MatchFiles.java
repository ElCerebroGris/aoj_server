/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Tools.Iniciar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Zamba
 */
public class MatchFiles {

    static BufferedReader b1 = null, b2 = null;
    static File f1, f2;

    public static String Match(long pid, int outN) {
        System.out.println("Matching Proccess sarted...");
        try {
            f1 = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/output"+outN+".txt");
            f2 = new File(Iniciar.getUrl()+"Problemas/P_" + pid + "/outUser.txt");
            b1 = new BufferedReader(new FileReader(f1));
            b2 = new BufferedReader(new FileReader(f2));

            String s1 = "", s2 = "", temp = "";
            
            while ((temp = b2.readLine()) != null) {
                s2 += temp.trim() + "\n";
            }
            System.out.println("##########################");
            System.out.println(s2);
            System.out.println("##########################");

            while ((temp = b1.readLine()) != null) {
                s1 += temp.trim() + "\n";
            }

            if (s1.equals(s2)) {
                return "Ok";
            } else {
                return "Wrong answer";
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                b1.close();
                b2.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return "Wrong answer";
    }

}
