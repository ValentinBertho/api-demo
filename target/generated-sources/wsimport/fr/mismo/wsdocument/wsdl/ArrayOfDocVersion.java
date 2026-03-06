
package fr.mismo.wsdocument.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ArrayOfDocVersion complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfDocVersion">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DocVersion" type="{http://schemas.datacontract.org/2004/07/S}DocVersion" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocVersion", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "docVersion"
})
public class ArrayOfDocVersion {

    @XmlElement(name = "DocVersion", nillable = true)
    protected List<DocVersion> docVersion;

    /**
     * Gets the value of the docVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the docVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocVersion }
     * 
     * 
     * @return
     *     The value of the docVersion property.
     */
    public List<DocVersion> getDocVersion() {
        if (docVersion == null) {
            docVersion = new ArrayList<>();
        }
        return this.docVersion;
    }

}
