package fr.mismo.demo_web_addin.services;

import fr.mismo.demo_web_addin.properties.WsDocumentProperties;

import java.nio.file.Path;
import java.util.Map;

public interface IWsDocumentService {

    public void creerDocument(WsDocumentProperties.NewDocumentProperties proprietesNewDocument, String titreDocument, String extension, Path contenuDocument, Map<String, String> listeCleValeur, String auteurDocument);

}
