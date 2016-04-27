/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HungTD
 * @since 25/04/2016
 */
public class SearchRoomFrm extends JFrame{
    private JTextField txtRoomID;
    private JTextField txtRoomName;
    private JButton btnSearch;
    private JTable tblResult;
    private ArrayList<JButton> btnSelects;
   
    public SearchRoomFrm(){
        super("Search Room");
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        txtRoomID = new JTextField(15);
        txtRoomName = new JTextField(15);
        btnSearch = new JButton("Search");
        btnSelects = new ArrayList<JButton>();
        String[] columns = {"ID", "Name", "Size", "Action"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columns);
        tblResult = new JTable(dtm);
        JScrollPane jScrollPane = new JScrollPane(tblResult);
        JPanel panelSearchTools = new JPanel(new GridLayout(3,2));
        
        panelSearchTools.add(new JLabel("ID: ")); panelSearchTools.add(txtRoomID);
        panelSearchTools.add(new JLabel("Name: ")); panelSearchTools.add(txtRoomName);
        panelSearchTools.add(btnSearch);
        
        this.setLayout(new BorderLayout());
        this.add(panelSearchTools, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        SearchRoomFrm searchRoomFrm = new SearchRoomFrm();
        
    }
    
}
