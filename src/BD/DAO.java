/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Zamba
 */
public class DAO {
    
    public static void submeter_resultado(String url, String status) {
        try
        {
            Connection connect = ConectaNormal.getConnection();
            String sql = "update submissao set status='"+status+"' where url='"+url+"';";
                                    
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeUpdate();
            connect.close();
            //JOptionPane.showMessageDialog(null, "Adicionado na BD com sucesso");
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao adicionar na BD");
        }
    }

    public static List<Submissao> listar_url_N_Avaliadas() {
        
        List<Submissao> lista = new ArrayList<>();
        
        try
        {
            Connection connect = ConectaNormal.getConnection();
            String sql = "SELECT * FROM submissao WHERE status='Evaluating';";
                                    
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Submissao(rs.getLong("id_problema"),rs.getLong("id_usuario"),
                        rs.getString("status"), rs.getString("url"), rs.getString("linguagem"), 
                        tempo_problema(rs.getLong("id_problema"))));
            }
            connect.close();
            return lista;
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex+ "Erro ao pesquisar na BD");
        }
        return lista;
    }
    
    public static void atualizar_pontos(long id_usuario, long id_problema) {
        try
        {
            Connection connect = ConectaNormal.getConnection();
            String sql = "select atualizar_ponto("+id_usuario+","+id_problema+")";
                                    
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            connect.close();
            //JOptionPane.showMessageDialog(null, "Adicionado na BD com sucesso");
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex+" Erro ao adicionar na BD");
        }
    }
    
    public static long tempo_problema(long id){
        
        long tempo = -1;
        
        try
        {
            Connection connect = ConectaNormal.getConnection();
            String sql = "SELECT tempo_limite FROM problema WHERE id_problema="+id+";";
                                    
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            tempo = rs.getLong("tempo_limite");
            connect.close();
            return tempo;
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex+ "Erro ao pesquisar na BD");
        }
        return tempo;
    }
    
    
}
