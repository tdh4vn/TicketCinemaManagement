/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Room;
import model.Seat;
import view.SearchRoomFrm;
import interf.*;
import view.EditRoomFrm;

/**
 *
 * @author HungTD
 * @since 25/04/2016
 */
public class RoomControl {
    private Connection con;
    private SearchRoomFrm searchRoomFrm;
    private EditRoomFrm editRoomFrm;
    public RoomControl(){
        //Chuoi ket noi csdl
        String dbUrl = "jdbc:mysql://localhost:3306/ticket_management";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = (Connection) DriverManager.getConnection (dbUrl, "root", "123456");
        }catch(Exception e) {
            e.printStackTrace();
        }

        searchRoomFrm = new SearchRoomFrm();
        //Them listener cho nut Search trong SearchRoomFrm
        searchRoomFrm.addSearchListener(new SearchRoomListener());
        searchRoomFrm.setEditRoomAction(new OpenEditRoomFrm());
        searchRoomFrm.setVisible(true);
    }
    
    class OpenEditRoomFrm implements OpenEditRoomFrmInterface{

        @Override
        public void openEditRoomFrm(Room room) {
            searchRoomFrm.setVisible(false);
            editRoomFrm = new EditRoomFrm(room);
            editRoomFrm.addEditRoomAction(new EditRoomListener());
            editRoomFrm.addDoneEditRoomAction(new CloseEditRoomListener());
            editRoomFrm.setVisible(true);
        }
    
    }
    class CloseEditRoomListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            editRoomFrm.setVisible(false);
            editRoomFrm.dispose();
            searchRoomFrm.setVisible(true);
            searchRoomFrm.fillTable(getAllRoom());
        }
    }
    
    class EditRoomListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Room room = editRoomFrm.getRoom();
            updateRoom(room);
            editRoomFrm.showMessage("Success");
        }
    }
    
    class SearchRoomListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchRoomFrm.getRoomID().equals("") && searchRoomFrm.getRoomName().equals("")){
                searchRoomFrm.fillTable(getAllRoom());
            } else if(!searchRoomFrm.getRoomID().equals("") && !searchRoomFrm.getRoomName().equals("")){
                Room room = searchRoomByIDAndName(Integer.parseInt(searchRoomFrm.getRoomID()),
                        searchRoomFrm.getRoomName());
                if(room == null){
                    searchRoomFrm.showMessage("No data found");
                } else {
                    searchRoomFrm.fillTable(room);
                }
            } else if (!searchRoomFrm.getRoomID().equals("")) {
                Room room = searchRoomByID(Integer.parseInt(searchRoomFrm.getRoomID()));
                if(room == null){
                    searchRoomFrm.showMessage("No data found");
                } else {
                    searchRoomFrm.fillTable(room);
                }
            } else if (!searchRoomFrm.getRoomName().equals("")) {
                ArrayList<Room> rooms = searchRoomByName(searchRoomFrm.getRoomName());
                if(rooms == null){
                    searchRoomFrm.showMessage("No data found");
                } else {
                    searchRoomFrm.fillTable(rooms);
                }
            }
        }
    }
    
    public void updateRoom(Room room){
        String sqlUpdateRoom = "UPDATE tblroom SET name=?,size=?,description=? WHERE id = ?";
        String sqlInsertSeat = "INSERT INTO tblseat (positionX, positionY, tblRoom_id) VALUES(?,?,?)";
        String sqlDeleteSeatByID = "DELETE FROM tblseat WHERE tblRoom_id = ?";
        try{
            PreparedStatement psUpdateRoom = con.prepareStatement(sqlUpdateRoom);
            psUpdateRoom.setString(1, room.getName());
            psUpdateRoom.setInt(2, room.getListSeat().size());
            psUpdateRoom.setString(3, room.getDescription());
            System.out.println(room.getDescription());   
            psUpdateRoom.setInt(4, room.getId());
            psUpdateRoom.execute();
            
            PreparedStatement psDeleteSeat = con.prepareStatement(sqlDeleteSeatByID);
            psDeleteSeat.setInt(1, room.getId());
            psDeleteSeat.execute();
            for(Seat seat : room.getListSeat()){
                PreparedStatement psInsertSeat = con.prepareStatement(sqlInsertSeat);
                psInsertSeat.setInt(1, seat.getPositionX());
                psInsertSeat.setInt(2, seat.getPositionY());
                psInsertSeat.setInt(3, room.getId());
                psInsertSeat.execute();
            }
         }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Room> getAllRoom(){
        String sql = "SELECT * FROM tblroom";
        ArrayList<Room> rooms = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSize(rs.getInt("size"));
                room.setDescription(rs.getString("description"));
                room.setListSeat(getSeatsInRoom(room.getId()));
                rooms.add(room);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rooms;
    }
    
    public ArrayList<Room> searchRoomByName(String name){
        String sql = "SELECT * FROM tblroom WHERE name LIKE ?";
        ArrayList<Room> rooms = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSize(rs.getInt("size"));
                room.setDescription(rs.getString("description"));
                room.setListSeat(getSeatsInRoom(room.getId()));
                rooms.add(room);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rooms;
    }
    
    public Room searchRoomByIDAndName(int roomID, String name){
        String sql = "SELECT * FROM tblroom WHERE id = ? and name LIKE ?";
        Room room = new Room();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomID);
            ps.setString(2, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            } else {
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSize(rs.getInt("size"));
                room.setDescription(rs.getString("description"));
                room.setListSeat(getSeatsInRoom(room.getId()));
                return room;
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Room searchRoomByID(int roomID){
        String sql = "SELECT * FROM tblroom WHERE id = ?";
        Room room = new Room();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomID);        
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            } else {
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSize(rs.getInt("size"));
                room.setDescription(rs.getString("description"));
                room.setListSeat(getSeatsInRoom(room.getId()));
                return room;
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Seat> getSeatsInRoom(int roomID){
        String sql = "SELECT * FROM tblseat WHERE tblRoom_id = ?";
        ArrayList<Seat> seats = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setPositionX(rs.getInt("positionX"));
                seat.setPositionY(rs.getInt("positionY"));
                seats.add(seat);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return seats;
    }
}
