package dev.pizzawunsch.configuration;

import dev.pizzawunsch.UHCenario;
import lombok.Getter;
import lombok.SneakyThrows;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This class handles the configurations of a given yaml-file.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 19.04.2022
 */
@Getter
public class AbstractConfiguration {

    // instance variables.
    private final String folderPath;
    private final String fileName;
    private YamlFile config;

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     *
     * @param folderPath the path of the folder of the file.
     * @param fileName the name of the file.
     */
    @SneakyThrows
    public AbstractConfiguration(String folderPath, String fileName) {
        this.folderPath = folderPath;
        this.fileName = fileName;
        // Copies the file into the given destination file.
        if(isCopyable())
            this.copy();
        // Builds the yaml-configuration file.
        this.build();
        this.config.load();
    }

    /**
     * This method allows you to build yaml-configuration.
     * Only use it, when the file does exist.
     */
    public void build() {
        this.config = new YamlFile(new File(folderPath, fileName));
    }

    /**
     * This method allows you to check if the file in the resource file does exist.
     * @return if the file in the resource folder does exist.
     */
    public boolean isCopyable() {
        try {
            InputStream inputStream = UHCenario.getInstance().getClass().getClassLoader().getResourceAsStream(fileName);
            // If the file is in the resource folder.
            if(inputStream != null)
                return true;
        } catch(Exception ignored) {}
        return false;
    }

    /**
     * This method allows you to copy a file from the resource folder to the given path's folder.
     */
    public void copy() {
        File file = new File(folderPath, fileName);
        // if the file does not exist.
        if(!file.exists()) {
            File folder = new File(folderPath);
            // If the file does not exist.
            if (!folder.exists())
                folder.mkdir();
            try {
                InputStream inputStream = UHCenario.getInstance().getClass().getClassLoader().getResourceAsStream(fileName);
                assert inputStream != null;
                Files.copy(inputStream, new File(folder.toPath().toString() + "/" + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            } catch(Exception ignored) {}
        }
    }

}