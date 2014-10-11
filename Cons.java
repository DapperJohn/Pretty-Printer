// Parser and parse tree written by: Alexandra Willis

class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;
  
    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form.  It would be possible to fully parse
    // special forms at this point.  Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    void parseList() { 
		
		// if the car is an Ident, set Special to the correct type
		if(car.isSymbol()){
			String name = car.getName();
			
			//  Quote, Lambda, Begin, If, Let, Cond, Define, Set, and Regular
			switch(name) {
				case "quote" : form = new Quote(); break;
				case "lambda" : form = new Lambda(); break;
				case "begin" : form = new Begin(); break;
				case "if" : form = new If(); break;
				case "let" : form = new Set(); break;
				case "cond" : form = new Cond(); break;
				case "define" : form = new Define(); break;
				case "set" : form = new Set(); break;
				default: form = new Regular(); break;
			}
		}
		else
			form = new Regular();
		
	}

    public Cons(Node a, Node d) {
		car = a;
		cdr = d;
		parseList();
    }

    void print(int n) {
		form.print(this, n, false);
    }

    void print(int n, boolean p) {
		form.print(this, n, p);
    }

}
