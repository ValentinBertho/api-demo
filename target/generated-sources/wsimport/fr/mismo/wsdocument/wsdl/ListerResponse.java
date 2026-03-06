
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
 *         <element name="ListerResult" type="{http://schemas.datacontract.org/2004/07/S}ArrayOfDocInfo" minOccurs="0"/>
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
    "listerResult"
})
@XmlRootElement(name = "ListerResponse")
public class ListerResponse {

    @XmlElementRef(name = "ListerResult", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfDocInfo> listerResult;

    /**
     * Obtient la valeur de la propriété listerResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     *     
     */
    public JAXBElement<ArrayOfDocInfo> getListerResult() {
        return listerResult;
    }

    /**
     * Définit la valeur de la propriété listerResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocInfo }{@code >}
     *     
     */
    public void setListerResult(JAXBElement<ArrayOfDocInfo> value) {
        this.listerResult = value;
    }

}
