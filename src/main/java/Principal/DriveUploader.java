package Principal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

/* class to demonstrate use of Drive files list API */
public class DriveUploader {
  /**
   * Application name.
   */
  private static final String APPLICATION_NAME = "Banco de Buracos";
  /**
   * Global instance of the JSON factory.
   */
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  /**
   * Directory to store authorization tokens for this application.
   */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
   */
  private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
  private static final String CREDENTIALS_FILE_PATH = "/Principal_resources/secrets.json";

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)  throws IOException {
		
    InputStream in = DriveUploader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).setCallbackPath("/Callback").build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    return credential;
  }

  /**
   * Uploads a file to Google Drive.
   *
   * @param localFilePath The path to the local file to upload.
   * @param mimeType      The MIME type of the file.
   * @param driveFileName The name to give the file on Google Drive.
   * @throws IOException              If an error occurs during the upload.
   * @throws GeneralSecurityException If an error occurs with security.
   */
  public void post(String localFilePath, String mimeType, String driveFileName)  throws IOException, GeneralSecurityException {
	
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();

    // Create file metadata
    File fileMetadata = new File();
    fileMetadata.setName(driveFileName);
    fileMetadata.setParents(Collections.singletonList("1CuJLqPsk0rc3poQyDlhyt5dtSSVDs6ys"));

    // Specify the file path and MIME type
    
    java.io.File filePath = new java.io.File(localFilePath);
    FileContent mediaContent = new FileContent(mimeType, filePath);

    // Upload
    File uploadedFile = service.files().create(fileMetadata, mediaContent)
        .setFields("id, name")
        .execute();

    System.out.printf("Archive sent: %s (ID: %s)\n", uploadedFile.getName(), uploadedFile.getId());
  }


//  public void get() throws IOException, GeneralSecurityException {
//    // Build a new authorized API client service.
//    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//        .setApplicationName(APPLICATION_NAME)
//        .build();
//    
//    FileList result = service.files().list()
//    	    .setQ("'1CuJLqPsk0rc3poQyDlhyt5dtSSVDs6ys' in parents ")
//    	    .setCorpora("allDrives")   
//    	    .setPageSize(10)
//    	    .setFields("nextPageToken, files(id, name)")
//    	    .setSupportsAllDrives(true)            
//    	    .setIncludeItemsFromAllDrives(true)    
//    	    .execute();
//    
//    List<File> files = result.getFiles();
//    if (files == null || files.isEmpty()) {
//      System.out.println("No files found.");
//    } else {
//      System.out.println("Files:");
//      for (File file : files) {
//        System.out.printf("%s (%s)\n", file.getName(), file.getId());
//      }
//    }
//  }

  public void get() throws IOException, GeneralSecurityException {
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME)
	        .build();

	    String folderId = "1CuJLqPsk0rc3poQyDlhyt5dtSSVDs6ys";

	    FileList result = service.files().list()
	        .setQ("'" + folderId + "' in parents and trashed = false")
	        .setPageSize(20)
	        .setFields("files(id, name, mimeType)")
	        .execute();

	    List<File> files = result.getFiles();
	    if (files == null || files.isEmpty()) {
	        System.out.println("Nenhum arquivo encontrado.");
	    } else {
	        System.out.println("Arquivos encontrados:");
	        for (File file : files) {
	            System.out.printf("%s (%s) [%s]\n", file.getName(), file.getId(), file.getMimeType());
	        }
	    }
	}
  
  
  
}
