//package edu.osu.lisp;

public class Printer {
	 public void printBT(SExp exp) {
	        if (exp.getType() == 3) {
	            System.out.print("(");
	            printBT(exp.getLeft());
	            System.out.print(".");
	            printBT(exp.getRight());
	            System.out.print(")");
	        } else if (exp.getType() == 2) {
	            System.out.print(exp.getName());
	        } else {
	            System.out.print(exp.getVal());
	        }
	    }

}
