package collinsworth_lab10_2015;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Expression tree class
 * @author Tobyn Collinsworth
 */
public class ExpressionTree
{
    /**
     * Tree node class
     */
    public class TreeNode
    {
        String value;
        TreeNode right;
        TreeNode left;

        /**
         * explicit constructor
         * @param value - the node's payload containing the postfix expression
         * @param right - the node to the right
         * @param left - the node to the left
         */
        public TreeNode(String value, TreeNode right, TreeNode left)
        {
            this.value = value;
            this.right = right;
            this.left = left;
        }
    }

    //stack to hold the individual characters of the expression and the root of the tree
    private Stack<TreeNode> stack;
    private TreeNode root;

    /**
     * explicit constructor that takes in the postfix expression string
     * @param postfixExpression - user input for the postfix expression
     */
    public ExpressionTree(String postfixExpression)
    {
        //initialize variables
        this.stack = new Stack<>();
        this.root = null;
        
        //string builder needed for appending decimal values together
        StringBuilder sb = new StringBuilder();
        
        //seperates the expression into individual characters and appropriately pushes to stack
        for (int i = 0; i < postfixExpression.length(); i++)
        {
            String value = String.valueOf(postfixExpression.charAt(i));

            //if operator
            if (value.equals("*") || value.equals("/") || value.equals("+") || value.equals("-"))
            {
                TreeNode right = stack.pop();
                TreeNode left = stack.pop();
                stack.push(new TreeNode(value, left, right));
            }
            //appends if the value is not a space to the stringbuilder
            else if (!value.equals(" "))
            {
                sb.append(value);
            }
            //if a space is found then the whole value is found and it is added to the stack
            else if (value.equals(" "))
            {
                stack.add(new TreeNode(sb.toString(), null, null));
                
                //resets the stringbuilder
                sb = new StringBuilder();
            }
        }
        root = stack.pop();
    }

    /**
     * calls a recursive function to find the height of the tree
     * @return the height of the tree 
     */
    public String getHeight()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(recursiveHeight(root));
        return sb.toString();
    }

    /**
     * recursive function to find height of the tree
     * @param focusNode - current node being handled
     * @return the total height of the tree
     */
    private int recursiveHeight(TreeNode focusNode)
    {
        if (focusNode == null)
        {
            return 0;
        }

        int left = recursiveHeight(focusNode.left);
        int right = recursiveHeight(focusNode.right);

        return left > right ? left + 1 : right + 1;
    }

    /**
     * calls a recursive function to get the original postfix expression from the tree
     * @return the postfix expression
     */
    public String getPostFix()
    {
        StringBuilder sb = new StringBuilder();
        postfixTraversal(root, sb);
        return sb.toString();
    }

    /**
     * recursive function to find the original postfix expression from the tree
     * @param focusNode - current node being handled
     * @param sb - string builder
     */
    private void postfixTraversal(TreeNode focusNode, StringBuilder sb)
    {
        if (focusNode == null)
        {
            return;
        }

        postfixTraversal(focusNode.left, sb);
        postfixTraversal(focusNode.right, sb);
        sb.append(focusNode.value).append(" ");
    }

    /**
     * calls a recursive function to get the total value of the expression
     * @return value of the expression
     */
    public double getValue()
    {
        return recursiveGetValue(root);
    }

    /**
     * a recursive function to find the value of the expression
     * @param focusNode - current node being handled
     * @return value of the expression
     */
    private double recursiveGetValue(TreeNode focusNode)
    {
        //if the node is a leaf then it is a value
        if (focusNode.left == null && focusNode.right == null)
        {
            return Double.parseDouble(focusNode.value);
        }

        //else each operator and perform each math function
        double leftValue = recursiveGetValue(focusNode.left);
        double rightValue = recursiveGetValue(focusNode.right);

        switch (focusNode.value)
        {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            default:
                return leftValue / rightValue;
        }
    }

    /**
     * performs all functions in the ExpressionTree class to display the height, 
     * the expression value, and the original postfix expression. It also prints
     * out the tree by level.
     */
    public void displayTree()
    {
        //calls the previous functions to show the value, height, and original expression
        System.out.println("Value of expression: " + getValue());
        System.out.println("Height of tree: " + getHeight());
        System.out.println("Original Postfix Expression: " + getPostFix());

        //checks if empty
        if (root == null)
        {
            System.out.println("List is empty.");
        }
        else
        {
            //creates a list of levels which contain all tree nodes in that level
            LinkedList<ArrayList<TreeNode>> treeLevelList = new LinkedList<>();
            
            //creates a queue of tree nodes as a linked list which act as the levels
            Queue<TreeNode> treeNodeQueue = new LinkedList<>();
            
            //root added to the queue
            treeNodeQueue.add(root);
            
            //goes through each item in the queue
            while (!treeNodeQueue.isEmpty())
            {
                //each level is made and added
                ArrayList<TreeNode> treeLevel = new ArrayList<>(treeNodeQueue.size());
                treeLevelList.add(treeLevel);
                for (TreeNode treeNode : new ArrayList<>(treeNodeQueue))
                {
                    treeLevel.add(treeNode);
                    
                    //looks left
                    if (treeNode.left != null)
                    {
                        treeNodeQueue.add(treeNode.left);
                    }
                    //looks right
                    if (treeNode.right != null)
                    {
                        treeNodeQueue.add(treeNode.right);
                    }
                    //atempts to remove the head of the queue
                    try
                    {
                        treeNodeQueue.remove();
                    } catch (Exception e)
                    {
                        throw e;
                    }
                }
            }
            
            //prints out the levels
            int count = 1;
            for (ArrayList<TreeNode> treeLevel : treeLevelList)
            {
                System.out.print("Level " + count + ": ");
                for (TreeNode node : treeLevel)
                {
                    System.out.print(node.value + " ");
                }
                count++;
                System.out.println();
            }
        }
    }
}
