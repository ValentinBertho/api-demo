package fr.mismo.demo_web_addin.services;

import fr.mismo.demo_web_addin.properties.WsDocumentProperties;
import fr.mismo.wsdocument.wsdl.*;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Service
@Profile("!dev")
public class WsDocumentService implements IWsDocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsDocumentService.class);

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    private ArrayOfKeyValueOfstringstring.KeyValueOfstringstring createKeyValue(final String key, final String value) {
        final ArrayOfKeyValueOfstringstring.KeyValueOfstringstring kv = new ArrayOfKeyValueOfstringstring.KeyValueOfstringstring();
        kv.setKey(key);
        kv.setValue(value);
        return kv;
    }

    private Integer creerDocInfo(final WsDocumentProperties.NewDocumentProperties proprietesNewDocument, final String titreDocument, final Map<String, String> listeCleValeur, String auteurDocument) {
        final ObjectFactory factory = new ObjectFactory();

        final DocInfo docInfo = factory.createDocInfo();
        docInfo.setIdDocInfo(-1);
        docInfo.setIdDerniereVersion(-1);
        docInfo.setLibelle(factory.createDocInfoDLibelle(titreDocument));
        docInfo.setDescription(factory.createDocInfoDDescription(""));
        docInfo.setCreerPar(factory.createDocInfoDCreerPar(auteurDocument));

        final ArrayOfKeyValueOfstringstring listeCleValeurKV = factory.createArrayOfKeyValueOfstringstring();
        listeCleValeurKV.getKeyValueOfstringstring().add(createKeyValue("CODE_USER", auteurDocument));
        listeCleValeurKV.getKeyValueOfstringstring().add(createKeyValue("COD_TYPE_C", proprietesNewDocument.getTypeDocument()));
        if (listeCleValeur != null)
            listeCleValeur.forEach((k, v) -> listeCleValeurKV.getKeyValueOfstringstring().add(createKeyValue(k, v)));
        docInfo.setListeCleValeur(factory.createDocInfoDListeCleValeur(listeCleValeurKV));

        final CreerDocInfo request = factory.createCreerDocInfo();
        request.setDoc(factory.createCreerDocInfoDoc(docInfo));
        request.setUtilisateur(factory.createCreerDocInfoUtilisateur(auteurDocument));

        final CreerDocInfoResponse response = (CreerDocInfoResponse) webServiceTemplate.marshalSendAndReceive(request, //
                new SoapActionCallback("http://mismo.fr/WSDocumentAth/IWSDocumentAth/CreerDocInfo"));

        return response.getCreerDocInfoResult();
    }

    private Integer creerDocVersion(final WsDocumentProperties.NewDocumentProperties proprietesNewDocument, final Integer idDocInfo, final String nomFichier, final String extension, final Long tailleFichier, String auteurDocument) {
        final ObjectFactory factory = new ObjectFactory();

        final DocVersion docVersion = factory.createDocVersion();
        docVersion.setIdDocVersion(-1);
        docVersion.setIdDocInfo(idDocInfo);
        docVersion.setNomFichier(factory.createDocVersionDNomFichier(nomFichier));
        docVersion.setExtension(factory.createDocVersionDExtension(extension));
        docVersion.setTailleFichier(tailleFichier.intValue());
        docVersion.setCreerPar(factory.createDocVersionDCreerPar(auteurDocument));

        final CreerDocVersion request = factory.createCreerDocVersion();
        request.setDoc(factory.createCreerDocVersionDoc(docVersion));
        request.setUtilisateur(factory.createCreerDocVersionUtilisateur(auteurDocument));

        final CreerDocVersionResponse response = (CreerDocVersionResponse) webServiceTemplate.marshalSendAndReceive(request, //
                new SoapActionCallback("http://mismo.fr/WSDocumentAth/IWSDocumentAth/CreerDocVersion"));

        return response.getCreerDocVersionResult();

    }

    private void televerser(final WsDocumentProperties.NewDocumentProperties proprietesNewDocument, final Integer idDocVersion, final byte[] paquet, String auteurDocument) {
        final ObjectFactory factory = new ObjectFactory();

        final PublierDoc request = factory.createPublierDoc();
        request.setIdDocVersion(idDocVersion);
        request.setUtilisateur(factory.createPublierDocUtilisateur(auteurDocument));
        request.setFichier(factory.createPublierDocFichier(Base64.getEncoder().encodeToString(paquet)));

        webServiceTemplate.marshalSendAndReceive(request, //
                new SoapActionCallback("http://mismo.fr/WSDocumentAth/IWSDocumentAth/PublierDoc"));
    }

    private static final int taillePaquetMax = 512000;

    public void creerDocument(final WsDocumentProperties.NewDocumentProperties proprietesNewDocument, final String titreDocument, final String extension, final Path contenuDocument, final Map<String, String> listeCleValeur, String auteurDocument) {
        final byte[] paquet = new byte[taillePaquetMax];
        int taillePaquet;

        final Integer idDocInfo = creerDocInfo(proprietesNewDocument, titreDocument, listeCleValeur,auteurDocument);
        final Integer idDocVersion = creerDocVersion(proprietesNewDocument, idDocInfo, titreDocument, extension, (new File(contenuDocument.toString())).length(),auteurDocument);

        try (InputStream contenu = Files.newInputStream(contenuDocument)) {
            while ((taillePaquet = contenu.read(paquet)) > 0) {
                televerser(proprietesNewDocument, idDocVersion, taillePaquet < taillePaquetMax ? Arrays.copyOf(paquet, taillePaquet) : paquet,auteurDocument);
            }
        } catch (final IOException ex) {
            throw new ServiceException("Impossible de lire le fichier");
        }

    }

}
