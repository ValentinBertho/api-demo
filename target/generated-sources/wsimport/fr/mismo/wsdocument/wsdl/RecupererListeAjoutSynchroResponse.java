
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
 *         <element name="RecupererListeAjoutSynchroResult" type="{http://schemas.datacontract.org/2004/07/S}ArrayOfDocSynchro" minOccurs="0"/>
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
    "recupererListeAjoutSynchroResult"
})
@XmlRootElement(name = "RecupererListeAjoutSynchroResponse")
public class RecupererListeAjoutSynchroResponse {

    @XmlElementRef(name = "RecupererListeAjoutSynchroResult", namespace = "http://mismo.fr/WSDocumentAth", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfDocSynchro> recupererListeAjoutSynchroResult;

    /**
     * Obtient la valeur de la propriété recupererListeAjoutSynchroResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     *     
     */
    public JAXBElement<ArrayOfDocSynchro> getRecupererListeAjoutSynchroResult() {
        return recupererListeAjoutSynchroResult;
    }

    /**
     * Définit la valeur de la propriété recupererListeAjoutSynchroResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDocSynchro }{@code >}
     *     
     */
    public void setRecupererListeAjoutSynchroResult(JAXBElement<ArrayOfDocSynchro> value) {
        this.recupererListeAjoutSynchroResult = value;
    }

}
