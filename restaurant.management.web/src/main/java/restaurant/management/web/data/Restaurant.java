package restaurant.management.web.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Restaurant {
	private String id;
	private String name;
	private String adresse;
	private double Longitude;
	private double latitude;
	private String cateId;
	
	
	public Restaurant() {}
	
	public Restaurant(String name, String adresse, String cateId) {
		this.name = name;
		this.adresse = adresse;
		this.cateId = cateId;	
	}
	
	public Restaurant(String id, String name, String adresse, double Longitude, double latitude, String cateId){
		this.id = id;
		this.name = name;
		this.adresse = adresse;
		this.Longitude = Longitude;
		this.latitude = latitude;
		this.cateId = cateId;	
			
	}

	public Restaurant(String id, String name, String adresse, double Longitude, double latitude) {
		this.id = id;
		this.name = name;
		this.adresse = adresse;
		this.Longitude = Longitude;
		this.latitude = latitude;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ",name=" + name + ", adresse=" + adresse + ", Longitude =" + Longitude + ", latitude =" + latitude +", cateId =" + cateId + "]";
	}
}
