/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Elevador implements Runnable {

	//Variáveis básicas.
	
    Filas filas;
    int vazio = 4;
    int contadorElevador = 1;
    int contadorEsquiador = 0;


    
    
    
    //Strings para impressão de resultados.
    
    String elevador = "";
    String tempoEmFila = "";

    public Elevador(Filas f) {
        filas = f;
    }

    public Filas getFilas() {
        return filas;
    }

    public void setFilas(Filas filas) {
        this.filas = filas;
    }

    @Override
    public synchronized void run() {
    	
        long start = System.currentTimeMillis();

        long end = start + 155 * 1000;

        //Cria-se a thread carrinho, cada carrinho irá descarregar até 4 esquiadores seguindo as regras definidas.
        
        while(System.currentTimeMillis() < end) {
            boolean leftTriple = false;
            boolean rightTriple = false;
            Esquiador esqui;
            
            
            int random = new Random().nextInt(2);

            if (random == 0) {
                if (filas.getLeftTriple().size() > 2
                        && vazio > 2) {
                    for (int i = 2; i >= 0; i--) {
                        esqui = filas.getLeftTriple().remove();
                        vazio--;

                        tempoEmFila = tempoEmFila
                                + "\nTempo em fila de esquiador em LeftTriple: "
                                + esqui.tempoEmFila() + " milisegundos.";
                    }

                    elevador = "LT LT LT";
                    contadorEsquiador++;
                    contadorEsquiador++;
                    contadorEsquiador++;
                    leftTriple = true;
                }
            } else {
                if (filas.getRightTriple().size() > 2
                        && vazio > 2) {
                    for (int i = 2; i >= 0; i--) {
                        esqui = filas.getRightTriple().remove();
                        vazio--;

                        tempoEmFila = tempoEmFila
                                + "\nTempo em fila de esquiador em RightTriple: "
                                + esqui.tempoEmFila() + " milisegundos.";
                    }

                    elevador = "RT RT RT";
                    contadorEsquiador++;
                    contadorEsquiador++;
                    contadorEsquiador++;
                    rightTriple = true;
                }
            }

            //Caso não tenha esquiadores nas filas triplas.
            
            if (!leftTriple && !rightTriple) {

                random = new Random().nextInt(2);

                //Utilizada para alternar as filas.

                boolean chave = random == 0;

                while (vazio > 0
                        && (filas.getLeftSingle().size() > 0
                        || filas.getRightSingle().size() > 0)) {

                    if (chave) {
                        if (filas.getLeftSingle().size() > 0) {
                            esqui = filas.getLeftSingle().remove();
                            vazio--;

                            tempoEmFila = tempoEmFila
                                    + "\nTempo em fila de esquiador em LeftSingle: "
                                    + esqui.tempoEmFila() + " milisegundos.";

                            elevador = "LS " + elevador;
                            contadorEsquiador++;
                        }

                        chave = false;
                    } else {
                        if (filas.getRightSingle().size() > 0) {
                            esqui = filas.getRightSingle().remove();
                            vazio--;

                            tempoEmFila = tempoEmFila
                                    + "\nTempo em fila de esquiador em RightSingle: "
                                    + esqui.tempoEmFila() + " milisegundos.";

                            elevador = elevador + "RS ";
                            contadorEsquiador++;
                        }

                        chave = true;
                    }
                }
            } else {
                if (leftTriple
                        && filas.getRightSingle().size() > 0) {
                    esqui = filas.getRightSingle().remove();
                    vazio--;

                    tempoEmFila = tempoEmFila
                            + "\nTempo em fila de esquiador em RightSingle: "
                            + esqui.tempoEmFila() + " milisegundos.";

                    elevador = elevador + " RS";
                    contadorEsquiador++;
                }

                if (rightTriple
                        && filas.getLeftSingle().size() > 0) {
                    esqui = filas.getLeftSingle().remove();
                    vazio--;

                    tempoEmFila = tempoEmFila
                            + "\nTempo em fila de esquiador em LeftSingle: "
                            + esqui.tempoEmFila() + " milisegundos.";

                    elevador = "LS " + elevador;
                    contadorEsquiador++;
                }
            }

            imprimir();
            
            elevador = "";
            tempoEmFila = "";
            contadorElevador++;
            vazio = 4;

            //Tempo de dormência do elevador = 5s
            
            try {
                this.wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Elevador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        float porcentagem = (float) 120/(contadorElevador*4);
        System.out.println();
        System.out.printf("Aproveitamento total de: %.2f", porcentagem*100);
        System.out.println("%");
        System.out.println();
        System.out.println("Fim da execução");
        System.exit(0);
    }

    private void imprimir() {
        

        System.out.println();
        System.out.println("*******************************************");
        System.out.println("Elevador: " + contadorElevador);
        System.out.println(elevador);
        System.out.println("Tempos em fila dos esquiadores:");
        System.out.println(tempoEmFila);

        
        System.out.println();
        System.out.println("Esquiadores que ja subiram: " + contadorEsquiador);
        System.out.println("********************************************");
        
        System.out.println();
        System.out.println("############################################");
        System.out.println("Filas após a ida do elevador:");
        System.out.println("LeftSingle: " + filas.getLeftSingle().size());
        System.out.println("RightSingle: " + filas.getRightSingle().size());
        System.out.println("LeftTriple: " + filas.getLeftTriple().size());
        System.out.println("RightTriple: " + filas.getRightTriple().size());
       /* float porcentagem = (float) 120/(contadorElevador*4);
        System.out.println();
        System.out.printf("Aproveitamento total de: %.2f", porcentagem*100);
        System.out.println("%");
     */ System.out.println("#############################################");


        
    
    }
}
