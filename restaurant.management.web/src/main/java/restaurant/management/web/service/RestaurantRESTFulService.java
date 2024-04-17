package restaurant.management.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import restaurant.management.web.data.Category;
import restaurant.management.web.data.Restaurant;

import restaurant.map.OpenStreetMap;


/**
 * @class RestaurantService
 * @brief This class represents the main logics of web service
 * 
 * This class contains methods to perform various operations, such as get restaurant by its name.
 */
public class RestaurantRESTFulService {
	
	private static Map<Integer, Restaurant> RESTAURANT_DATA = new HashMap<Integer, Restaurant>();

	public Restaurant addRestaurant(Restaurant r) {
		
		OpenStreetMap info = new OpenStreetMap();
		String name = r.getName().replace(" ","+");
		String infoMap = info.getRestaurantInfo(name);
		
		if(infoMap!=null) {
			r.setLatitude(info.getLat(infoMap));
			r.setLongitude(info.getLon(infoMap));
		}
		else {
			infoMap = info.getRestaurantInfo(r.getAdresse());
			r.setLatitude(info.getLat(infoMap));
			r.setLongitude(info.getLon(infoMap));
		}
		
		Connection connection = PostgresqlJDBC.getConnection();
		try {
			Statement statement = connection.createStatement();
			String query = "INSERT INTO rests(id_r, name_r, lon_r, lat_r, adr_r, id_c) VALUES(DEFAULT, '"
							+ r.getName() + "',"+ r.getLongitude() + "," + r.getLatitude() + ",'"
							+ r.getAdresse() + "','" + r.getCateId() + "');";
			int res = statement.executeUpdate(query);
			if(res == 1) {

				System.out.println("data was added");
				return r;
			}else {
				System.out.println("error : executeUpdate");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		return null;
	}


	public Restaurant getRestaurant_BD_Id(String id) {
		Restaurant r = null;
		Connection connection = PostgresqlJDBC.getConnection();
		try {
			Statement statement = connection.createStatement();
			String query = "select * from rests where id_r = " + id;
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()) {
				String name = rs.getString("name_r");
				double lon = rs.getDouble("lon_r");
				double lat = rs.getDouble("lat_r");
				String adr = rs.getString("adr_r");
				String id_c = rs.getString("id_c");

				r = new Restaurant(id, name, adr, lon, lat, id_c);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		return r;
	}

	public Restaurant getRestaurant_BD_Name(String name) {
		Restaurant r = null;
		Connection connection = PostgresqlJDBC.getConnection();
		try {
			Statement statement = connection.createStatement();
			String query = "select * from rests where name_r = " + "'"+name+"'";
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()) {
				String id_r = rs.getString("id_r");
				String name_r = rs.getString("name_r");
				double lon_r = rs.getDouble("lon_r");
				double lat_r = rs.getDouble("lat_r");
				String adr_r = rs.getString("adr_r");
				String id_c = rs.getString("id_c");
				r = new Restaurant(id_r, name_r, adr_r, lon_r, lat_r, id_c);
				return r;

			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		return r;
	}
	
	public Restaurant[] getAllRestaurants() {
		Connection connection = PostgresqlJDBC.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			String query = "select * from rests";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				String id_r = rs.getString("id_r");
				String name_r = rs.getString("name_r");
				double lon_r = rs.getDouble("lon_r");
				double lat_r = rs.getDouble("lat_r");
				String adr_r = rs.getString("adr_r");
				String id_c = rs.getString("id_c");
				Restaurant r = new Restaurant(id_r, name_r, adr_r, lon_r, lat_r, id_c);
				RESTAURANT_DATA.put(rs.getInt("id_r"), r);

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		
		Collection<Restaurant> rest = RESTAURANT_DATA.values();
		Restaurant[] restaurants = rest.toArray(new Restaurant[RESTAURANT_DATA.size()]);
		return restaurants;
	}
	
	
	
	public Collection<Restaurant> searchRestaurantsByCate(String cate) {
		PostgresqlJDBC.con = PostgresqlJDBC.getConnection();
		Collection<Restaurant> restaurants = new ArrayList<>();
		try {
			Statement statement = PostgresqlJDBC.con.createStatement();
			String query = "SELECT * FROM cates join Rests R on Cates.id_c = R.id_c WHERE name_c = '" + cate + "';";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				
				String id_r = rs.getString("id_r");
				String name_r = rs.getString("name_r");
				double lon_r = rs.getDouble("lon_r");
				double lat_r = rs.getDouble("lat_r");
				String adr_r = rs.getString("adr_r");
				String id_c = rs.getString("id_c");
				Restaurant restaurant = new Restaurant(id_r, name_r, adr_r, lon_r, lat_r, id_c);
				restaurants.add(restaurant);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		return restaurants;
	}
	
	public Category addCategory(Category category) {
		PostgresqlJDBC.con = PostgresqlJDBC.getConnection();
		try {
			Statement statement = PostgresqlJDBC.con.createStatement();
			String sql = "Insert Into cates(id_c, name_c) VALUES ('"+  category.getId() +"','"
						+category.getName()+ "');";
			int res = statement.executeUpdate(sql);
			if(res == 1) {
				System.out.println("data was added");
				return category;
			}
			else {
				System.out.println("error : Insert");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PostgresqlJDBC.releaseConnection();
		return null;
	}

}


/**
 * @class PostgresqlJDBC
 * @brief This class allows developers or users to get and release the resource more easily.
 * 
 * This class contains methods to perform 2 operations : getting DB connection and releasing connection.
 */
class PostgresqlJDBC{
	 
	 static Connection con = null;
	 
	 public static Connection getConnection() {
	  
	  try {
	          Class.forName("org.postgresql.Driver");
	            con = DriverManager.getConnection(
	                    "jdbc:postgresql://postgresql-espacedemo.alwaysdata.net:5432/espacedemo_restaurant", "espacedemo", "linjianke830");
	            System.out.println("连接已建立");
	            return con;
	     } catch (Exception e) {
	          e.printStackTrace();
	          System.err.println(e.getClass().getName()+": "+e.getMessage());
	          return null;
	     }
	 }
	 
	 public static void releaseConnection() {
	  try {
	   con.close();
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }
	  con = null;
	 }
	 
	}








