/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.xe.pass.pkg1;


public class Instruction {
    String label;
    String operation;
    String operand ;
    String objectcode ;
    String adress ;
    String error="";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Instruction(String label, String operation, String operand, String objectcode, String adress) {
        this.label = label;
        this.operation = operation;
        this.operand = operand;
        this.objectcode = objectcode;
        this.adress = adress;
    }

   

    public String getObjectcode() {
        return objectcode;
    }

    public void setObjectcode(String objectcode) {
        this.objectcode = objectcode;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

   

}
