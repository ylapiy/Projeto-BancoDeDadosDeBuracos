package Principal;

import java.sql.Date;

public class Foto extends Metadados {
    
    private String caminho;
	private Double Longitude;
	private Double Latitude;
	private Date data;
	
 public void Fotos(String CaminhoDaImagem) {
	     this.Longitude = PegarDadosGPSLongitude(CaminhoDaImagem);
	     this.Latitude = PegarDadoGPSLatitude(CaminhoDaImagem);
	     this.data =  PegarDadoData(CaminhoDaImagem);
		 this.caminho = CaminhoDaImagem;
		
		
	}
	
	



}
