/*
*Author: Indra Shrestha
*Purpose: Class Project for displaying sorting method
           Communication between Client and Server
           Export and write File
* Date: 4/11/2021
* Version 1.3.0

 */
package NetworkMathQuiz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

public class Teacher_GUI extends javax.swing.JFrame {
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static String QuestString;
    // arrays    
    ArrayList<MathQuiz> mathQuiz_ArrayList = new ArrayList<>();
    static List<MathQuiz> mathList = new ArrayList<MathQuiz>();
    //Create object for Incorrect math questions
    static LinkedList<MathQuiz> mathQuiz_LinkedList=new LinkedList<>();
    //Create Binary tree object      
    BinaryTree<MathQuiz> bTree=new BinaryTree<>();
    
    public Teacher_GUI() {
        initComponents();        
    }
   /*************************************************************************************
       Method:   mathWrongANS(char charType)
       Purpose:    When server receive single for incorrect ans, Math question added in LINKED LIST
       Input:      CLIENT (student) input 'N'
       Output:     CORRECT QUESTION ANSWERS SETUP IN LINKEDLIST
    *************************************************************************************/
     static void mathWrongANS(char charType){
   
        int num1=Integer.parseInt(fNum_jTextField.getText());
        int num2=Integer.parseInt(sNum_jTextField.getText());
        int ans=Integer.parseInt(ans_jTextField.getText());
         // count=count+1;
        Object op =op_jComboBox.getSelectedItem();
         String sop=op.toString();                                     
        String displayAns=String.valueOf(ans);
        ans_jTextField.setText(displayAns);
        if(charType=='N'){
            mathQuiz_LinkedList.add(new MathQuiz(num1,sop,num2,ans));
        }         
     }
     
