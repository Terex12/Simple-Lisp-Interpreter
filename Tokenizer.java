//package edu.osu.lisp;

import java.util.StringTokenizer;

public class Tokenizer {

    StringTokenizer tokenizer;
    String expression;
    

    public void tokenize(String expression) {
        this.expression = expression;
        this.tokenizer = new StringTokenizer(this.expression, " ().\t\n", true);
    }

    public String ckNextToken() throws interException{
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            
            if (token.charAt(0) == ' ' || token.charAt(0) == '\t' || token.charAt(0) == '\n') {
                continue;
            }
            if (token.charAt(0) == '(') {
                return "(";
            }
            if (token.charAt(0) == ')') {
                return ")";
            }
            if (token.charAt(0) == '.') {
                return ".";
            }
            
            if (Character.isLetter(token.charAt(0))) {
	            	if (token.matches(Patterns.LITERAL)){
	                    return token;
	                }
	                throw new interException("invalid symbol name");
	            }
            if (Character.isDigit(token.charAt(0))) {
            		if (token.matches(Patterns.NUMERIC_ATOM)) {
                    return token;
                }
                throw new interException("invalid number");

            }
            if (token.charAt(0) == '+' || token.charAt(0) == '-') {
            		if (token.matches(Patterns.NUMERIC_ATOM)) {
                    return token;
                }
                throw new interException("invalid number");
            }
        }
        throw new interException("incomplete expression");
    }
}
