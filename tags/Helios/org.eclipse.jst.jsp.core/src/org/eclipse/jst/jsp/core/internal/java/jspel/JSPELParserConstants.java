/*******************************************************************************
 * Copyright (c) 2005 BEA Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BEA Systems - initial implementation
 *     
 *******************************************************************************/
/* Generated By:JJTree&JavaCC: Do not edit this line. JSPELParserConstants.java */
package org.eclipse.jst.jsp.core.internal.java.jspel;

public interface JSPELParserConstants {

  int EOF = 0;
  int INTEGER_LITERAL = 5;
  int FLOATING_POINT_LITERAL = 6;
  int EXPONENT = 7;
  int STRING_LITERAL = 8;
  int BADLY_ESCAPED_STRING_LITERAL = 9;
  int TRUE = 10;
  int FALSE = 11;
  int NULL = 12;
  int DOT = 13;
  int GT1 = 14;
  int GT2 = 15;
  int LT1 = 16;
  int LT2 = 17;
  int EQ1 = 18;
  int EQ2 = 19;
  int LE1 = 20;
  int LE2 = 21;
  int GE1 = 22;
  int GE2 = 23;
  int NEQ1 = 24;
  int NEQ2 = 25;
  int LPAREN = 26;
  int RPAREN = 27;
  int COMMA = 28;
  int COLON = 29;
  int LBRACKET = 30;
  int RBRACKET = 31;
  int PLUS = 32;
  int MINUS = 33;
  int MULTIPLY = 34;
  int DIVIDE1 = 35;
  int DIVIDE2 = 36;
  int MODULUS1 = 37;
  int MODULUS2 = 38;
  int NOT1 = 39;
  int NOT2 = 40;
  int AND1 = 41;
  int AND2 = 42;
  int OR1 = 43;
  int OR2 = 44;
  int EMPTY = 45;
  int COND = 46;
  int IDENTIFIER = 47;
  int IMPL_OBJ_START = 48;
  int LETTER = 49;
  int DIGIT = 50;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<INTEGER_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<STRING_LITERAL>",
    "<BADLY_ESCAPED_STRING_LITERAL>",
    "\"true\"",
    "\"false\"",
    "\"null\"",
    "\".\"",
    "\">\"",
    "\"gt\"",
    "\"<\"",
    "\"lt\"",
    "\"==\"",
    "\"eq\"",
    "\"<=\"",
    "\"le\"",
    "\">=\"",
    "\"ge\"",
    "\"!=\"",
    "\"ne\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "\":\"",
    "\"[\"",
    "\"]\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"div\"",
    "\"%\"",
    "\"mod\"",
    "\"not\"",
    "\"!\"",
    "\"and\"",
    "\"&&\"",
    "\"or\"",
    "\"||\"",
    "\"empty\"",
    "\"?\"",
    "<IDENTIFIER>",
    "\"#\"",
    "<LETTER>",
    "<DIGIT>",
  };

}
