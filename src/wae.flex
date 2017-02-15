/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
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
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
dec_int_lit = 0 | [1-9][0-9]*
   
/* Defining string literal. Can be any characters between two single quotes.
   Currently no method for escaping single quotes, that's a TODO item. */
string_lit = \'[^\']*\'
   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
   /* YYINITIAL is the state at which the lexer begins scanning.  So
   these regular expressions will only be matched if the scanner is in
   the start state YYINITIAL. */
   
<YYINITIAL> {
   
    /* Return the token SEMI declared in the class sym that was found. */
    ";" { return symbol(sym.SEMI); }
   
    /* Print the token found that was declared in the class sym and then
       return it. */
    "[sS][eE][lL][eE][cC][tT]" { return symbol(sym.SELECT); }
    "[dD][iI][sS][tT][iI][nN][cC][tT]" { return symbol(sym.DISTINCT); }
    "[nN][aA][mM][eE]" { return symbol(sym.NAME); }
    "[fF][rR][oO][mM]" { return symbol(sym.FROM); }
    "[wW][hH][eE][rR][eE]" { return symbol(sym.WHERE); }
    "[aA][nN][dD]" { return symbol(sym.AND); }
    "[nN][oO][tT]" { return symbol(sym.NOT); }
    "[cC][oO][mM][pP][aA][rR][iI][sS][oO][nN]" { return symbol(sym.COMPARISON); }
    "[iI][nN]" { return symbol(sym.IN); }
    "[eE][xX][iI][sS][tT][sS]" { return symbol(sym.EXISTS); }
    "," { return symbol(sym.COMMA); }
    "." { return symbol(sym.DOT); }
    "(" { return symbol(sym.LPAREN); }
    ")" { return symbol(sym.RPAREN); }
   
    /* If an integer is found print it out, return the token NUMBER
       that represents an integer and the value of the integer that is
       held in the string yytext which will get turned into an integer
       before returning */
    {dec_int_lit}      { return symbol(sym.INTNUM, new Integer(yytext())); }
   
    /* Since the regex starts with a single quote and ends with a single   quote, they need to be stripped out before the text is returned. */
    {string_lit}       { String text = new String(yytext());
                         if (text.length() > 2) {
                            text = new String(text.substring(1, text.length() - 1));
                         } else {
                            //This could happen with the data ''
                            text = new String("");
                         }
                         return symbol(sym.STRING, new String(text())) ;}
   
    /* Don't do anything if whitespace is found */
    {WhiteSpace}       { /* just skip what was found, do nothing */ }   
}


/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Lexical Error"); }
