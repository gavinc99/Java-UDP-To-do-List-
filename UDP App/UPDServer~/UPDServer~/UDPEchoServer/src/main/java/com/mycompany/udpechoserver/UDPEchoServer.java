/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.udpechoserver;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class UDPEchoServer {
 private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) 
    {
	System.out.println("Opening port...\n");
	try 
        {
            dgramSocket = new DatagramSocket(PORT);//Step 1.
	} 
        catch(SocketException e) 
        {
            System.out.println("Unable to attach to port!");
	    System.exit(1);
	}
	run();
    }

    private static void run()
    {
         ArrayList<String> task = new ArrayList(); //Array for all tasks
         ArrayList<String> list = new ArrayList(); //Array for current day tasks
         
	try 
        {
            String messageIn, messageOut;
          

            do 
            {
                buffer = new byte[256]; 		//Step 2.
                inPacket = new DatagramPacket(buffer, buffer.length); //Step 3.
                dgramSocket.receive(inPacket);	//Step 4.

                InetAddress clientAddress = inPacket.getAddress();	//Step 5.
                int clientPort = inPacket.getPort();		//Step 5.

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());	//Step 6.

             

          //Message received in server
          System.out.println("Message received.");
          
      //Getting current date and assigning it to a variable
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      Calendar obj = Calendar.getInstance();
      String date = formatter.format(obj.getTime());
       //adding task to array of tasks
       task.add(messageIn);
            //checks if task contains a date
            if(messageIn.contains(date)){
                
               
                //checking to see if task already exists in current day tasks and validating current date
                for(String element:task){
            if(!list.contains(element) && element.contains(date)){
                //adding current day task to array of current tasks
                list.add(element);
                //message output
                messageOut = ("Here are your tasks for today: ") + list.toString() + "\n";
                outPacket = new DatagramPacket(messageOut.getBytes(),
                                         messageOut.length(),
                                         clientAddress,	
                                         clientPort);		//Step 7.
                dgramSocket.send(outPacket);	//Step 8.
                

            }
            

        }
              
            }
            
           //else statement for any input
            else{
                
                //outputs array for current day tasks
                messageOut = ("Here are your tasks for today: ") + list.toString() + "\n";
                outPacket = new DatagramPacket(messageOut.getBytes(),
                                         messageOut.length(),
                                         clientAddress,	
                                         clientPort);		//Step 7.
                dgramSocket.send(outPacket);	//Step 8.
               
                
            }
        
        

       
          
            
            
           
           
                
              
               
            
           
            
           
            
            
        
            }while (true);
        } 
        catch(IOException e)
        {
            e.printStackTrace();
	} 
        finally 
        {		//If exception thrown, close connection.
            System.out.println("\n Closing connection... ");
            dgramSocket.close();				//Step 9.
	}
    }
}
