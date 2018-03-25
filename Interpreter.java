//package edu.osu.lisp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Interpreter {
	
	static Evaluator ev = new Evaluator();
	static Printer printer = new Printer();

    public static void main(String[] args) throws IOException {		
        String inputLine, expression = "";
        //System.out.println("input: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        while ((inputLine = input.readLine()) != null) {
        		//System.out.println(inputLine);
            inputLine = inputLine.trim().toUpperCase();
            if (inputLine.equals("$$")) {
                if (expression.trim().equals(""))
                    break;	//no expression anymore, exit
                performInterpretation(expression);
                break;
            }
            if (inputLine.equals("$")) {
                performInterpretation(expression);
                expression = "";
                continue;
            }
            expression = expression + " " + inputLine;
        }
    }

    public static void performInterpretation(String expression){
        Parser p = new Parser(expression);
        try {
            SExp exp = p.input("");
            if (exp != null && p.check()) {
            		System.out.println("Dot : ");
            		printer.printBT(exp);
            		System.out.println();
            		try {
            			SExp result = ev.performEvaluation(exp);
            			//System.out.println();
            			System.out.println("Result : ");
            			printer.printBT(result);
            			System.out.println();
            		} catch(interException ex) {
            			System.out.println();
            			System.out.println("eval error: " + ex.getMessage());
            		}
            		
                
                System.out.println();
            }
            else{
                System.out.println("error: more than one SExpression detected");
            }
        } catch (interException ex) {
            System.out.println("error: " + ex.getMessage());
        }
    }
}
