
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
 *         <element name="ListerDocVersionResult" type="{http://schemas.datacontract.org/2004/07/S}ArrayOfDocVersion" minOccurs="0"/>
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
    "listerDocVersionResult"
})
@XmlRootElement(name = "ListerDocVersionResponse")
public class ListerDocVersionResponse {

    @XmlElementRef(name = "ListerDocVersionResult", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfDocVersion> listerDocVersionResult;

    /**
     * Obtient la valeur de la propriété listerDocVersionResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     *     
     */
    public JAXBElement<ArrayOfDocVersion> getListerDocVersionResult() {
        return listerDocVersionResult;
    }

    /**
     * Définit la valeur de la propriété listerDocVersionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocVersion }{@code >}
     *     
     */
    public void setListerDocVersionResult(JAXBElement<ArrayOfDocVersion> value) {
        this.listerDocVersionResult = value;
    }

}
