
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.sound.midi.MidiUnavailableException;

public class conex{

 static Comando hn = new Comando();
 static boolean finalizado=false;
 
 static String basedatos="db_"+Comando.miTime().replace(':', 'x').replace('-', 'x').replace(" ", "_").replace("x", "")+".db";
 
 public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Conexiones Activas Conytec");
		
		System.out.println("Para finalizar precione q + ENTER");

		//inicio de la captura
		hn.start();
		
		
		
		//para salir del codigo
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String q = "";
		do {
			  try {
				q = br.readLine();
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
			  if(q.equals("q")){
				  //finalizando hilo
				  hn.fin();
				  
				  
				  //mostrar resumen
				  
				  //***Resumen 1***
				  
				  String [][]result= selectSql("select *,count(*) from conexiones1 group by ipExterno, fecha order by fecha", "");
				  //String [][]result= selectSql("select *,count(*) from conexiones1 group by ipExterno ", "");
				  
				  /*System.out.println("\n\nEste resumen muestra la ip agupada por ip y por fecha \n");
				  
	               for(int n=0;n<result.length;n++){
	          		  for(int n2=1;n2<result[0].length;n2++){
	          			  
	          			  System.out.print( result[n][n2] +"\t");  
	          		  }  
	          		System.out.println();
	          		}*/
	                
	              //***Resumen 2***
	               
	                String [][]result2= selectSql("select *,count(*) from conexiones1 group by ipExterno order by fecha", "");
					  //String [][]result= selectSql("select *,count(*) from conexiones1 group by ipExterno ", "");
					
	                //System.out.println("\nEste resumen muestra la cantidad de ips que aparcen, las que son: "+result2.length+"\n\n");
	                
		                /*for(int n=0;n<result2.length;n++){
		          		  for(int n2=1;n2<result2[0].length;n2++){
		          			  
		          			  System.out.print( result2[n][n2] +"\t");  
		          		  }  
		          		System.out.println();
		          		}*/
	                
		              //***Resumen 3***
						
		                //System.out.println("\n Intento de logica XD\n\n");
		                //System.out.println("\n Rwesumen 3\n\n");
		                
		                String resumen="";
		                String info="";
		                
			            for(int n=0;n<result2.length;n++){
			          			  
			          		String [][]result3= selectSql("select *,count(*) from conexiones1 where ipExterno='"+result2[n][4]+"'  group by estado,fecha  order by fecha", "");
					        
			          		
					        
			          		String oldEstado = "";
			          		int cantidadCambios=0;
			          		int cambioConeDescone=0;
			          		String oldIp="";
			          		
			          		for(int n3=0;n3<result3.length;n3++){
			          					
			          				String newEstado = result3[n3][6];
			          				//System.out.println("ip: "+result2[n][4]+"--> estado: "+newEstado);
			          				if(oldEstado.equals(newEstado)){
			          					oldEstado = newEstado;
			          				}else{
			          					cantidadCambios++;
			          					
			          					if(newEstado.equals("ESTABLISHED")){
				          					cambioConeDescone++;
				          					char c='"';
				          					String newIp=result2[n][4];
				          					if(!oldIp.equals(newIp)){
				          						//Comando.ejecuarCMD("ping -a "+newIp+" -n 1 | find /i "+c+"ping"+c, "");
				          						oldIp=newIp;
				          					}
				          				}
			          					
			          					oldEstado = newEstado;
			          				}
			          				
			          				
			          				
			          			
					        }
			          		resumen+="<tr>"
			          				+ "<td>"+result2[n][2]+"</td><td>"+result2[n][3]+"</td>"
			          				+ "<td>"+result2[n][4]+"</td><td>"+result2[n][5]+"</td>"
			          				+ "<td>"+result2[n][6]+"</td></tr>"
			          				+ "<td>"+cambioConeDescone+"</td></tr>";
			          		
			          		//System.out.println("cantidad de cambios de estado: "+cantidadCambios);
			          		//System.out.println("cantidad de cambios pero solo si esta conectado: "+cambioConeDescone);
			          		System.out.print(".");
			          		//System.out.println(info);
			          		//System.out.println();
			          	}    
			            String encabezado=
			            "<h2>INFORME DE CONEXIONES</h2>"
	         			+"<br>"
	         			+"<table border='1'>"
	         			+"<tr>"
	         			+ "<td><strong>IP Local</strong></td>"
	         			+ "<td><strong>Port Local</strong></td>"
	         			+ "<td><strong>IP Externa</strong></td>"
	         			+ "<td><strong>Port Externo</strong></td>"
	         			+ "<td><strong>Estado</strong></td>"
	         			+ "<td><strong>Cantidad de conexiones por ip</strong></td>"
	         			+"</tr>";
			            generarPdf("informe_"+Comando.miTime().replace(':', 'x').replace('-', 'x').replace(" ", "_").replace("x", ""), encabezado+resumen);
			            
			            //conecionsqlserver();
			            
			            
			            
			            
		                
	               //saliendo del programa
				  System.exit(0);
			  }else{
				  System.out.println("No se realizo ninguna acción\npara salir use q + Enter");
			  }
			} while(!q.equals("q"));
		//fin del salir del codigo		
 
 }//fin main
 
 
 public static void carpetasCompartidas(){
	 
	 try{
		 
		 
		conex.ejecutarSql("CREATE TABLE IF NOT EXISTS share (ids INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "nombre TEXT, recurso TEXT,descripcion TEXT)","");

		 
		 conex.ejecutarSql("CREATE TABLE IF NOT EXISTS share_session (ids INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "ip TEXT, usuario TEXT)","");
		 
		 conex.ejecutarSql("CREATE TABLE IF NOT EXISTS share_file (ids INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "ruta TEXT, usuario TEXT)","");
		 
		 File miDir = new File (".");
		 String ruta= miDir.getCanonicalPath();
		 System.out.println(ruta);
	     Comando.ejecuarCMD("cd "+ruta, "cd "+ruta);
	     System.out.println( Comando.ejecuarCMD("cscript //Nologo "+ruta +"\\scriptDatos\\share.vbs", ""));
		 String share=muestraContenido(ruta+"\\share.txt");
		 
			 
			 String []vShare=share.split("@@");
			 for(int m=0; m<vShare.length;m++){
				 String []vvShare=vShare[m].split(",");
				 	String sql="select descripcion from share where nombre='"+vvShare[0]+"' AND descripcion='"+vvShare[2]+"'";
					
					if(!tieneDatosSelectSql(sql)){
						System.out.println("i1");
						conex.ejecutarSql("", "insert into share (nombre, recurso, descripcion) values('"+vvShare[0]+"','"+vvShare[1]+"','"+vvShare[2]+"')");
						//System.out.println("insert into share (nombre, recurso, descripcion) values('"+vvShare[0]+"','"+vvShare[1]+"','"+vvShare[2]+"')");
					}else{
						System.out.println("-1");
					}
					 
				
			 }//fin for
		 
			 
	 	}catch(Exception x){
	 		System.out.println("Error controlado: "+x.getMessage());
	 	}
		
	 	try{
		 System.out.println("--------------------------xxAAAxx------------------------------");
		 char comilla='"';
		 System.out.println("Net session | find /i "+comilla+"\\\\"+comilla);
		 String ejec=Comando.ejecuarCMDforSplit("Net session | find /i "+comilla+"\\\\"+comilla, "");
		 
		 
			 if(!ejec.equals("null")){
				 String []vEjec=ejec.split("zzz");
				 for(int i=0; i<vEjec.length;i++){
					 String []vvEjec=vEjec[i].split(",");
					 /*for(int j=0; j<vvEjec.length;j++){
						 System.out.println( vvEjec[j]);
					 }*/
					 String sql2="select ip from share_session where ip='"+vvEjec[0]+"' AND usuario='"+vvEjec[1]+"'";
					 System.out.println(sql2);
						
						if(!tieneDatosSelectSql(sql2)){
							conex.ejecutarSql("", "insert into share_session (ip, usuario) values('"+vvEjec[0]+"','"+vvEjec[1]+"')");
							System.out.println("i2");
						}else{
							System.out.println("-2");
						}
					 //System.out.println("insert into share_session (ip, usuario) values('"+vvEjec[0]+"','"+vvEjec[1]+"')");
					 
					 String ejec2=Comando.ejecuarCMDforSplit("net file | find /i "+comilla+vvEjec[1]+comilla,"");
					 String []vEjec2=ejec2.split("zzz");
					 for(int g=0; g<vEjec2.length;g++){
						 String []vvEjec2=vEjec2[g].split(",");
						 
						 String sql3="select ruta from share_file where ruta='"+vvEjec2[1]+"' AND usuario='"+vvEjec2[2]+"'";
						 System.out.println(sql3);
							
							if(!tieneDatosSelectSql(sql3)){
								conex.ejecutarSql("", "insert into share_file (ruta, usuario) values('"+vvEjec2[1]+"','"+vvEjec2[2]+"')");
						 		System.out.println("i3");
							}else{
								System.out.println("-3");
							}
						 	
					 }
				 }
			}else{
				System.out.println("sin conexiones activas");
			}
				 
	 }catch(Exception x){
		 System.out.println("Error: "+x.getMessage());
	 }
	 
 }
 
