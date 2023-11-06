/*START 101
READ N
MOVER BREG ONE 
MOVEM BREG TERM
MULT BREG TERM 
MOVER CREG TERM
ADD CREG ONE
MOVEM CREG TERM
COMP CREG N
BC LE AGAIN
MOVEM BREG RESULT
PRINT RESULT
STOP*/

import java.util.*;
import java.io.*;
public class spos1 {
    int find(String[][] opcode, String line){
        for (int j = 0; j < 21; j++) {
            if (line.equals(opcode[j][0])) {
                return j;
            }
        }
        return -1;
    }
    boolean findtab(String[] syms, String line){
        for (int i = 0; i < syms.length; i++) {
            if (line.equals(syms[i])) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        spos1 obj = new spos1();
        String opcode[][] = {
          {"STOP", "00", "IS"},
          {"ADD", "01", "IS"},
          {"SUB", "02", "IS"},
          {"MULT", "03", "IS"},
          {"MOVER", "04", "IS"},
          {"MOVEM", "05", "IS"},
          {"COMP", "06", "IS"},
          {"BC", "07", "IS"},
          {"DIV", "08", "IS"},
          {"READ", "09", "IS"},
          {"PRINT", "10", "IS"},
          {"START", "01", "AD"},
          {"END", "02", "AD"},
          {"ORIGIN", "03", "AD"},
          {"EQU", "04", "AD"},
          {"LTORG", "05", "AD"},
          {"DS", "01", "DL"},
          {"DC", "02", "DL"},
          {"AREG", "01", "RG"},
          {"BREG", "02", "RG"},
          {"CREG", "03", "RG"}
        };
        HashMap<String, Integer> symtab = new HashMap<>();
        HashMap<String, Integer> littab = new HashMap<>();
        String[] lits = new String[10];
        String[] syms = new String[10];
        String[] ic = new String[20];
        int lc = 0;
        int litcount = 0;
        int symcount = 0;
        int linecount = 0;
        Arrays.fill(lits, "nulll");
        Arrays.fill(syms, "nulll");
        try(Scanner sc = new Scanner(new File("text.txt"))){
            System.out.println("Reading File...");
            while (sc.hasNextLine()) {
                String lines = sc.nextLine();
                String[] line = lines.split(" ");
                if (line[0].equals(opcode[11][0])) {
                    lc = Integer.parseInt(line[1]);
                    ic[linecount] = lc + " (" + opcode[11][2] + ", " + opcode[11][1] + ")";
                    System.out.println(ic[linecount]);
                }
                if (line.length == 2 && !line[0].equals(opcode[11][0])) {
                    int a = obj.find(opcode, line[0]);
                    if (a == -1) {
                        System.out.println("Error!");
                        break;
                    }
                    else{
                        //System.out.println(opcode[a][0]);
                        if (line[1].startsWith("=")) {
                            if(!littab.containsKey(line[1])){
                                lits[litcount] = line[1];
                                littab.put(line[1], lc);
                                litcount++;
                            }
                            //System.out.println(line[1] +"present");
                        
                            ic[linecount] = lc + " (" + opcode[a][2] + ", " + opcode[a][1] + ")" + " (L, " + littab.get(line[1]) + ")";
                        }
                        else{
                            if(!symtab.containsKey(line[1]))
                            syms[symcount] = line[1];
                            symtab.put(line[1], lc);
                            symcount++;
                            //System.out.println(line[1] +"present");
                            ic[linecount] = lc + " (" + opcode[a][2] + ", " + opcode[a][1] + ")" + " (S, " + symtab.get(line[1]) + ")";
                        }
                        System.out.println(ic[linecount]);
                    }
                }
                if (line.length == 3) {
                    int a = obj.find(opcode, line[0]);
                    if (a == -1) {
                        System.out.println("Error!");
                        break;
                    }
                    else{
                        //System.out.println(opcode[a][0]);
                        int b = obj.find(opcode, line[1]);
                        if (b == -1) {
                            System.out.println("Error!");
                            break;
                        }
                        else{
                            if (line[2].startsWith("=")) {
                                if(!littab.containsKey(line[2])){
                                    lits[litcount] = line[2];
                                    littab.put(line[2], lc);
                                    litcount++;
                                }
                                //System.out.println(line[2] +"present");
                                ic[linecount] = lc + " (" + opcode[a][2] + ", " + opcode[a][1] + ") " + "(" + opcode[b][2] + ", " + opcode[b][1] + ") " + "(L, " + littab.get(line[2]) + ")";
                            }
                            else{
                                if(!symtab.containsKey(line[2])){
                                    syms[symcount] = line[2];
                                    symtab.put(line[2], lc);
                                    symcount++;
                                }
                                //System.out.println(line[2] +"present");
                                ic[linecount] = lc + " (" + opcode[a][2] + ", " + opcode[a][1] + ") " + "(" + opcode[b][2] + ", " + opcode[b][1] + ") " + "(S, " + symtab.get(line[2]) + ")";
                            }
                            System.out.println(ic[linecount]);
                        }
                    }
                }
                lc++;
            }
        }catch (Exception e) {
            System.out.println("Error!");
        }
        System.out.println("\n\n\nSymbol Table : ");
        for (int i = 0; i < syms.length; i++) {
            if(!syms[i].equals("nulll"))
                System.out.println((i + 1) + "\t" + syms[i] + "\t" + symtab.get(syms[i]));
        }

        System.out.println("\n\n\nLiteral Table : ");
        for (int i = 0; i < lits.length; i++) {
            if(!lits[i].equals("nulll"))
                System.out.println((i + 1) + "\t" + lits[i] + "\t" + littab.get(lits[i]));
        }
    }
}
