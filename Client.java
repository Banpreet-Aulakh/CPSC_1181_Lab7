package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Protocol {

	public static void main(String[] args) {
		
		try(Socket client = new Socket("localhost", Protocol.PORT)){
			DataInputStream in = new DataInputStream(client.getInputStream());
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
			String response = in.readUTF();
			System.out.println(response);
			
			String user;
			System.out.println("What is your name?");
			Scanner scan = new Scanner(System.in);
			user = scan.nextLine();
		
			out.writeUTF("USER");
			out.writeUTF(user);
			out.flush();
			response = in.readUTF();
			System.out.println(response+"\n\n");
			
			System.out.println("To reserve a room enter 1.\nTo cancel a reservation enter 2.\nTo check availibility Enter 3.\nTo quit enter 4.\n\n");
			int input = 0;
			
			while(input != 4) {
				out.flush();
				try {
					input = scan.nextInt();
					if(input == 1) {
						System.out.println("Enter the first day of your reservation");
						int first = scan.nextInt();
						System.out.println("Enter the last day of your reservation");
						int last = scan.nextInt();
						out.writeUTF("RESERVE");
			    		out.writeInt(first);
			    		out.writeInt(last);
			    		out.flush();
			    		System.out.println(in.readUTF()+"\n\n");
					}
					
					else if (input == 2) {
						out.writeUTF("CANCEL");
			    		out.flush();
			    		System.out.println(in.readUTF()+"\n\n");
					}
					
					else if(input == 3) {
						out.writeUTF("AVAIL");
			    		out.flush();		    	  
				    	System.out.println("\n" + in.readUTF()+"\n\n");	
					}
					
					else if (input == 4){
						System.out.println("Goodbye.\n\n");
						out.writeUTF("QUIT");
			    		out.flush();	
						break;
					}
					else {
						System.out.println("Invalid input try again.\n\n");
					}
					
				} catch(NumberFormatException e) {
					System.out.println("Error try again.\n\n");
					continue;
				}
				System.out.println("To reserve a room enter 1.\nTo cancel a reservation enter 2.\nTo check availibility Enter 3.\nTo quit enter 4.\n\n");

			}
		} catch(IOException e) {
			System.out.println("IOException.");
		}
		
		
		
		
	}

}
