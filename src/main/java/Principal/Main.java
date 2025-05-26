package Principal;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {

	 public static void main(String[] args) {

		//  Scanner Entrada = new Scanner(System.in);
		 
		//  System.out.println("Digite o caminho do banco de dados");
		//  String url = Entrada.next();
		 
		//  System.out.println("Disite o usuario do banco de dados");
		//  String usuario = Entrada.next();
		 
		//  System.out.println("Digite a senha do banco de dados");
		//  String senha = Entrada.next();
		 
		//  System.out.println("Digite a API_KEY da geografia reversa");
		//  String KEY = Entrada.next();
		 
		//  Principal.GeografiaReversa.ColocarKEY(KEY);
		//  BancoDeDados teste = new BancoDeDados(url,usuario,senha);
		  
		//  teste.ConcetarPost();
		  
		//  teste.printarTabela();
		
		  
		try {
		DriveUploader drive = new DriveUploader();
		drive.post("src/main/java/Principal_resources/20560981.jpeg", "image/jpeg", "20560981.jpeg");
		} catch (IOException | GeneralSecurityException e) {
		 	e.printStackTrace();  		}
	 
		 
//		 try {
//			 DriveUploader drive = new DriveUploader();
//			 drive.get();
			 
//		 }catch(IOException | GeneralSecurityException e) {
//   		e.printStackTrace();
//  		 }
			 
			 
			 
		 }
		 
		 
		/*
		 
		 Principal.GeografiaReversa.RuaeBairro(Principal.Metadados.PegarDadoGPSLatitude(caminho),Principal.Metadados.PegarDadosGPSLongitude(caminho));
		 Principal.Metadados.PegarDadoData(caminho);
		 Principal.Metadados.PegarDadosGPSLongitude(caminho);
		 Principal.Metadados.PegarDadoGPSLatitude(caminho);

         */
		  
}
	

