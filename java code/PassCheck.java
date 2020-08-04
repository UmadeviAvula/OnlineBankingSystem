package dynamicproject;

import java.sql.*;  

public class PassCheck {  
//static	String acn;
public static String  validate(String name,String pass){  
String status="null";  
try{  
	Class.forName("org.mariadb.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/uma","root",""); 
      
PreparedStatement ps=con.prepareStatement(  
"select * from userregistration where name=? and password=?");  
ps.setString(1,name);  
ps.setString(2,pass);  
      
ResultSet rs=ps.executeQuery(); 
rs.next(); 
status=rs.getString("accountnumber"); 
 
}catch(Exception e){System.out.println(e);}  
return status;  
}  
}  