package dynamicproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  

@WebServlet("/reg")
public class Register extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
response.setContentType("text/html");  
PrintWriter out = response.getWriter();  
          
String name=request.getParameter("userName");  
String pass=request.getParameter("userPass");  
String email=request.getParameter("userEmail");  
String bank=request.getParameter("bankName");
String accNo=request.getParameter("accNumber");
String gend=request.getParameter("gender");
String phNo=request.getParameter("number");
String add=request.getParameter("address");
try{  
	Class.forName("org.mariadb.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/uma","root","");  
PreparedStatement ps=con.prepareStatement(  
"insert into userregistration values(?,?,?,?,?,?,?,?)");  
  
ps.setString(1,name);  
ps.setString(2,email);  
ps.setString(3,pass);  
ps.setString(4,bank);  
ps.setString(5,accNo);  
ps.setString(6,gend);  
ps.setString(7,phNo);  
ps.setString(8,add);  
 ps=con.prepareStatement(  
"insert into useraccounts values(?,?,?,?)");  
  
ps.setString(1,name);  
ps.setString(2,bank);  
ps.setString(3,accNo);
ps.setInt(4,50000);
         
int i=ps.executeUpdate();  
if(i>0)  
out.print("You are successfully registered...");  
RequestDispatcher rd=request.getRequestDispatcher("login.html");
rd.include(request,response);      
          
}catch (Exception e2) {System.out.println(e2);}  
          
out.close();  
}  
  
}  

