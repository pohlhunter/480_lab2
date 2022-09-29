import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;


public class Main{
    
	public static void main(String[] args) {
	    boolean lastWasNumber = false;
        boolean canBeSolved = true;
	    
	    System.out.println("Type and enter one of the following: Positive Number, -, (");
       System.out.println("One thing per line entered. Do not do something like '+ 5' all on one line");
       System.out.println("Radians are the measurement used. Enter negative numbers as: - enter then Number");
       System.out.println("Enter 'exit' to end early (without the ' ')");
       
       System.out.print("First item:  ");
	    String infix = ""; // where all of the user input will go
	    
	    Scanner sc = new Scanner(System.in);
		String s  = sc.next();
		
		if (s.equals("solve")) {
		    System.out.println("Need to enter equation first");
		    return;
		} else if ((s.equals("*")) || (s.equals("/")) || (s.equals(")")) || (s.equals("^")) || (s.equals("+"))) {
		    System.out.println("Cannot start with that");
		    return;
		} else if (!((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("(")) || (s.equals(")")))) {
        	lastWasNumber = true;
        	infix = s;
        } else {
		    infix = s;
		}
        
        
        int itemCounter = 0;     // var to keep track of enters - to limit equatino size
        while ((!(s.equals("solve"))) && itemCounter < 50) {     // while the user is inputting something that is not solve
            if(s.equals("exit")) {
               return;
            }
            if (lastWasNumber == false) {
        	        System.out.println("Use of the following: Positive Number, -, +, /, *, ^, (, )      type and enter 'solve' when ready (without the ' ')");
        	        System.out.print("Next item:  ");
        	        s  = sc.next();
        	        
        	        if (!(s.equals("solve"))) {
        	            infix = infix + " " + s;
        	        }
        	        
        	        if (!((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("(")) || (s.equals(")")))) {
        	            lastWasNumber = true;
        	        }
                 
                  // if the most recent thing entered was one of these then it cannot be a correct equation
     	            if ((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("("))) {
     	                canBeSolved = false;
     	            } else {
                      if (!(s.equals("solve"))) {        // as long as the entered item is valid and not 'solve' then it goes back to can be solve True
                        canBeSolved = true;
                      }
                  }              
        	        
        	        System.out.print("\n");
        	    
            } else {
        	        System.out.println("Use of the following: -, +, /, *, ^, (, )      type and enter 'solve' when ready (without the ' ')");
        	        System.out.print("Next item:  ");
        	        s  = sc.next();
        	        
        	        if ((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("(")) || (s.equals(")"))) {
        	            infix = infix + " " + s;
        	            lastWasNumber = false;
                     
                     // if the most recent thing entered was one of these then it cannot be a correct equation
        	            if ((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("("))) {
        	                canBeSolved = false;
        	            } else {
                         if (!(s.equals("solve"))) {        // as long as the entered item is valid and not 'solve' then it goes back to can be solve True
                           canBeSolved = true;
                         }
                     }
        	        }
        	        System.out.print("\n");
            }
            ++itemCounter;
        }
        
        //first check if itemCounter restriction was reached
        if (itemCounter > 49) {
            System.out.println("Equation was too long - entered information too many times.");
        	   return;
        }
        
        
        //System.out.print("canBeSolved =" + canBeSolved);
        // if solve is entered but the equation cannot be a correct one due to the far right item entered (the most recent) then end it here
        if ((canBeSolved == false)) {
            System.out.println("Cannot be solved. What you entered last cannot be the last thing in equation");
        	   return;
        }
		
        System.out.printf("infix:   %s%n", infix);
        
        //try - putting it to post fix
        try
        {
            System.out.printf("postfix: %s%n", infixToPostfix(infix));
        }
        catch(Exception e)
        {
            System.out.println("Equation invalid");
            return;
        }
    
      //try - using the post fix from above -> putting it thru reverse polish notation
      try
        {
            evalRPN(infixToPostfix(infix));
        }
        catch(Exception e)
        {
            System.out.println("");
            System.out.println("Equation invalid");
            return;
        }
	}

    
    //https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Java
    static String infixToPostfix(String infix) {
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down). 
           This will give us: 0, 0, 1, 1, 2 */
        final String ops = "-+/*^";

        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();
        
        String nextToken = "";
        for (String token : infix.split("\\s")) {
            
            System.out.println("$" + infix[0] + "$");
            
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);
            
            nextToken = "" + c;
            System.out.println("next:" + nextToken);
            
            // check for operator
            if (idx != -1) {
                if (s.isEmpty()) {
                    s.push(idx);
                    System.out.println("c0");
                } else if (nextToken.equals("+") && token.equals("-")) {      // if the one that was just enter was a - and the current is -  ->   then pop and insert +
                    System.out.println("c1");
                    s.pop();
                    sb.append('-').append(' ');
                    
                } else if (nextToken.equals("-") && token.equals("-")) {      // if the one that was just enter was a - and the current is -  ->   then pop and insert +
                    System.out.println("c2");
                    s.pop();
                    sb.append('+').append(' ');
                    
                } else if (nextToken.equals("-") && token.equals("+")) {      // if the one that was just enter was a - and the current is -  ->   then pop and insert +
                    System.out.println("c3");
                    s.pop();
                    sb.append('-').append(' ');
                    
                } else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                           sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } 
            else if (c == '(') {
                s.push(-2); // -2 stands for '('
            } 
            else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        
        }
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        
        return sb.toString();
    }



    //https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java
    //added the case of # / 0 - when the token equals '/' case
    
	private static void evalRPN(String expr){
		LinkedList<Double> stack = new LinkedList<Double>();
		System.out.println("Input\tOperation\tStack after");
		for (String token : expr.split("\\s")){
         System.out.print("\n  ~" + expr + "~   ");
         System.out.print("  ~ " + token + " ~   \n");
      
			System.out.print(token + "\t");
			if (token.equals("*")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand * secondOperand);
			} else if (token.equals("/")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
            if (secondOperand == 0) {                      //added special case of dividing by 0
               System.out.println("\nError: cannot divide by 0");
    				return;
            }
				stack.push(firstOperand / secondOperand);
			} else if (token.equals("-")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
            
           if (Double.toString(firstOperand).equals("-") && Double.toString(secondOperand).equals("-")) {     //added special case of unary - where the left and right are both '-' -> turn into '+'
              //stack.push('+');
           } else {
               stack.push(firstOperand - secondOperand);
           }
			} else if (token.equals("+")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand + secondOperand);
			} else if (token.equals("^")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(Math.pow(firstOperand, secondOperand));
			} else {
				System.out.print("Push\t\t");
				try {
					stack.push(Double.parseDouble(token+""));
				} catch (NumberFormatException e) {
    					System.out.println("\nError: invalid token " + token);
    					return;
				}
			}
			System.out.println(stack);
		}
		if (stack.size() > 1) {
			System.out.println("Error, too many operands: " + stack);
			return;
		}
		System.out.println("Final answer: " + stack.pop());
	}
}