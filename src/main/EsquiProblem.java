/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class EsquiProblem {
	
    public static void main(String[] args) {    
    	
        
        Filas filas = new Filas();
        
        new Thread(new Elevador(filas)).start();
        
        Random gerador = new Random();
        
        //Determinado o tempo de execução 120s, cria-se 1 esquiador a cada segundo, então teremos 120 esquiadores totais
        
        long start = System.currentTimeMillis();

        long end = start + 120 * 1000;
        
        while(System.currentTimeMillis() < end){
            try {
                //Cria um novo thread esquiador.
                new Thread(new Esquiador(filas)).start();
                
                //Tempo de dormência do thread.
                Thread.sleep(gerador.nextInt(1980)+1);
                                
            } catch (InterruptedException ex) {
                Logger.getLogger(EsquiProblem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
}
