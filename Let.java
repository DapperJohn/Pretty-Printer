import java.io.*;

class Let extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Let(){}

    void print(Node t, int n, boolean p) {
		for (int i = 0; i < n; i++)
			System.out.print(" ");
			
		System.out.print("(let");
    }
}
