
package fr.mismo.wsdocument.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ArrayOfDocSynchro complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfDocSynchro">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DocSynchro" type="{http://schemas.datacontract.org/2004/07/S}DocSynchro" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocSynchro", namespace = "http://schemas.datacontract.org/2004/07/S", propOrder = {
    "docSynchro"
})
public class ArrayOfDocSynchro {

    @XmlElement(name = "DocSynchro", nillable = true)
    protected List<DocSynchro> docSynchro;

    /**
     * Gets the value of the docSynchro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the docSynchro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocSynchro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocSynchro }
     * 
     * 
     * @return
     *     The value of the docSynchro property.
     */
    public List<DocSynchro> getDocSynchro() {
        if (docSynchro == null) {
            docSynchro = new ArrayList<>();
        }
        return this.docSynchro;
    }

}
