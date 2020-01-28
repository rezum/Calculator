package edu.csc413.calculator.evaluator;


import edu.csc413.calculator.operators.OpenParenthesisOperator;
import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;
    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "()+-*^/ ";

    public Evaluator() {
        operandStack = new Stack<>(); //stack that will takes all operands ( 1,2,3,4,...)
        operatorStack = new Stack<>(); // stack that will take all operators ( (,),+,-,*,^,...)
    }

    public int eval(String expression) {
        String token; //a given operator or operand

        // The 3rd argument is true to indicate that the delimiters should be used
        // as tokens, too. But, we'll need to remember to filter out spaces.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        while (this.tokenizer.hasMoreTokens()) {
            // filter out spaces
            if (!(token = this.tokenizer.nextToken()).equals(" ")) {
                // check if token is an operand
                if (Operand.check(token)) {
                    operandStack.push(new Operand(token));
                }
                else {

                    if (!Operator.check(token)) { // if not an operator
                        System.out.println("*****invalid token******");
                        throw new RuntimeException("*****invalid token******");
                    }
                    else {
                        // TODO Operator is abstract - these two lines will need to be fixed:
                        // The Operator class should contain an instance of a HashMap,
                        // and values will be instances of the Operators.  See Operator class
                        // skeleton for an example.
                        Operator newOperator = Operator.getOperator(token); //token is now equals to newOperator

                        if (newOperator == Operator.getOperator("(")) { //according to priority =, "(" should go first, I use PEMDAS for example
                            operatorStack.push(newOperator); //operator stack now has "(" at bottom
                            continue; //it will stop otherwise
                        }

                        else if (newOperator == Operator.getOperator(")")) { //if the last thing in the operator stack is )

                            while ((operatorStack.peek() != Operator.getOperator("("))) { //checks if the top of operator stack is not (
                                Operator oldPr = operatorStack.pop(); // gets operator
                                Operand op2 = operandStack.pop(); //gets both operands
                                Operand op1 = operandStack.pop();
                                operandStack.push(oldPr.execute(op1, op2)); // applies the math
                            }
                            operatorStack.pop(); // once all run executes/ produces answer in ()
                            continue;// continues math equation in case there is any math after ()
                        }

                        else {
                            while (!operatorStack.isEmpty() && operatorStack.peek().priority() >= newOperator.priority()) {
                                //not empty and the top of stack is of greatest priority or equal to the token's priority
                                // note that when we eval the expression 1 - 2 we will
                                // push the 1 then the 2 and then do the subtraction operation
                                // This means that the first number to be popped is the
                                // second operand, not the first operand - see the following code
                                Operator oldOpr = operatorStack.pop();
                                Operand op2 = operandStack.pop();
                                Operand op1 = operandStack.pop();
                                operandStack.push(oldOpr.execute(op1, op2));
                            }
                            operatorStack.push(newOperator);// pushes the math equation again, anything that is not in (), probably exponents first if there

                        }
                    }
                }
            }
        }
        while ((!operatorStack.isEmpty())) {
            Operator oldOpr = operatorStack.pop();
            Operand op2 = operandStack.pop();
            Operand op1 = operandStack.pop();
            operandStack.push(oldOpr.execute(op1, op2));
        }// this takes anything that was in the operator stack and executes it out

        // Control gets here when we've picked up all of the tokens; you must add
        // code to complete the evaluation - consider how the code given here
        // will evaluate the expression 1+2*3
        // When we have no more tokens to scan, the operand stack will contain 1 2
        // and the operator stack will have + * with 2 and * on the top;
        // In order to complete the evaluation we must empty the stacks,
        // that is, we should keep evaluating the operator stack until it is empty;
        // Suggestion: create a method that processes the operator stack until empty.

        //Don't forget to change the return value!
        return operandStack.pop().getValue(); // want to get the answer! its a value
    }
}
