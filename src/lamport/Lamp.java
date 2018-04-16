/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamport;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tx
 */
public class Lamp {
    private int proc[][];
    private int numProcesos;
    private int numTiempos;
    private int incrementos;
    private int desv;
    private JTable tabla;
    private DefaultTableModel dtm;

    public Lamp(int numProcesos, int numTiempos,int incrementos,int desv,JTable tabla) {
        this.numProcesos = numProcesos;
        this.numTiempos = numTiempos;
        this.incrementos=incrementos;
        this.desv=desv;
        this.tabla=tabla;
        
        
        proc=new int[numProcesos][numTiempos];
        
        //los primeros son ceros
        for(int x=0;x<numProcesos;x++){
            proc[x][0]=0;
        }
        
        int incTemp=incrementos;
        for(int y=0;y<numProcesos;y++){
            for(int x=1;x<numTiempos;x++){
              proc[y][x]=incTemp; 
              incTemp+=incrementos;
            }
           incTemp=incrementos+desv;
           incrementos+=desv;
        }
        
    }
   
    
    public void sincronizarRelojes(int emisor,int time_emisor,int receptor,int time_receptor){
        int desface;
        
        if(proc[emisor][time_emisor]>=proc[receptor][time_receptor]){
            desface= proc[receptor][1] - proc[receptor][0];
            proc[receptor][time_receptor] = proc[emisor][time_emisor] + 1;
        
            for(int i = time_receptor + 1; i<this.numTiempos; i++){
                proc[receptor][i] = proc[receptor][i-1] + desface;
            }
        } else {
           JOptionPane.showMessageDialog(null, "no se cumple (a, b) < (c, d)", "información", INFORMATION_MESSAGE);
        }
        
        
    }
    
    public void mostrarProc(){
        String[] encabezado=new String[proc.length];
        
        for(int x=0;x<proc.length;x++){
           encabezado[x]="Proceso "+x;
        }
        
        dtm = new DefaultTableModel(null,encabezado);
        tabla.setModel(dtm);
   
        Object[] columna=new Object[proc[0].length];
        for(int x=0;x<proc[0].length;x++){
            for(int y=0;y<proc.length;y++){
                System.out.print(proc[y][x]+"  ");
                columna[y]=proc[y][x];
            }
            dtm.addRow(columna);
            System.out.println();
        }   
    }
}
