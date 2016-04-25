/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotriqui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author leyu
 */
public class ProyectoTriqui extends Application {
    
    static GridPane tablero;
    private int row = 0;
    private int colum = 0;
    private Tablero logicaTablero;
    
    @Override
    public void start(Stage escenario) {
        logicaTablero=new Tablero();
        int filas = 3;
        int columnas = 3;
         
        escenario.setTitle("Triqui");
        tablero = new GridPane();
         for(int i = 0; i < columnas; i++) {
            ColumnConstraints columna = new ColumnConstraints(40);
            tablero.getColumnConstraints().add(columna);
        }

        for(int i = 0; i < filas; i++) {
            RowConstraints fila = new RowConstraints(40);
            tablero.getRowConstraints().add(fila);
        }
        
        tablero.setGridLinesVisible(true);
        tablero.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for( Node node: tablero.getChildren()) {

                    if( node instanceof Label) {
                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
//                            System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                            row=GridPane.getRowIndex( node);
                            colum=GridPane.getColumnIndex( node);
                        }
                    }
                }
            }
        });
          
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label label = new Label("Label "+i + "/" + j);
                label.setMouseTransparent(true);
                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                label.setVisible(false);
                tablero.getChildren().add(label);
            }
        }         
        
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                final Pane pane = new Pane();
                pane.setOnMouseReleased(new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent me) {
                       pane.getChildren().add(Fichas.getFicha(row, colum,logicaTablero));                      
                    }
                });
                pane.getStyleClass().add("game-grid-cell");
                if (i == 0) {
                    pane.getStyleClass().add("first-column");
                }
                if (j == 0) {
                    pane.getStyleClass().add("first-row");
                }
                tablero.add(pane, i, j);                
            }
        }
          
        Scene scene = new Scene(tablero, (filas * 40) + 100, (columnas * 40) + 100, Color.WHITE);
        scene.getStylesheets().add("game.css");
        escenario.setScene(scene);
        escenario.show();
        
        
    }
    
        public static class Fichas {
        
            private static int jugada;
            private static boolean ganador=false;
            
        public static Node getFicha(int row, int colum, Tablero logicaTablero) {
            jugada = jugada+1;
            
            Rectangle rectangle = new Rectangle(10, 10f, 20, 20);
            rectangle.setFill(Color.BLUE);
            
            Circle circle = new Circle(20, 20f, 7);
            circle.setFill(Color.RED);
   
            
            Group group = new Group();
            if(jugada%2==0){
                boolean jugadaRealizada=false;
                for (int i = 0; i < logicaTablero.getCeldas().size(); i++) {
                    int posV=logicaTablero.getCeldas().get(i).getPosicionV();
                    int posH=logicaTablero.getCeldas().get(i).getPosicionH();
                    if(posV==colum && posH==row ){
                        jugadaRealizada=true;
                    }                       
                }
                if(!jugadaRealizada && !ganador){
                    Celda celda = new Celda('x', row, colum);
                    group.getChildren().add(rectangle);
                    logicaTablero.getCeldas().add(celda);
                    if(validarJugadaGanadora(logicaTablero, 'x')){
                      System.out.println("El ganador es x");   
                      ganador= true;
                    }                        
                }else if(ganador){
                    System.err.println("Juego Terminado");
                }else if(jugadaRealizada){
                    System.err.println("Por favor usar un espacio vacio");
                    jugada=jugada-1;
                }
            }else{
                boolean jugadaRealizada=false;
                for (int i = 0; i < logicaTablero.getCeldas().size(); i++) {
                    int posV=logicaTablero.getCeldas().get(i).getPosicionV();
                    int posH=logicaTablero.getCeldas().get(i).getPosicionH();
                    if(posV==colum && posH==row ){
                        jugadaRealizada=true;
                    }                       
                }
                if(!jugadaRealizada && !ganador){
                    Celda celda = new Celda('o', row, colum);
                    group.getChildren().add(circle);
                    logicaTablero.getCeldas().add(celda);
                    if(validarJugadaGanadora(logicaTablero, 'o')){
                      System.out.println("El ganador es o");   
                      ganador= true;
                    }                        
                }else if(jugadaRealizada){
                    System.err.println("Por favor usar un espacio vacio");
                    jugada=jugada-1;
                }else if(ganador){
                    System.err.println("Juego Terminado");
                }
            }
                      
            return group;
        }
        
       private static boolean validarJugadaGanadora(Tablero logicaTablero, char jugador){
           boolean ganador=false;
           int cantidadJugadas=logicaTablero.getCeldas().size();
           List<Celda> jugadas=logicaTablero.getCeldas();
           List<Celda> auxJugador= new ArrayList<Celda>();
           for (int i = 0; i <cantidadJugadas; i++) {
               if(jugadas.get(i).getValor()==jugador){
                   Celda celdaJugador= new Celda(jugador, jugadas.get(i).getPosicionH(), jugadas.get(i).getPosicionV());
                   auxJugador.add(celdaJugador);                   
               }
           }
           if(auxJugador.size()>=3){
               
               if(combinacion1(auxJugador)){
                   ganador=true;
               } else if(combinacion2(auxJugador)){
                   ganador=true;
               } else if(combinacion3(auxJugador)){
                   ganador=true;
               } else if(combinacion4(auxJugador)){
                   ganador=true;
               } else if(combinacion5(auxJugador)){
                   ganador=true;
               } else if(combinacion6(auxJugador)){
                   ganador=true;
               } else if(combinacion7(auxJugador)){
                   ganador=true;
               } else if(combinacion8(auxJugador)){
                   ganador=true;
               }               
           }
           return ganador;
       }
       
       private static boolean combinacion1(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==0 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==0 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==0 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
       
       private static boolean combinacion2(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==1 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==1 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
       
       private static boolean combinacion3(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==2 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==2 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==2 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
       
       private static boolean combinacion4(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==0 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==0){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==2 && jugadas.get(k).getPosicionV()==0){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
        
       private static boolean combinacion5(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==0 && jugadas.get(i).getPosicionV()==1){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==2 && jugadas.get(k).getPosicionV()==1){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
       
       private static boolean combinacion6(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==0 && jugadas.get(i).getPosicionV()==2){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==2){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==2 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
        
        private static boolean combinacion7(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==0 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==2 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
        
        private static boolean combinacion8(List<Celda> jugadas){
           boolean cumplida=false;
           
           for (int i = 0; i < jugadas.size(); i++) {
               if(jugadas.get(i).getPosicionH()==2 && jugadas.get(i).getPosicionV()==0){
                   for (int j = 0; j < jugadas.size(); j++) {
                       if(jugadas.get(j).getPosicionH()==1 && jugadas.get(j).getPosicionV()==1){
                           for (int k = 0; k < jugadas.size(); k++) {
                               if(jugadas.get(k).getPosicionH()==0 && jugadas.get(k).getPosicionV()==2){
                                   cumplida=true;
                               }
                            }
                        }
                    }
                }
            }
                    
           return cumplida;
       }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
