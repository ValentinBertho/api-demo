
package fr.mismo.wsdocument.wsdl;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour DocVersionD complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="DocVersionD">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CreerLe" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="CreerPar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Extension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="IdDocInfo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="IdDocVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NbBloc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NomFichier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="NomFichierSysteme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="NomFichierTelechargement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Position" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="TailleFichier" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="Version" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocVersionD", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "creerLe",
    "creerPar",
    "extension",
    "idDocInfo",
    "idDocVersion",
    "nbBloc",
    "nomFichier",
    "nomFichierSysteme",
    "nomFichierTelechargement",
    "position",
    "tailleFichier",
    "version"
})
@XmlSeeAlso({
    DocVersion.class
})
public class DocVersionD {

    @XmlElement(name = "CreerLe")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creerLe;
    @XmlElementRef(name = "CreerPar", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> creerPar;
    @XmlElementRef(name = "Extension", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> extension;
    @XmlElement(name = "IdDocInfo")
    protected Integer idDocInfo;
    @XmlElement(name = "IdDocVersion")
    protected Integer idDocVersion;
    @XmlElement(name = "NbBloc")
    protected Integer nbBloc;
    @XmlElementRef(name = "NomFichier", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nomFichier;
    @XmlElementRef(name = "NomFichierSysteme", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nomFichierSysteme;
    @XmlElementRef(name = "NomFichierTelechargement", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nomFichierTelechargement;
    @XmlElement(name = "Position")
    protected Integer position;
    @XmlElement(name = "TailleFichier")
    protected Integer tailleFichier;
    @XmlElement(name = "Version")
    protected Integer version;

    /**
     * Obtient la valeur de la propriété creerLe.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreerLe() {
        return creerLe;
    }

    /**
     * Définit la valeur de la propriété creerLe.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreerLe(XMLGregorianCalendar value) {
        this.creerLe = value;
    }

    /**
     * Obtient la valeur de la propriété creerPar.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCreerPar() {
        return creerPar;
    }

    /**
     * Définit la valeur de la propriété creerPar.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCreerPar(JAXBElement<String> value) {
        this.creerPar = value;
    }

    /**
     * Obtient la valeur de la propriété extension.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExtension() {
        return extension;
    }

    /**
     * Définit la valeur de la propriété extension.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExtension(JAXBElement<String> value) {
        this.extension = value;
    }

    /**
     * Obtient la valeur de la propriété idDocInfo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdDocInfo() {
        return idDocInfo;
    }

    /**
     * Définit la valeur de la propriété idDocInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdDocInfo(Integer value) {
        this.idDocInfo = value;
    }

    /**
     * Obtient la valeur de la propriété idDocVersion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdDocVersion() {
        return idDocVersion;
    }

    /**
     * Définit la valeur de la propriété idDocVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdDocVersion(Integer value) {
        this.idDocVersion = value;
    }

    /**
     * Obtient la valeur de la propriété nbBloc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNbBloc() {
        return nbBloc;
    }

    /**
     * Définit la valeur de la propriété nbBloc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNbBloc(Integer value) {
        this.nbBloc = value;
    }

    /**
     * Obtient la valeur de la propriété nomFichier.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNomFichier() {
        return nomFichier;
    }

    /**
     * Définit la valeur de la propriété nomFichier.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNomFichier(JAXBElement<String> value) {
        this.nomFichier = value;
    }

    /**
     * Obtient la valeur de la propriété nomFichierSysteme.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNomFichierSysteme() {
        return nomFichierSysteme;
    }

    /**
     * Définit la valeur de la propriété nomFichierSysteme.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNomFichierSysteme(JAXBElement<String> value) {
        this.nomFichierSysteme = value;
    }

    /**
     * Obtient la valeur de la propriété nomFichierTelechargement.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNomFichierTelechargement() {
        return nomFichierTelechargement;
    }

    /**
     * Définit la valeur de la propriété nomFichierTelechargement.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNomFichierTelechargement(JAXBElement<String> value) {
        this.nomFichierTelechargement = value;
    }

    /**
     * Obtient la valeur de la propriété position.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Définit la valeur de la propriété position.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPosition(Integer value) {
        this.position = value;
    }

    /**
     * Obtient la valeur de la propriété tailleFichier.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTailleFichier() {
        return tailleFichier;
    }

    /**
     * Définit la valeur de la propriété tailleFichier.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTailleFichier(Integer value) {
        this.tailleFichier = value;
    }

    /**
     * Obtient la valeur de la propriété version.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Définit la valeur de la propriété version.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersion(Integer value) {
        this.version = value;
    }

}
