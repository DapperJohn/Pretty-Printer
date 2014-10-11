// Parser.java -- the implementation of class Parser
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         |  exp+ [. exp] )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
//
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL) token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.

// Parser and parse tree written by: Alexandra Willis

class Parser {
  private Scanner scanner;

  public Parser(Scanner s) { scanner = s; }
  
  // made two forms of parseExp() so that parseRest() can call parseExp() on the token it just parsed
  public Node parseExp() {
	// get next token
	Token toke = scanner.getNextToken();
	return parseExp(toke);
  }
  
  public Node parseExp(Token toke) {
    
	// if the token is empty, return nothing
	if(toke == null) return null;
	
	// create leaf node of appropriate type from token
	Node leaf;
	int t = toke.getType();
	if ( t == 0 )
		leaf = new Ident(toke.getName());
	else if ( t == 1 )
		return parseRest();
	else if ( t == 4 )
		leaf = new BooleanLit(true);	
	else if ( t == 5 )
		leaf = new BooleanLit(false);
	else if ( t == 6 )
		leaf = new IntLit(toke.getIntVal());
	else if ( t == 7 )
		leaf = new StrLit(toke.getStrVal());	
	else if ( t == 8 )
		leaf = new Ident(toke.getName());
	else
		throw new RuntimeException("Token type not recognized, or is rparen (2) or dot (3).");
	
	
	return leaf;
	
	
  }
  
  protected Node parseRest() {
  
	// get next token
	Token toke = scanner.getNextToken();
	int t = toke.getType();
	
	// if token is a dot, create cons node where
		// car = parseExp(current token)
		// cdr = parseRest()
	if( t == 3 )
		return new Cons(parseExp(), parseRest());
	// if token is an rparen, return null
    else if( t == 2 )
		return null;
	// create cons node where
		// car = leaf node
		// cdr = parseRest()
	else
		return new Cons(parseExp(toke), parseRest());
	
  }

};
