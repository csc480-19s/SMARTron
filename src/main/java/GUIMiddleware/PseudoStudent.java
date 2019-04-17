/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIMiddleware;

public class PseudoStudent {

    private String[][] multi;
    private String[] array;
    
    public PseudoStudent(String[][] a, String[] s){
        this.multi = a;
        this.array = s;
    }

    public String[][] getMulti() {
        return multi;
    }

    public void setMulti(String[][] multi) {
        this.multi = multi;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
    
}