      void displayIncorrectAns(Collection c){
        Iterator iterator = c.iterator();
        String strQue="";        
        while (iterator.hasNext())
        {
            Object obj = iterator.next();
            strQue=strQue+" "+obj;
           // System.out.println(obj);
        }
        linkList_jTextArea.setText("HEAD<->"+strQue+"<->TAIL");
       // System.out.println();    
     }
    /*************************************************************************************
       Method:    MathQuestionSetUp()
       Purpose:    calculate math and get answers in accordance of assigning operator
       Input:      Teacher math (INPUT value 1, Value 2 , Select operator)
       Output:     Teacher correct ans
    *************************************************************************************/
     private void MathQuestionSetUp(){
         int num1=Integer.parseInt(fNum_jTextField.getText()) ;
        int num2=Integer.parseInt(sNum_jTextField.getText()) ;
        int ans=0;        
        Object op =op_jComboBox.getSelectedItem();
         String sop=op.toString();      
        switch (sop)
        {
            case "+":
                ans=(num1+num2);               
                break;
            case "-":
                ans=(num1-num2);               
                break;
            case "*":
                ans=(num1*num2);                
                break;
            case "/":               
                 ans =num1/num2;                
                break;
            default:
                break;
        }                          
        String displayAns=String.valueOf(ans);
        ans_jTextField.setText(displayAns);
        //*************************************************************
        // JTable SETUP
        DefaultTableModel model=(DefaultTableModel)ans_jTable.getModel();
        model.addRow(new Object[]{fNum_jTextField.getText(),op_jComboBox.getSelectedItem().toString(),sNum_jTextField.getText(),"=",ans_jTextField.getText()});       
        /*
         ***********************************************************
        */
        String strList="";        
        mathQuiz_ArrayList.add(new MathQuiz(num1,sop,num2,ans));      
        mathList.add(new MathQuiz(num1,sop,num2,ans));
        Iterator itr=mathQuiz_ArrayList.iterator();  
            while(itr.hasNext()){ 
            strList=strList+itr.next()+"\n";
        //System.out.println(itr.next());  
        }  
            
        binaryTree_jTextArea.setText(strList);
         //************************************************************                      
        // Math Question Store in Binary Tree      
        bTree.add(new MathQuiz(num1,sop,num2,ans));
     }      
  
      
     /******************************************************************/
    /************* Binary/Selection/Insertion sort method***************************/
    /******************************************************************/
     void SortOrdering(String shortType)
     {        
        if (mathList.size() == 0)
        {
            return;
        }        
        // convert the mathList to a fixed quizzes
        MathQuiz[] quizzes = mathList.toArray(new MathQuiz[mathList.size()]);
        if(shortType=="bubbleSort")
        {      //asc sort bubble sort method                  
            for (int i = 0; i < quizzes.length; i++)
            {
                for (int j = 0; j < quizzes.length - 1; j++)
                {
                    if (quizzes[i].compareTo(quizzes[j]) < 0)
                    {
                        // swap values
                        MathQuiz temp = quizzes[i];
                        quizzes[i] = quizzes[j];
                        quizzes[j] = temp;     
                    }
                }
            } // end inner for loop
        }
        else if(shortType=="selectionSort")
        {        
           for(int i=0; i<quizzes.length-1;i++)
           {  
               for(int j=i+1; j<quizzes.length;j++)
               {
                   if (quizzes[j].compareTo(quizzes[i]) > 0)
                   {
                    MathQuiz temp = quizzes[j];
                    quizzes[j] = quizzes[i];
                    quizzes[i] = temp;
                   }                                      
               }
           }
        }
        else if(shortType=="insertionSort"){
            for (int i = 1; i < quizzes.length; i++)
            {
                for (int j = i; j > 0; j--)
                {
                    if (quizzes[j].compareTo(quizzes[j-1]) < 0)
                    {                                                                                    
                        MathQuiz temp = quizzes[j];
                        quizzes[j] = quizzes[j-1];
                        quizzes[j-1] = temp;
                    }
                }
            }      
        }
         // reassign mathList
        mathList = new ArrayList<MathQuiz>(Arrays.asList(quizzes));            
     }
     void displaySortingTable(){
         DefaultTableModel model=(DefaultTableModel) ans_jTable.getModel(); 
        //  model.rowsRemoved(event);
        //first, remove all existing rows                
        if (model.getRowCount() > 0)
        {
          for (int i = model.getRowCount() -1; i > -1; i--)
          { model.removeRow(i);
          }
        }
         for(int i=0;i<mathQuiz_ArrayList.size();i++){            
            model.addRow(new Object[]{mathList.get(i).getLeft_op(),mathList.get(i).getOperator(),mathList.get(i).getRight_op(),"=",mathList.get(i).getAnswer()});         
        } 
     }
     /******************************************************************/
    /************* BINARY TREE TRAVERSALS ***************************/
    /******************************************************************/
    private void BinaryTreeDisplay(String tab){
        if(bTree.size()>0){
            String displayNode="";           
            if(tab=="PreOrder"){        
                //display elements preorder (depth-first) traversal method
                //Preorder traversal of elements (Root-L-R) 
                bTree.preOrder(bTree.getRoot());                
                displayNode=bTree.getTraversalString();
                displayNode="PRE -ORDER : "+displayNode.replace(",", " ");
                bTree.resetTraversalString();
            }
            else if(tab=="InOrder"){                
                //display elements in postorder (depth-first) traversal method
                //Inorder traversal of elements (L-Root-R)
                bTree.inOrder(bTree.getRoot());                
                displayNode=bTree.getTraversalString();
                displayNode="IN -ORDER : "+displayNode.replace(",", " ");
                bTree.resetTraversalString();
                //  System.out.println("**************************************************");
           } 
           else if(tab=="PostOrder"){
               //Postorder traversal of elements (L-R-Root)
                bTree.postOrder(bTree.getRoot());               
                displayNode=bTree.getTraversalString();
                displayNode="POST -ORDER : "+displayNode.replace(",", " ");
                bTree.resetTraversalString();               
           }
           binaryTree_jTextArea.setText(displayNode);
        }
        else
        {
            binaryTree_jTextArea.setText("There are no math questions to display");
        }

    }
    /******************************************************************/
    /************* BINARY TREE SAVE IN EXTERNAL TXT FILE ***************************/
    /******************************************************************/
    private void BinaryTreeSaveToTxt(String tab){    
    try {
        if(bTree.size()>0){
            BinaryTreeDisplay(tab);
            File file_name=null;
            String strFile="";
            if(tab=="PreOrder"){ 
              
                file_name=new File("preorder.txt");
                if(file_name.createNewFile())                     
                  System.out.println("File created: "+ file_name.getName());
                else                   
                 System.out.println("File already exists.");
                strFile="preorder.txt";                                                      
            } 
            else if(tab=="InOrder"){ 
                 file_name=new File("inorder.txt");
                if(file_name.createNewFile())                     
                  System.out.println("File created: "+ file_name.getName());
                else                   
                 System.out.println("File already exists.");

                 strFile="inorder.txt";                   
            }
             else if(tab=="PostOrder"){ 
                  file_name=new File("postorder.txt");
                if(file_name.createNewFile())                     
                  System.out.println("File created: "+ file_name.getName());
                else                   
                 System.out.println("File already exists.");

                 strFile="postorder.txt";      
             }
            int result = JOptionPane.showConfirmDialog(null, 
                 "You are about to write "+binaryTree_jTextArea.getText()+" \t to the external file "+ strFile+"\n Do you wish continue?","Booking Cancellation",JOptionPane.YES_NO_OPTION);
             // 0=yes, 1=no,
             if(result==0){
                 FileWriter myWriter = new FileWriter(strFile);
                 // FileWriter myWriter = new FileWriter("preorder.txt");
                 myWriter.write(binaryTree_jTextArea.getText());
                 myWriter.close();
             }

            }
            else
               binaryTree_jTextArea.setText("There are no math questions to save");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String ValidatorField(){
        String errMsg="Error:";
        if(fNum_jTextField.getText().isEmpty()){
            errMsg="First numeric fields are empty or contain non-numeric values!";    
        }
        if(sNum_jTextField.getText().isEmpty()){
            if(errMsg.length()>6)
             errMsg="First and Second numeric fields are empty or contain non-numeric values!";
            else 
              errMsg=errMsg+" Second numeric fields are empty or contain non-numeric values!";
        }        
        return errMsg;
    }
 
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fNum_jTextField = new javax.swing.JTextField();
        op_jComboBox = new javax.swing.JComboBox<>();
        sNum_jTextField = new javax.swing.JTextField();
        ans_jTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ans_jTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        bubbleAsc_jButton = new javax.swing.JButton();
        selectionDesc_jButton = new javax.swing.JButton();
        insertionAsc_jButton = new javax.swing.JButton();
        send_jButton = new javax.swing.JButton();
        exit_jButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        display_jButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        binaryTree_jTextArea = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        linkList_jTextArea = new javax.swing.JTextArea();
        displayPre_jButton = new javax.swing.JButton();
        savePre_jButton = new javax.swing.JButton();
        displayIn_jButton = new javax.swing.JButton();
        saveIn_jButton = new javax.swing.JButton();
        savePost_jButton = new javax.swing.JButton();
        displayPost_jButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Teacher");

        jPanel1.setBackground(new java.awt.Color(231, 244, 231));
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 600));

