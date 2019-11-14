/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Tools.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Zamba
 */
public class DAO_Contest {

    public static Date dataInicioContest(long cid){
        Date res = null;
        Connection conn = null;
        try {
            conn = ConectaNormal.getConnection();
            String sql = "SELECT inicio FROM contest WHERE id_contest="+cid+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] data1 = rs.getString("inicio").split(" ");

                Date d1 = Data.FormatarData(data1[0]);
                d1.setHours(Integer.parseInt(data1[1].split(":")[0]));
                d1.setMinutes(Integer.parseInt(data1[1].split(":")[1]));
                d1.setSeconds(0);
                res = d1;
            }
           conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ler da BD");
        }
        return res;
    }
    public static long isRunningNContest() {
        long res = 0L;
        Connection conn = null;
        try {
            conn = ConectaNormal.getConnection();
            String sql = "SELECT id_contest, inicio, fim FROM contest";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] data1 = rs.getString("inicio").split(" ");
                String[] data2 = rs.getString("fim").split(" ");

                Date d1 = Data.FormatarData(data1[0]);
                d1.setHours(Integer.parseInt(data1[1].split(":")[0]));
                d1.setMinutes(Integer.parseInt(data1[1].split(":")[1]));
                d1.setSeconds(0);

                Date d2 = Data.FormatarData(data2[0]);
                d2.setHours(Integer.parseInt(data2[1].split(":")[0]));
                d2.setMinutes(Integer.parseInt(data2[1].split(":")[1]));
                d2.setSeconds(0);
                Calendar c = Calendar.getInstance();

                if (c.getTime().after(d1) && c.getTime().before(d2)) {
                    res = rs.getLong(1);
                }
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ler da BD");
        }
        return res;
    }

    public static void submeter_resultado(long cid, String status) {
        try {
            Connection connect = ConectaNormal.getConnection();
            String sql = "update contest_submission set status='" + status + "' where id_sublmissao=" + cid + ";";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeUpdate();
            connect.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar na BD");
        }
    }

    public static List<Submissao> listar_url_N_Avaliadas() {

        List<Submissao> lista = new ArrayList<>();

        try {
            Connection connect = ConectaNormal.getConnection();
            String sql = "SELECT * FROM contest_submission WHERE status='Evaluating';";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Submissao(rs.getLong("id_sublmissao"), rs.getLong("id_contest"), rs.getLong("id_problema"),
                        rs.getLong("id_usuario"), rs.getString("status"), rs.getString("url"),
                        rs.getString("linguagem"), tempo_problema(rs.getLong("id_problema"))));
            }
            connect.close();
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "Erro ao pesquisar na BD");
        }
        return lista;
    }

    public static void atualizar_pontos_usuario(Submissao s) {
        try {
            long cupid = getID("contest_user_problem");
            ++cupid;
            
            try (Connection connect = ConectaNormal.getConnection()) {
                //BUG
                Date d = dataInicioContest(s.getId_contest());
                long penality = ((new Date().getTime() - d.getTime())) / 1000L;
                
                String sql = "insert into contest_user_problem values(" + cupid + "," + s.getId_contest() + ", "
                        + s.getId_usuario() + ", "+s.getId_problema()+", "+penality+");";
                
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex+" Erro ao adicionar na BD 1");
        }
    }

    public static long tempo_problema(long id) {

        long tempo = -1;

        try {
            Connection connect = ConectaNormal.getConnection();
            String sql = "SELECT tempo_limite FROM problema WHERE id_problema=" + id + ";";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            tempo = rs.getLong("tempo_limite");
            connect.close();
            return tempo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "Erro ao pesquisar na BD");
        }
        return tempo;
    }

    private static long getID(String tabela) {
        long id = 0;

        try {
            Connection connect = ConectaNormal.getConnection();
            String sql = "SELECT count(*) FROM " + tabela + " ;";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            id = rs.getLong(1);
            connect.close();
            return id;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "Erro ao pesquisar na BD");
        }
        return id;
    }

}
