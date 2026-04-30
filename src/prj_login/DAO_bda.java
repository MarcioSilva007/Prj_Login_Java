package prj_login;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


/*
create database bancotesteuni9;

create table usuarios
(
id int primary_key,
nome varchar(50),
senha varchar(50)

);

INSERT INTO `bancotesteuni9`.`usuarios` (`id`, `nome`, `senha`) 
VALUES ('1', 'root', '12345');





*/
// VARIÁVEL OU OBJETO GLOBAL -são objetos ou variáveis que o PROGRAMA
// INTEIRO RECONHECE.
//Palmeira,2021:"O que for fora do main é global"
public class DAO_bda {

    /**
     * @return the ultimoid
     */
    public int getUltimoid() {
        return ultimoid;
    }

    /**
     * @param ultimoid the ultimoid to set
     */
    public void setUltimoid(int ultimoid) {
        this.ultimoid = ultimoid;
    }

    Connection con;
    Statement stmt;
    ResultSet rs;
    private int ultimoid=-1;
   
   
public void conexao() {
    try {
        // Define o driver do MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
                  
        // Conexão com o Docker (localhost na porta 3306)
      
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/meubanco?useSSL=false&allowPublicKeyRetrieval=true",
            "root", 
            "senha123"
        );
         
        stmt = con.createStatement();
        System.out.println("Conexão estabelecida com sucesso!");
        
    } catch(Exception e) { 
        System.out.println("Erro ao conectar no banco local: " + e);
    }
}
    
    public String pesquisa(String pesquisa, String campo)
    {
        String resultado="";
         try{
           
             
           //  if( campo.equals("Nome"))
              //   pesquisa= "'"+pesquisa+"'";
             
        //   String perguntasql="select * from usuarios "+
          //         " where "+campo+"="+pesquisa;
            //Statement stmtaux;
         
            
                
            String perguntasql="select * from usuarios "+
                   " where "+campo+"= ?";
         
            
                
         ResultSet rsaux;
         
        // stmtaux = con.createStatement();
        PreparedStatement stmtaux = con.prepareStatement(perguntasql);
        
        if(campo.equals("Nome"))
            stmtaux.setString(1, pesquisa);
        else
            stmtaux.setInt(1, Integer.parseInt(pesquisa) );
        
       
         rsaux = stmtaux.executeQuery();
       
             
                while (rsaux.next()) { 
              
                 resultado =
                    "ID:" + rsaux.getInt(1)+ " - " +
                      "Nome:" + rsaux.getString(2) + " - " +
                      "Senha:" + rsaux.getString(3);
                 
               
                 
                   // System.out.println("resultado:"+resultado+"\n"+perguntasql);
                    
                }
                
               
                rsaux.close();
                stmtaux.close();
                return resultado;     
        }catch(Exception e){
            System.out.println("Erro:"+e);
            return resultado;
        }
    
    
    
    }
    
    public void fechar()
    {
        try{
        rs.close();
        stmt.close();
        con.close();
        }catch(Exception e){
               System.out.println("Erro"+e);}
    
    }
    
    //FUNÇÃO PARA VER SE O LOGIN EXISTE CADASTRADO NO BANCO
    public boolean existe_O_login(String loginaux, String senhaaux)
    {
         
        try{
           //NESSA LINHA PERGUNTAMOS PARA O BANCO SE EXISTE
           // O LOGIN E A SENHA LÁ.
           
          // String perguntasql="select * from usuarios "+
            //       " where nome='"+loginaux+"' "+
              //     " AND "+
                //   " senha='"+senhaaux+"' " ;
                
                 String perguntasql="select * from usuarios "+
                   " where nome= ? " +
                   " AND "+
                   " senha= ? ;" ;
                 
                 
           
         PreparedStatement prepstmtaux;
         ResultSet rsaux;
         //stmtaux = con.createStatement();
         prepstmtaux = con.prepareStatement(perguntasql);
         prepstmtaux.setString(1,loginaux);
         prepstmtaux.setString(2,senhaaux);
         
         
         
         rsaux = prepstmtaux.executeQuery();
             
                while (rsaux.next()) { 
                rsaux.close();
                prepstmtaux.close();
                 
                 return true;
                }
                rsaux.close();
                prepstmtaux.close();
                return false;     
        }catch(Exception e){
            System.out.println("Erro:"+e);
            return false;
        }
        
    }
    
    public String getDados()
    {
        String resultado="";
        try{
           
               rs = stmt.executeQuery("select * from usuarios ");
             
                while (rs.next()) {                    
                  setUltimoid(rs.getInt(1));
                  resultado = resultado + 
                    "ID:" + getUltimoid() + " - " +
                      "Nome:" + rs.getString(2) + " - " +
                      "Senha:" + rs.getString(3)+ "\n";
                  
            }
                rs.close();               
               return resultado;
        }catch(Exception e){
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Erro:"+e);
        }
        
        return resultado;
    }
    
       public void editar(String idaux, String nomeaux,String senhaaux)
    {
         try {
            stmt.executeUpdate(
                    "Update usuarios SET "+
                           "nome='"+nomeaux+"', "
                          + "senha='"+senhaaux+"' "
                           +  " where id="+ idaux);
                  
                  PreparedStatement stmtaux=
                  con.prepareStatement("Update usuarios SET nome=?, senha=? where id=?");
                  //?
                  stmtaux.setString(1,nomeaux);
                  stmtaux.setString(2,senhaaux);
                  stmtaux.setInt(3,Integer.parseInt(idaux));
                  stmtaux.executeUpdate();
                    
                    
        } catch (Exception ex) {
            Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
       
    
    public DefaultListModel<String> getDadosModel()
    {
        DefaultListModel<String> resultado=
                new DefaultListModel<String>();
        
        
        try{
           
               rs = stmt.executeQuery("select * from usuarios ");
             
                while (rs.next()) {                    
                  setUltimoid(rs.getInt(1));
                  resultado.addElement( 
                    "ID:" + getUltimoid() + " - " +
                      "Nome:" + rs.getString(2) + " - " +
                    "Senha:" + "***" );// "Senha:" + rs.getString(3));
                  
            }
                rs.close();               
               return resultado;
        }catch(Exception e){
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Erro:"+e);
        }
        
        return resultado;
    }
      
    public void inserir(String idaux, String nomeaux,String senhaaux)
    {
         try {
          //  stmt.executeUpdate(
            //        "Insert into usuarios values("
              //              + idaux +","
                //                    + "'"+nomeaux+"',"
                  //                          + "'"+senhaaux+"');");
                  
          
            PreparedStatement stmtaux = 
                   con.prepareStatement("Insert into usuarios values(?,?,?);");
              
            stmtaux.setInt(1, Integer.parseInt(idaux));
            stmtaux.setString(2, nomeaux);
            stmtaux.setString(3, senhaaux);
                    
                  stmtaux.executeUpdate();
                  
        } catch (Exception ex) {
            Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
    
     public void excluir(String idaux)
    {
         try {
            stmt.executeUpdate(
                 "Delete from  usuarios where id="+ idaux );
                
                
                
                PreparedStatement stmtaux =
                        
                        con.prepareStatement("Delete from  usuarios where id=?");
                stmtaux.setInt(1,Integer.parseInt(idaux));
            
            //stmt.executeUpdate(
              //      "SET @aux = 0;UPDATE usuarios SET id = @aux:= @aux + 1;" );
        } catch (Exception ex) {
            Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
    public void LendoBanco() {
     
       
        try{
           
               
               
               rs = stmt.executeQuery("select * from usuarios ");
             
                while (rs.next()) {                    
                  System.out.println(
                    "ID:" + rs.getInt(1) + " - " +
                      "Nome:" + rs.getString(2) + " - " +
                      "Email:" + rs.getString(3)
                                              
                  );
            }
                              
        }catch(Exception e){
            System.out.println("Erro:"+e);
        }
        
    }
    
 // VARIAVEL LOCAL - dentro do BLOCO.
  // Palmeira,2021:"O que for dentro do main é local" 
    public void gravandoBanco()
    {
        String id="", nome="", email="";

id = JOptionPane.showInputDialog("Digite o ID");
nome = JOptionPane.showInputDialog("Digite o Nome");
email = JOptionPane.showInputDialog("Digite o Email");


        try {
            stmt.executeUpdate(
                    "Insert into usuarios values("
                            + id +","
                                    + "'"+nome+"',"
                                            + "'"+email+"');");
        } catch (Exception ex) {
            Logger.getLogger(DAO_bda.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
}
