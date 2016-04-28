/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Room;
import model.Seat;

/**
 *
 * @author HungTD
 */
public class EditRoomFrm extends JFrame{
    public static final int MAX_LEN_OF_SEAT_BOARD = 10;
    private JLabel lbID;
    private JTextField txtName;
    private JTextField txtDescription;
    private Room mRoom;
    private JButton btnShowEditSeat;
    private JButton btnEdit;
    private JButton btnDone;
    private JButton[][] maSeats;

    public EditRoomFrm(Room room){
        super("Edit Room");
        this.mRoom = room;
        maSeats = new JButton[10][10];
        lbID = new JLabel(String.valueOf(mRoom.getId()));
        txtName = new JTextField(mRoom.getName());
        txtDescription = new JTextField(mRoom.getDescription());
        btnShowEditSeat = new JButton("Edit Seats");
        btnEdit = new JButton("Edit");
        btnDone = new JButton("Done");
        this.setLayout(new GridLayout(1, 2));
        JPanel panel1 = new JPanel(new GridLayout(2, 1));
        JPanel panelControl = new JPanel(new GridLayout(4, 2));
        panelControl.setSize(600, 300);
        panelControl.add(new JLabel("ID :")); panelControl.add(lbID);
        panelControl.add(new JLabel("Name :")); panelControl.add(txtName);
        panelControl.add(new JLabel("Description :")); panelControl.add(txtDescription);
        panelControl.add(btnEdit); panelControl.add(btnDone);
        panel1.add(panelControl);
        panel1.add(new JPanel());
        this.add(panel1);
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(createSeatBoard(), BorderLayout.CENTER);
        this.add(panel2);
        this.setSize(1200, 600);
    }
    
    private JPanel createSeatBoard(){
        JPanel panelSeats = new JPanel(new GridLayout(MAX_LEN_OF_SEAT_BOARD, MAX_LEN_OF_SEAT_BOARD));
        for (int i = 0; i < MAX_LEN_OF_SEAT_BOARD; i++){
            for(int j = 0; j < MAX_LEN_OF_SEAT_BOARD; j++){
                JButton btn = new JButton(i + "-" + j);
                btn.setBackground(Color.BLUE);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(btn.getBackground().equals(Color.RED)){
                            btn.setBackground(Color.BLUE);
                        } else {
                            btn.setBackground(Color.RED);
                        }
                    }
                });
                maSeats[i][j] = btn;
                panelSeats.add(maSeats[i][j]);
            }
        }
        for(Seat seat : mRoom.getListSeat()){
            maSeats[seat.getPositionX()][seat.getPositionY()].setBackground(Color.RED);
        }
        
        return panelSeats;
    }
   
    
    public Room getRoom(){
        Room room = new Room();
        room.setId(mRoom.getId());
        room.setName(txtName.getText().toString());
        room.setDescription(txtDescription.getText().toString());
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(maSeats[i][j].getBackground().equals(Color.RED)){
                    Seat seat = new Seat();
                    seat.setPositionX(i);
                    seat.setPositionY(j);
                    seats.add(seat);
                }
            }
        }
        room.setListSeat(seats);
        return room;
    }
    
    public void addEditRoomAction(ActionListener actionListener){
        btnEdit.addActionListener(actionListener);
    }
    
    public void addDoneEditRoomAction(ActionListener actionListener){
        btnDone.addActionListener(actionListener);
    }
    
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
}
