/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import BD.ConectaNormal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Zamba
 */
public class Iniciar {
    
    public static String getUrl() {
        String r = "";
        Connection connect;
        try {
            connect = ConectaNormal.getConnection();
            String sql = "SELECT fonte_dados FROM projeto;";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            r = rs.getString(1);
            connect.close();
            return r;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + " Erro ao pesquisar na BD");
        }
        return r;
    }
    
}
