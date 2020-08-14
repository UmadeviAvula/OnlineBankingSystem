package dynamicproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime; 

@WebServlet("/trans")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static java.sql.Timestamp gettime(){
	LocalDateTime now = LocalDateTime.now();
	Timestamp timestamp = Timestamp.valueOf(now);
	return timestamp;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		          
		String name=request.getParameter("userName");  
		String acc=request.getParameter("faccNumber"); 
		String tacc=request.getParameter("taccNumber"); 
		int amount=Integer.parseInt(request.getParameter("amount"));  
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
		ps.setTimestamp(5, gettime());
		ps.executeQuery();
		out.print("Money Transfered Successfully"+"\n");
		//Update query
		ps=con.prepareStatement(  
				"update useraccounts set balance=balance-? where accountnumber=+?");
		ps.setInt(1,amount);
		ps.setString(2, acc);
		ps.executeQuery();
		ResultSet rs2 = stat.executeQuery(
				"select * from useraccounts where accountnumber="+acc);
		if(rs2.next()) {
			int bal=rs2.getInt("balance");
			out.print("Amount is debited from your account-"+acc+"\n");
		out.print("Available balance"+"--"+bal+"\n");}
		out.print("Last Five Transactions...");
		ResultSet rs1 = stat.executeQuery(
				"select * from usertransactions order by TransferTime DESC limit 5");

        while (rs1.next()) {    
			out.print(rs1.getString("name")  +"--From--"  + rs1.getString("faccountnumber")+"--To--"+rs1.getString("taccountnumber")+"--"+rs1.getInt("amount")+"--"+rs1.getTimestamp("TransferTime"));
		    
        }
				}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close();  	
	}

}
