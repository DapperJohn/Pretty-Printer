// Scanner written by: John Anny

import java.io.*;
import java.util.*;
import java.lang.StringBuilder;

@SuppressWarnings("deprecation")
class Scanner {
	private PushbackInputStream in;
	private byte[] buf = new byte[1000];

	public Scanner(InputStream i) { in = new PushbackInputStream(i); }

	public Token getNextToken() {
		int bit = -1;

		// It would be more efficient if we'd maintain our own input buffer
		// and read characters out of that buffer, but reading individual
		// characters from the input stream is easier.
		try {
			bit = in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}

		if (bit == -1)
			return null;

		char ch = (char) bit;
		System.out.println(ch);
		
		// Skip whitespace and comments
		if (ch == ' ' || ch == '\n' || ch =='\t') {
			return getNextToken();
		} 
		else if (ch == ';') {
			do {
				try {
					bit = in.read();
				} catch (IOException e) {
					System.err.println("We fail: " + e.getMessage());
				  }
				ch = (char) bit;
			} while (!(ch == '\n'));
				return getNextToken();
		}

		// Special characters
		if (ch == '\'')
			return new Token(Token.QUOTE);
		else if (ch == '(')
			return new Token(Token.LPAREN);
		else if (ch == ')')
			return new Token(Token.RPAREN);
		else if (ch == '.')
			// We ignore the special identifier `...'.
			return new Token(Token.DOT);

		// Boolean constants
		else if (ch == '#') {
			try {
				bit = in.read();
			} catch (IOException e) {
				System.err.println("We fail: " + e.getMessage());
		  	  }

			if (bit == -1) {
				System.err.println("Unexpected EOF following #");
				return null;
			}
			ch = (char) bit;
			if (ch == 't')
				return new Token(Token.TRUE);
			else if (ch == 'f')
				return new Token(Token.FALSE);
			else {
				System.err.println("Illegal character '" + ch + "' following #");
				return getNextToken();
			}
		}

		// String constants
		else if (ch == '"') {
			StringBuilder str = new StringBuilder();

			try {
				bit = in.read();
		  	} catch (IOException e) {
				System.err.println("We fail: " + e.getMessage());
		  	  }
			ch = (char) bit;

			while (!(ch == '"')) {
				str.append(ch);
				try {
					bit = in.read();
				} catch (IOException e) {
					System.err.println("We fail: " + e.getMessage());
				  }
				ch = (char) bit;
			}

			return new StrToken(str.toString());
		}

		// Integer constants
	   	else if (ch >= '0' && ch <= '9') {
			StringBuilder sb = new StringBuilder();

            		try {
                		in.unread((byte) ch);
            		} catch (java.io.IOException e) {
                		System.err.println("No character to unread!");
           	 	}

            		return new IntToken(Integer.parseInt(sb.toString()));
		}		
	   	
		/*
		/		TODO: 
		/		John- I need you to change the name you're giving IdentTokens 
		/				to something more descriptive than the literal character
		/				that was read. Look at all of the classes that inherit
		/				from Special (namely, Quote, Lambda, Begin, If, Let, Cond, Define, 
		/				Set, and Regular) and please have Scanner create Idents with these
		/				names instead.
		/				For example- 
		/
		*/
		// Identifiers
		else if (ch >= 'A' && ch <= 'z') {
			StringBuilder str = new StringBuilder();
			str.append(ch);
			do {
				try {
					bit = in.read();
				} catch (IOException e) {
					System.err.println("We fail: " + e.getMessage());
				  }
				ch = (char) bit;
				str.append(ch);
			} while (ch >= 'a' && ch <= 'z'); 
			
			str.deleteCharAt(str.length() - 1);

		  // put the character after the identifier back into the input
		  try {
			in.unread(ch);
		  } catch (IOException e) {
				System.err.println("We fail: " + e.getMessage());
		  }

			return new IdentToken(str.toString());
		}

		else if (ch == '+' || ch == '-' || ch == '/' || ch == '*') {
			return new IdentToken(Character.toString(ch));
		}

		// Illegal character
		else {
			System.err.println("Illegal input character " + (char) ch);
			return getNextToken();
		}
	};
}

