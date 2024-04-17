package restaurant.management.client;

import javax.ws.rs.core.Response;

import restaurant.management.client.Restaurant;

public class Test {
	
	public static void main(String[] args) {
		RestClient client = new RestClient();
		
		Restaurant restaurant = new Restaurant("Pedra Alta","25 Rue Marbeuf, 75008 Paris","3");
		
		Response response = client.createXMLRestaurant(restaurant);
		if(response.getStatus() == 200 || response.getStatus() == 201) {
			System.out.println("Restaurant added");
		}
		else {
			System.out.println("reponse status" + response.getStatus());
		}
	}
	
	

}
