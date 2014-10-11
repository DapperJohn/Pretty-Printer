class IdentToken extends Token {
  private String name;

  public IdentToken(String s) {
    super(TokenType.IDENT);
    name = s;
  }
  
  public void print(int n){
	for (int i = 0; i < n; i++)
      System.out.print(" ");
	  
	System.out.print(name);
  }

  String getName() {
    return name;
  }
}
