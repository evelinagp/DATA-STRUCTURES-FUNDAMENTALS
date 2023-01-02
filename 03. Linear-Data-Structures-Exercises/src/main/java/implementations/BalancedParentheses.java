package implementations;

import interfaces.Deque;
import interfaces.Solvable;

import java.util.Scanner;
import java.util.Stack;

public class BalancedParentheses implements Solvable {


    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {

        Stack<Character> openBracketsStack = new Stack<>();
        /*I can not understand why this structure works as queue instead of stack
        /*    ArrayDeque<Character> openBracketsStack = new ArrayDeque<>();*/
        boolean areBalanced = false;

        for (int i = 0; i < parentheses.length(); i++) {
            char currentSymbol = parentheses.charAt(i);
            if (currentSymbol == '(' || currentSymbol == '[' || currentSymbol == '{') {
                openBracketsStack.push(currentSymbol);
            } else if (currentSymbol == ')' || currentSymbol == ']' || currentSymbol == '}') {
                if (openBracketsStack.isEmpty()) {
                    areBalanced = false;
                    break;
                }
                char lastOpenBracket = openBracketsStack.pop();
                if (lastOpenBracket == '(' && currentSymbol == ')') {
                    areBalanced = true;
                } else if (lastOpenBracket == '[' && currentSymbol == ']') {
                    areBalanced = true;
                } else if (lastOpenBracket == '{' && currentSymbol == '}') {
                    areBalanced = true;
                } else {
                    areBalanced = false;
                    break;
                }
            }
        }
        return areBalanced;
    }
}




