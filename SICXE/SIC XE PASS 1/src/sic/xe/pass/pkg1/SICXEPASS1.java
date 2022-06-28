/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.xe.pass.pkg1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SICXEPASS1 {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        SIC x =new SIC();
        x.ReadOperations();
        x.ReadInstructions();
        x.calculateaddresses();
        x.printProgram();
        x.printSYMBOLTABLE();
        x.printLiteralTABLE();
    }

}