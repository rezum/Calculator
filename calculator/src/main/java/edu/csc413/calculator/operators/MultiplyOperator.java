package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class MultiplyOperator extends Operator {
    //extends makes this class a child to the super class operator
    @Override
    //override makes so that the parent class can't really mess with child class, especially when objects have the same name
    public int priority() {

        return 2;// such as PEMDAS
    }

    public Operand execute (Operand op1, Operand op2){ // how the math is applied

        Operand ans =  new Operand (op1.getValue() * op2.getValue()) ;

        return ans;

    }

}