        jPanel2.setBackground(new java.awt.Color(0, 0, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Teacher");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Enter Question, then Click Send");

        jLabel3.setText("First Number:");

        jLabel4.setText("Operator:");

        jLabel5.setText("Second Number:");

        jLabel6.setText("Answer:");

        fNum_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fNum_jTextFieldKeyTyped(evt);
            }
        });

        op_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

        sNum_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sNum_jTextFieldKeyTyped(evt);
            }
        });

        ans_jTextField.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fNum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(op_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ans_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sNum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ans_jTextField, fNum_jTextField, op_jComboBox, sNum_jTextField});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(fNum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(op_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(sNum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ans_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ans_jTextField, fNum_jTextField, op_jComboBox, sNum_jTextField});

        ans_jTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ans_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lop", "op", "Rop", "=", "ans"
            }
        ));
        ans_jTable.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(ans_jTable);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Sort:");

        bubbleAsc_jButton.setText("1-Bubble (asc)");
        bubbleAsc_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubbleAsc_jButtonActionPerformed(evt);
            }
        });

        selectionDesc_jButton.setText("2-Selection (desc)");
        selectionDesc_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionDesc_jButtonActionPerformed(evt);
            }
        });

        insertionAsc_jButton.setText("3-Insertion (asc)");
        insertionAsc_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertionAsc_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(40, 40, 40)
                .addComponent(bubbleAsc_jButton)
                .addGap(51, 51, 51)
                .addComponent(selectionDesc_jButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(insertionAsc_jButton)
                .addGap(55, 55, 55))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(bubbleAsc_jButton)
                    .addComponent(selectionDesc_jButton)
                    .addComponent(insertionAsc_jButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        send_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        send_jButton.setText("Send");
        send_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_jButtonActionPerformed(evt);
            }
        });

        exit_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exit_jButton.setText("Exit");
        exit_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_jButtonActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Linked List (of all incorrectly answered exercises):");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        display_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        display_jButton.setText("Display List");
        display_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display_jButtonActionPerformed(evt);
            }
        });

        binaryTree_jTextArea.setEditable(false);
        binaryTree_jTextArea.setColumns(20);
        binaryTree_jTextArea.setRows(5);
        binaryTree_jTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(binaryTree_jTextArea);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Binary Tree(of all questions - added in the order that they were asked):");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(404, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        linkList_jTextArea.setEditable(false);
        linkList_jTextArea.setColumns(20);
        linkList_jTextArea.setRows(5);
        linkList_jTextArea.setWrapStyleWord(true);
        linkList_jTextArea.setMaximumSize(new java.awt.Dimension(164, 74));
        linkList_jTextArea.setMinimumSize(new java.awt.Dimension(164, 74));
        jScrollPane3.setViewportView(linkList_jTextArea);

        displayPre_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        displayPre_jButton.setText("Display");
        displayPre_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayPre_jButtonActionPerformed(evt);
            }
        });

        savePre_jButton.setText("Save");
        savePre_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePre_jButtonActionPerformed(evt);
            }
        });

        displayIn_jButton.setText("Display");
        displayIn_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayIn_jButtonActionPerformed(evt);
            }
        });

        saveIn_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveIn_jButton.setText("Save");
        saveIn_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveIn_jButtonActionPerformed(evt);
            }
        });

        savePost_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        savePost_jButton.setText("Save");
        savePost_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePost_jButtonActionPerformed(evt);
            }
        });

        displayPost_jButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        displayPost_jButton.setLabel("Display");
        displayPost_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayPost_jButtonActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pre-Order");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(0, 0, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("In-Order");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(0, 0, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Post-Order");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(send_jButton)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(exit_jButton))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(display_jButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(displayPre_jButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(savePre_jButton)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(displayIn_jButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveIn_jButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(displayPost_jButton)
                                .addGap(18, 18, 18)
                                .addComponent(savePost_jButton))
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exit_jButton)
                    .addComponent(send_jButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(display_jButton)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayPre_jButton)
                    .addComponent(savePre_jButton)
                    .addComponent(displayIn_jButton)
                    .addComponent(saveIn_jButton)
                    .addComponent(savePost_jButton)
                    .addComponent(displayPost_jButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void send_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_jButtonActionPerformed
       String errorMsg=ValidatorField();
        if(errorMsg.length()>6){ 
            JOptionPane.showMessageDialog(null, errorMsg,"SORRY-CHECK YOUR NUMBERS PLEASE!",2);
        }else{            
            try
            {
              MathQuestionSetUp();
              String messageOut = "";
              messageOut = fNum_jTextField.getText()+ " "+op_jComboBox.getSelectedItem().toString()+" "+sNum_jTextField.getText();
              //sendText_JTextField.getText().trim();
              messageOut=messageOut+","+ans_jTextField.getText();              
              dataOutputStream.writeUTF(messageOut);            
              send_jButton.setEnabled(false);             
          }
          catch(Exception e) 
          {
              String exceptionStr = "Server Send Error: " + e.getMessage();
              System.out.println("Error: "+exceptionStr);
              JOptionPane.showMessageDialog(null, exceptionStr, "SERVER SEND ERROR", JOptionPane.ERROR_MESSAGE);
          } // end send_JButtonActionPerformed() method
        }
        
          
        //send_jButton.setEnabled(false);
    }//GEN-LAST:event_send_jButtonActionPerformed

    private void fNum_jTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fNum_jTextFieldKeyTyped
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_fNum_jTextFieldKeyTyped
    public static void increment(){
        
    }
    private void sNum_jTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sNum_jTextFieldKeyTyped
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_sNum_jTextFieldKeyTyped

    private void display_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display_jButtonActionPerformed
        if(mathQuiz_LinkedList.isEmpty()){
        linkList_jTextArea.setText("There are no incorrectly answered questions");
        }
        else displayIncorrectAns(mathQuiz_LinkedList);                      
    }//GEN-LAST:event_display_jButtonActionPerformed

    private void bubbleAsc_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubbleAsc_jButtonActionPerformed
        //Array buuble sort               
        SortOrdering("bubbleSort");  
        displaySortingTable();                
    }//GEN-LAST:event_bubbleAsc_jButtonActionPerformed

    private void selectionDesc_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionDesc_jButtonActionPerformed
        SortOrdering("selectionSort");
        displaySortingTable();
    }//GEN-LAST:event_selectionDesc_jButtonActionPerformed

    private void insertionAsc_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertionAsc_jButtonActionPerformed
     SortOrdering("insertionSort");
     displaySortingTable();        
    }//GEN-LAST:event_insertionAsc_jButtonActionPerformed

    private void displayPre_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayPre_jButtonActionPerformed
        BinaryTreeDisplay("PreOrder");       
    }//GEN-LAST:event_displayPre_jButtonActionPerformed

    private void savePre_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePre_jButtonActionPerformed
       
        BinaryTreeSaveToTxt("PreOrder");                       
    }//GEN-LAST:event_savePre_jButtonActionPerformed

    private void saveIn_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveIn_jButtonActionPerformed
          BinaryTreeSaveToTxt("InOrder");
    }//GEN-LAST:event_saveIn_jButtonActionPerformed

    private void savePost_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePost_jButtonActionPerformed
         BinaryTreeSaveToTxt("PostOrder");
    }//GEN-LAST:event_savePost_jButtonActionPerformed

    private void displayIn_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayIn_jButtonActionPerformed
      BinaryTreeDisplay("InOrder");        
    }//GEN-LAST:event_displayIn_jButtonActionPerformed

    private void displayPost_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayPost_jButtonActionPerformed
        // display elements in postorder (depth-first) traversal method
        BinaryTreeDisplay("PostOrder");
    }//GEN-LAST:event_displayPost_jButtonActionPerformed

    private void exit_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_jButtonActionPerformed
       System.exit(0);
    }//GEN-LAST:event_exit_jButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Teacher_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teacher_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teacher_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teacher_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Teacher_GUI().setVisible(true);
            }            
        });
        try 
        { 
            serverSocket = new ServerSocket(1201); 
            socket = serverSocket.accept(); 
            dataInputStream = new 
            DataInputStream(socket.getInputStream()); 
            dataOutputStream = new 
            DataOutputStream(socket.getOutputStream());
            String messageIn = "";
            while(! messageIn.equals("exit"))
            { 
                messageIn = dataInputStream.readUTF();
               // System.out.println("message in: "+ messageIn);
                if(messageIn.length()>0){                    
                    char ansIn=(messageIn).charAt(0);
                    if(ansIn=='Y'){
                        linkList_jTextArea.setText("Student answered Correctly");                                        
                    }
                    else if(ansIn=='N')
                    {                       
                        mathWrongANS('N');                             
                        linkList_jTextArea.setText("Student answered incorrectly");                        
                    }
                    fNum_jTextField.setText("");
                    sNum_jTextField.setText("");
                    ans_jTextField.setText("");
                    send_jButton.setEnabled(true);
                    System.out.println("Client Message In "+ ansIn);
                } 
            }
            
        } 
        catch (SocketException se)
        {
            String exceptionStr = "Server Socket Error: " + se.getMessage();
            JOptionPane.showMessageDialog(null, exceptionStr, "SERVER ERROR", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e) 
        {
            String exceptionStr = "Server Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, exceptionStr, "SERVER ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTable ans_jTable;
    private static javax.swing.JTextField ans_jTextField;
    private static javax.swing.JTextArea binaryTree_jTextArea;
    private javax.swing.JButton bubbleAsc_jButton;
    private javax.swing.JButton displayIn_jButton;
    private javax.swing.JButton displayPost_jButton;
    private javax.swing.JButton displayPre_jButton;
    private javax.swing.JButton display_jButton;
    private javax.swing.JButton exit_jButton;
    private static javax.swing.JTextField fNum_jTextField;
    private javax.swing.JButton insertionAsc_jButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextArea linkList_jTextArea;
    private static javax.swing.JComboBox<String> op_jComboBox;
    private static javax.swing.JTextField sNum_jTextField;
    private javax.swing.JButton saveIn_jButton;
    private javax.swing.JButton savePost_jButton;
    private javax.swing.JButton savePre_jButton;
    private javax.swing.JButton selectionDesc_jButton;
    private static javax.swing.JButton send_jButton;
    // End of variables declaration//GEN-END:variables
}
