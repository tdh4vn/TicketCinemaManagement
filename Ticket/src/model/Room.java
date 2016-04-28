/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Lớp phòng chiếu phim
 * @author HungTD
 * @since 4/25/2016
 */
public class Room {
    private int id;
    private String name;
    private ArrayList<Seat> listSeat;
    private int size;
    private String description;

    public Room() {
        super();
    }

    public Room(int id, String name, ArrayList<Seat> listSeat, int size, String description) {
        super();
        this.id = id;
        this.name = name;
        this.listSeat = listSeat;
        this.size = size;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Seat> getListSeat() {
        return listSeat;
    }

    public void setListSeat(ArrayList<Seat> listSeat) {
        this.listSeat = listSeat;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
