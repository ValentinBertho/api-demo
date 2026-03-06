
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
 * <p>Classe Java pour DocInfoD complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="DocInfoD">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CreerLe" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="CreerPar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Etat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="IdDerniereVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="IdDocInfo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="Libelle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="ListeCleValeur" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfKeyValueOfstringstring" minOccurs="0"/>
 *         <element name="ModifLe" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="ModifPar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="UtilisateurEnCoursModification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Valide" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocInfoD", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "creerLe",
    "creerPar",
    "description",
    "etat",
    "idDerniereVersion",
    "idDocInfo",
    "libelle",
    "listeCleValeur",
    "modifLe",
    "modifPar",
    "utilisateurEnCoursModification",
    "valide"
})
@XmlSeeAlso({
    DocInfo.class
})
public class DocInfoD {

    @XmlElement(name = "CreerLe")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creerLe;
    @XmlElementRef(name = "CreerPar", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> creerPar;
    @XmlElementRef(name = "Description", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> description;
    @XmlElementRef(name = "Etat", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> etat;
    @XmlElement(name = "IdDerniereVersion")
    protected Integer idDerniereVersion;
    @XmlElement(name = "IdDocInfo")
    protected Integer idDocInfo;
    @XmlElementRef(name = "Libelle", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> libelle;
    @XmlElementRef(name = "ListeCleValeur", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfKeyValueOfstringstring> listeCleValeur;
    @XmlElement(name = "ModifLe")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifLe;
    @XmlElementRef(name = "ModifPar", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> modifPar;
    @XmlElementRef(name = "UtilisateurEnCoursModification", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> utilisateurEnCoursModification;
    @XmlElement(name = "Valide")
    protected Boolean valide;

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
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescription(JAXBElement<String> value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriété etat.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEtat() {
        return etat;
    }

    /**
     * Définit la valeur de la propriété etat.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEtat(JAXBElement<String> value) {
        this.etat = value;
    }

    /**
     * Obtient la valeur de la propriété idDerniereVersion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdDerniereVersion() {
        return idDerniereVersion;
    }

    /**
     * Définit la valeur de la propriété idDerniereVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdDerniereVersion(Integer value) {
        this.idDerniereVersion = value;
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
     * Obtient la valeur de la propriété libelle.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLibelle() {
        return libelle;
    }

    /**
     * Définit la valeur de la propriété libelle.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLibelle(JAXBElement<String> value) {
        this.libelle = value;
    }

    /**
     * Obtient la valeur de la propriété listeCleValeur.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfKeyValueOfstringstring> getListeCleValeur() {
        return listeCleValeur;
    }

    /**
     * Définit la valeur de la propriété listeCleValeur.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public void setListeCleValeur(JAXBElement<ArrayOfKeyValueOfstringstring> value) {
        this.listeCleValeur = value;
    }

    /**
     * Obtient la valeur de la propriété modifLe.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifLe() {
        return modifLe;
    }

    /**
     * Définit la valeur de la propriété modifLe.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifLe(XMLGregorianCalendar value) {
        this.modifLe = value;
    }

    /**
     * Obtient la valeur de la propriété modifPar.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getModifPar() {
        return modifPar;
    }

    /**
     * Définit la valeur de la propriété modifPar.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setModifPar(JAXBElement<String> value) {
        this.modifPar = value;
    }

    /**
     * Obtient la valeur de la propriété utilisateurEnCoursModification.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUtilisateurEnCoursModification() {
        return utilisateurEnCoursModification;
    }

    /**
     * Définit la valeur de la propriété utilisateurEnCoursModification.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUtilisateurEnCoursModification(JAXBElement<String> value) {
        this.utilisateurEnCoursModification = value;
    }

    /**
     * Obtient la valeur de la propriété valide.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValide() {
        return valide;
    }

    /**
     * Définit la valeur de la propriété valide.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValide(Boolean value) {
        this.valide = value;
    }

}
