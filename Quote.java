import java.io.*;

class Quote extends Special {
    private Cons plist = null;
    
    public Quote(Cons list) {
    	this.plist = plist;
    } 

    void print(Node t, int n, boolean p) {
		for (int i = 0; i < n; i++)
			System.out.print(" ");
			((Cons)plist.getCar()).printQuote(n, false);		
		System.out.print("'");
    }
	
    void printQuote(Node c, int n, boolean p) {
    	print(c, n, p); 
}
