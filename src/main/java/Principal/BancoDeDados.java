package Principal;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BancoDeDados {

	private static final String url = "jdbc:postgresql://db.hvezapxfmgzmhppqllmi.supabase.co:5432/postgres?sslmode=require";
    private static final String user = "postgres";
    private static final String password = "10126824!euamopostgis"; // coloque a senha que está no seu supabase
    
    public static List<Registro> printarTabela() {

        try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver JDBC PostgreSQL carregado com sucesso.");
        } catch (ClassNotFoundException e) {
        System.err.println("Driver JDBC PostgreSQL NÃO encontrado!");
        e.printStackTrace();
        }

    	List<Registro> linhaTabela = new ArrayList<>();
        Registro NovaLinha;
    	
    	try(Connection conn = DriverManager.getConnection(url,user,password);ResultSet resultado = conn.prepareStatement("SELECT fid,data,categoria,status,observacao,imagem,ST_AsText(geom) AS geom ,rua,bairro FROM registro_popular;").executeQuery();){
    		while(resultado.next()){
    			int     fid        = resultado.getInt("fid");
                Date    data       = resultado.getDate("data");
                int     categoria  = resultado.getInt("categoria");
                int     status     = resultado.getInt("status");
                String  obs        = resultado.getString("observacao");
                String  imagem     = resultado.getString("imagem");
                String  geom       = resultado.getString("geom");
                String  rua        = resultado.getString("rua");
                String  bairro     = resultado.getString("bairro");

                String datas = data.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                
                NovaLinha = new Registro(fid, datas, categoria, status, obs, imagem, geom, rua, bairro);
                linhaTabela.add(NovaLinha);
    		}
    	
            return linhaTabela;

    	}catch(SQLException e) {
    		e.printStackTrace();
    		return linhaTabela;
    	}
    	
    	
    }
    
    public static void InserirITEM(
    		
    		java.sql.Date data,
            long categoria,
            long status,
            String observacao,
            String caminhoimagem,
            double longitude,
            double latitude,
            String rua,
            String bairro) {
 
    	
    	Random idAleatorio = new Random();

        try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver JDBC PostgreSQL carregado com sucesso.");
    } catch (ClassNotFoundException e) {
        System.err.println("Driver JDBC PostgreSQL NÃO encontrado!");
        e.printStackTrace();
    }
    
    	try(Connection conn = DriverManager.getConnection(url,user,password)){
    		PreparedStatement NovaInserção = conn.prepareStatement("INSERT INTO registro_popular (fid,data,categoria,status,observacao,imagem,geom,rua,bairro) VALUES (?,?,?,?,?,?,ST_SetSRID(ST_MakePoint(?, ?), 4326),?,?)");
    		NovaInserção.setInt(1, idAleatorio.nextInt());
    		NovaInserção.setDate(2,data);
    		NovaInserção.setLong(3, categoria);
    		NovaInserção.setLong(4, status);
    		NovaInserção.setString(5, observacao);
    		NovaInserção.setString(6, caminhoimagem);
    		NovaInserção.setDouble(7, longitude);
    		NovaInserção.setDouble(8, latitude);
    		NovaInserção.setString(9, rua);
    		NovaInserção.setString(10, bairro);
    	   
    		NovaInserção.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    		
    	}
    	
    }
	
}
