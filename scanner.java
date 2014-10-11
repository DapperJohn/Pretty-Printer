import java.io.*;
 
class Scanner {
  private PushbackInputStream in;
  private byte[] buf;
  private String identifiers = "+-.*/<=>!?:$%_&~^";
  public Scanner(InputStream i) { in = new PushbackInputStream(i); }
   
  public Token getNextToken() {
        buf = new byte[1000];
    int bite = -1;
        int next = -1;
    // It would be more efficient if we'd maintain our own input buffer
    // and read characters out of that buffer, but reading individual
    // characters from the input stream is easier.
    try {
      bite = in.read();
    } catch (IOException e) {
      System.err.println("We fail: " + e.getMessage());
    }
 
    // TODO: skip white space and comments !!done!!
   
    if(bite == 32)  //32 is unicode value for whitespace. skip it.
                return getNextToken();
   
    if(bite == 9) //9 is unicode value for tab. Trash.
        return getNextToken();
   
    if(bite == 10) //10 is line feed unicode value. Trash.
        return getNextToken();
   
    if(bite == 13) //13 is carriage return unicode value. Trash.
        return getNextToken();
   
    if(bite == 12) //12 is form feed character unicode value. Trash.
        return getNextToken();
   
    if(bite == 59) { //when it comes across a ; (value 59), read until end of line (value 10). Then return getNextToken again
                while(bite != 10) {
                        try {
                              bite = in.read();
                            } catch (IOException e) {
                              System.err.println("We fail: " + e.getMessage());
                            }          
                }      
                return getNextToken();
        }
       
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
      // We ignore the special identifier `...'.
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
      // TODO: scan a string into the buffer variable buf
        int counter = 0;
        while(ch != '"'){
                buf[counter] = (byte)ch;
                counter++;
                try {
                        ch = (char) in.read();
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
               
        }
      return new StrToken(new String(buf,0,counter));
    }
 
    // Integer constants
    else if (ch >= '0' && ch <= '9') {
      int i = ch - '0';
      // TODO: scan the number and convert it to an integer
      try {
                        next = in.read();
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
      while((char)next >= '0' && (char)next <= '9'){
                i = i*10 + (char)next - '0';
                try {
                        next = in.read();
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
      }
      // put the character after the integer back into the input
      // in->putback(ch);
      try {
                        in.unread(next);
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
      return new IntToken(i);
    }
 
    // Identifiers
    else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a') && (ch <= 'z') || (identifiers.indexOf(ch) > -1)) {
      // TODO: scan an identifier into the buffer
        int counter = 0;
        while((ch >= 'A' && ch <= 'Z') || (ch >= 'a') && (ch <= 'z') || (identifiers.indexOf(ch) > -1)){
                buf[counter] = (byte)ch;
                try {
                        ch = (char) in.read();
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
                counter++;
        }
      // put the character after the identifier back into the input
      // in->putback(ch);
         try {
                        in.unread((byte)ch);
                    } catch (IOException e) {
                      System.err.println("We fail: " + e.getMessage());
                    }
      return new IdentToken(new String(buf, 0, counter));
    } else {
        // Illegal character
        System.err.println("Illegal input character '" + (char) ch + '\'');
        return getNextToken();
    }  
  };
}
