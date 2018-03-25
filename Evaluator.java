//package edu.osu.lisp;

public class Evaluator {
	
	static Printer printer = new Printer();
	static SExp dList = new SExp("NIL");
	
	public SExp performEvaluation(SExp s) throws interException {
		SExp aList = new SExp("NIL");
		return EVAL(s, aList, this.dList);	
	}
	
	public SExp EVAL(SExp s, SExp aList, SExp dList) throws interException {
		
//		System.out.println("EVAL : ");
//		printer.printBT(s);
		
		if (s.isAtom) {  /* 1: integer atom; 2: symbolic atom; 3: non-atom */
			if(s.getType() == 2) {
				if (s.getName().equalsIgnoreCase("T")) {
                    return new SExp("T");
                } 
				else if (s.getName().equalsIgnoreCase("NIL")) {
                    return new SExp("NIL");
				}
				else {
					// literal is looked up in the a-list
					if (IN(s, aList)) {
						return GETVAL(s, aList);
					}
					else 
						throw new interException("unable to find it in aList, unbound variable");
				}
			}
			else if (s.getType() == 1) {
				return s;
			}
			else 
				throw new interException("unbound literal");
        }
		else {
			String functionName = s.getLeft().getName();
			if (functionName == null)
				throw new interException("Interger can not be a function name");
			
			if (functionName.equalsIgnoreCase("QUOTE")) {
				if (s.getRight().isAtom ||  !EQ(s.getRight().getRight(), new SExp("NIL")).getName().equals("T") )
                    throw new interException("Argument for QUOTE is not a valid list.");
				if (size(CDR(s)) == 1)
					return CAR(CDR(s));
				else
					throw new interException("QUOTE invalid number of parameter");
			}
			else if (functionName.equalsIgnoreCase("COND")) {
				return EVCON(CDR(s),aList,dList);
			}
			else if (functionName.equalsIgnoreCase("DEFUN")) {	//add to dList
				SExp func = transfer(CDR(s));
                this.dList = CONS(func, dList);
                return new SExp(CAR(CDR(s)).getName());
			}
			else {	//apply
				return APPLY(CAR(s), EVLIST(CDR(s), aList, dList), aList, dList) ;
			}
		}
	}
	
