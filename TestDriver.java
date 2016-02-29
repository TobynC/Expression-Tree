/**
 * Lab 10 for CS 1181
 * Creates an expression tree that evaluates a postfix mathematical expression
 * and evaluates the value as well as the height and prints the information to 
 * the console.
 */

package collinsworth_lab10_2015;

import java.util.Scanner;

/**
 * @author Tobyn Collinsworth
 * CS1181
 * Instructor: R. Volkers
 * TA: R. Brant
 */

public class TestDriver 
{
    /**
     * test driver
     * @param args - command line arguments 
     */
    public static void main(String[] args) 
    {
        //prompts for user input
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter expression.");        
        ExpressionTree tree = new ExpressionTree(in.nextLine());
        
        //calls the all inclusive displayTree method
        tree.displayTree();
    }
}
