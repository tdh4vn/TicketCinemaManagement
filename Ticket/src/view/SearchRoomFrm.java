/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import interf.OpenEditRoomFrmInterface;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.Room;

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
    private OpenEditRoomFrmInterface openEditRoomFrmInterface;
   
    public SearchRoomFrm(){
        super("Search Room");
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        txtRoomID = new JTextField(15);
        txtRoomName = new JTextField(15);
        btnSearch = new JButton("Search");
        String[] columns = {"ID", "Name", "Size", "Description", "Action"};
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
    }
    /**
     * Phuong thuc lay ID cua Room can tim
     * @param null
     * @return String chua ID cua Room can tim
     * @author HungTD
    */
    public String getRoomID(){
        return txtRoomID.getText();
    }
    
    /**
     * Phuong thuc lay Name cua Room can tim
     * @param null
     * @return String chua Name cua Room can tim
     * @author HungTD
    */
    public String getRoomName(){
        return txtRoomName.getText();
    }
    public void addSearchListener(ActionListener actionListener){
        btnSearch.addActionListener(actionListener);
    }
    
    public void fillTable(ArrayList<Room> rooms){
        String[] columns = {"ID", "Name", "Size", "Description", "Action"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columns);
        for(Room room : rooms){
            Object[] objects = new Object[5];
            objects[0] = room.getId();
            objects[1] = room.getName();
            objects[2] = room.getSize();
            objects[3] = room.getDescription();
            objects[4] = "Edit";
            dtm.addRow(objects);
        }
        tblResult.setModel(dtm); 
        tblResult.getColumn("Action").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                if (hasFocus) {
                    if (column == 4) {
                        openEditRoomFrmInterface.openEditRoomFrm(rooms.get(row));
                    }
                }
                return new JButton("Edit");
            }
        });
    }
    public void fillTable(Room room){
        String[] columns = {"ID", "Name", "Size", "Description", "Action"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columns);
        
        Object[] objects = new Object[5];
        objects[0] = room.getId();
        objects[1] = room.getName();
        objects[2] = room.getSize();
        objects[3] = room.getDescription();
        objects[4] = "Edit";
        dtm.addRow(objects);
        
        tblResult.setModel(dtm); 
        tblResult.getColumn("Action").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                if (hasFocus) {
                    if (column == 4) {
                        openEditRoomFrmInterface.openEditRoomFrm(room);
                    }
                }
                return new JButton("Edit");
            }
        });
    }
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }

    
    public void setEditRoomAction(OpenEditRoomFrmInterface editRoomFrmInterface){
        this.openEditRoomFrmInterface = editRoomFrmInterface;
    }
}
