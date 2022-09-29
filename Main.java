import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;


public class Main{
    
	public static void main(String[] args) {
	    boolean lastWasNumber = false;
        boolean canBeSolved = true;
	    
	    System.out.println("Type and enter one of the following: Number, -, (");
       System.out.println("One thing per line entered. Do not do something like '+ 5' all on one line");
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
        
        
        while ((!(s.equals("solve"))) && canBeSolved == true) {
            if (lastWasNumber == false) {
        	        System.out.println("Use of the following: Number, -, +, /, *, ^, (, )   -   enter solve when ready");
        	        System.out.print("Next item:  ");
        	        s  = sc.next();
        	        
        	        if (!(s.equals("solve"))) {
        	            infix = infix + " " + s;
        	        }
        	        
        	        if (!((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("(")) || (s.equals(")")))) {
        	            lastWasNumber = true;
        	        }
        	        
        	        System.out.print("\n");
        	    
            } else {
        	        System.out.println("Use of the following: -, +, /, *, ^, (, )   -   enter solve when ready");
        	        System.out.print("Next item:  ");
        	        s  = sc.next();
        	        
        	        if ((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("(")) || (s.equals(")"))) {
        	            infix = infix + " " + s;
        	            lastWasNumber = false;
        	           // if ((s.equals("-")) || (s.equals("+")) || (s.equals("/")) || (s.equals("*")) || (s.equals("^")) || (s.equals("("))) {
        	           //     canBeSolved = false;
        	           // }
        	        }
        	        System.out.print("\n");
            }
        }
        
        if (s.equals("solve") && (canBeSolved == false)) {
            System.out.println("Cannot be solved. Last item cannot be last");
        	return;
        }
		
        System.out.printf("infix:   %s%n", infix);
        
        try
        {
            System.out.printf("postfix: %s%n", infixToPostfix(infix));
        }
        catch(Exception e)
        {
            System.out.println("Equation invalid");
            return;
        }
    

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

        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);
          
                else {
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
    //added the case of # / 0 in the token is / case
    
	private static void evalRPN(String expr){
		LinkedList<Double> stack = new LinkedList<Double>();
		System.out.println("Input\tOperation\tStack after");
		for (String token : expr.split("\\s")){
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
            if (secondOperand == 0) {
               System.out.println("\nError: cannot divide by 0");
    				return;
            }
				stack.push(firstOperand / secondOperand);
			} else if (token.equals("-")) {
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand - secondOperand);
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