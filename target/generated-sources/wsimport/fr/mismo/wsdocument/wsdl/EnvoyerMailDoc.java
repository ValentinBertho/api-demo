
package fr.mismo.wsdocument.wsdl;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="mailAEnvoyer" type="{http://schemas.datacontract.org/2004/07/S}Mail" minOccurs="0"/>
 *         <element name="docVersion" type="{http://schemas.datacontract.org/2004/07/S}ArrayOfDocVersion" minOccurs="0"/>
 *         <element name="utilisateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mailAEnvoyer",
    "docVersion",
    "utilisateur"
})
@XmlRootElement(name = "EnvoyerMailDoc")
public class EnvoyerMailDoc {

    @XmlElementRef(name = "mailAEnvoyer", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<Mail> mailAEnvoyer;
    @XmlElementRef(name = "docVersion", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfDocVersion> docVersion;
    @XmlElementRef(name = "utilisateur", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<String> utilisateur;

    /**
     * Obtient la valeur de la propriété mailAEnvoyer.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Mail }{@code >}
     *     
     */
    public JAXBElement<Mail> getMailAEnvoyer() {
        return mailAEnvoyer;
    }

    /**
     * Définit la valeur de la propriété mailAEnvoyer.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Mail }{@code >}
     *     
     */
    public void setMailAEnvoyer(JAXBElement<Mail> value) {
        this.mailAEnvoyer = value;
    }

    /**
     * Obtient la valeur de la propriété docVersion.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     *     
     */
    public JAXBElement<ArrayOfDocVersion> getDocVersion() {
        return docVersion;
    }

    /**
     * Définit la valeur de la propriété docVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     *     
     */
    public void setDocVersion(JAXBElement<ArrayOfDocVersion> value) {
        this.docVersion = value;
    }

    /**
     * Obtient la valeur de la propriété utilisateur.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit la valeur de la propriété utilisateur.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUtilisateur(JAXBElement<String> value) {
        this.utilisateur = value;
    }

}
