import java.io.*;
 
class Quote extends Special {
//    private Cons plist = null;
    
    public Quote() {
    	//this.plist = plist;
    } 

    void print(Node t, int n, boolean p) {
		for (int i = 0; i < n; i++)
			System.out.print(" ");		
		System.out.print("'");
    
    }
}
