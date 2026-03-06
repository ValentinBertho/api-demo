
package fr.mismo.wsdocument.wsdl;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour EzgedDocInfoD complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="EzgedDocInfoD">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ChampPleinTexte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Contexte" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfKeyValueOfstringstring" minOccurs="0"/>
 *         <element name="Extension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="ImageVignette" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="NoDocAth" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NoDocEzged" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NoFichierEzged" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NoVersionAth" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NoVersionEzged" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="NomFichier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Ripe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Taille" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="Titre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EzgedDocInfoD", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "champPleinTexte",
    "contexte",
    "extension",
    "imageVignette",
    "noDocAth",
    "noDocEzged",
    "noFichierEzged",
    "noVersionAth",
    "noVersionEzged",
    "nomFichier",
    "ripe",
    "taille",
    "titre"
})
@XmlSeeAlso({
    EzgedDocInfo.class
})
public class EzgedDocInfoD {

    @XmlElementRef(name = "ChampPleinTexte", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> champPleinTexte;
    @XmlElementRef(name = "Contexte", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfKeyValueOfstringstring> contexte;
    @XmlElementRef(name = "Extension", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> extension;
    @XmlElementRef(name = "ImageVignette", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> imageVignette;
    @XmlElement(name = "NoDocAth")
    protected Integer noDocAth;
    @XmlElement(name = "NoDocEzged")
    protected Integer noDocEzged;
    @XmlElement(name = "NoFichierEzged")
    protected Integer noFichierEzged;
    @XmlElement(name = "NoVersionAth")
    protected Integer noVersionAth;
    @XmlElement(name = "NoVersionEzged")
    protected Integer noVersionEzged;
    @XmlElementRef(name = "NomFichier", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nomFichier;
    @XmlElementRef(name = "Ripe", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ripe;
    @XmlElement(name = "Taille")
    protected Integer taille;
    @XmlElementRef(name = "Titre", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> titre;

    /**
     * Obtient la valeur de la propriété champPleinTexte.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChampPleinTexte() {
        return champPleinTexte;
    }

    /**
     * Définit la valeur de la propriété champPleinTexte.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChampPleinTexte(JAXBElement<String> value) {
        this.champPleinTexte = value;
    }

    /**
     * Obtient la valeur de la propriété contexte.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfKeyValueOfstringstring> getContexte() {
        return contexte;
    }

    /**
     * Définit la valeur de la propriété contexte.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public void setContexte(JAXBElement<ArrayOfKeyValueOfstringstring> value) {
        this.contexte = value;
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
     * Obtient la valeur de la propriété imageVignette.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getImageVignette() {
        return imageVignette;
    }

    /**
     * Définit la valeur de la propriété imageVignette.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setImageVignette(JAXBElement<String> value) {
        this.imageVignette = value;
    }

    /**
     * Obtient la valeur de la propriété noDocAth.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoDocAth() {
        return noDocAth;
    }

    /**
     * Définit la valeur de la propriété noDocAth.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoDocAth(Integer value) {
        this.noDocAth = value;
    }

    /**
     * Obtient la valeur de la propriété noDocEzged.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoDocEzged() {
        return noDocEzged;
    }

    /**
     * Définit la valeur de la propriété noDocEzged.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoDocEzged(Integer value) {
        this.noDocEzged = value;
    }

    /**
     * Obtient la valeur de la propriété noFichierEzged.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoFichierEzged() {
        return noFichierEzged;
    }

    /**
     * Définit la valeur de la propriété noFichierEzged.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoFichierEzged(Integer value) {
        this.noFichierEzged = value;
    }

    /**
     * Obtient la valeur de la propriété noVersionAth.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoVersionAth() {
        return noVersionAth;
    }

    /**
     * Définit la valeur de la propriété noVersionAth.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoVersionAth(Integer value) {
        this.noVersionAth = value;
    }

    /**
     * Obtient la valeur de la propriété noVersionEzged.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoVersionEzged() {
        return noVersionEzged;
    }

    /**
     * Définit la valeur de la propriété noVersionEzged.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoVersionEzged(Integer value) {
        this.noVersionEzged = value;
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
     * Obtient la valeur de la propriété ripe.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRipe() {
        return ripe;
    }

    /**
     * Définit la valeur de la propriété ripe.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRipe(JAXBElement<String> value) {
        this.ripe = value;
    }

    /**
     * Obtient la valeur de la propriété taille.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTaille() {
        return taille;
    }

    /**
     * Définit la valeur de la propriété taille.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTaille(Integer value) {
        this.taille = value;
    }

    /**
     * Obtient la valeur de la propriété titre.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTitre() {
        return titre;
    }

    /**
     * Définit la valeur de la propriété titre.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTitre(JAXBElement<String> value) {
        this.titre = value;
    }

}