 public static boolean tieneDatosSelectSql(String comando){
	 
	 //Conexion
	 boolean datos=false;
	 Connection con = BaseDeDatos.conectarA(basedatos);
	 
		
	 try {
		 Statement stmt = con.createStatement();
		 
			 stmt.setQueryTimeout(30);
			 ResultSet rs = stmt.executeQuery(comando);
		
			 //contando cantidad de Filas
			 int nFila=0;
			 while(rs.next()){
				 nFila++;
			 }
			 
			 if(nFila>0){
				 datos=true;
			 }
			 
	 	}catch(SQLException sql){
	 		System.out.println("Error selectSql(): "+sql.getMessage());
	 	}  

	 return datos;
}

 
 public static String muestraContenido(String archivo) {
	 BufferedReader b=null;
	 String contenido="";
	 try{
	 String cadena;
     FileReader f = new FileReader(archivo);
     b = new BufferedReader(f);
     
     while((cadena = b.readLine())!=null) {
         contenido=contenido+cadena+"@@";
     }
     b.close();
     }catch(Exception ex){
    	 
    	System.out.println(""+ex.getMessage());
     }
	return contenido;
}
 
 public static void conecionsqlserver(){
	 
	 String connectionUrl = "jdbc:sqlserver://192.168.0.100:1433;" +
	         "databaseName=master;user=sa; password=Admin1987;";      
	 // Declaramos los sioguientes objetos
	 Connection con = null;
	 Statement stmtR = null;
	 ResultSet rsR = null;
	 
	 Statement stmtD = null;
	 ResultSet rsD = null;
	 try {
	     //Establecemos la conexión
	     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	     con = DriverManager.getConnection(connectionUrl);
	     // Create and execute an SQL statement that returns some data.
	     if(con==null){System.out.println("cagamos");}else{System.out.println("vamos bien");}
	     
	     String SQLResumen = 
	    		 "SELECT server, usuario,COUNT(*) as conexiones "+ 
	    		 "FROM Registros "+
	    		 "group by usuario,server "+
	    		 "order by server;";
	     
	     stmtR = con.createStatement();
	     rsR = stmtR.executeQuery(SQLResumen);
	     
	     
	     String resumenResumen = "";
	     while(rsR.next())
	        {    // hasta fin de archivo
	    	 	resumenResumen = resumenResumen+ "<tr><td>"+rsR.getString(1)+"</td><td>"+rsR.getString(2)+"</td><td>"+rsR.getString(3)+"</td></tr>";	            
	        }
	     
	     String SQLDetalle = "SELECT * FROM Registros";
	     
	     stmtD = con.createStatement();
	     rsD = stmtD.executeQuery(SQLDetalle);
	     
	     String resumenDetalle = "";
	     while(rsD.next())
	        {    // hasta fin de archivo
	    	 	resumenDetalle = resumenDetalle + "<tr><td>"+rsD.getString(2)+"</td><td>"+rsD.getString(3)+"</td><td>"+rsD.getString(4)+"</td><td>"+rsD.getString(5)+"</td><td>"+rsD.getString(6)+"</td></tr>";	            
	        }
	     
	     
	     
	     String Titulo="<h2>INFORME DE CONEXIONES SQL</h2><br>";
	     
	     String encabezadoDetalle=      
		        "<h3>Sql detalle</h3>"
      			+"<table border='1'>"
      			+"<tr>"
      			//+ "<td><strong>id</strong></td>"
      			+ "<td><strong>Usuario</strong></td>"
      			+ "<td><strong>Fecha Resgistro</strong></td>"
      			+ "<td><strong>SPID</strong></td>"
      			+ "<td><strong>Instancia</strong></td>"
      			+ "<td><strong>Servidor</strong></td>"
      			+"</tr>";
	     String finTablaDetalle="</table>";
	     
	     String encabezadoResumen=      
			        "<h3>sql Resumen</h3>"
	      			+"<table border='1'>"
	      			+"<tr>"
	      			+ "<td><strong>Server</strong></td>"
	      			+ "<td><strong>Usuario</strong></td>"
	      			+ "<td><strong>N Conexiones</strong></td>"
	      			+"</tr>";
	     String finTablaResumen="</table>";
	     
	     	generarPdf("Sql_"+Comando.miTime().replace(':', 'x').replace('-', 'x').replace(" ", "_").replace("x", ""), Titulo+encabezadoResumen+resumenResumen+finTablaResumen+encabezadoDetalle+resumenDetalle);
	 }
	 catch (Exception e)
	 {
	     e.printStackTrace();
	 }
	 finally {
	     if (rsD != null) try { rsD.close(); } catch(Exception e) {}
	     if (rsR != null) try { rsR.close(); } catch(Exception e) {}
	     
	     if (stmtD != null) try { stmtD.close(); } catch(Exception e) {}
	     if (stmtR != null) try { stmtR.close(); } catch(Exception e) {}
	     
	     if (con != null) try { con.close(); } catch(Exception e) {}
	 }
}
 public static void generarPdf(String Archivo, String Contenido){
	 try {
         Document document = new Document(PageSize.A4);
         PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(Archivo+".pdf"));
         document.open();
         document.addAuthor("Conytec");
         document.addCreator("itextpdf5");
         document.addCreationDate();
         document.addTitle("Informe de Conexiones");
         
         HTMLWorker htmlWorker = new HTMLWorker(document);
         String str ="<HTML> <HEAD> </HEAD> <BODY> "
         			//+"<table><tr><td><img src='http://conytec.com/LOGO.jpg' width='150px'></td><td></td></tr></table>"
         			
         			+Contenido
         			+"</table>"
         			
         			+"</BODY> </HTML>"; 
         htmlWorker.parse(new StringReader(str)); 
         document.close();
         
         String so = System.getProperty("os.name"); 
         File miDir = new File (".");
         if (so.equals("Linux")){
        	 System.out.println("\nResumen generado en: "+miDir.getCanonicalPath()+"/"+Archivo+".pdf");
         }else{
        	 System.out.println("\nResumen generado en: "+miDir.getCanonicalPath()+"\\"+Archivo+".pdf");
         }
         
       } catch (Exception e) { 
         System.out.println("\nError de Pdf: "+e.getMessage());
       } 
 }

 public static void ejecutarSql(String crearTabla,String comando){
		//Conexion
	
		Connection con = BaseDeDatos.conectarA(basedatos);
		
		try {
	     Statement stmt = con.createStatement();
	     try {
	         stmt.setQueryTimeout(30);
	         if(!crearTabla.equals("")){
	        	 stmt.executeUpdate(crearTabla);
	         	 //System.out.println("t");
	         	 }
	         if(!comando.equals("")){
	        	 stmt.executeUpdate(comando);
	        	 //System.out.println("i");
	         }
	         
	     } finally {
	         try { stmt.close(); } catch (Exception ignore) {}
	     }
	 }catch(SQLException sql){
		 System.out.println("Error ejecutarSql(): "+sql.getMessage());
	 }finally { try { con.close(); } catch (Exception ignore) {}
	 }
}

 public static String[][] selectSql(String comando,String columna){
		//Conexion
	 String [][] datos=null;
	 Connection con = BaseDeDatos.conectarA(basedatos);
	 
		
	try {
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();
      try {
          stmt.setQueryTimeout(30);
          stmt2.setQueryTimeout(30);
          //ejecutando consulta
          ResultSet rs = stmt.executeQuery(comando);
          ResultSet rs2= stmt2.executeQuery(comando);
          //cantidad de columnas
          int nCol=rs.getMetaData().getColumnCount();
          
          //contando cantidad de Filas
          int nFila=0;
          while(rs2.next()){
        	  nFila++;
          }
          
	          datos= new String[nFila][nCol];
	          
          try {
        	  //System.out.println("inicio: "+Comando.miTime());
        	  int n=0;
              while(rs.next())
                  {
            	  //esto va de 0 , 7
            	  for(int n2=0;n2<nCol;n2++){
        			  //Solo para verificar lo que se esta obteniendo
        			  //System.out.print( "datos["+n+"]["+n2+"] =" +rs.getString(n2+1)+"\t");
        			  datos[n][n2]=rs.getString(n2+1);
        		  }
            	  //Solo Usar cuando esta activo el la otra impresion..es solo para dar un orden
            	  //System.out.println();
            	  n++;
            	  
                  }
          } finally {
              try { rs.close();rs2.close(); } catch (Exception ignore) {}
          }
          
          //System.out.println("fin: "+Comando.miTime());
          
      } finally {
          try { stmt.close();stmt2.close();} catch (Exception ignore) {}
      }
  }catch(SQLException sql){
	System.out.println("Error selectSql(): "+sql.getMessage());  
  }finally {
      try { con.close(); } catch (Exception ignore) {}
  }
 
 return datos;
}

 
}//fin class

