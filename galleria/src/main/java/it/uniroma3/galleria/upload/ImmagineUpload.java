package it.uniroma3.galleria.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImmagineUpload {

    private static final String TOMCAT_HOME_PATH = System.getProperty("catalina.home");
    private static final String PATH_IMMAGINI = TOMCAT_HOME_PATH + File.separator + "immagini";

    private static final File DIR_IMMAGINI = new File(PATH_IMMAGINI);
    private static final String ABSOLUTE_PATH_DIR_IMMAGINI = DIR_IMMAGINI.getAbsolutePath() + File.separator;


    public void creaDirectoryImmaginiSeNecessario() {
        if (!DIR_IMMAGINI.exists()) {
        	DIR_IMMAGINI.mkdirs();
        }
    }


	public void creaFileImmagine(String nome, MultipartFile file) {
        try {
            File immagine = new File(ABSOLUTE_PATH_DIR_IMMAGINI + nome);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(immagine));
            stream.write(file.getBytes());
            stream.close();

        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	public void rimuovi(String nome){
		File file = getPathFile(nome);
		file.delete();
	}
	
	public File getPathFile(String nomeImmagine){
       return new File(ABSOLUTE_PATH_DIR_IMMAGINI + nomeImmagine +".jpg");
	}

}