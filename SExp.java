//package edu.osu.lisp;

public class SExp {
	private int type; /* 1: integer atom; 2: symbolic atom; 3: non-atom */
	private int val; /* if type is 1 */
	private String name; /* if type is 2 */
	private SExp left; 
	private SExp right; /* if type is 3 */
	
	public boolean isAtom;
	public boolean isNIL;
	public boolean isT;
	
	/**
     * constructor for integer atom
     */
	SExp(int num) {
        isAtom = true;
        setType(1);
        setVal(num);
        setName(null);
        isNIL = false;
        setLeft(null);
        setRight(null);
    }
	
	/**
     * constructor for symbolic atom
     */
	SExp(String sym) {
        isAtom = true;
        setType(2);
        setName(sym);
        isNIL = (name.equalsIgnoreCase("NIL")) ? true : false;
        isT = (name.equalsIgnoreCase("T")) ? true : false;
        setLeft(null);
        setRight(null);
    }
	
	/**
     * constructor for non-atom
     */
	SExp(SExp carSExp, SExp cdrSExp) {
        isAtom = false;
        setType(3);
        isNIL = false;
        setLeft(carSExp);
        setRight(cdrSExp);
    }
	
	/**
     * set/get type
     */
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	/**
     * set/get value
     */
	public void setVal(int val) {
		this.val = val;
	}
	
	public int getVal() {
		return this.val;
	}
	
	/**
     * set/get name
     */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
     * set/get left
     */
	public void setLeft(SExp s) {
		this.left = s;
	}
	
	public SExp getLeft() {
		return this.left;
	}
	
	/**
     * set/get right
     */
	public void setRight(SExp s) {
		this.right = s;
	}
	
	public SExp getRight() {
		return this.right;
	}
}
