package fr.mismo.demo_web_addin.util;

public class FilesUtil {

    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex >= 0) {
            return fileName.substring(lastDotIndex);  // Inclure le point dans l'extension
        }
        return "";
    }

    public String removeExtension(String fileName) {
        if (fileName != null) {
            int lastDotIndex = fileName.lastIndexOf(".");
            if (lastDotIndex >= 0) {
                return fileName.substring(0, lastDotIndex);
            }
        }
        return fileName;
    }
}
