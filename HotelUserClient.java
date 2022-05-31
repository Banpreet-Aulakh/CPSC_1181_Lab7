

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class HotelUserClient implements Protocol {
	static DataInputStream in;
	static DataOutputStream out;
	static Scanner userInput = new Scanner(System.in);
  public static void main(String[] args)throws IOException {
	  Socket client = new Socket("localhost",Protocol.PORT);
      in = new DataInputStream(client.getInputStream());
      out= new DataOutputStream(client.getOutputStream());
      //User Name
      String user="";
      
      while(user.length()==0) {
    	  System.out.print("Please write your name: ");
    	  String name = userInput.next();
    	  user=name.strip();
      }
      //Reads the first response of the server
      String response = in.readUTF();
      System.out.println("Receiving: " + response);
      //Save the user name to the server
      out.writeUTF("USER");
      out.writeUTF(Character.toUpperCase(user.charAt(0)) + user.substring(1));
      response = in.readUTF();
      System.out.println("Receiving: " + response);
    //Controls the client running process
      boolean run=true;
      int command=0;
      
      while(run) {
    	  System.out.println("What do you want to do");
    	  System.out.println("1. Get Available dates.\n2. Reserve.\n3. My Reservation\n4. Cancel reservation\n5. Quit");
    	  System.out.print("Anwer: ");
    	  command = userInput.nextInt();
    	  switch(command) {
    	  case 1:
    		  response= askForAvailableDates();
    		  System.out.println();
    		  System.out.println(response);
    		  System.out.println();
    		  break;
    	  case 2:
    		  response=makeReservation();
    		  System.out.println();
    		  System.out.println(response);
    		  System.out.println();
    		  break;
    	  case 3:
    		  response = getMyReservation();
    		  System.out.println();
    		  System.out.println(response);
    		  System.out.println();
    		  break;
    	  case 4:
    		  response =cancelReservation();
    		  System.out.println();
    		  System.out.println(response);
    		  System.out.println();
    		  break;
    	  case 5:
    		 out.writeUTF(QUIT);
    		 response = in.readUTF();
    		 System.out.println(response);
    		  run=false;
    		  break;
    	  default:
    		  System.out.println("Check your choice;");
    	  }
      }
      
  }
  
  public static String askForAvailableDates() throws IOException {
		out.writeUTF(AVAIL);
		return in.readUTF();
  }
  
  public static String makeReservation() throws IOException{
	  System.out.print("Write first date: ");
	  int firstDate = userInput.nextInt();
	  System.out.print("Write second date: ");
	  int secondDate= userInput.nextInt();
	  out.writeUTF(RESERVE);
	  out.writeInt(firstDate);
	  out.writeInt(secondDate);
	  return in.readUTF();
  }
  public static String cancelReservation() throws IOException{
	  out.writeUTF(CANCEL);
	  return in.readUTF();
  }
  public static String getMyReservation() throws IOException{
	  out.writeUTF(MYRESERV);
	  return in.readUTF();
  }
}
