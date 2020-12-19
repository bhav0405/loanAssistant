import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;

public class LoanAssistant extends JPanel implements ActionListener,MouseListener{
    
    private static final long serialVersionUID = 1L;

    static JFrame frame;
    JLabel loanBal, intRate, nofPay, monPay, loanAna;
    JTextField loanBalT,intRateT,nofPayT,monPayT;
    JTextArea loanAnaTa;
    JButton computMonPay,newLoanAna,exit,enable1,enable2;

    public  LoanAssistant(){
        setLayout(null);
        
        //LABELS
        loanBal = new JLabel("Loan Balance"); 
        loanBal.setFont(new Font("Vardana",Font.PLAIN,22));
        loanBal.setBounds(10,20,150,24);
        add(loanBal);

        intRate = new JLabel("Interest Rate");
        intRate.setFont(new Font("Vardana",Font.PLAIN,22));
        intRate.setBounds(10,70,150,24);
        add(intRate);

        nofPay = new JLabel("Number of Payments");
        nofPay.setFont(new Font("Vardana",Font.PLAIN,22));
        nofPay.setBounds(10,120,250,24);
        add(nofPay);

        monPay = new JLabel("Monthly Payments");
        monPay.setFont(new Font("Vardana",Font.PLAIN,22));
        monPay.setBounds(10,170,250,24);
        add(monPay);

        loanAna = new JLabel("Loan Analysis");
        loanAna.setFont(new Font("Vardana",Font.PLAIN,22));
        loanAna.setBounds(600,20,250,24);
        add(loanAna);

        //TEXTFIELD
        loanBalT = new JTextField();
        loanBalT.setFont(new Font("Vardana",Font.PLAIN,14));
        loanBalT.setBounds(250,20,200,26);
        add(loanBalT);
        loanBalT.setFocusable(true);
        loanBalT.setHorizontalAlignment(SwingConstants.RIGHT);

        intRateT = new JTextField();
        intRateT.setFont(new Font("Vardana",Font.PLAIN,14));
        intRateT.setBounds(250,70,200,26);
        add(intRateT);
        intRateT.setFocusable(true);
        intRateT.setHorizontalAlignment(SwingConstants.RIGHT);

        nofPayT = new JTextField();
        nofPayT.setFont(new Font("Vardana",Font.PLAIN,14));
        nofPayT.setBounds(250,120,200,26);
        add(nofPayT);
        nofPayT.setFocusable(true);
        nofPayT.setHorizontalAlignment(SwingConstants.RIGHT);
        
        monPayT = new JTextField();
        monPayT.setFont(new Font("Vardana",Font.PLAIN,14));
        monPayT.setBounds(250,170,200,26);
        add(monPayT);
        monPayT.setFocusable(true);
        monPayT.setHorizontalAlignment(SwingConstants.RIGHT);

        //TEXTAREA
        loanAnaTa = new JTextArea();
        loanAnaTa.setFont(new Font("Vardana",Font.PLAIN,14));
        loanAnaTa.setBounds(600,70,350,200);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        loanAnaTa.setBorder(border);
        loanAnaTa.setEditable(false);
        add(loanAnaTa);
        loanAnaTa.setFocusable(false);

       //BUTTON
        computMonPay = new JButton("Compute Monthly Payment");
        computMonPay.setFont(new Font("Vardana",Font.PLAIN,16));
        computMonPay.setBounds(80,250,300,26);
        add(computMonPay);
        computMonPay.addActionListener(this);
        computMonPay.addMouseListener(this);
        computMonPay.setFocusable(false);
        computMonPay.setActionCommand("MonPay");

        newLoanAna = new JButton("New Loan Analysis");
        newLoanAna.setFont(new Font("vardana",Font.PLAIN,16));
        newLoanAna.setBounds(100,300,250,26);
        add(newLoanAna);
        newLoanAna.addActionListener(this);
        newLoanAna.addMouseListener(this);
        newLoanAna.setFocusable(false);
        newLoanAna.setEnabled(false);

        exit = new JButton("Exit");
        exit.setFont(new Font("vardana",Font.PLAIN,16));
        exit.setBounds(730,300,80,26);
        add(exit);
        exit.addActionListener(this);
        exit.addMouseListener(this);
        exit.setFocusable(false);

        enable1 = new JButton("X");
        enable1.setFont(new Font("Vardana",Font.PLAIN,20));
        enable1.setBounds(480,120,80,26);
        add(enable1);
        enable1.addActionListener(this);
        enable1.addMouseListener(this);
        enable1.setFocusable(false);

        enable2 = new JButton("X");
        enable2.setFont(new Font("Vardana",Font.PLAIN,20));
        enable2.setBounds(480,170,80,26);
        add(enable2);
        enable2.addActionListener(this);
        enable2.addMouseListener(this);
        enable2.setFocusable(false);

        setPreferredSize(new Dimension(960,340));
    }

