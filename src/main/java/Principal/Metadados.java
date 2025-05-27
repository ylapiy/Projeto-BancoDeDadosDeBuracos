package Principal;

import java.io.File;
import java.sql.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

//clase para limpar os metadados da foto

public class Metadados {

	public  static String PegarNomeDaIamgem(File Imagem) { 

		//retorn o nome da foto (bem autoexplicativo sinceramente)

		try {  
			
			File imagemfile = Imagem;  
	        String Nome =  imagemfile.getName();
			return Nome;
			
			}catch (Exception e){e.printStackTrace();return "";}
		
	}

	 public static double PegarDadoGPSLatitude(File Imagem) {
		
		//retorna os dados de latitude da foto, se tiver
		//amtigamente o back bugava quando mandava uma foto sem metadados
		//hoje parece que era antigamente

		try {  
			
		File imagemfile = Imagem ;
		Metadata MetadataDaImagem = ImageMetadataReader.readMetadata(imagemfile);
		GpsDirectory DadosDeLocalizacao = MetadataDaImagem.getFirstDirectoryOfType(GpsDirectory.class);
		 if(DadosDeLocalizacao == null) {System.out.println("Sem dados de gps para a image");}
		GeoLocation LocalizaçãoExata = DadosDeLocalizacao.getGeoLocation();
		 if(LocalizaçãoExata == null) {System.out.println("Sem localização exata");}
		double Latitude = LocalizaçãoExata.getLatitude();
		
		
		
		return Latitude;
		
		
		}catch (Exception e){e.printStackTrace();return 0;}

		
		
		}

	 public static  double PegarDadosGPSLongitude(File Imagem) {
			
			
		//retorna os dados de longitude da foto, se tiver
		//amtigamente o back bugava quando mandava uma foto sem metadados
		//hoje parece que era antigamente


		try {  
				
		File imagemfile = Imagem; 
		Metadata MetadataDaImagem = ImageMetadataReader.readMetadata(imagemfile);
		GpsDirectory DadosDeLocalizacao = MetadataDaImagem.getFirstDirectoryOfType(GpsDirectory.class);
		 if(DadosDeLocalizacao == null) {System.out.println("Sem dados de gps para a image");return 0;}
		GeoLocation LocalizaçãoExata = DadosDeLocalizacao.getGeoLocation();
		 if(LocalizaçãoExata == null) {System.out.println("Sem localização exata");return 0;}
		double Longitude = LocalizaçãoExata.getLongitude();
				
				
				 
		return Longitude;
				
				
		}catch (Exception e){e.printStackTrace();}return 0;}
		
	 public static Date PegarDadoData(File Imagem){
		 
		//esse aqui pega os dados de dia e horaio da foto 

		 Date hoje = new java.sql.Date(System.currentTimeMillis());
		 
		 try {
			 File imagemfile = Imagem;
			 Metadata MetadataDaImagem = ImageMetadataReader.readMetadata(imagemfile);
			 ExifSubIFDDirectory DiretorioDaImagem = MetadataDaImagem.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			  if(DiretorioDaImagem == null) {System.out.println("A imagem não apresenta data registrada");return hoje;}
			 
			 java.util.Date DatadaImagem = DiretorioDaImagem.getDateOriginal();
			 java.sql.Date DatadaImagemSQL = new java.sql.Date(DatadaImagem.getTime());
			 return DatadaImagemSQL;
			 
			 
		 }catch(Exception e){e.getStackTrace();return hoje;}
	 
		 
	}

	
	
}
