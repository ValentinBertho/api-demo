
package fr.mismo.wsdocument.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour DocInfo complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="DocInfo">
 *   <complexContent>
 *     <extension base="{http://schemas.datacontract.org/2004/07/S}DocInfoD">
 *       <sequence>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocInfo", namespace = "http://schemas.datacontract.org/2004/07/S")
public class DocInfo
    extends DocInfoD
{


}
