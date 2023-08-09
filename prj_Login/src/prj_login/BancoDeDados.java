package prj_login;

import java.sql.*;
import java.io.*;


/**
 create table usuarios 
(
id int not null PRIMARY KEY,
nome varchar(80),
senha varchar(80) );
 */



public class BancoDeDados {


    
     public static void main(String args[]) {
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = 
               DriverManager.getConnection(
               "jdbc:mysql://www.db4free.net:3306/farofeiro?" +  
               "user=gostosinho&password=1234567890"
                   );
                 

//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from usuarios ");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
