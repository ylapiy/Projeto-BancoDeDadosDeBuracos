package Principal;

import java.io.File;
import java.sql.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;



public class Metadados {

	public  static String PegarNomeDaIamgem(File Imagem) { 
		
		try {  
			
			File imagemfile = Imagem;  
	        String Nome =  imagemfile.getName();
			return Nome;
			
			}catch (Exception e){e.printStackTrace();return "";}
		
	}

	 public static double PegarDadoGPSLatitude(File Imagem) {
		
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
		 
		 System.out.println("ja começo");
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