class Comando extends Thread {

	private boolean continuar = true;
	
	public void fin()
	{
		System.out.println("Escaneo Finalizado\nEspere por resumen!");
	    continuar=false;
	}
	 
	public void run() {

		while(continuar){
			comandosWyL();
			System.out.println("...scaning (Terminar: q+Enter)...");
			conex.carpetasCompartidas();
			System.out.println("...scaning (Terminar: q+Enter)...");
			try {
				Comando.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void escribirTxt(String Texto, String Archivo){
		 
		try
		{
			//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
			File archivo=new File(Archivo+".txt");
			//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
			FileWriter escribir=new FileWriter(archivo,true);
			//Escribimos en el archivo con el metodo write
			
			escribir.write(Texto+"\r\n");
			//Cerramos la conexion
			escribir.close();
		}//Si existe un problema al escribir cae aqui
		catch(Exception e)
		{
			System.out.println("Error al escribir en texto");
		}
	}
	static String miTime(){
		Date dtFechaActual = new Date ();
		DateFormat dfLocal = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		return dfLocal.format(dtFechaActual);
	}
	
	public static String ejecuarCMD(String CmdWin,String CmdLinux){
		
		String s = null;
		// Determinar en qué SO estamos
        String so = System.getProperty("os.name");                
        //System.out.println("Tipo SO: "+so);
        
        //limpiamos las variables
        Process p=null;
        String contCmd="";
        
		if (so.equals("Linux")){
			
			// Comando para Linux
        	String [] comandoarg = {"/bin/sh","-c",CmdLinux};
        	// Ejcutamos el comando
        	try {
				p = Runtime.getRuntime().exec(comandoarg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        else{
        	// Comando para Windows
        	char comilla='"';
        	String comando = "cmd /c "+ CmdWin;
        	// Ejcutamos el comando
        	try {
				p = Runtime.getRuntime().exec(comando);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       
       
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
        
        //Leemos la salida del comando
        //Damos formato a la salida
        try {
			while ((s = stdInput.readLine()) != null) {
					//quitando espacios blancos al inicio y al final
					s=s.trim();
					System.out.println(s);
					//quitando espacios blancos y reemplazandolos por comas
					//contCmd+=s.replaceAll("\\s+", ",")+"000";
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return s;
	}
public static String ejecuarCMDforSplit(String CmdWin,String CmdLinux){
		
		String s = "nada XD";
        String so = System.getProperty("os.name");                
        Process p=null;
        String contCmd="";
        
		if (so.equals("Linux")){
			
			// Comando para Linux
        	String [] comandoarg = {"/bin/sh","-c",CmdLinux};
        	// Ejcutamos el comando
        	try {
				p = Runtime.getRuntime().exec(comandoarg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        else{
        	// Comando para Windows
        	char comilla='"';
        	String comando = "cmd /c "+ CmdWin;
        	// Ejcutamos el comando
        	try {
				p = Runtime.getRuntime().exec(comando);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       
       
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
        
        //Leemos la salida del comando
        //Damos formato a la salida
        try {
			while ((s = stdInput.readLine()) != null) {
					//quitando espacios blancos al inicio y al final
					s=s.trim();
					//System.out.println(s);
					//quitando espacios blancos y reemplazandolos por comas
					contCmd+=s.replaceAll("\\s+", ",")+"zzz";
					//System.out.println(contCmd);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return contCmd;
	}
	static void comandosWyL(){
		String s = null;
        try {
                // Determinar en qué SO estamos
                String so = System.getProperty("os.name");                
                //System.out.println("Tipo SO: "+so);
                
                //limpiamos las variables
                Process p=null;
                String contCmd="";

                //elegimos ejecucion de comando segun sistema operativo
                
                if (so.equals("Linux")){
                	// Comando para Linux
                	String [] comandoarg = {"/bin/sh","-c","netstat -utna | grep \'tcp\'"};
                	// Ejcutamos el comando
                	p = Runtime.getRuntime().exec(comandoarg);
                }
                
                else{
                	// Comando para Windows
                	char comilla='"';
                	String comando = "cmd /c netstat -nb | find /i "+comilla+"TCP"+comilla+"";
                	// Ejcutamos el comando
                	p = Runtime.getRuntime().exec(comando);
                }
               
               
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                                p.getInputStream()));
                
                //Leemos la salida del comando
                //Damos formato a la salida
                while ((s = stdInput.readLine()) != null) {
                		//quitando espacios blancos al inicio y al final
                		s=s.trim();
                		//quitando espacios blancos y reemplazandolos por comas
                		contCmd+=s.replaceAll("\\s+", ",")+"000";
                		
                }
                
                //Limpiar tabla :) solo en ocaciones 
                //conex.ejecutarSql("", "drop table conexiones1");
                //creando tabla
                conex.ejecutarSql("CREATE TABLE IF NOT EXISTS conexiones1 (idc INTEGER PRIMARY KEY AUTOINCREMENT, "
                														+ "protocolo TEXT, ipLocal TEXT,puertoIpLocal TEXT, ipExterno TEXT, puertoIpExterno TEXT, estado TEXT, fecha TEXT)","");
                
                //tomando hora de la muestra
                String fecha= miTime();
               
                //imprimirndo todos los datos antes de dividirlo
                //System.out.println(so+"|"+miTime()+"|"+ contCmd);
                String[] vcontCmd = contCmd.split("000");
                
                if (so.equals("Linux")){
                	/*LINUX*/
                	 for(int i=0;i<vcontCmd.length;i++){
                		 //System.out.println(i+" -> "+vcontCmd[i]);
                		 
                		 String[] vvcontCmd = vcontCmd[i].split(","); 
                		 for(int j=0;j<vvcontCmd.length;j++){
                			//solo verificar que esta en funcionamiento
                     		//System.out.println(j+" -> "+vvcontCmd[j]);
                			System.out.print(".");
                     		if (vvcontCmd[0].trim().equals("tcp")){
                     			String proto=vvcontCmd[0];
                        		
                     			String ipLocal="";
                        		String puertoIpLocal="";
                     			
                        		try{
                        		String []vIplocal=vvcontCmd[3].split(":");
	                        		try{
	                        			ipLocal=vIplocal[0];
	                        			puertoIpLocal=vIplocal[1];
	                        		}catch(Exception e){
	                        			ipLocal="";
	                            		puertoIpLocal="";	
	                        		}
                        		}catch(Exception e){}
                        		
                        		String ipExterno="";
                        		String puertoIpExterno="";
                        		try{
                        		String []vIpExterno=vvcontCmd[4].split(":");
	                        		try{
	                        			ipExterno=vIpExterno[0];
	                    				puertoIpExterno=vIpExterno[1];
	                        		}catch(Exception e){
	                        			ipExterno="";
	                    				puertoIpExterno="";
	                        		}
                        		}catch(Exception e){}
                        		
                        		String estado=vvcontCmd[5];
                        		
                        		//ingresando datos de la muestra en la bd
                        		conex.ejecutarSql("","INSERT INTO conexiones1 (protocolo,ipLocal,puertoIplocal,ipExterno,puertoIpExterno,estado,fecha) VALUES ('"+proto+"','"+ipLocal+"','"+puertoIpLocal+"','"+ipExterno+"','"+puertoIpExterno+"','"+estado+"','"+fecha+"')");
                     			
                     			
                     		}
                		 }
                	 }
                		 
                }else{
                	/*WINDOWS*/
                	//dividiendo los datos obatenidos
                    for(int i=0;i<vcontCmd.length;i++){
                    	//System.out.println(i+" -> "+vcontCmd[i]);
                    	
                    	String[] vvcontCmd = vcontCmd[i].split(",");
                    	
                    	for(int j=0;j<vvcontCmd.length;j++){
                    		//solo verificar que esta en funcionamiento
                    		//System.out.println(j+" -> "+vvcontCmd[j]);
                    		System.out.print(".");
                    	try{
                    		String proto=vvcontCmd[0];
                    		
                    		String ipLocal="";
                    		String puertoIpLocal="";
                    		try{
                    		String []vIplocal=vvcontCmd[1].split(":");
                    			try{
	                    			ipLocal=vIplocal[0];
	                    			puertoIpLocal=vIplocal[1];
	                    		}catch(Exception e){
	                    			ipLocal="";
	                        		puertoIpLocal="";	
	                    		}
                    		}catch(Exception e){}
                    		
                    		String ipExterno="";
                    		String puertoIpExterno="";
                    		try{
                    			String []vIpExterno=vvcontCmd[2].split(":");
                    			try{
                        			ipExterno=vIpExterno[0];
                    				puertoIpExterno=vIpExterno[1];
                        		}catch(Exception e){
                        			ipExterno="";
                    				puertoIpExterno="";
                        		}
                    		}catch(Exception e){}
                    		
                    		
                    		String estado=vvcontCmd[3];
                    		
                    		//ingresando datos de la muestra en la bd
                    		conex.ejecutarSql("","INSERT INTO conexiones1 (protocolo,ipLocal,puertoIplocal,ipExterno,puertoIpExterno,estado,fecha) VALUES ('"+proto+"','"+ipLocal+"','"+puertoIpLocal+"','"+ipExterno+"','"+puertoIpExterno+"','"+estado+"','"+fecha+"')");
                    	}catch(Exception x){}	
                    		
                    		
                    	}
                    	//System.out.println("\n");
                    }
                }
                
                
                
                //escribirTxt(so+"|"+miTime(),"test1");
                //escribirTxt(contCmd,"test1");
                
                //conex.ejecutarSql("CREATE TABLE IF NOT EXISTS nueva (id INTEGER PRIMARY KEY AUTOINCREMENT, texto text)","INSERT INTO nueva (texto) Values('"+contCmd+"')");
                
                //conex.selectSql("Select * from nueva", "texto");
                
        } catch (IOException e) {
                System.out.println("Excepción: "+ e.getMessage());
                System.exit(-1);
        }
	}
	
	
	
}//finfin


