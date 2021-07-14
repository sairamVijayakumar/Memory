import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.util.*;
/**
 * Memory Spiel welches in einer Klasse als Controller, GUI und Main methode dient
 * <p>
 * M326: Memory
 *
 * @author Sairam Vijayakumar
 * @since 2021-06-20
 * @version 1.0
 */

public class GameM implements ActionListener {
    JFrame frame = new JFrame("Memory Game");

    JPanel field = new JPanel();    //Panels realisieren
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct_screen = new JPanel();

    JButton btn[] = new JButton[20];
    JButton start = new JButton("Start"); //Texte realisieren
    JButton over = new JButton("Exit");
    JButton easy = new JButton("Einfach");
    JButton hard = new JButton("Schwer");
    JButton inst = new JButton("Anleitung");
    JButton redo = new JButton("Nochmal Spielen");
    JButton goBack = new JButton("Main Menu");

    Random randomGenerator = new Random();
    private boolean purgatory = false;
    JLabel winner;
    Boolean game_over = false;
    int level=0;
    int score=0;

    String[] board;
    int[] boardQ=new int[20];
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String a[]=new String[10];
    boolean eh=true;

    private JLabel label = new JLabel("Anzahl Paare von 1-10");
    private JTextField text = new JTextField(10);
    private JTextArea instructM = new JTextArea("Wenn das Spiel beginnt, wird der Bildschirm\nmit Schaltflächenpaaren gefüllt.\n Merken Sie sich ihre Platzierung.\nSobald Sie eine beliebige Schaltfläche drücken, werden sie alle gelöscht. \n Ihr Ziel ist es, auf die passenden Schaltflächen in einer Reihe zu klicken.\nWenn Sie damit fertig sind, gewinnen Sie.\nJeder falsche Klick gibt Ihnen einen Punkt (diese sind schlecht).\n VIEL GLÜCK! \n"+"für eine einzelne Stufe: Geben Sie eine Stufe zwischen 1 und 10 ein.\nEinfache Stufe --> Wörter\nSchwere Stufe --> Zeichen\nWählen Sie einfach oder schwer und drücken Sie dann auf Start.");


    public GameM(){
        frame.setSize(500,300);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        start_screen.add(menu, BorderLayout.NORTH);
        start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
        menu3.add(mini, BorderLayout.CENTER);
        menu.add(label);
        menu.add(text);
        mini.add(easy, BorderLayout.NORTH);
        mini.add(hard, BorderLayout.NORTH);
        mini.add(inst, BorderLayout.SOUTH);


        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);
        easy.addActionListener(this);
        easy.setEnabled(true);
        hard.addActionListener(this);
        hard.setEnabled(true);
        inst.addActionListener(this);
        inst.setEnabled(true);


        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void setUpGame(int x,Boolean what){
        level=x;
        clearMain();

        board = new String[2*x];
        for(int i=0;i<(x*2);i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }

        String[] b = {":-D","X","O","-(*.*)-","<>","<(^-^)>","=>",";^P","ABC","123"};//Schwere version
        String[] c = {"Auto","Apfel","Biene","Clown","Diamant","Eichhörnchen","Falke","Graben","Hundert","Information"};//Einfache version
        if(what) a=c;//when "what" true ist mache a zu c uns starte
        else a=b;//sonst wähle b und mache es schwer

        for(int i=0;i<x;i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(x*2);
                    if(board[y]==null){
                        btn[y].setText(a[i]);
                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    public void hideField(int x){ // macht eine box leer
        for(int i=0;i<(x*2);i++){
            /*if(boardQ[i]==0)*/ btn[i].setText("");
        }
        shown=false;
    }
    public void switchSpot(int i){//vergleicht Karten miteinander um damit zu wissen welche Paare zusammen gehören
            if(btn[i].getText()==""){
                btn[i].setText(board[i]);
                //shown=true;
            } else {
                btn[i].setText("");
                //shown=false;
            }
        }

    public void showSpot(int i){
        btn[i].setText(board[i]);
    }
    public void showField(int x, String a[]){//zeigt die symbole
        for(int i=0;i<(x*2);i++){
            btn[i].setText(a[i]);
        }
        shown=true;
    }
    void waitABit(){

        try{
            Thread.sleep(5);
        } catch(Exception e){

        }
    }
    public boolean checkWin(){//kontrolliert ob alle Karten aufgedeckt wurden
        for(int i=0;i<(level*2);i++){
            if (board[i]!="done")return false;
        }
        winner();
        return true;
    }
    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
        end_screen.add(new TextField("Anzahl Fehler: " + score), BorderLayout.SOUTH);
        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);




    }
    public void goToMainScreen(){
        new GameM();
    }
    public void createBoard(){//einfach ein GUI
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.black);
        field.requestFocus();
    }
    public void clearMain(){//räumt das Main Menu auf
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }

    public void exeption() {

    }

    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            score++;
            temp=(level*2);
            temp2=30;
            purgatory=false;
        }
        if(source==start){ //start setzt die schwierigkeit fest und macht ein neues feld bereit
            try{
                level = Integer.parseInt(text.getText());
                if (level > 10 || level < 1){
                throw new Exception();
                }
            } catch (Exception e){
                System.out.println(level);
                level=1;
                JOptionPane.showMessageDialog(null,"Bitte Wählen Sie eine gültige Auswahl zwischen 1-10\n Das Programm wird nun Beendet","Es ist ein Fehler Aufgetreten",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }

            setUpGame(level, eh);// eh ist entweder true oder false
        }
        if(source==over){//quits
            System.exit(0);
        }
        if(source==inst){//einfach das Anleitungs GUI
            clearMain();

            start_screen.add(instruct_screen, BorderLayout.NORTH);

            JPanel one = new JPanel();
            one.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel two = new JPanel();
            two.setLayout(new FlowLayout(FlowLayout.CENTER));
            instruct_screen.setLayout(new BorderLayout());
            instruct_screen.add(one, BorderLayout.NORTH);
            instruct_screen.add(two, BorderLayout.SOUTH);

            one.add(instructM);
            two.add(goBack);
            goBack.addActionListener(this);
            goBack.setEnabled(true);
        }
        if(source==goBack){//zurück zum Menu
            frame.dispose();
            goToMainScreen();
        }
        if(source==easy){//
            eh=true;
            easy.setForeground(Color.BLUE);
            hard.setForeground(Color.BLACK);
        } else if(source==hard){
            eh=false;
            hard.setForeground(Color.BLUE);
            easy.setForeground(Color.BLACK);
        }

        for(int i =0;i<(level*2);i++){//Spielgeschehen falls Knopf gedrückt wird
            if(source==btn[i]){
                if(shown){
                    hideField(level);//falls es das erste mal ist werden Felder verdeckt
                }else{//sonst normal abspielen
                    switchSpot(i);
                    if(temp>=(level*2)){
                        temp=i;
                    } else {
                        if((board[temp]!=board[i])||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
                        }

                    }
                }

            }


        }


    }
    public static void main(String[] args) {
        new GameM();// Main Methode welche denm Konstruktor aufruft
    }

}
