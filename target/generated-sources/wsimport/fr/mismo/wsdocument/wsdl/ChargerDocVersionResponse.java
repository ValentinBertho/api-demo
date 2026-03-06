
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
 *         <element name="ChargerDocVersionResult" type="{http://schemas.datacontract.org/2004/07/S}DocVersion" minOccurs="0"/>
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
    "chargerDocVersionResult"
})
@XmlRootElement(name = "ChargerDocVersionResponse")
public class ChargerDocVersionResponse {

    @XmlElementRef(name = "ChargerDocVersionResult", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<DocVersion> chargerDocVersionResult;

    /**
     * Obtient la valeur de la propriété chargerDocVersionResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     *     
     */
    public JAXBElement<DocVersion> getChargerDocVersionResult() {
        return chargerDocVersionResult;
    }

    /**
     * Définit la valeur de la propriété chargerDocVersionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DocVersion }{@code >}
     *     
     */
    public void setChargerDocVersionResult(JAXBElement<DocVersion> value) {
        this.chargerDocVersionResult = value;
    }

}
