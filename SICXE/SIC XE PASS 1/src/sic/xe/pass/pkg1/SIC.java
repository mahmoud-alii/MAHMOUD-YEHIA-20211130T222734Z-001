/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.xe.pass.pkg1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class
SIC {

    Hashtable<String, String> SYMBOLTABLE = new Hashtable<>();
    ArrayList<Operation> operations;
    ArrayList<Instruction> Instructions;
    ArrayList<Literal> LiteralTable = new ArrayList<Literal>();

    public void ReadOperations() throws FileNotFoundException {
        operations = new ArrayList<>();
        File f = new File("D:\\Downloads\\MAHMOUD&YEHIA-20211130T222734Z-001\\SICXE\\SIC XE PASS 1\\Instruction set.txt");
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String name = sc.next();
            String oppcode = sc.next();
            operations.add(new Operation(name, oppcode));
        }
    }

    public void ReadInstructions() throws FileNotFoundException {
        Instructions = new ArrayList<>();
        File f = new File("D:\\Downloads\\MAHMOUD&YEHIA-20211130T222734Z-001\\SICXE\\SIC XE PASS 1\\sourcecode.txt");
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.trim();
            String words[] = line.split("\\s+");
            if (words.length == 3) {
                //     System.out.println("3");
                String label = words[0];
                String operation = words[1];
                String operand = words[2];
                Instructions.add(new Instruction(label, operation, operand, "", ""));
            } else if (words.length == 2) {
                String label = "";
                String operation = words[0];
                String operand = words[1];
                Instructions.add(new Instruction(label, operation, operand, "", ""));
            } else if (words.length == 1) {
                String label = "";
                String operation = words[0];

                Instructions.add(new Instruction(label, operation, "", "", ""));
            }
        }

    }

    public void calculateaddresses() {
        String start = Instructions.get(0).getOperand();
        //  System.out.println(start);
        Instructions.get(0).setAdress(start);
        int x = Integer.parseInt(start, 16);
        int increment = 0;
        SYMBOLTABLE.put(Instructions.get(0).getLabel(), start);
        for (int i = 1; i < Instructions.size(); i++) {
            x += increment;
            String addressHexa = Integer.toHexString(x);
            addressHexa = addZeros(addressHexa, 4);
            Instructions.get(i).setAdress(addressHexa);
            String Operand = Instructions.get(i).getOperand();
            String label = (Instructions.get(i).getOperation());
            String line = Instructions.get(i).getLabel();

            String opname = Instructions.get(i).getOperation();

            if (opname.startsWith("+") || opname.startsWith("&")) {
                opname = opname.substring(1);
            }

            if (Operand.startsWith("=")) {
                Literal m = new Literal();
                m.setName(Operand);
                if (Operand.startsWith("=X")) {
                    m.setLength((Operand.length() - 4) / 2);
                    m.setValue(Operand.substring(3, Operand.length() - 1));
                } else {
                    m.setLength((Operand.length() - 4));
                    String value = "";
                    for (int j = 3; j < Operand.length() - 1; j++) {
                        int y = (int) Operand.charAt(j);
                        value += Integer.toHexString(y);
                    }
                    m.setValue(value);
                }
                LiteralTable.add(m);
            }

            if (!isValidOperation(opname) && !line.contains(".")) {
                Instructions.get(i).setError("Error Invalid Instruction");
                increment = 0;
            } else if (Instructions.get(i).getOperation().equalsIgnoreCase("RESW")) {
                int z = Integer.parseInt(Operand);
                increment = z * 3;
            } else if (label.startsWith("+")) {
                increment = 4;
            } else if (label.startsWith("&")) {
                increment = 5;
            } else if (line.startsWith(".")) {
                Instructions.get(i).setError("COMMENT");
                increment = 0;
            } else if (Instructions.get(i).getOperation().equalsIgnoreCase("RESTW")) {
                int z = Integer.parseInt(Operand);
                increment = z * 9;
            } else if (Instructions.get(i).getOperation().equalsIgnoreCase("BASE") || Instructions.get(i).getOperation().equalsIgnoreCase("Equ")) {
                increment = 0;
            } else if (label.equalsIgnoreCase("FIX") || label.equalsIgnoreCase("FLOAT") || label.equalsIgnoreCase("SIO") || label.equalsIgnoreCase("HIO") || label.equalsIgnoreCase("TIO")) {
                increment = 1;
            } else if (label.equalsIgnoreCase("MULR") || label.equalsIgnoreCase("SHIFTR") || label.equalsIgnoreCase("SHIFTL") || label.equalsIgnoreCase("SVC") || label.equalsIgnoreCase("TIXR") || label.equalsIgnoreCase("SUBR") || label.equalsIgnoreCase("CLEAR") || label.equalsIgnoreCase("DIVR") || label.equalsIgnoreCase("ADDR") || label.equalsIgnoreCase("COMPR") || label.equalsIgnoreCase("RMO")) {
                increment = 2;
            } else if (Instructions.get(i).getOperation().equalsIgnoreCase("RESB")) {
                int z = Integer.parseInt(Operand);
                increment = z;
            } else if (Instructions.get(i).getOperation().equalsIgnoreCase("BYTE")) {
                if (Operand.startsWith("x") || Operand.startsWith("X")) {
                    increment = (Operand.length() - 3) / 2;
                } else {
                    increment = (Operand.length() - 3);
                }
            } else {
                increment = 3;
            }

            if (!Instructions.get(i).getLabel().equals("")) {

                if (Instructions.get(i).getOperation().equalsIgnoreCase("EQU")) {
                    String value = SYMBOLTABLE.get(Instructions.get(i).getOperand());
                    SYMBOLTABLE.put(Instructions.get(i).getLabel(), value);

                } else {
                    SYMBOLTABLE.put(Instructions.get(i).getLabel(), addressHexa);
                }
            }

        }
        for (int i = 0; i < LiteralTable.size(); i++) {
            String addressHexa = Integer.toHexString(x);

            LiteralTable.get(i).setAddress(addressHexa);
            x += LiteralTable.get(i).getLength();
        }

    }

    public void printSYMBOLTABLE() throws IOException {
        System.out.println(SYMBOLTABLE);
        File f = new File("SymbolTable.txt");
        FileWriter w = new FileWriter(f);
        w.write(String.valueOf(SYMBOLTABLE));
        w.close();
    }

    public void printLiteralTABLE() throws IOException {
        for (Literal m : LiteralTable) {
            System.out.println(m.getName() + "\t" + m.getLength() + "\t" + m.getValue()+"\t"+m.getAddress());

        }
        File f = new File("D:\\Downloads\\MAHMOUD&YEHIA-20211130T222734Z-001\\SICXE\\SIC XE PASS 1\\LiteralTable.txt");
        FileWriter w = new FileWriter(f);

        w.write("Name\tLength\tValue\tAddress");
        w.write(System.lineSeparator());

        for (Literal m : LiteralTable) {
            w.write(m.getName() + "\t" + m.getLength() + "\t" + m.getValue()+"\t"+m.getAddress());
            w.write(System.lineSeparator());

        }
        w.close();
    }

    public String getoperationcode(String Operation) {
        for (Operation x : operations) {
            if (x.getOpname().equalsIgnoreCase(Operation)) {
                return x.getOpcode();
            }

        }
        return "";

    }

    public void printProgram() throws IOException {
        File f = new File("D:\\Downloads\\MAHMOUD&YEHIA-20211130T222734Z-001\\SICXE\\SIC XE PASS 1\\Location Count.txt");
        FileWriter w = new FileWriter(f);
        for (Instruction x : Instructions) {
            System.out.println(x.getAdress() + "\t" + x.getLabel() + "\t" + x.getOperation() + "\t" + x.getOperand() + "\t" + x.getObjectcode() + "\t" + x.getError());

            w.write(x.getAdress() + "\t" + x.getLabel() + "\t" + x.getOperation() + "\t" + x.getOperand() + "\t" + x.getObjectcode() + "\t");
            w.write(x.getError());
            w.write(System.lineSeparator());
        }
        w.close();
    }

    public String addZeros(String x, int length) {
        while (x.length() < length) {
            x = "0" + x;
        }
        return x;
    }

    public boolean isValidOperation(String op) {
        for (Operation s : operations) {
            if (s.getOpname().equalsIgnoreCase(op)) {
                return true;
            }
        }
        if (op.equalsIgnoreCase("EQU") || op.equalsIgnoreCase("BASE") || op.equalsIgnoreCase("word") || op.equalsIgnoreCase("byte") || op.equalsIgnoreCase("RESW") || op.equalsIgnoreCase("RESB") || op.equalsIgnoreCase("RESTW") || op.equalsIgnoreCase("start") || op.equalsIgnoreCase("end")) {
            return true;
        }
        return false;
    }

    public boolean isInstruction(String Instructionname) {
        if (!Instructionname.equalsIgnoreCase("word") && !Instructionname.equalsIgnoreCase("byte") && !Instructionname.equalsIgnoreCase("RESW") && !Instructionname.equalsIgnoreCase("RESB")) {
            return true;
        } else {
            return false;
        }
    }

}

