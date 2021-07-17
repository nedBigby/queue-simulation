/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ManejoArchivo {
    
    private File archivoEntrada;
    private File archivoSalida;
    private TablaLlegadas Tl = new TablaLlegadas();
    private ArrayList<TablaServicio> Ts = new ArrayList();
    private String salida;

    public ManejoArchivo() {
        this.salida="";
        this.archivoEntrada = new File("archivos/Entrada.txt");
        this.archivoSalida =  new File("archivos/Salida.txt");
        if(!this.archivoEntrada.exists()){
            try{
               this.archivoEntrada.createNewFile();
            }catch(IOException ex){
            }
            
        }
        if(!this.archivoSalida.exists()){
            try{
               this.archivoSalida.createNewFile();
            }catch(IOException ex){
            }
            
        }
    }
    
    public int obtenerPosicion(String linea){
        char spc = ' ';
        for(int i=0;i<linea.length();i++)
            if(linea.charAt(i) == spc)
                return i;
        return linea.length();
    }
    
    public String actualizarLinea(String linea){
        char spc = ' ';
        String newLine;
        for(int i=0;i<linea.length();i++)
            if(linea.charAt(i) == spc)
                return newLine = linea.substring(i+1);
        return "";
    }
    
    public int obtenerValor(String Linea,int pos){
        String number = Linea.substring(0,pos);
        return Integer.parseInt(number);
    }
    
    public void leerArchivoLlegadas(){
        String op = "Inicio";
        int valorArreglo=-1;
        try{
            BufferedReader bf = new BufferedReader(new FileReader(this.archivoEntrada));
            String bfRead;
            while((bfRead = bf.readLine()) != null){
                if(bfRead.equals("Tiempos entre Llegadas"))
                    op = "Llegadas";
                else
                    if(bfRead.equals("Tiempos de Servicio")){
                        op = "Servicio";
                        valorArreglo++;
                        Ts.add(valorArreglo,new TablaServicio());
                    }else
                        if(op.equals("Llegadas") && !bfRead.equals(""))
                            Tl.addtiempoEntreLlegadas(this.obtenerValor(bfRead,this.obtenerPosicion(bfRead)),
                                this.obtenerValor(this.actualizarLinea(bfRead),this.obtenerPosicion(this.actualizarLinea(bfRead))));
                                
            }         
        }catch(IOException e){
        }
    }
    
    public void leerArchivoServicios(int valor){
        String op = "Inicio";
        int valorArreglo=-1;
        try{
            BufferedReader bf = new BufferedReader(new FileReader(this.archivoEntrada));
            String bfRead;
            while((bfRead = bf.readLine()) != null){
                if(bfRead.equals("Tiempos entre Llegadas"))
                    op = "Llegadas";
                else
                    if(bfRead.equals("Tiempos de Servicio")){
                        op = "Servicio";
                        valorArreglo++;
                        Ts.add(valorArreglo,new TablaServicio());
                    }else
                        if(op.equals("Servicio") && !bfRead.equals("") && valor==valorArreglo)
                            Ts.get(valorArreglo).addtiempoDeServicios(this.obtenerValor(bfRead,this.obtenerPosicion(bfRead)),
                                this.obtenerValor(this.actualizarLinea(bfRead),this.obtenerPosicion(this.actualizarLinea(bfRead))));
            }         
        }catch(IOException e){
        }
    }
    
    public void escribirArchivo(String valor){
        this.salida = this.salida + valor;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivoSalida));
            PrintWriter pw = new PrintWriter(bw);
            pw.write(this.salida);
            pw.close();
            bw.close();
        }catch(IOException e){
        }
    }

    public TablaLlegadas getTablaLlegadas() {
        return Tl;
    }

    public TablaServicio getTablaServicio(int i) {
        return Ts.get(i);
    }
    
}
