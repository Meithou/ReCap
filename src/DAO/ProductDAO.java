package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO {
	static Connection currentCon = null;
    static ResultSet rs = null;  
	
    private static void connect(){
    	if(currentCon==null)
    	{
			try {
				currentCon = ConnectionManager.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public static ResultSet getfromcat(String cat) throws SQLException{
    	connect();
    	String searchQuery =
                "select * from produit where cat='"
                         + cat
                         +"'";
    	Statement stmt=currentCon.createStatement();
        ResultSet result = stmt.executeQuery(searchQuery);	  
    	return result;
    }
    public static ResultSet getfromname(String name) throws SQLException{
    	connect();
    	if(name==null)
    		return null;
    	String searchQuery =
                "select * from produit where nom like'%"
                         + name
                         +"%'";
    	Statement stmt=currentCon.createStatement();
        ResultSet result = stmt.executeQuery(searchQuery);	  
    	return result;
    
    }
    
    public static ResultSet getfromref(String ref) throws SQLException{
    	connect();
    	if(ref==null)
    		return null;
    	String searchQuery =
                "select * from produit where id_prod ='"
                         + ref
                         +"'";
    	Statement stmt=currentCon.createStatement();
        ResultSet result = stmt.executeQuery(searchQuery);	  
    	return result;
    	
    }
 
}
