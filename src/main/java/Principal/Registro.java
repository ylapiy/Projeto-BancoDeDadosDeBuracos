package Principal;

public class Registro {
    public int fid;
    public String data;
    public int categoria;
    public int status;
    public String observacao;
    public String imagem;
    public String geom;
    public String rua;
    public String bairro;

    public Registro(int fid, String data, int categoria, int status, String observacao, String imagem, String geom, String rua, String bairro) {
        this.fid = fid;
        this.data = data;
        this.categoria = categoria;
        this.status = status;
        this.observacao = observacao;
        this.imagem = imagem;
        this.geom = geom;
        this.rua = rua;
        this.bairro = bairro;
    }
}