	public SExp APPLY(SExp f, SExp x, SExp aList, SExp dList) throws interException {
		
//		System.out.println("APPLY : ");
//		printer.printBT(x);
		
		int para_size = size(x);
		if (f.isAtom) {
			if (f.getName().equalsIgnoreCase("CAR")) {
				return CAR(CAR(x));
			}
			else if (f.getName().equalsIgnoreCase("CDR")) {
				return CDR(CAR(x));
			}
			else if (f.getName().equalsIgnoreCase("CONS")) {
				if (para_size == 2)
					return CONS(CAR(x), CAR(CDR(x)));
				else
					throw new interException("CONS invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("EQ")) {
				return EQ(CAR(x), CAR(CDR(x)));
			}
			else if (f.getName().equalsIgnoreCase("PLUS")) {
				if (para_size == 2) 
					return PLUS(CAR(x), CAR(CDR(x)));
				else
					throw new interException("PLUS invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("MINUS")) {
				if (para_size == 2) 
					return MINUS(CAR(x), CAR(CDR(x)));
				else
					throw new interException("MINUS invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("TIMES")) {
				if (para_size == 2) 
					return TIMES(CAR(x), CAR(CDR(x)));
				else
					throw new interException("TIMES invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("QUOTIENT")) {
				if (para_size == 2) 
					return QUOTIENT(CAR(x), CAR(CDR(x)));
				else
					throw new interException("QUOTIENT invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("REMAINDER")) {
				if (para_size == 2) 
					return REMAINDER(CAR(x), CAR(CDR(x)));
				else
					throw new interException("REMAINDER invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("LESS")) {
				if (para_size == 2) 
					return LESS(CAR(x), CAR(CDR(x)));
				else
					throw new interException("LESS invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("GREATER")) {
				if (para_size == 2) 
					return GREATER(CAR(x), CAR(CDR(x)));
				else
					throw new interException("GREATER invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("INT")) {
				if (para_size == 1) 
					return isInt(CAR(x));
				else
					throw new interException("INT invalid number of parameter");
					
			}
			else if (f.getName().equalsIgnoreCase("NULL")) {
				if (para_size == 1) 
					return isNUll(CAR(x));
				else
					throw new interException("NULL invalid number of parameter");
			}
			else if (f.getName().equalsIgnoreCase("ATOM")) {
				if (para_size == 1) 
					return isAtom(CAR(x));
				else
					throw new interException("ATOM invalid number of parameter");
			}
			else {
				//user def function
				return EVAL(
						CDR(GETVAL(f, dList)), 
						ADDPAIRS(CAR(GETVAL(f, dList)), x, aList), 
						dList);
			}
		}
		else
			throw new interException("Error: cannot have non-atomic exp");
	}
	
    /*
     * Built-in Functions
     */
    public SExp CAR(SExp s) throws interException {
    		if (s.isAtom) 
    			throw new interException("CAR Error : CAR cannot be applied to an atom.");
    		return s.getLeft();
    		
    }
    
    public SExp CDR(SExp s) throws interException {
		if (s.isAtom) 
			throw new interException("CAR Error : CAR cannot be applied to an atom.");
		return s.getRight();
    }
		
    public SExp CONS(SExp s1, SExp s2) {
    		return new SExp(s1, s2);
    }
    
    public SExp PLUS(SExp s1, SExp s2) throws interException {
    		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
    			int r = s1.getVal() + s2.getVal();
    			return new SExp(r);
    		}
    		else 
    			throw new interException("PLUS Error : PLUS cannot be applied to an non-atom/non-INT.");
    }
    
    public SExp MINUS(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = s1.getVal() - s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("MINUS Error : MINUS cannot be applied to an non-atom/non-INT.");
    }
    
    public SExp TIMES(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = s1.getVal() * s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("TIMES Error : TIMES cannot be applied to an non-atom/non-INT.");
    }
    
    
    public SExp QUOTIENT(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = s1.getVal() / s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("QUOTIENT Error : QUOTIENT cannot be applied to an non-atom/non-INT.");
    }
    
    public SExp REMAINDER(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = s1.getVal() % s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("REMAINDER Error : REMAINDER cannot be applied to an non-atom/non-INT.");
    }
    
    
    public SExp LESS(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = (s1.getVal() < s2.getVal()) ? s1.getVal() : s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("LESS Error : LESS cannot be applied to an non-atom/non-INT.");
    }
    
    
    public SExp GREATER(SExp s1, SExp s2) throws interException {
		if(s1.isAtom && s2.isAtom && s1.getType() == 1 && s1.getType() == 1) {
			int r = (s1.getVal() > s2.getVal()) ? s1.getVal() : s2.getVal();
			return new SExp(r);
		}
		else 
			throw new interException("GREATER Error : GREATER cannot be applied to an non-atom/non-INT.");
    }
    
    
    public SExp isAtom(SExp s) {
    		if (s.isAtom)	return new SExp("T");
    		else				return new SExp("NIL");
    }
    
    public SExp isNUll(SExp s) {
		if (s.isAtom && s.isNIL)	return new SExp("T");
		else				return new SExp("NIL");
    }
    
    public SExp isInt(SExp s) {
		if (s.isAtom && s.getType() == 1)	return new SExp("T");
		else				return new SExp("NIL");
    }
	
	
    public SExp EQ(SExp s1, SExp s2) throws interException {
		if (s1.isAtom == true && s2.isAtom == true) {
			if (s1.getType() == 2 && s2.getType() == 2) {
				if (s1.getName().equalsIgnoreCase(s2.getName())) 
                    return new SExp("T");
                else 
                    return new SExp("NIL");
			}
			if (s1.getType() == 1 && s2.getType() == 1) {
				if (s1.getVal() == s2.getVal()) 
                    return new SExp("T");
                else 
                    return new SExp("NIL");
			}
			
			return new SExp("NIL");
		}
		else 
			throw new interException("EQ Error : not given atoms.");
//			System.out.println("EQ error: not given atoms");
//			return new SExp("NIL");
	}
    
    
    /*
     * Helper Functions
     */
    
    /* to get size of List*/
    private int size(SExp s) {
    		SExp pointer = s;
        int size = 0;
        while (pointer != null) {
            if (pointer.getLeft() != null) {
                size++;
            }
            pointer = pointer.getRight();
        }
        return size;
    }
    
    private SExp transfer(SExp s) throws interException {
        SExp s1 = CONS(CAR(s), CONS(CAR(CDR(s)), CAR(CDR(CDR(s)))));
        return s1;
    }
    
	private boolean IN(SExp s, SExp aList) throws interException {
		if (aList.isNIL)	
			return false;
		if (EQ(s, CAR(CAR(aList))).isT) 
			return true;
		else
			return IN(s, CDR(aList));
	}
	

	private SExp GETVAL(SExp s, SExp aList) throws interException {
		if (aList.isNIL)	
			throw new interException("GETVAL Error : can not find  it in DList");
		if (EQ(s, CAR(CAR(aList))).isT) 
			return CDR(CAR(aList));
		return GETVAL(s, CDR(aList));
	}
	
	private SExp ADDPAIRS(SExp para, SExp argu, SExp aList) throws interException {
		if (para.isNIL && !argu.isNIL) 
			throw new interException("ADDPAIR Error : arguments length is not same; argu too long ");		
		if (!para.isNIL && argu.isNIL)
			throw new interException("ADDPAIR Error : arguments length is not same; argu too short.");
		if (para.isNIL && argu.isNIL)
			return aList;
		else {
			SExp newpair = CONS(CAR(para), CAR(argu));
			return ADDPAIRS(CDR(para), CDR(argu), CONS(newpair, aList));
		}
	}

	private SExp EVLIST(SExp x, SExp aList, SExp dList) throws interException {
		if (x.isNIL) 
			return new SExp("NIL");
		else {
			SExp list =  CONS(EVAL(CAR(x),aList,dList), EVLIST(CDR(x), aList, dList));
			return list;
		}
	}
	
	private SExp EVCON(SExp s, SExp aList, SExp dlist) throws interException {
        if (s.getName() != null && s.getName().equalsIgnoreCase("NIL")) {
        		throw new interException("Error: At least one condition should evaluate to true.");
        } 
        else {
            if (EVAL(CAR(CAR(s)), aList, dlist).isT) 
                return EVAL(CAR(CDR(CAR(s))), aList, dlist);
            else 
                return EVCON(CDR(s), aList, dlist);
        }

	}
	
}
