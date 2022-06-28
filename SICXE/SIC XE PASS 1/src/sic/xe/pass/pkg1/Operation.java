/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.xe.pass.pkg1;


public class Operation {
    String opname;
    String opcode;

    public Operation(String opname, String opcode) {
        this.opname = opname;
        this.opcode = opcode;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
    
}
