// Scanner.java -- the implementation of class Scanner

import java.io.*;
import java.lang.Math;

class Scanner {
  private PushbackInputStream in;
  private byte[] buf = new byte[1000];
  private final String idents = "+-.*/<=>!?:$%_&~^";
  public Scanner(InputStream i) { in = new PushbackInputStream(i); }
    
  public Token getNextToken() {
    int bite = -1;
	
    try {
      bite = in.read();
    } catch (IOException e) {
      System.err.println("We fail: " + e.getMessage());
    }
	
	// Skip whitespace/tab/newline/return
	
	if( bite == 32 || bite == 9 || bite == 10 || bite == 12 || bite == 13 )
		return getNextToken();

    if (bite == -1)
      return null;

    char ch = (char) bite;
	
	
    // Special characters
    if (ch == '\'')
      return new Token(Token.QUOTE);
    else if (ch == '(')
      return new Token(Token.LPAREN);
    else if (ch == ')')
      return new Token(Token.RPAREN);
    else if (ch == '.')
      return new Token(Token.DOT);

    // Boolean constants
    else if (ch == '#') {
      try {
		bite = in.read();
      } catch (IOException e) {
		System.err.println("We fail: " + e.getMessage());
      }

      if (bite == -1) {
	System.err.println("Unexpected EOF following #");
	return null;
      }
      ch = (char) bite;
      if (ch == 't')
	return new Token(Token.TRUE);
      else if (ch == 'f')
	return new Token(Token.FALSE);
      else {
	System.err.println("Illegal character '" + (char) ch + "' following #");
	return getNextToken();
      }
    }

    // String constants
    else if (ch == '"') {
	  // counter to keep track of length of buf
	  int numChars = 0;
	  
	  // read first char of string
	  try {
			ch = (char) in.read();
      } catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
	  }
	  
      // while not reached end of string
	  while(ch != '"'){
		// continue to read the string
		buf[numChars] = (byte) ch;
		numChars ++;
		try {
			ch = (char) in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}
		
	  }
	  
	  return new StrToken(buf.toString());
    }

    // Integer constants
    else if (ch >= '0' && ch <= '9') {
      int i = Character.getNumericValue(ch);
      
	  int[] digits = new int[1000];
	  int count = 0;
	  digits[count] = i;
	  
	  // while still digits left in the number
	  while( ch >= '0' && ch <= '9' ){
		// continue to read number
		i = Character.getNumericValue(ch);
		digits[count] = i;
		count++;
		
		try {
			ch = (char) in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}
		
	  }
	  
	  // convert digits into actual number
	  int number = 0;
	  for(int j=0; j<count; j++){
		int cij = 10 ^ (count - 1 - j);
		if( count - 1 - j == 0 )
			number += digits[j];
		else
			number += digits[j] * ( Math.pow(10, count - 1 - j ) );
	  }
	  
      /* put the character after the integer back into the input
      // in->putback(ch);
	  try {
			in.unread(ch);
	  } catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
	  }*/
	  
	  
      return new IntToken(number);
    }

    // Identifiers
    else if ( ch >= 'A' && ch <= 'Z' || (ch >= 'a') && (ch <= 'z')  || idents.contains(ch+"") ) {
      
	  int count = 0;
	  try {
			ch = (char) in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}
	  buf[count] = (byte) ch; 
	
	  while( ch >= 'A' && ch <= 'Z' || (ch >= 'a') && (ch <= 'z') || idents.contains(ch+"") ){
		
		buf[count] = (byte) ch;
		
		try {
			ch = (char) in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}
		
	  
	  }
	  
      // put the character after the identifier back into the input
      // in->putback(ch);
      return new IdentToken(buf.toString());
    }

    // Illegal character
    else {
      System.err.println("Illegal input character '" + (char) ch + '\'');
      return getNextToken();
    }
  };
}
