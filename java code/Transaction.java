package dynamicproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/trans")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		          
		String name=request.getParameter("userName");  
		String acc=request.getParameter("faccNumber"); 
		String tacc=request.getParameter("taccNumber"); 
		int amount=Integer.parseInt(request.getParameter("amount"));  
		//Date date=new Date();
		
		try{  
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/uma","root","");  
		PreparedStatement ps=con.prepareStatement(  
		"insert into usertransactions values(?,?,?,?,?)");  
		 Statement stat=con.createStatement(); 
		ps.setString(1,name);  
		ps.setString(2,acc);  
		ps.setString(3,tacc);  
		ps.setInt(4,amount);  
		ps.setDate(5,getCurrentDate() );
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			System.out.println("Money Transfered Successfully");
		Login l=new Login();
		//String a;
		//a=l.ac;
		ResultSet rs2 = stat.executeQuery(
				"select * from useraccounts where accountnumber="+acc);
		rs2.next();
		int bal=rs2.getInt("balance");
		bal=bal-amount;
		ResultSet rs3 = stat.executeQuery(
				"update useraccounts set balance=balance-amount where accountnumber="+acc);
		if(rs3.next())
			System.out.println("Amount is debited from your account-"+acc);
		System.out.println("Available balance"+"--"+bal);
		System.out.println("Last Five Transactions...");
		ResultSet rs1 = stat.executeQuery(
				"select * from usertransactions order by TransferTime ASC");
		int i=1;
        while (rs1.next()) {    
			System.out.println(rs1.getString("name")  +"--From--"  + rs1.getString("faccountnumber")+"--To--"+rs1.getString("taccountnumber")+"--"+rs1.getInt("amount")+"--"+rs1.getDate("TransferTime"));
		    if(i==5)
		    	break;
			i++;
		//rs1.next();
        }
				}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close();  	
	}

}
