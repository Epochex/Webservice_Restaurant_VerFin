
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

@XmlRootElement(name = "getRestaurantByNameResponse", namespace = "http://soap.management.restaurant/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRestaurantByNameResponse", namespace = "http://soap.management.restaurant/")

public class GetRestaurantByNameResponse {

    @XmlElement(name = "return")
    private restaurant.management.soap.Restaurant _return;

    public restaurant.management.soap.Restaurant getReturn() {
        return this._return;
    }

    public void setReturn(restaurant.management.soap.Restaurant new_return)  {
        this._return = new_return;
    }

}

