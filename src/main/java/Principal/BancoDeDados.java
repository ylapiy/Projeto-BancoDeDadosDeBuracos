package Principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BancoDeDados {

	private String url;
    private String user;
    private String password;
   
    public void ConcetarPost() {

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        System.out.println("Conectado com sucesso!");
        DatabaseMetaData metadados = conn.getMetaData();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }}

    public void VerTabelas() {
    	
    	try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metadados = conn.getMetaData();
            try( ResultSet resultadosME = metadados.getTables(null, null, "%",new String[] {"TABLE"} )){
            	System.out.println("Databases disponíveis:");
            	while(resultadosME.next()) {
            		String dbNome = resultadosME.getString("TABLE_NAME");
                    System.out.println("  - " + dbNome);}
            }catch(SQLException e) {
            	e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }}
    
    public void printarTabela() {
    	
    	DateTimeFormatter dia =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	
    	try(Connection conn = DriverManager.getConnection(url,user,password);ResultSet resultado = conn.prepareStatement("SELECT id,data,categoria,status,observacao,imagem,ST_AsText(geom) AS geom ,rua,bairro FROM registro_popular;").executeQuery();){
    		while(resultado.next()){
    			int     id         = resultado.getInt("id");
                Date    data       = resultado.getDate("data");
                int     categoria  = resultado.getInt("categoria");
                int     status     = resultado.getInt("status");
                String  obs        = resultado.getString("observacao");
                String  imagem     = resultado.getString("imagem");
                String  geom       = resultado.getString("geom");
                String  rua        = resultado.getString("rua");
                String  bairro     = resultado.getString("bairro");
    		
    		 System.out.printf(
    				 "ID: %d | Data: %s | Cat: %d | Status: %d%n" +
                     "Observação: %s%n" +
                     "Imagem: %s%n" +
                     "Geom: %s%n" +
                     "Rua: %s | Bairro: %s%n" +
                     "---------------------------------------%n",
                     id,
                     data.toLocalDate().format(dia),
                     categoria,
                     status,
                     obs,
                     imagem,
                     geom,
                     rua,
                     bairro
                 );
    		
    		
    		
    		}
    	
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
    	}
    	
    	
    	
    	
    }
    
    public void InserirITEM(
    		
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
    
    	try(Connection conn = DriverManager.getConnection(url,user,password)){
    		PreparedStatement NovaInserção = conn.prepareStatement("INSERT INTO registro_popular (id,data,categoria,status,observacao,imagem,geom,rua,bairro) VALUES (?,?,?,?,?,?,ST_SetSRID(ST_MakePoint(?, ?), 4326),?,?)");
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
	
    public BancoDeDados(String URL, String user, String Senha) {
    	this.url = URL;
    	this.user = user;
    	this.password = Senha;
    	
    	
    }
	
}
