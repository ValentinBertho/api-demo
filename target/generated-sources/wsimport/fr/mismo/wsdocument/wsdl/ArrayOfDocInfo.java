
package fr.mismo.wsdocument.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ArrayOfDocInfo complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfDocInfo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DocInfo" type="{http://schemas.datacontract.org/2004/07/S}DocInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocInfo", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "docInfo"
})
public class ArrayOfDocInfo {

    @XmlElement(name = "DocInfo", nillable = true)
    protected List<DocInfo> docInfo;

    /**
     * Gets the value of the docInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the docInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocInfo }
     * 
     * 
     * @return
     *     The value of the docInfo property.
     */
    public List<DocInfo> getDocInfo() {
        if (docInfo == null) {
            docInfo = new ArrayList<>();
        }
        return this.docInfo;
    }

}
