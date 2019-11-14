/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;


/**
 *
 * @author Zamba
 */
public class Submissao {
    
    private long id_submissao;
    private long id_problema;
    private long id_usuario;
    private String status;
    private String url;
    private String linguagem;

    private String codigo;
    private long tempo;
    //Para concurso
    private long id_contest;
    
    public Submissao(){}

    public Submissao(String linguagem, long id_problema, long id_usuario, String codigo, String url) {
        this.id_problema = id_problema;
        this.id_usuario = id_usuario;
        this.codigo = codigo;
        this.url = url;
        this.linguagem = linguagem;
    }

    public Submissao(long id_problema, long id_usuario, String status, String codigo, String url) {
        this.id_problema = id_problema;
        this.id_usuario = id_usuario;
        this.status = status;
        this.codigo = codigo;
        this.url = url;
       
    }

    public Submissao(long id_problema, long id_usuario, String status, String url, String linguagem, long tempo) {
        this.id_problema = id_problema;
        this.id_usuario = id_usuario;
        this.status = status;
        this.url = url;
        this.linguagem = linguagem;
        this.tempo = tempo;
    }
    
    public Submissao(long id_submissao, long id_contest, long id_problema, long id_usuario, String status, 
            String url, String linguagem, long tempo) {
        this.id_problema = id_problema;
        this.id_usuario = id_usuario;
        this.status = status;
        this.url = url;
        this.linguagem = linguagem;
        this.tempo = tempo;
        this.id_submissao = id_submissao;
        this.id_contest = id_contest;
    }
   
    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    public long getId_problema() {
        return id_problema;
    }

    public void setId_problema(long id_problema) {
        this.id_problema = id_problema;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public long getId_contest() {
        return id_contest;
    }

    public void setId_contest(long id_contest) {
        this.id_contest = id_contest;
    }

    public long getId_submissao() {
        return id_submissao;
    }

    public void setId_submissao(long id_submissao) {
        this.id_submissao = id_submissao;
    }
    
    
    
}
