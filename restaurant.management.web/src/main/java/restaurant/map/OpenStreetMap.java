package restaurant.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @class OpenStreetMao
 * @brief This class allows users and developer to fetch lat and lon by Nominatim api
 * 
 * This class contains methods to perform just one operation : getting a given restaurant's lat and lon by processing through strings
 */
public class OpenStreetMap {
	
	public String getRestaurantInfo(String nameORadresse) {
		String url = "https://nominatim.openstreetmap.org/search?q=" + nameORadresse + "&format=json&limit=1";
		try {
			URL obj = new URL(url);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection)obj.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			String inputLine;
			StringBuilder reponse = new StringBuilder();
			while((inputLine = in.readLine()) != null) {
				reponse.append(inputLine);
			}
			in.close();
			return reponse.toString();
		}catch (Exception e) {
			System.out.println("error : " + e.getMessage());
		}
		return null;
	}
	
	public String[] getAll(String info) {
		
		String[] s = info.split("[,:]");
		for(int i = 0; i < s.length; i++){
		    System.out.println(s[i] + " " + i);
		}
		return s;
	}
	
	public double getLat(String info) {
		
		String[] s = info.split("[,:]");
		for (int i=0;i<s.length;i++) {
			if(s[i].equals("\"lat\"")) {		
				return Double.parseDouble(s[i+1].toString().replace("\"", ""));
								
			}
		}
		return 0;
	}
	public double getLon(String info) {
		
		String[] s = info.split("[,:]");
		
		for (int i=0;i<s.length;i++) {
			if(s[i].equals("\"lon\"")) {		
				return Double.parseDouble(s[i+1].toString().replace("\"", ""));				
			}
		}
		return 0;
	}
	
}
