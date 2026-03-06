
package fr.mismo.wsdocument.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
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
 *         <element name="CreerDocInfoResult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "creerDocInfoResult"
})
@XmlRootElement(name = "CreerDocInfoResponse")
public class CreerDocInfoResponse {

    @XmlElement(name = "CreerDocInfoResult")
    protected Integer creerDocInfoResult;

    /**
     * Obtient la valeur de la propriété creerDocInfoResult.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCreerDocInfoResult() {
        return creerDocInfoResult;
    }

    /**
     * Définit la valeur de la propriété creerDocInfoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCreerDocInfoResult(Integer value) {
        this.creerDocInfoResult = value;
    }

}
