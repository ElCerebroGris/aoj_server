/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iojserver;

import BD.DAO;
import BD.DAO_Contest;
import BD.Submissao;
import Servidor.Ficheiro;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOJServer implements Runnable {

    private Thread t;
    public String log = "";

    public static void main(String[] args) {
        Thread t = new Thread(new IOJServer());
        t.start();
    }

    public void Ligar() {
        Thread t = new Thread(new IOJServer());
        t.start();
        log = log.concat("Servidor ligado \n");
    }

    public void Desligar() {
        t.interrupt();
        log = log.concat("Servidor desligado \n");
    }

    @Override
    public void run() {
        while (true) {
            avaliar_concurso();
            try {
                //Lista apenas as submissoes não avaliadas
                List<Submissao> lista = DAO.listar_url_N_Avaliadas();

                for (Submissao s : lista) {
                    //Pega o codigo desta submissão
                    String codigo = Ficheiro.lerCodigo(s.getUrl());
                    //Quantidade de casos de textes certos
                    int ac = 0;
                    //Numero de inputs e outputs
                    int max = Ficheiro.listarTextNFiles(s.getId_problema());
                    String res = "";
                    //i é o valor de cada caso de texto
                    for (int i = 0; i < max; i++) {
                        //Recebe o resultado da submissao
                        res = Ficheiro.GerarMain(s.getId_problema(), codigo, s.getTempo(),
                                s.getLinguagem(), i);

                        if (res.equals("Ok")) {
                            ++ac;
                        } else {
                            break;
                        }
                    }
                    //Add resultado na tabela de submissões
                    DAO.submeter_resultado(s.getUrl(), res);
                    //O numero de casos de textes acertados = ao numero de todos os casos de texte
                    if (ac == max) {
                        //Atualiza pontos do usuario e do problema
                        DAO.atualizar_pontos(s.getId_usuario(), s.getId_problema());
                        System.out.println("Resposta correta");
                    }
                    System.out.println("MAX=" + max + " AC=" + ac);
                    //System.out.println("URL = " + s.getUrl() + " Resultado = " + s.);
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
                Logger.getLogger(IOJServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(IOJServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void avaliar_concurso() {
        long cid = DAO_Contest.isRunningNContest();
        if (cid != 0L) {
            try {
                //Lista apenas as submissoes não avaliadas
                List<Submissao> lista = DAO_Contest.listar_url_N_Avaliadas();

                for (Submissao s : lista) {
                    //Pega o codigo desta submissão
                    String codigo = Ficheiro.lerCodigo(s.getUrl());
                    //Quantidade de casos de textes certos
                    int ac = 0;
                    //Numero de inputs e outputs
                    int max = Ficheiro.listarTextNFiles(s.getId_problema());
                    String res = "";
                    //i é o valor de cada caso de texto
                    for (int i = 0; i < max; i++) {
                        //Recebe o resultado da submissao
                        res = Ficheiro.GerarMain(s.getId_problema(), codigo, s.getTempo(),
                                s.getLinguagem(), i);

                        if (res.equals("Ok")) {
                            ++ac;
                        } else {
                            break;
                        }
                    }
                    //Add resultado na tabela de submissões
                    DAO_Contest.submeter_resultado(s.getId_submissao(), res);
                    //O numero de casos de textes acertados = ao numero de todos os casos de texte
                    if (ac == max) {
                        DAO_Contest.atualizar_pontos_usuario(s);
                        System.out.println("Resposta correta");
                    }
                    System.out.println(res);
                    System.out.println("MAX=" + max + " AC=" + ac);
                    //System.out.println("URL = " + s.getUrl() + " Resultado = " + s.);
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
                Logger.getLogger(IOJServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
