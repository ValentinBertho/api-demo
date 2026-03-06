
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
 *         <element name="ChargerDocInfoResult" type="{http://schemas.datacontract.org/2004/07/S}DocInfo" minOccurs="0"/>
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
    "chargerDocInfoResult"
})
@XmlRootElement(name = "ChargerDocInfoResponse")
public class ChargerDocInfoResponse {

    @XmlElementRef(name = "ChargerDocInfoResult", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<DocInfo> chargerDocInfoResult;

    /**
     * Obtient la valeur de la propriété chargerDocInfoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     *     
     */
    public JAXBElement<DocInfo> getChargerDocInfoResult() {
        return chargerDocInfoResult;
    }

    /**
     * Définit la valeur de la propriété chargerDocInfoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DocInfo }{@code >}
     *     
     */
    public void setChargerDocInfoResult(JAXBElement<DocInfo> value) {
        this.chargerDocInfoResult = value;
    }

}
