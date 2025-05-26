package Principal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONObject;

public class GeografiaReversa {

	private static final String API_KEY = "pk.7ac70b80805183e2466b163c071075dc";

	private String Bairro;
	private String Rua;
		
	public static GeografiaReversa RuaeBairro(Double Latitude,Double Longitude) {
			URL Url = null;
			 try {
			  String UrldaPagina = "https://us1.locationiq.com/v1/reverse.php?key=" + API_KEY + "&lat=" + Latitude + "&lon=" + Longitude + "&format=json";
			  Url = new URI(UrldaPagina).toURL();
			  HttpURLConnection conect = (HttpURLConnection) Url.openConnection();
			  conect.setRequestMethod("GET");
			  conect.setRequestProperty("User-Agent","Trabalho_Teste/1.0 (java)");
			  
			  InputStream respostaServidor = conect.getInputStream();
			  String resposta = new String(respostaServidor.readAllBytes());
			  
			  JSONObject Jsonobjeto = new JSONObject (resposta);
			  JSONObject endereco = Jsonobjeto.getJSONObject("address");
			  String rua = endereco.optString("road", "Rua não encontrada");
			  String bairro = endereco.optString("suburb", "bairro não encontrada");
			  
			  GeografiaReversa saida = new GeografiaReversa();
			  saida.Bairro = bairro;
			  saida.Rua = rua;
			  System.out.println("Rua: " + rua);
	       	  System.out.println("Bairro: " + bairro);
			  return saida;
				
			 } 
			
			 catch (MalformedURLException e) {e.printStackTrace();} 
			 catch (URISyntaxException e) {e.printStackTrace();} 
			 catch (IOException e) {e.printStackTrace();}
			return null;
		}
	
	public String GetRua(){return this.Rua;}
	public String GetBairro(){return this.Bairro;}
	
}
