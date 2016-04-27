/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Lớp mô tả ghế ngồi trong phòng chiếu
 * @author Hungtdh4vn
 * @since 4/25/2016
 */
public class Seat {
    private int id;
    private int positionX;
    private int positionY;

    public Seat(int id, int positionX, int positionY) {
        super();
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Seat() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
