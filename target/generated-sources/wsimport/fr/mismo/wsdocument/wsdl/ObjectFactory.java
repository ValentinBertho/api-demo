
package fr.mismo.wsdocument.wsdl;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.mismo.wsdocument.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _DocInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocInfo");
    private static final QName _DocInfoD_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocInfoD");
    private static final QName _DocVersion_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocVersion");
    private static final QName _DocVersionD_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocVersionD");
    private static final QName _ArrayOfDocInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ArrayOfDocInfo");
    private static final QName _ArrayOfDocVersion_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ArrayOfDocVersion");
    private static final QName _Mail_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Mail");
    private static final QName _MailD_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "MailD");
    private static final QName _EzgedDocInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "EzgedDocInfo");
    private static final QName _EzgedDocInfoD_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "EzgedDocInfoD");
    private static final QName _DocSynchro_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocSynchro");
    private static final QName _DocSynchroD_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "DocSynchroD");
    private static final QName _ArrayOfDocSynchro_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ArrayOfDocSynchro");
    private static final QName _ArrayOfKeyValueOfstringstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfKeyValueOfstringstring");
    private static final QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private static final QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private static final QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private static final QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private static final QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private static final QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private static final QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private static final QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private static final QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private static final QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private static final QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private static final QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private static final QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private static final QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private static final QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private static final QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private static final QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private static final QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private static final QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private static final QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private static final QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private static final QName _GetDataResponseGetDataResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "GetDataResult");
    private static final QName _CreerDocInfoDoc_QNAME = new QName("http://mismo.fr/WSDocumentAth", "doc");
    private static final QName _CreerDocInfoUtilisateur_QNAME = new QName("http://mismo.fr/WSDocumentAth", "utilisateur");
    private static final QName _PublierDocFichier_QNAME = new QName("http://mismo.fr/WSDocumentAth", "fichier");
    private static final QName _ListerResponseListerResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "ListerResult");
    private static final QName _ChargerDocInfoResponseChargerDocInfoResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "ChargerDocInfoResult");
    private static final QName _ListerDocVersionResponseListerDocVersionResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "ListerDocVersionResult");
    private static final QName _ChargerDocVersionResponseChargerDocVersionResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "ChargerDocVersionResult");
    private static final QName _TelechargerDocVersion_QNAME = new QName("http://mismo.fr/WSDocumentAth", "docVersion");
    private static final QName _TelechargerResponseTelechargerResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "TelechargerResult");
    private static final QName _TelechargerParIdResponseTelechargerParIdResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "TelechargerParIdResult");
    private static final QName _ModifierInfoDocDocInfo_QNAME = new QName("http://mismo.fr/WSDocumentAth", "docInfo");
    private static final QName _EnvoyerMailDocMailAEnvoyer_QNAME = new QName("http://mismo.fr/WSDocumentAth", "mailAEnvoyer");
    private static final QName _MajInfoEzgedDansAthResponseMajInfoEzgedDansAthResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "MajInfoEzgedDansAthResult");
    private static final QName _LireInfoEzgedDansAthResponseLireInfoEzgedDansAthResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "LireInfoEzgedDansAthResult");
    private static final QName _AjoutDocVersionDansCourrierSynchroDocSynchro_QNAME = new QName("http://mismo.fr/WSDocumentAth", "docSynchro");
    private static final QName _RecupererListeAjoutSynchroResponseRecupererListeAjoutSynchroResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "RecupererListeAjoutSynchroResult");
    private static final QName _RecupererListeSuppressionSynchroResponseRecupererListeSuppressionSynchroResult_QNAME = new QName("http://mismo.fr/WSDocumentAth", "RecupererListeSuppressionSynchroResult");
    private static final QName _DocSynchroDChemin_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Chemin");
    private static final QName _DocSynchroDCodTypeC_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "CodTypeC");
    private static final QName _EzgedDocInfoDChampPleinTexte_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ChampPleinTexte");
    private static final QName _EzgedDocInfoDContexte_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Contexte");
    private static final QName _EzgedDocInfoDExtension_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Extension");
    private static final QName _EzgedDocInfoDImageVignette_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ImageVignette");
    private static final QName _EzgedDocInfoDNomFichier_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "NomFichier");
    private static final QName _EzgedDocInfoDRipe_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Ripe");
    private static final QName _EzgedDocInfoDTitre_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Titre");
    private static final QName _MailDBody_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Body");
    private static final QName _MailDFrom_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "From");
    private static final QName _MailDSubject_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Subject");
    private static final QName _MailDTo_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "To");
    private static final QName _DocVersionDCreerPar_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "CreerPar");
    private static final QName _DocVersionDNomFichierSysteme_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "NomFichierSysteme");
    private static final QName _DocVersionDNomFichierTelechargement_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "NomFichierTelechargement");
    private static final QName _DocInfoDDescription_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Description");
    private static final QName _DocInfoDEtat_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Etat");
    private static final QName _DocInfoDLibelle_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "Libelle");
    private static final QName _DocInfoDListeCleValeur_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ListeCleValeur");
    private static final QName _DocInfoDModifPar_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "ModifPar");
    private static final QName _DocInfoDUtilisateurEnCoursModification_QNAME = new QName("http://schemas.datacontract.org/2004/07/S", "UtilisateurEnCoursModification");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.mismo.wsdocument.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring }
     * 
     * @return
     *     the new instance of {@link ArrayOfKeyValueOfstringstring }
     */
    public ArrayOfKeyValueOfstringstring createArrayOfKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link GetData }
     * 
     * @return
     *     the new instance of {@link GetData }
     */
    public GetData createGetData() {
        return new GetData();
    }

    /**
     * Create an instance of {@link GetDataResponse }
     * 
     * @return
     *     the new instance of {@link GetDataResponse }
     */
    public GetDataResponse createGetDataResponse() {
        return new GetDataResponse();
    }

    /**
     * Create an instance of {@link CreerDocInfo }
     * 
     * @return
     *     the new instance of {@link CreerDocInfo }
     */
    public CreerDocInfo createCreerDocInfo() {
        return new CreerDocInfo();
    }

    /**
     * Create an instance of {@link DocInfo }
     * 
     * @return
     *     the new instance of {@link DocInfo }
     */
    public DocInfo createDocInfo() {
        return new DocInfo();
    }

    /**
     * Create an instance of {@link CreerDocInfoResponse }
     * 
     * @return
     *     the new instance of {@link CreerDocInfoResponse }
     */
    public CreerDocInfoResponse createCreerDocInfoResponse() {
        return new CreerDocInfoResponse();
    }

    /**
     * Create an instance of {@link CreerDocVersion }
     * 
     * @return
     *     the new instance of {@link CreerDocVersion }
     */
    public CreerDocVersion createCreerDocVersion() {
        return new CreerDocVersion();
    }

    /**
     * Create an instance of {@link DocVersion }
     * 
     * @return
     *     the new instance of {@link DocVersion }
     */
    public DocVersion createDocVersion() {
        return new DocVersion();
    }

    /**
     * Create an instance of {@link CreerDocVersionResponse }
     * 
     * @return
     *     the new instance of {@link CreerDocVersionResponse }
     */
    public CreerDocVersionResponse createCreerDocVersionResponse() {
        return new CreerDocVersionResponse();
    }

    /**
     * Create an instance of {@link PublierDoc }
     * 
     * @return
     *     the new instance of {@link PublierDoc }
     */
    public PublierDoc createPublierDoc() {
        return new PublierDoc();
    }

    /**
     * Create an instance of {@link PublierDocResponse }
     * 
     * @return
     *     the new instance of {@link PublierDocResponse }
     */
    public PublierDocResponse createPublierDocResponse() {
        return new PublierDocResponse();
    }

    /**
     * Create an instance of {@link Lister }
     * 
     * @return
     *     the new instance of {@link Lister }
     */
    public Lister createLister() {
        return new Lister();
    }

    /**
     * Create an instance of {@link ListerResponse }
     * 
     * @return
     *     the new instance of {@link ListerResponse }
     */
    public ListerResponse createListerResponse() {
        return new ListerResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDocInfo }
     * 
     * @return
     *     the new instance of {@link ArrayOfDocInfo }
     */
    public ArrayOfDocInfo createArrayOfDocInfo() {
        return new ArrayOfDocInfo();
    }

    /**
     * Create an instance of {@link ChargerDocInfo }
     * 
     * @return
     *     the new instance of {@link ChargerDocInfo }
     */
    public ChargerDocInfo createChargerDocInfo() {
        return new ChargerDocInfo();
    }

    /**
     * Create an instance of {@link ChargerDocInfoResponse }
     * 
     * @return
     *     the new instance of {@link ChargerDocInfoResponse }
     */
    public ChargerDocInfoResponse createChargerDocInfoResponse() {
        return new ChargerDocInfoResponse();
    }

    /**
     * Create an instance of {@link ListerDocVersion }
     * 
     * @return
     *     the new instance of {@link ListerDocVersion }
     */
    public ListerDocVersion createListerDocVersion() {
        return new ListerDocVersion();
    }

    /**
     * Create an instance of {@link ListerDocVersionResponse }
     * 
     * @return
     *     the new instance of {@link ListerDocVersionResponse }
     */
    public ListerDocVersionResponse createListerDocVersionResponse() {
        return new ListerDocVersionResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDocVersion }
     * 
     * @return
     *     the new instance of {@link ArrayOfDocVersion }
     */
    public ArrayOfDocVersion createArrayOfDocVersion() {
        return new ArrayOfDocVersion();
    }

    /**
     * Create an instance of {@link ChargerDocVersion }
     * 
     * @return
     *     the new instance of {@link ChargerDocVersion }
     */
    public ChargerDocVersion createChargerDocVersion() {
        return new ChargerDocVersion();
    }

    /**
     * Create an instance of {@link ChargerDocVersionResponse }
     * 
     * @return
     *     the new instance of {@link ChargerDocVersionResponse }
     */
    public ChargerDocVersionResponse createChargerDocVersionResponse() {
        return new ChargerDocVersionResponse();
    }

    /**
     * Create an instance of {@link Telecharger }
     * 
     * @return
     *     the new instance of {@link Telecharger }
     */
    public Telecharger createTelecharger() {
        return new Telecharger();
    }

    /**
     * Create an instance of {@link TelechargerResponse }
     * 
     * @return
     *     the new instance of {@link TelechargerResponse }
     */
    public TelechargerResponse createTelechargerResponse() {
        return new TelechargerResponse();
    }

    /**
     * Create an instance of {@link TelechargerParId }
     * 
     * @return
     *     the new instance of {@link TelechargerParId }
     */
    public TelechargerParId createTelechargerParId() {
        return new TelechargerParId();
    }

    /**
     * Create an instance of {@link TelechargerParIdResponse }
     * 
     * @return
     *     the new instance of {@link TelechargerParIdResponse }
     */
    public TelechargerParIdResponse createTelechargerParIdResponse() {
        return new TelechargerParIdResponse();
    }

    /**
     * Create an instance of {@link ModifierInfoDoc }
     * 
     * @return
     *     the new instance of {@link ModifierInfoDoc }
     */
    public ModifierInfoDoc createModifierInfoDoc() {
        return new ModifierInfoDoc();
    }

    /**
     * Create an instance of {@link ModifierInfoDocResponse }
     * 
     * @return
     *     the new instance of {@link ModifierInfoDocResponse }
     */
    public ModifierInfoDocResponse createModifierInfoDocResponse() {
        return new ModifierInfoDocResponse();
    }

    /**
     * Create an instance of {@link EnvoyerMailDoc }
     * 
     * @return
     *     the new instance of {@link EnvoyerMailDoc }
     */
    public EnvoyerMailDoc createEnvoyerMailDoc() {
        return new EnvoyerMailDoc();
    }

    /**
     * Create an instance of {@link Mail }
     * 
     * @return
     *     the new instance of {@link Mail }
     */
    public Mail createMail() {
        return new Mail();
    }

    /**
     * Create an instance of {@link EnvoyerMailDocResponse }
     * 
     * @return
     *     the new instance of {@link EnvoyerMailDocResponse }
     */
    public EnvoyerMailDocResponse createEnvoyerMailDocResponse() {
        return new EnvoyerMailDocResponse();
    }

    /**
     * Create an instance of {@link ReserverDoc }
     * 
     * @return
     *     the new instance of {@link ReserverDoc }
     */
    public ReserverDoc createReserverDoc() {
        return new ReserverDoc();
    }

    /**
     * Create an instance of {@link ReserverDocResponse }
     * 
     * @return
     *     the new instance of {@link ReserverDocResponse }
     */
    public ReserverDocResponse createReserverDocResponse() {
        return new ReserverDocResponse();
    }

    /**
     * Create an instance of {@link LibererDoc }
     * 
     * @return
     *     the new instance of {@link LibererDoc }
     */
    public LibererDoc createLibererDoc() {
        return new LibererDoc();
    }

    /**
     * Create an instance of {@link LibererDocResponse }
     * 
     * @return
     *     the new instance of {@link LibererDocResponse }
     */
    public LibererDocResponse createLibererDocResponse() {
        return new LibererDocResponse();
    }

    /**
     * Create an instance of {@link LibererDocParId }
     * 
     * @return
     *     the new instance of {@link LibererDocParId }
     */
    public LibererDocParId createLibererDocParId() {
        return new LibererDocParId();
    }

    /**
     * Create an instance of {@link LibererDocParIdResponse }
     * 
     * @return
     *     the new instance of {@link LibererDocParIdResponse }
     */
    public LibererDocParIdResponse createLibererDocParIdResponse() {
        return new LibererDocParIdResponse();
    }

    /**
     * Create an instance of {@link ReserverDocParId }
     * 
     * @return
     *     the new instance of {@link ReserverDocParId }
     */
    public ReserverDocParId createReserverDocParId() {
        return new ReserverDocParId();
    }

    /**
     * Create an instance of {@link ReserverDocParIdResponse }
     * 
     * @return
     *     the new instance of {@link ReserverDocParIdResponse }
     */
    public ReserverDocParIdResponse createReserverDocParIdResponse() {
        return new ReserverDocParIdResponse();
    }

    /**
     * Create an instance of {@link MajEtatDocInfo }
     * 
     * @return
     *     the new instance of {@link MajEtatDocInfo }
     */
    public MajEtatDocInfo createMajEtatDocInfo() {
        return new MajEtatDocInfo();
    }

    /**
     * Create an instance of {@link MajEtatDocInfoResponse }
     * 
     * @return
     *     the new instance of {@link MajEtatDocInfoResponse }
     */
    public MajEtatDocInfoResponse createMajEtatDocInfoResponse() {
        return new MajEtatDocInfoResponse();
    }

    /**
     * Create an instance of {@link MajInfoEzgedDansAth }
     * 
     * @return
     *     the new instance of {@link MajInfoEzgedDansAth }
     */
    public MajInfoEzgedDansAth createMajInfoEzgedDansAth() {
        return new MajInfoEzgedDansAth();
    }

    /**
     * Create an instance of {@link EzgedDocInfo }
     * 
     * @return
     *     the new instance of {@link EzgedDocInfo }
     */
    public EzgedDocInfo createEzgedDocInfo() {
        return new EzgedDocInfo();
    }

    /**
     * Create an instance of {@link MajInfoEzgedDansAthResponse }
     * 
     * @return
     *     the new instance of {@link MajInfoEzgedDansAthResponse }
     */
    public MajInfoEzgedDansAthResponse createMajInfoEzgedDansAthResponse() {
        return new MajInfoEzgedDansAthResponse();
    }

    /**
     * Create an instance of {@link ConfirmerSuppressionEzgedDocInfo }
     * 
     * @return
     *     the new instance of {@link ConfirmerSuppressionEzgedDocInfo }
     */
    public ConfirmerSuppressionEzgedDocInfo createConfirmerSuppressionEzgedDocInfo() {
        return new ConfirmerSuppressionEzgedDocInfo();
    }

    /**
     * Create an instance of {@link ConfirmerSuppressionEzgedDocInfoResponse }
     * 
     * @return
     *     the new instance of {@link ConfirmerSuppressionEzgedDocInfoResponse }
     */
    public ConfirmerSuppressionEzgedDocInfoResponse createConfirmerSuppressionEzgedDocInfoResponse() {
        return new ConfirmerSuppressionEzgedDocInfoResponse();
    }

    /**
     * Create an instance of {@link LireInfoEzgedDansAth }
     * 
     * @return
     *     the new instance of {@link LireInfoEzgedDansAth }
     */
    public LireInfoEzgedDansAth createLireInfoEzgedDansAth() {
        return new LireInfoEzgedDansAth();
    }

    /**
     * Create an instance of {@link LireInfoEzgedDansAthResponse }
     * 
     * @return
     *     the new instance of {@link LireInfoEzgedDansAthResponse }
     */
    public LireInfoEzgedDansAthResponse createLireInfoEzgedDansAthResponse() {
        return new LireInfoEzgedDansAthResponse();
    }

    /**
     * Create an instance of {@link SupprimerDoc }
     * 
     * @return
     *     the new instance of {@link SupprimerDoc }
     */
    public SupprimerDoc createSupprimerDoc() {
        return new SupprimerDoc();
    }

    /**
     * Create an instance of {@link SupprimerDocResponse }
     * 
     * @return
     *     the new instance of {@link SupprimerDocResponse }
     */
    public SupprimerDocResponse createSupprimerDocResponse() {
        return new SupprimerDocResponse();
    }

    /**
     * Create an instance of {@link SupprimerDocVersion }
     * 
     * @return
     *     the new instance of {@link SupprimerDocVersion }
     */
    public SupprimerDocVersion createSupprimerDocVersion() {
        return new SupprimerDocVersion();
    }

    /**
     * Create an instance of {@link SupprimerDocVersionResponse }
     * 
     * @return
     *     the new instance of {@link SupprimerDocVersionResponse }
     */
    public SupprimerDocVersionResponse createSupprimerDocVersionResponse() {
        return new SupprimerDocVersionResponse();
    }

    /**
     * Create an instance of {@link ValiderDoc }
     * 
     * @return
     *     the new instance of {@link ValiderDoc }
     */
    public ValiderDoc createValiderDoc() {
        return new ValiderDoc();
    }

    /**
     * Create an instance of {@link ValiderDocResponse }
     * 
     * @return
     *     the new instance of {@link ValiderDocResponse }
     */
    public ValiderDocResponse createValiderDocResponse() {
        return new ValiderDocResponse();
    }

    /**
     * Create an instance of {@link AjoutDocVersionDansCourrierSynchro }
     * 
     * @return
     *     the new instance of {@link AjoutDocVersionDansCourrierSynchro }
     */
    public AjoutDocVersionDansCourrierSynchro createAjoutDocVersionDansCourrierSynchro() {
        return new AjoutDocVersionDansCourrierSynchro();
    }

    /**
     * Create an instance of {@link DocSynchro }
     * 
     * @return
     *     the new instance of {@link DocSynchro }
     */
    public DocSynchro createDocSynchro() {
        return new DocSynchro();
    }

    /**
     * Create an instance of {@link AjoutDocVersionDansCourrierSynchroResponse }
     * 
     * @return
     *     the new instance of {@link AjoutDocVersionDansCourrierSynchroResponse }
     */
    public AjoutDocVersionDansCourrierSynchroResponse createAjoutDocVersionDansCourrierSynchroResponse() {
        return new AjoutDocVersionDansCourrierSynchroResponse();
    }

    /**
     * Create an instance of {@link SupprimerDocVersionDansCourrierSynchro }
     * 
     * @return
     *     the new instance of {@link SupprimerDocVersionDansCourrierSynchro }
     */
    public SupprimerDocVersionDansCourrierSynchro createSupprimerDocVersionDansCourrierSynchro() {
        return new SupprimerDocVersionDansCourrierSynchro();
    }

    /**
     * Create an instance of {@link SupprimerDocVersionDansCourrierSynchroResponse }
     * 
     * @return
     *     the new instance of {@link SupprimerDocVersionDansCourrierSynchroResponse }
     */
    public SupprimerDocVersionDansCourrierSynchroResponse createSupprimerDocVersionDansCourrierSynchroResponse() {
        return new SupprimerDocVersionDansCourrierSynchroResponse();
    }

    /**
     * Create an instance of {@link RecupererListeAjoutSynchro }
     * 
     * @return
     *     the new instance of {@link RecupererListeAjoutSynchro }
     */
    public RecupererListeAjoutSynchro createRecupererListeAjoutSynchro() {
        return new RecupererListeAjoutSynchro();
    }

    /**
     * Create an instance of {@link RecupererListeAjoutSynchroResponse }
     * 
     * @return
     *     the new instance of {@link RecupererListeAjoutSynchroResponse }
     */
    public RecupererListeAjoutSynchroResponse createRecupererListeAjoutSynchroResponse() {
        return new RecupererListeAjoutSynchroResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDocSynchro }
     * 
     * @return
     *     the new instance of {@link ArrayOfDocSynchro }
     */
    public ArrayOfDocSynchro createArrayOfDocSynchro() {
        return new ArrayOfDocSynchro();
    }

    /**
     * Create an instance of {@link RecupererListeSuppressionSynchro }
     * 
     * @return
     *     the new instance of {@link RecupererListeSuppressionSynchro }
     */
    public RecupererListeSuppressionSynchro createRecupererListeSuppressionSynchro() {
        return new RecupererListeSuppressionSynchro();
    }

    /**
     * Create an instance of {@link RecupererListeSuppressionSynchroResponse }
     * 
     * @return
     *     the new instance of {@link RecupererListeSuppressionSynchroResponse }
     */
    public RecupererListeSuppressionSynchroResponse createRecupererListeSuppressionSynchroResponse() {
        return new RecupererListeSuppressionSynchroResponse();
    }

    /**
     * Create an instance of {@link ControlerDeplacerCheminDoc }
     * 
     * @return
     *     the new instance of {@link ControlerDeplacerCheminDoc }
     */
    public ControlerDeplacerCheminDoc createControlerDeplacerCheminDoc() {
        return new ControlerDeplacerCheminDoc();
    }

    /**
     * Create an instance of {@link ControlerDeplacerCheminDocResponse }
     * 
     * @return
     *     the new instance of {@link ControlerDeplacerCheminDocResponse }
     */
    public ControlerDeplacerCheminDocResponse createControlerDeplacerCheminDocResponse() {
        return new ControlerDeplacerCheminDocResponse();
    }

    /**
     * Create an instance of {@link DocInfoD }
     * 
     * @return
     *     the new instance of {@link DocInfoD }
     */
    public DocInfoD createDocInfoD() {
        return new DocInfoD();
    }

    /**
     * Create an instance of {@link DocVersionD }
     * 
     * @return
     *     the new instance of {@link DocVersionD }
     */
    public DocVersionD createDocVersionD() {
        return new DocVersionD();
    }

    /**
     * Create an instance of {@link MailD }
     * 
     * @return
     *     the new instance of {@link MailD }
     */
    public MailD createMailD() {
        return new MailD();
    }

    /**
     * Create an instance of {@link EzgedDocInfoD }
     * 
     * @return
     *     the new instance of {@link EzgedDocInfoD }
     */
    public EzgedDocInfoD createEzgedDocInfoD() {
        return new EzgedDocInfoD();
    }

    /**
     * Create an instance of {@link DocSynchroD }
     * 
     * @return
     *     the new instance of {@link DocSynchroD }
     */
    public DocSynchroD createDocSynchroD() {
        return new DocSynchroD();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring.KeyValueOfstringstring }
     * 
     * @return
     *     the new instance of {@link ArrayOfKeyValueOfstringstring.KeyValueOfstringstring }
     */
    public ArrayOfKeyValueOfstringstring.KeyValueOfstringstring createArrayOfKeyValueOfstringstringKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring.KeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocInfo")
    public JAXBElement<DocInfo> createDocInfo(DocInfo value) {
        return new JAXBElement<>(_DocInfo_QNAME, DocInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfoD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfoD }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocInfoD")
    public JAXBElement<DocInfoD> createDocInfoD(DocInfoD value) {
        return new JAXBElement<>(_DocInfoD_QNAME, DocInfoD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocVersion")
    public JAXBElement<DocVersion> createDocVersion(DocVersion value) {
        return new JAXBElement<>(_DocVersion_QNAME, DocVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocVersionD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocVersionD }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocVersionD")
    public JAXBElement<DocVersionD> createDocVersionD(DocVersionD value) {
        return new JAXBElement<>(_DocVersionD_QNAME, DocVersionD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ArrayOfDocInfo")
    public JAXBElement<ArrayOfDocInfo> createArrayOfDocInfo(ArrayOfDocInfo value) {
        return new JAXBElement<>(_ArrayOfDocInfo_QNAME, ArrayOfDocInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ArrayOfDocVersion")
    public JAXBElement<ArrayOfDocVersion> createArrayOfDocVersion(ArrayOfDocVersion value) {
        return new JAXBElement<>(_ArrayOfDocVersion_QNAME, ArrayOfDocVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Mail }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Mail }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Mail")
    public JAXBElement<Mail> createMail(Mail value) {
        return new JAXBElement<>(_Mail_QNAME, Mail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MailD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MailD }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "MailD")
    public JAXBElement<MailD> createMailD(MailD value) {
        return new JAXBElement<>(_MailD_QNAME, MailD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "EzgedDocInfo")
    public JAXBElement<EzgedDocInfo> createEzgedDocInfo(EzgedDocInfo value) {
        return new JAXBElement<>(_EzgedDocInfo_QNAME, EzgedDocInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EzgedDocInfoD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EzgedDocInfoD }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "EzgedDocInfoD")
    public JAXBElement<EzgedDocInfoD> createEzgedDocInfoD(EzgedDocInfoD value) {
        return new JAXBElement<>(_EzgedDocInfoD_QNAME, EzgedDocInfoD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocSynchro")
    public JAXBElement<DocSynchro> createDocSynchro(DocSynchro value) {
        return new JAXBElement<>(_DocSynchro_QNAME, DocSynchro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocSynchroD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocSynchroD }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "DocSynchroD")
    public JAXBElement<DocSynchroD> createDocSynchroD(DocSynchroD value) {
        return new JAXBElement<>(_DocSynchroD_QNAME, DocSynchroD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ArrayOfDocSynchro")
    public JAXBElement<ArrayOfDocSynchro> createArrayOfDocSynchro(ArrayOfDocSynchro value) {
        return new JAXBElement<>(_ArrayOfDocSynchro_QNAME, ArrayOfDocSynchro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfKeyValueOfstringstring")
    public JAXBElement<ArrayOfKeyValueOfstringstring> createArrayOfKeyValueOfstringstring(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<>(_ArrayOfKeyValueOfstringstring_QNAME, ArrayOfKeyValueOfstringstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "GetDataResult", scope = GetDataResponse.class)
    public JAXBElement<String> createGetDataResponseGetDataResult(String value) {
        return new JAXBElement<>(_GetDataResponseGetDataResult_QNAME, String.class, GetDataResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "doc", scope = CreerDocInfo.class)
    public JAXBElement<DocInfo> createCreerDocInfoDoc(DocInfo value) {
        return new JAXBElement<>(_CreerDocInfoDoc_QNAME, DocInfo.class, CreerDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = CreerDocInfo.class)
    public JAXBElement<String> createCreerDocInfoUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, CreerDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "doc", scope = CreerDocVersion.class)
    public JAXBElement<DocVersion> createCreerDocVersionDoc(DocVersion value) {
        return new JAXBElement<>(_CreerDocInfoDoc_QNAME, DocVersion.class, CreerDocVersion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = CreerDocVersion.class)
    public JAXBElement<String> createCreerDocVersionUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, CreerDocVersion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "fichier", scope = PublierDoc.class)
    public JAXBElement<String> createPublierDocFichier(String value) {
        return new JAXBElement<>(_PublierDocFichier_QNAME, String.class, PublierDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = PublierDoc.class)
    public JAXBElement<String> createPublierDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, PublierDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = Lister.class)
    public JAXBElement<String> createListerUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, Lister.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "ListerResult", scope = ListerResponse.class)
    public JAXBElement<ArrayOfDocInfo> createListerResponseListerResult(ArrayOfDocInfo value) {
        return new JAXBElement<>(_ListerResponseListerResult_QNAME, ArrayOfDocInfo.class, ListerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ChargerDocInfo.class)
    public JAXBElement<String> createChargerDocInfoUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ChargerDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "ChargerDocInfoResult", scope = ChargerDocInfoResponse.class)
    public JAXBElement<DocInfo> createChargerDocInfoResponseChargerDocInfoResult(DocInfo value) {
        return new JAXBElement<>(_ChargerDocInfoResponseChargerDocInfoResult_QNAME, DocInfo.class, ChargerDocInfoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ListerDocVersion.class)
    public JAXBElement<String> createListerDocVersionUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ListerDocVersion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "ListerDocVersionResult", scope = ListerDocVersionResponse.class)
    public JAXBElement<ArrayOfDocVersion> createListerDocVersionResponseListerDocVersionResult(ArrayOfDocVersion value) {
        return new JAXBElement<>(_ListerDocVersionResponseListerDocVersionResult_QNAME, ArrayOfDocVersion.class, ListerDocVersionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ChargerDocVersion.class)
    public JAXBElement<String> createChargerDocVersionUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ChargerDocVersion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "ChargerDocVersionResult", scope = ChargerDocVersionResponse.class)
    public JAXBElement<DocVersion> createChargerDocVersionResponseChargerDocVersionResult(DocVersion value) {
        return new JAXBElement<>(_ChargerDocVersionResponseChargerDocVersionResult_QNAME, DocVersion.class, ChargerDocVersionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docVersion", scope = Telecharger.class)
    public JAXBElement<DocVersion> createTelechargerDocVersion(DocVersion value) {
        return new JAXBElement<>(_TelechargerDocVersion_QNAME, DocVersion.class, Telecharger.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = Telecharger.class)
    public JAXBElement<String> createTelechargerUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, Telecharger.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "TelechargerResult", scope = TelechargerResponse.class)
    public JAXBElement<String> createTelechargerResponseTelechargerResult(String value) {
        return new JAXBElement<>(_TelechargerResponseTelechargerResult_QNAME, String.class, TelechargerResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = TelechargerParId.class)
    public JAXBElement<String> createTelechargerParIdUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, TelechargerParId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "TelechargerParIdResult", scope = TelechargerParIdResponse.class)
    public JAXBElement<String> createTelechargerParIdResponseTelechargerParIdResult(String value) {
        return new JAXBElement<>(_TelechargerParIdResponseTelechargerParIdResult_QNAME, String.class, TelechargerParIdResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docInfo", scope = ModifierInfoDoc.class)
    public JAXBElement<DocInfo> createModifierInfoDocDocInfo(DocInfo value) {
        return new JAXBElement<>(_ModifierInfoDocDocInfo_QNAME, DocInfo.class, ModifierInfoDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ModifierInfoDoc.class)
    public JAXBElement<String> createModifierInfoDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ModifierInfoDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Mail }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Mail }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "mailAEnvoyer", scope = EnvoyerMailDoc.class)
    public JAXBElement<Mail> createEnvoyerMailDocMailAEnvoyer(Mail value) {
        return new JAXBElement<>(_EnvoyerMailDocMailAEnvoyer_QNAME, Mail.class, EnvoyerMailDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docVersion", scope = EnvoyerMailDoc.class)
    public JAXBElement<ArrayOfDocVersion> createEnvoyerMailDocDocVersion(ArrayOfDocVersion value) {
        return new JAXBElement<>(_TelechargerDocVersion_QNAME, ArrayOfDocVersion.class, EnvoyerMailDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = EnvoyerMailDoc.class)
    public JAXBElement<String> createEnvoyerMailDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, EnvoyerMailDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docInfo", scope = ReserverDoc.class)
    public JAXBElement<DocInfo> createReserverDocDocInfo(DocInfo value) {
        return new JAXBElement<>(_ModifierInfoDocDocInfo_QNAME, DocInfo.class, ReserverDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ReserverDoc.class)
    public JAXBElement<String> createReserverDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ReserverDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docInfo", scope = LibererDoc.class)
    public JAXBElement<DocInfo> createLibererDocDocInfo(DocInfo value) {
        return new JAXBElement<>(_ModifierInfoDocDocInfo_QNAME, DocInfo.class, LibererDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = LibererDoc.class)
    public JAXBElement<String> createLibererDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, LibererDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = LibererDocParId.class)
    public JAXBElement<String> createLibererDocParIdUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, LibererDocParId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ReserverDocParId.class)
    public JAXBElement<String> createReserverDocParIdUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ReserverDocParId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docInfo", scope = MajEtatDocInfo.class)
    public JAXBElement<DocInfo> createMajEtatDocInfoDocInfo(DocInfo value) {
        return new JAXBElement<>(_ModifierInfoDocDocInfo_QNAME, DocInfo.class, MajEtatDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = MajEtatDocInfo.class)
    public JAXBElement<String> createMajEtatDocInfoUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, MajEtatDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docInfo", scope = MajInfoEzgedDansAth.class)
    public JAXBElement<EzgedDocInfo> createMajInfoEzgedDansAthDocInfo(EzgedDocInfo value) {
        return new JAXBElement<>(_ModifierInfoDocDocInfo_QNAME, EzgedDocInfo.class, MajInfoEzgedDansAth.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "MajInfoEzgedDansAthResult", scope = MajInfoEzgedDansAthResponse.class)
    public JAXBElement<String> createMajInfoEzgedDansAthResponseMajInfoEzgedDansAthResult(String value) {
        return new JAXBElement<>(_MajInfoEzgedDansAthResponseMajInfoEzgedDansAthResult_QNAME, String.class, MajInfoEzgedDansAthResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EzgedDocInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "LireInfoEzgedDansAthResult", scope = LireInfoEzgedDansAthResponse.class)
    public JAXBElement<EzgedDocInfo> createLireInfoEzgedDansAthResponseLireInfoEzgedDansAthResult(EzgedDocInfo value) {
        return new JAXBElement<>(_LireInfoEzgedDansAthResponseLireInfoEzgedDansAthResult_QNAME, EzgedDocInfo.class, LireInfoEzgedDansAthResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = SupprimerDoc.class)
    public JAXBElement<String> createSupprimerDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, SupprimerDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = SupprimerDocVersion.class)
    public JAXBElement<String> createSupprimerDocVersionUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, SupprimerDocVersion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ValiderDoc.class)
    public JAXBElement<String> createValiderDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ValiderDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docSynchro", scope = AjoutDocVersionDansCourrierSynchro.class)
    public JAXBElement<DocSynchro> createAjoutDocVersionDansCourrierSynchroDocSynchro(DocSynchro value) {
        return new JAXBElement<>(_AjoutDocVersionDansCourrierSynchroDocSynchro_QNAME, DocSynchro.class, AjoutDocVersionDansCourrierSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = AjoutDocVersionDansCourrierSynchro.class)
    public JAXBElement<String> createAjoutDocVersionDansCourrierSynchroUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, AjoutDocVersionDansCourrierSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "docSynchro", scope = SupprimerDocVersionDansCourrierSynchro.class)
    public JAXBElement<DocSynchro> createSupprimerDocVersionDansCourrierSynchroDocSynchro(DocSynchro value) {
        return new JAXBElement<>(_AjoutDocVersionDansCourrierSynchroDocSynchro_QNAME, DocSynchro.class, SupprimerDocVersionDansCourrierSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = SupprimerDocVersionDansCourrierSynchro.class)
    public JAXBElement<String> createSupprimerDocVersionDansCourrierSynchroUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, SupprimerDocVersionDansCourrierSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = RecupererListeAjoutSynchro.class)
    public JAXBElement<String> createRecupererListeAjoutSynchroUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, RecupererListeAjoutSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "RecupererListeAjoutSynchroResult", scope = RecupererListeAjoutSynchroResponse.class)
    public JAXBElement<ArrayOfDocSynchro> createRecupererListeAjoutSynchroResponseRecupererListeAjoutSynchroResult(ArrayOfDocSynchro value) {
        return new JAXBElement<>(_RecupererListeAjoutSynchroResponseRecupererListeAjoutSynchroResult_QNAME, ArrayOfDocSynchro.class, RecupererListeAjoutSynchroResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = RecupererListeSuppressionSynchro.class)
    public JAXBElement<String> createRecupererListeSuppressionSynchroUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, RecupererListeSuppressionSynchro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "RecupererListeSuppressionSynchroResult", scope = RecupererListeSuppressionSynchroResponse.class)
    public JAXBElement<ArrayOfDocSynchro> createRecupererListeSuppressionSynchroResponseRecupererListeSuppressionSynchroResult(ArrayOfDocSynchro value) {
        return new JAXBElement<>(_RecupererListeSuppressionSynchroResponseRecupererListeSuppressionSynchroResult_QNAME, ArrayOfDocSynchro.class, RecupererListeSuppressionSynchroResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://mismo.fr/WSDocumentAth", name = "utilisateur", scope = ControlerDeplacerCheminDoc.class)
    public JAXBElement<String> createControlerDeplacerCheminDocUtilisateur(String value) {
        return new JAXBElement<>(_CreerDocInfoUtilisateur_QNAME, String.class, ControlerDeplacerCheminDoc.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Chemin", scope = DocSynchroD.class)
    public JAXBElement<String> createDocSynchroDChemin(String value) {
        return new JAXBElement<>(_DocSynchroDChemin_QNAME, String.class, DocSynchroD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "CodTypeC", scope = DocSynchroD.class)
    public JAXBElement<String> createDocSynchroDCodTypeC(String value) {
        return new JAXBElement<>(_DocSynchroDCodTypeC_QNAME, String.class, DocSynchroD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ChampPleinTexte", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDChampPleinTexte(String value) {
        return new JAXBElement<>(_EzgedDocInfoDChampPleinTexte_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Contexte", scope = EzgedDocInfoD.class)
    public JAXBElement<ArrayOfKeyValueOfstringstring> createEzgedDocInfoDContexte(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<>(_EzgedDocInfoDContexte_QNAME, ArrayOfKeyValueOfstringstring.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Extension", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDExtension(String value) {
        return new JAXBElement<>(_EzgedDocInfoDExtension_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ImageVignette", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDImageVignette(String value) {
        return new JAXBElement<>(_EzgedDocInfoDImageVignette_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "NomFichier", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDNomFichier(String value) {
        return new JAXBElement<>(_EzgedDocInfoDNomFichier_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Ripe", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDRipe(String value) {
        return new JAXBElement<>(_EzgedDocInfoDRipe_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Titre", scope = EzgedDocInfoD.class)
    public JAXBElement<String> createEzgedDocInfoDTitre(String value) {
        return new JAXBElement<>(_EzgedDocInfoDTitre_QNAME, String.class, EzgedDocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Body", scope = MailD.class)
    public JAXBElement<String> createMailDBody(String value) {
        return new JAXBElement<>(_MailDBody_QNAME, String.class, MailD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "From", scope = MailD.class)
    public JAXBElement<String> createMailDFrom(String value) {
        return new JAXBElement<>(_MailDFrom_QNAME, String.class, MailD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Subject", scope = MailD.class)
    public JAXBElement<String> createMailDSubject(String value) {
        return new JAXBElement<>(_MailDSubject_QNAME, String.class, MailD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "To", scope = MailD.class)
    public JAXBElement<String> createMailDTo(String value) {
        return new JAXBElement<>(_MailDTo_QNAME, String.class, MailD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "CreerPar", scope = DocVersionD.class)
    public JAXBElement<String> createDocVersionDCreerPar(String value) {
        return new JAXBElement<>(_DocVersionDCreerPar_QNAME, String.class, DocVersionD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Extension", scope = DocVersionD.class)
    public JAXBElement<String> createDocVersionDExtension(String value) {
        return new JAXBElement<>(_EzgedDocInfoDExtension_QNAME, String.class, DocVersionD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "NomFichier", scope = DocVersionD.class)
    public JAXBElement<String> createDocVersionDNomFichier(String value) {
        return new JAXBElement<>(_EzgedDocInfoDNomFichier_QNAME, String.class, DocVersionD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "NomFichierSysteme", scope = DocVersionD.class)
    public JAXBElement<String> createDocVersionDNomFichierSysteme(String value) {
        return new JAXBElement<>(_DocVersionDNomFichierSysteme_QNAME, String.class, DocVersionD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "NomFichierTelechargement", scope = DocVersionD.class)
    public JAXBElement<String> createDocVersionDNomFichierTelechargement(String value) {
        return new JAXBElement<>(_DocVersionDNomFichierTelechargement_QNAME, String.class, DocVersionD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "CreerPar", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDCreerPar(String value) {
        return new JAXBElement<>(_DocVersionDCreerPar_QNAME, String.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Description", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDDescription(String value) {
        return new JAXBElement<>(_DocInfoDDescription_QNAME, String.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Etat", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDEtat(String value) {
        return new JAXBElement<>(_DocInfoDEtat_QNAME, String.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "Libelle", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDLibelle(String value) {
        return new JAXBElement<>(_DocInfoDLibelle_QNAME, String.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ListeCleValeur", scope = DocInfoD.class)
    public JAXBElement<ArrayOfKeyValueOfstringstring> createDocInfoDListeCleValeur(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<>(_DocInfoDListeCleValeur_QNAME, ArrayOfKeyValueOfstringstring.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "ModifPar", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDModifPar(String value) {
        return new JAXBElement<>(_DocInfoDModifPar_QNAME, String.class, DocInfoD.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/S", name = "UtilisateurEnCoursModification", scope = DocInfoD.class)
    public JAXBElement<String> createDocInfoDUtilisateurEnCoursModification(String value) {
        return new JAXBElement<>(_DocInfoDUtilisateurEnCoursModification_QNAME, String.class, DocInfoD.class, value);
    }

}
