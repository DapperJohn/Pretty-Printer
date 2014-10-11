import java.io.*;

class Cond extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Cond(){}

    void print(Node t, int n, boolean p) {
		for (int i = 0; i < n; i++)
			System.out.print(" ");
			
		System.out.print("(cond");
    }
}
