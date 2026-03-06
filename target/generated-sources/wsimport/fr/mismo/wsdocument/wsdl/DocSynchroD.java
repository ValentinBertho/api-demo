
package fr.mismo.wsdocument.wsdl;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour DocSynchroD complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="DocSynchroD">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Chemin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="CodTypeC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="IdDocInfo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="IdDocVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocSynchroD", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "chemin",
    "codTypeC",
    "idDocInfo",
    "idDocVersion"
})
@XmlSeeAlso({
    DocSynchro.class
})
public class DocSynchroD {

    @XmlElementRef(name = "Chemin", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> chemin;
    @XmlElementRef(name = "CodTypeC", namespace = "http://schemas.datacontract.org/2004/07/S", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codTypeC;
    @XmlElement(name = "IdDocInfo")
    protected Integer idDocInfo;
    @XmlElement(name = "IdDocVersion")
    protected Integer idDocVersion;

    /**
     * Obtient la valeur de la propriété chemin.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChemin() {
        return chemin;
    }

    /**
     * Définit la valeur de la propriété chemin.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChemin(JAXBElement<String> value) {
        this.chemin = value;
    }

    /**
     * Obtient la valeur de la propriété codTypeC.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodTypeC() {
        return codTypeC;
    }

    /**
     * Définit la valeur de la propriété codTypeC.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodTypeC(JAXBElement<String> value) {
        this.codTypeC = value;
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

}
