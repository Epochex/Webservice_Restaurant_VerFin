
package restaurant.management.soap.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.5.8
 * Sun Apr 14 15:01:32 CEST 2024
 * Generated source version: 3.5.8
 */

@XmlRootElement(name = "getRestaurantByName", namespace = "http://soap.management.restaurant/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRestaurantByName", namespace = "http://soap.management.restaurant/")

public class GetRestaurantByName {

    @XmlElement(name = "name")
    private java.lang.String name;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String newName)  {
        this.name = newName;
    }

}

