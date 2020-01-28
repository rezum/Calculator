package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField txField = new TextField();
    private Panel buttonPanel = new Panel();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] bText = {
            "7", "8", "9", "+", "4", "5", "6", "- ", "1", "2", "3",
            "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression (or entry), clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[bText.length];

    public static void main(String argv[]) {
        EvaluatorUI calc = new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.txField.setPreferredSize(new Dimension(600, 50));
        this.txField.setFont(new Font("Courier", Font.BOLD, 28));

        add(txField, BorderLayout.NORTH);
        txField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button bt;
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            bt = new Button(bText[i]);
            bt.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = bt;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed
     * on our Calculator GUI.
     * @param eventObject Event object generated when a
     *                    button is pressed.
     */
    public void actionPerformed(ActionEvent eventObject) {
        // to connect the button press to the corresponding operators and operands

        Evaluator eval = new Evaluator();

        if ( eventObject.getActionCommand().equals("=")){ //when button is pressed and the button pressed is =
             txField.setText(eval.eval(this.txField.getText()) +"");
                    //sets text in the text box to be evaluated and to get entered into equation
             // + "" makes int value into a sting
        }
        else{
            txField.setText(txField.getText() + eventObject.getActionCommand());
            // if not =, get text and set text to add it to the button
            if ( eventObject.getActionCommand().equals("C")){ //unless that button is C
                txField.setText(""); // set text to blank
            }
            else if  (eventObject.getActionCommand().equals("CE")) { //unless that button is CE
                try { // well is the text box has text, the letters CE will be added at the end and then last three characters will be deleted
                    txField.setText(txField.getText().substring(0, txField.getText().length() - 3));
                    // substring allows two parameters which is what we want since CE will be added to end
                }
                catch ( Exception e ){
                    txField.setText(""); // once CE is applied till 0, execute a blank instead of CE
                }
            }
            else{

            }
        }
    }
}
