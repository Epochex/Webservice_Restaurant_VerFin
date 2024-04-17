////RestaurantSoapService.java
package restaurant.management.soap;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "RestaurantService", targetNamespace = "http://restaurant.management.soap")
public class RestaurantSoapService {

    private static final String JDBC_URL = "jdbc:postgresql://postgresql-espacedemo.alwaysdata.net:5432/espacedemo_restaurant";
    private static final String DB_USER = "espacedemo";
    private static final String DB_PASS = "linjianke830";

    @WebMethod(operationName = "addRestaurant")
    public Restaurant addRestaurant(@WebParam(name = "restaurant") Restaurant restaurant) {
        OpenStreetMap map = new OpenStreetMap();
        String infoMap = map.getRestaurantInfo(restaurant.getName().replace(" ", "+"));
        if(infoMap != null) {
            restaurant.setLatitude(map.getLat(infoMap));
            restaurant.setLongitude(map.getLon(infoMap));
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO rests(name_r, lon_r, lat_r, adr_r, id_c) VALUES ('" +
                         restaurant.getName() + "', " + restaurant.getLongitude() + ", " + restaurant.getLatitude() + 
                         ", '" + restaurant.getAdresse() + "', '" + restaurant.getCateId() + "') RETURNING id_r;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                restaurant.setId(String.valueOf(rs.getInt("id_r")));
            }
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "getRestaurantById")
    public Restaurant getRestaurantById(@WebParam(name = "id") String id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM rests WHERE id_r = " + id;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
                                      rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod(operationName = "getRestaurantByName")
    public Restaurant getRestaurantByName(@WebParam(name = "name") String name) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM rests WHERE name_r = '" + name + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
                                      rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod(operationName = "getAllRestaurants")
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM rests";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                restaurants.add(new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
                                               rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    @WebMethod(operationName = "getRestaurantsByCategory")
    public List<Restaurant> getRestaurantsByCategory(@WebParam(name = "category") String category) {
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM rests JOIN cates ON rests.id_c = cates.id_c WHERE cates.name_c = '" + category + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                restaurants.add(new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
                                               rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    @WebMethod(operationName = "addCategory")
    public Category addCategory(@WebParam(name = "category") Category category) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO cates(name_c) VALUES('" + category.getName() + "') RETURNING id_c;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                category.setId(String.valueOf(rs.getInt("id_c")));
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}




//package restaurant.management.soap;
//
//import javax.jws.WebService;
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.sql.SQLException;
//
//@WebService(serviceName = "RestaurantService")
//public class RestaurantService {
//
//    @WebMethod(operationName = "addRestaurant")
//    public Restaurant addRestaurant(@WebParam(name = "restaurant") Restaurant restaurant) {
//        // Assuming OpenStreetMap and PostgresqlJDBC classes are implemented similarly
//        OpenStreetMap info = new OpenStreetMap();
//        String name = restaurant.getName().replace(" ","+");
//        String infoMap = info.getRestaurantInfo(name);
//
//        if(infoMap != null) {
//            restaurant.setLatitude(info.getLat(infoMap));
//            restaurant.setLongitude(info.getLon(infoMap));
//        } else {
//            infoMap = info.getRestaurantInfo(restaurant.getAdresse());
//            restaurant.setLatitude(info.getLat(infoMap));
//            restaurant.setLongitude(info.getLon(infoMap));
//        }
//
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "INSERT INTO rests(name_r, lon_r, lat_r, adr_r, id_c) VALUES('"
//                            + restaurant.getName() + "'," + restaurant.getLongitude() + "," 
//                            + restaurant.getLatitude() + ",'" + restaurant.getAdresse() 
//                            + "','" + restaurant.getCateId() + "') RETURNING id_r;";
//            ResultSet rs = statement.executeQuery(query);
//            if (rs.next()) {
//                restaurant.setId(Integer.toString(rs.getInt(1)));
//                return restaurant;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return null;
//    }
//
//    @WebMethod(operationName = "getRestaurantById")
//    public Restaurant getRestaurantById(@WebParam(name = "id") String id) {
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM rests WHERE id_r = " + id;
//            ResultSet rs = statement.executeQuery(query);
//            if(rs.next()) {
//                return new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
//                                      rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return null;
//    }
//
//    @WebMethod(operationName = "getRestaurantByName")
//    public Restaurant getRestaurantByName(@WebParam(name = "name") String name) {
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM rests WHERE name_r = '" + name + "'";
//            ResultSet rs = statement.executeQuery(query);
//            if(rs.next()) {
//                return new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
//                                      rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return null;
//    }
//
//    @WebMethod(operationName = "getRestaurantsByCategory")
//    public List<Restaurant> getRestaurantsByCategory(@WebParam(name = "category") String category) {
//        List<Restaurant> restaurants = new ArrayList<>();
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM rests R JOIN cates C ON R.id_c = C.id_c WHERE C.name_c = '" + category + "'";
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()) {
//                restaurants.add(new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
//                                               rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return restaurants;
//    }
//
//    @WebMethod(operationName = "addCategory")
//    public Category addCategory(@WebParam(name = "category") Category category) {
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "INSERT INTO cates(name_c) VALUES('" + category.getName() + "') RETURNING id_c;";
//            ResultSet rs = statement.executeQuery(query);
//            if (rs.next()) {
//                category.setId(Integer.toString(rs.getInt(1)));
//                return category;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return null;
//    }
//
//    @WebMethod(operationName = "getAllRestaurants")
//    public List<Restaurant> getAllRestaurants() {
//        List<Restaurant> restaurants = new ArrayList<>();
//        Connection connection = PostgresqlJDBC.getConnection();
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM rests";
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()) {
//                restaurants.add(new Restaurant(rs.getString("id_r"), rs.getString("name_r"), rs.getString("adr_r"),
//                                               rs.getDouble("lon_r"), rs.getDouble("lat_r"), rs.getString("id_c")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            PostgresqlJDBC.releaseConnection(connection);
//        }
//        return restaurants;
//    }
//}
