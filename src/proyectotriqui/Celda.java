/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotriqui;

/**
 *
 * @author leyu
 */
public class Celda {
    
    private char valor;
    private int posicionH;
    private int posicionV;

    public Celda(char valor, int posicionH,int posicionV){
        this.valor=valor;
        this.posicionH=posicionH;
        this.posicionV=posicionV;
    }
    
    public char getValor() {
        return valor;
    }

    public void setValor(char valor) {
        this.valor = valor;
    }

    public int getPosicionH() {
        return posicionH;
    }

    public void setPosicionH(int posicionH) {
        this.posicionH = posicionH;
    }

    public int getPosicionV() {
        return posicionV;
    }

    public void setPosicionV(int posicionV) {
        this.posicionV = posicionV;
    }
    
    
    
}
