
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

@XmlRootElement(name = "addCategory", namespace = "http://soap.management.restaurant/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addCategory", namespace = "http://soap.management.restaurant/")

public class AddCategory {

    @XmlElement(name = "category")
    private restaurant.management.soap.Category category;

    public restaurant.management.soap.Category getCategory() {
        return this.category;
    }

    public void setCategory(restaurant.management.soap.Category newCategory)  {
        this.category = newCategory;
    }

}

