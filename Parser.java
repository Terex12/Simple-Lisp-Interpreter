//package edu.osu.lisp;

public class Parser {

    Tokenizer tokenizer = new Tokenizer();
    String expression = "";

    public Parser(String expression) {
        this.expression = expression;
        this.tokenizer.tokenize(this.expression);
    }

    public SExp input(String token) throws interException {
        String nextToken = token;

        if (nextToken.equals("")) {
            nextToken = tokenizer.ckNextToken();
        }

        if (nextToken.equals(")")) {
            throw new interException("start with )");
        }
        if (nextToken.equals(".")) {
            throw new interException("single dot");
        }
        if (nextToken.equals("(")) {     //Type 1
            nextToken = tokenizer.ckNextToken();
            if (nextToken.equals(")")) {     //corner case:()
                return new SExp("NIL");
            }

            SExp left = input(nextToken);
            if (left == null) {
                return null;
            }

            SExp right;
            nextToken = tokenizer.ckNextToken();
            if (nextToken.equals(".")) {     //Type 3
                right = input("");
                nextToken = tokenizer.ckNextToken();
                if (!nextToken.equals(")")) {
                    throw new interException("missing right parenthesis");
                }
            } 
            else {
                right = input2(nextToken);
            }
            return new SExp(left, right);

        }
        if (nextToken.matches(Patterns.NUMERIC_ATOM) || nextToken.matches(Patterns.DIGIT)) {
        		return new SExp(Integer.parseInt(nextToken));
        }
        else if (nextToken.matches(Patterns.LITERAL)){
        		return new SExp(nextToken);
        }
        else {
        		throw new interException("invalid symbol");
        }     
        
       
    }

    public SExp input2(String token) throws interException {
        if (token.equals(")")) {
            return new SExp("NIL");
        }
        SExp left = input(token);
        if (left == null) {
            return null;
        }
        SExp right = input2(tokenizer.ckNextToken());
        if (right == null) {
            return null;
        }

        return new SExp(left, right);
    }

    public boolean check() {
        try {
        	tokenizer.ckNextToken();
            return false;
        } catch (interException ex) {
            return ex.getMessage().equals("incomplete expression");
        }
    }

    
   
}

