/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotriqui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leyu
 */
public class Tablero {
    
    private int numeroCeldas;
    private List<Celda> celdas;
    
    public Tablero(){
        
        numeroCeldas=9;
        celdas= new ArrayList<Celda>();
        
    }

    public List<Celda> getCeldas() {
        return celdas;
    }

    public void setCeldas(List<Celda> celdas) {
        this.celdas = celdas;
    }
    
    
}