    public void actionPerformed(ActionEvent ae){

        if(ae.getSource()==computMonPay){
            computMonPay.setEnabled(false);
            String str = computMonPay.getActionCommand();

            double balance, interest, payment,loanBalance,finalPayment;
            int months;
            double monthlyInterest, multiplier;
            balance = Double.valueOf(loanBalT.getText()).doubleValue();
            interest = Double.valueOf(intRateT.getText()).doubleValue();
            monthlyInterest = interest / 1200;

            if(str.equals("MonPay")){
                months = Integer.valueOf(nofPayT.getText()).intValue();
                multiplier = Math.pow(1 + monthlyInterest, months);
                payment = balance * monthlyInterest * multiplier / (multiplier - 1);
                monPayT.setText(new DecimalFormat("0.00").format(payment));
            }

            else{       
                payment = Double.valueOf(monPayT.getText()).doubleValue();
                months = (int)((Math.log(payment) - Math.log(payment - balance * monthlyInterest))/Math.log(1 + monthlyInterest));
                nofPayT.setText(String.valueOf(months)); 
            }

            payment = Double.valueOf(monPayT.getText()).doubleValue();
            // show analysis
            loanAnaTa.setText("Loan Balance: $" + new DecimalFormat("0.00").format(balance));
            loanAnaTa.append("\n" + "Interest Rate: " + new DecimalFormat("0.00").format(interest) + "%");
            // process all but last payment
            loanBalance = balance;
            for (int paymentNumber = 1; paymentNumber <= months - 1; paymentNumber++)
            {
                loanBalance += loanBalance * monthlyInterest - payment;
            }
            // find final payment
            finalPayment = loanBalance;
            if (finalPayment > payment)
            {
            // apply one more payment
                loanBalance += loanBalance * monthlyInterest - payment;
                finalPayment = loanBalance;
                months++;
                //monPayT.setText(String.valueOf(months));
            }

            loanAnaTa.append("\n\n" + String.valueOf(months - 1) + " Payments of $" + new DecimalFormat("0.00").format(payment));
            loanAnaTa.append("\n" + "Final Payment of: $" + new DecimalFormat("0.00").format(finalPayment));
            loanAnaTa.append("\n" + "Total Payments: $" + new DecimalFormat("0.00").format((months - 1) * payment + finalPayment));
            loanAnaTa.append("\n" + "Interest Paid $" + new DecimalFormat("0.00").format((months - 1) * payment + finalPayment - balance));
        }
        
        else if(ae.getSource()==newLoanAna){
            enable1.setVisible(true);
            enable2.setVisible(true);
            
            monPayT.setEditable(true);
            nofPayT.setEditable(true);
            monPayT.setText("");
            monPayT.setFocusable(true);

            monPayT.setBackground(Color.WHITE);
            nofPayT.setBackground(Color.WHITE);
            nofPayT.setText("");
            nofPayT.setFocusable(true);

            newLoanAna.setEnabled(false);
            loanAnaTa.setText("");
            computMonPay.setEnabled(true);
        }

        else if(ae.getSource()==exit){
            frame.dispose();
        }

        else if(ae.getSource()==enable2){
            enable2.setVisible(false);
            monPayT.setBackground(new Color(255,255,128));
            monPayT.setText("");
            monPayT.setEditable(false);
            monPayT.setFocusable(false);
            newLoanAna.setEnabled(true);
            computMonPay.setText("Compute Monthly Payment");
            computMonPay.setActionCommand("MonPay");
        }

        else if(ae.getSource()==enable1){
            enable1.setVisible(false);
            nofPayT.setBackground(new Color(255,255,128));
            nofPayT.setText("");
            nofPayT.setEditable(false);
            nofPayT.setFocusable(false);
            newLoanAna.setEnabled(true);
            computMonPay.setText("Compute Number of payments");
            computMonPay.setActionCommand("NoOfPay");
      }
    }

    public void mouseEntered(MouseEvent me){
        enable1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enable2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        computMonPay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newLoanAna.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void mouseExited(MouseEvent me){}
    public void mouseClicked(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}

    public static void createAndShowGUI() {
        frame = new JFrame("Loan Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(220,180);
        frame.setResizable(false);

        LoanAssistant loanAssis = new LoanAssistant();
        loanAssis.setOpaque(true);
        frame.setContentPane(loanAssis);

        frame.pack();
        frame.setVisible(true);  
    }

    public static void main(String...arg){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowGUI();
            }
        });
    }
}