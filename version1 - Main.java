import java.lang.Math.*;

import java.util.LinkedList;
import java.util.Queue;

import java.util.Scanner;  // Import the Scanner class


public class Main {
  
  
  public static boolean parenthesesBalanced(String input) { // check if there is an equal number of ( and )
      int balanced = 0;
      for (int i = 0; i < input.length(); i++) {
         if (input.charAt(i) == '(') {
            balanced++;
         }
         
         if (input.charAt(i) == ')') {
            balanced--;
         }
      }
      
      if (balanced == 0) {
         return true;
      } else {
         return false;
      }
  }
  
  
  public static boolean curlyBalanced(String input) { // check if there is an equal number of { and }
      int balanced = 0;
      for (int i = 0; i < input.length(); i++) {
         if (input.charAt(i) == '{') {
            balanced++;
         }
         
         if (input.charAt(i) == '}') {
            balanced--;
         }
      }
      
      if (balanced == 0) {
         return true;
      } else {
         return false;
      }
  }
  
  
  
  public static String answerOut(String input) {
      for (int i = 0; i < userInput.length(); i++) {
         if (userInput.charAt(i) == '+') {
            System.out.println("Error. Do not use a space");
         }
      }
      
      if(parenthesesBalanced(input) == false){
         return "Error. Parentheses are not balanced";
      }
      if(curlyBalanced(input) == false){
         return "Error. Curly brackets are not balanced";
      }
      
      return output;
     
  }
  
  
  
  public static void main(String[] args) {
    
    String answer;
    int queue[];
  
  
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter equation then press Enter. No spaces. Parentheses and curly brackets must be used and balanced.");

    String userInput = myObj.nextLine();  // Read user input
    
    Queue<Integer> q = new LinkedList<>();
    
    for (int i = 0; i < userInput.length(); i++) {
      if (userInput.charAt(i) == ' ') {
         System.out.println("Error. Do not use a space");
         return;
      }
    }
    
    
    if (userInput.length() == 1) {        // return the input the user typed if it is only size of one  --  if it is int then return as it, if not int then return error message
         try 
   		{ 
   			Integer.parseInt(userInput); 
            return;
   		}  
   		catch (NumberFormatException e)  
   		{ 
   			System.out.println("Error. '" + userInput + "' isn't an integer."); 
            return;
   		} 
    }
      

    answer = answerOut(userInput);
    System.out.println("userInput is: " + userInput);  // Output user input
 
    
    
    System.out.println("Answer is: " + answer);  // Output user input
  }  
  
}









//https://en.wikipedia.org/wiki/Shunting_yard_algorithm
