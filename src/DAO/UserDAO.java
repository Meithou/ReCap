package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
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
    
    public static boolean check_username(String user) throws SQLException{
    	connect();
    	String searchQuery =
                "select * from users where login='"
                         + user
                         +"'";
    	Statement stmt=currentCon.createStatement();
        ResultSet rs = stmt.executeQuery(searchQuery);	        
        boolean more = rs.next();
	       
        // if user exist return false;
        if (more)
        	return false;
        else
        	return true;
    }
    
    public static boolean check_mail(String mail) throws SQLException{
    	connect();
    	String searchQuery =
                "select * from users where email='"
                         + mail
                         +"'";
    	Statement stmt=currentCon.createStatement();
        ResultSet rs = stmt.executeQuery(searchQuery);	        
        boolean more = rs.next();
	       
        // if user exist return false;
        if (more)
        	return false;
        else
        	return true;
    }
    
    public static boolean check_availability(User bean) throws SQLException{
    	return(check_mail(bean.getMail())&&check_username(bean.getLogin()));
    }
    
    public static void register(User bean) throws SQLException{
    	connect();
    	String query = "INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStmt = currentCon.prepareStatement(query);
		preparedStmt.setString (1, bean.getFirstName());
		preparedStmt.setString (2, bean.getfamilyName());
		preparedStmt.setString (3, bean.getMail());
		preparedStmt.setString (4, bean.getLogin());
		preparedStmt.setString (5, bean.getPwd());
		preparedStmt.setString (6, bean.getBday_day());
		preparedStmt.setString (7, bean.getBday_month());
		preparedStmt.setString (8, bean.getBday_year());
		preparedStmt.setString (9, bean.getPhone());
		preparedStmt.setString (10, bean.getSalt());
		preparedStmt.execute();
    }
    
    public static User login(User bean) {
    	 Statement stmt = null;    
    		
         String username = bean.getLogin();    
         String password = bean.getPwd();   
	    
         
         String searchQuery =
               "select * from users where login='"
                        + username
                        +"'";
                     
         try 
         {
            //connect to DB 
            connect();
            stmt=currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);	        
            boolean more = rs.next();
   	       
            // if user does not exist set the isValid variable to false
            if (!more) 
            {
            	//Do something ?
               System.out.println("Sorry, you are not a registered user! Please sign up first");
               bean.setValid(false);
            } 
   	        
            //if user exists set the isValid variable to true
            else if (more) 
            {
            	MessageDigest md = null;
        		try {
        			md = MessageDigest.getInstance("MD5");
        			 
        		} catch (NoSuchAlgorithmException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        			
            	   String salt = rs.getString("salt");
            	   String mdp = password+salt;
            	   md.update(mdp.getBytes());
            	   searchQuery =
                           "select * from users where login='"
                                    + username
                                    +"'AND password='"
                                    +new String(md.digest())
                                    +"'"
                                    ;
            	   rs = stmt.executeQuery(searchQuery);	        
                   more = rs.next();
                   if(!more)         {
                	   System.out.println("Sorry, your password is wrong !!");
                       bean.setValid(false);
                   }
                   else{
            	      String firstName = rs.getString("prim_name");
               String lastName = rs.getString("fam_name");
   	     	
               System.out.println("Welcome " + firstName);
               bean.setFirstName(firstName);
               bean.setfamilyName(lastName);
               bean.setValid(true);
                   }
            }
         } 

         catch (Exception ex) 
         {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
         } 
         
    	return bean;
    }
}
