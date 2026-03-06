
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
 *         <element name="CreerDocVersionResult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "creerDocVersionResult"
})
@XmlRootElement(name = "CreerDocVersionResponse")
public class CreerDocVersionResponse {

    @XmlElement(name = "CreerDocVersionResult")
    protected Integer creerDocVersionResult;

    /**
     * Obtient la valeur de la propriété creerDocVersionResult.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCreerDocVersionResult() {
        return creerDocVersionResult;
    }

    /**
     * Définit la valeur de la propriété creerDocVersionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCreerDocVersionResult(Integer value) {
        this.creerDocVersionResult = value;
    }

}
