/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;


%%

/*      Usercode Section    */


/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column

/*
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup

/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

/*      Macro Declaration                      */

lineTerminator = \r|\n|\r\n

whiteSpace = {lineTerminator} | [ \t\f]

intNum = 0 | [1-9][0-9]* | [0-9]+\.[0-9]+

name = [a-zA-Z0-9_]+

stringLit = (\'[^\']*\'|\"[^\"]*\")


%%

/*      Lexical Rules Section                  */

<YYINITIAL>{

    ; { return symbol(sym.SEMI); }
    [sS][eE][lL][eE][cC][tT] { return symbol(sym.SELECT); }
    [dD][iI][sS][tT][iI][nN][cC][tT] { return symbol(sym.DISTINCT); }
    [fF][rR][oO][mM] { return symbol(sym.FROM); }
    [wW][hH][eE][rR][eE] { return symbol(sym.WHERE); }
    [aA][nN][dD] { return symbol(sym.AND); }
    [nN][oO][tT] { return symbol(sym.NOT); }
    [iI][nN] { return symbol(sym.IN); }
    [eE][xX][iI][sS][tT][sS] { return symbol(sym.EXISTS); }
    "," { return symbol(sym.COMMA); }
    "." { return symbol(sym.DOT); }
    "(" { return symbol(sym.LPAREN); }
    ")" { return symbol(sym.RPAREN); }
    "=" { return symbol(sym.COMPARISON); }

    {intNum}      { return symbol(sym.INTNUM, new Integer(yytext()));}
    
    {stringLit}       { String text = new String(yytext());
                         if (text.length() > 2) {
                            text = new String(text.substring(1, text.length() - 1));
                         } else {
                            //This could happen with the data ''
                            text = new String("");
                         }
                         return symbol(sym.STRING, new String(text));}
                         
    {name}              { return symbol(sym.NAME, new String(yytext()));}
    
    {whiteSpace}       { /* just skip what was found, do nothing */ } 
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Lexical Error"); }