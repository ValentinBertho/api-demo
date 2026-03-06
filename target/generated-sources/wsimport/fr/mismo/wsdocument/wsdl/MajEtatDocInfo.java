
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
 *         <element name="docInfo" type="{http://schemas.datacontract.org/2004/07/S}DocInfo" minOccurs="0"/>
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
    "docInfo",
    "utilisateur"
})
@XmlRootElement(name = "MajEtatDocInfo")
public class MajEtatDocInfo {

    @XmlElementRef(name = "docInfo", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<DocInfo> docInfo;
    @XmlElementRef(name = "utilisateur", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<String> utilisateur;

    /**
     * Obtient la valeur de la propriété docInfo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     *     
     */
    public JAXBElement<DocInfo> getDocInfo() {
        return docInfo;
    }

    /**
     * Définit la valeur de la propriété docInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     *     
     */
    public void setDocInfo(JAXBElement<DocInfo> value) {
        this.docInfo = value;
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
