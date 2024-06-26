//Category.java
package restaurant.management.soap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category", namespace = "http://restaurant.management.soap")
public class Category {
	String id;
	String name;

	public Category() {}

	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

}
