

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Solver {
    byte[][] initialGrid;
    byte[] possibilities = {1,2,3,4};
    byte[] inverse = {0,3,4,1,2};
    byte[] morePossibilities = {0,1,2,3,4};
    Evaluator eval;
    ArrayList<LinkedList> paths = new ArrayList<>();
    byte x = 0;
    byte y = 0;
    byte mx = 0;
    byte my = 0;
    byte[] max;
    byte trueMax = 0;
    LinkedList<Byte>[] directions;
    
    LinkedList<Byte>[] master;
    
    LinkedList<Byte> trueMaster;

    public Solver(Evaluator eval, byte range1, byte range2){
        this.eval = eval;
        initialGrid = eval.grid;
    }
    
    private byte[][] copy(byte[][] grid){
        byte[][]temp = new byte[5][6];
        for (byte i = 0; i < 6; i++) {
            for (byte j = 0; j < 5; j++) {
                temp[j][i] = grid[j][i];
            }
        }
        return temp;
    }
    
    private byte[] copy(byte[] grid){
        byte[]temp = new byte[grid.length];
            for (byte j = 0; j < grid.length; j++) {
                temp[j] = grid[j];
            }
        return temp;
    }

    
    public void clear(int length){
        max = new byte[length];
        directions = new LinkedList[length];
        master = new LinkedList[length];
        for (byte i = 0; i < length; i++) {
            max[i] = 0;
            directions[i] = new LinkedList<>();
            master[i] = new LinkedList<>();
            trueMaster = new LinkedList<>();;
        }
    }
    
    byte pointer = 0;
    //byte path = 0;
    public void Solve(byte path,byte range1,byte range2){
        //starting variables:
        //this.path = path;
        initialGrid = copy(eval.grid);
        
        //double pointer = 1;
        
        for (byte i = range1; i < range2; i++) 
        {
            
            for (byte j = 0; j < 5; j++) 
            {
                //System.out.println("Moving to starting pos "+pointer);
                exploreFrom(i,j, path);
                //System.out.println((pointer/.3)+"% complete");
                pointer++;
            }
        }
        findMax(range1);
    }

    private void findMax(int range1){
        byte i;
        byte mptr = 0;
        //System.out.println("Start (X) = "+range1);
        for (i = 0; i < max.length; i++) {
            //System.out.println(master[i]+"\t"+max[i]+"\t");
            if(max[i] > trueMax){
               // System.out.println("trump");
                mptr=i;
                trueMax = max[i];
                trueMaster = (LinkedList)master[i].clone();
            }
            //System.out.println(max[i]);
        }
        byte tx = (byte)range1;
        byte ty = 0;
        
        for (int j = 0; j < mptr; j++) {
            
            if(ty==4){
                ty = 0;
                tx++;
            }
            else
                ty++;
        }   
        mx = tx;
        my = ty;
        
    }
    public void exploreFrom(byte x, byte y, byte path){
        this.x = x;
        this.y = y;
            //System.out.println("PATH = "+path);
            explore(copy(possibilities),(LinkedList)master[pointer].clone(),4,12,false);
            //beginning[pointer] = (LinkedList)master[pointer].clone();
            if(path > 12)
                explore(copy(possibilities),(LinkedList)master[pointer].clone(),4,12,false);
            //beginning[pointer] = (LinkedList)master[pointer].clone();
            if(path > 24)
                explore(copy(possibilities),(LinkedList)master[pointer].clone(),4,8,true);
            
            explore(copy(morePossibilities),(LinkedList)master[pointer].clone(),5,1,true);

        
        //explore(copy(possibilities), directions, 4, path );
        
        
        
        //System.out.println("PATH = " + path);
    }
    
    private byte explore(byte[] set, LinkedList directions, int n, int k,boolean enable){
        try{
        if (k==0){
            //System.out.println(x+"\t"+y);
            //System.out.println(directions.toString());
            byte temp = calculate(directions,x,y);
            //System.out.println(temp);
            //System.out.println("\n");
            if(temp>=max[pointer]){
            max[pointer] = temp;
            //directions.addAll(0, beginning[pointer]);
            master[pointer] = (LinkedList)directions.clone();
            }
            //directions.clear();
            return 1;
        }
        for (byte i = 0; i < n; i++) {
            if(!enable && directions.size()!=0 && inverse[set[i]]==(byte)directions.getLast())
                continue;
            LinkedList<Byte> newd = (LinkedList)directions.clone();
            newd.add(set[i]);
            explore(set,newd,n,k-1, enable);
        }}catch(Exception e){
            System.out.println("Stack trace!");
            System.out.println(e.getStackTrace());
        }
        return 1;
        
    }
    
    private byte calculate(LinkedList<Byte> d, byte x, byte y){

        eval.resetCombos();//reset evaluator
        byte[][] localGrid = copy(initialGrid);//get local grid
        for (byte b: d) {
            switch (b){
                case 1:
                    if (!eval.isValid(x-1,y,localGrid)){
                        return 0;
                        //eval.evaluate(localGrid);
                        //return eval.totalCombos();
                       }
                    localGrid = copy(swap(x,y,(byte)(x-1),(byte)y,localGrid));
                    x--;
                    
                    break;
                case 2:
                    if (!eval.isValid(x,y-1,localGrid)){
                        return 0;
                        //eval.evaluate(localGrid);
                        //return eval.totalCombos();
                       }
                    localGrid = copy(swap(x,y,(byte)(x),(byte)(y-1),localGrid));
                    y--;
                    break;
                    
                case 3:
                    if (!eval.isValid(x+1,y,localGrid)){
                        return 0;
                        //eval.evaluate(localGrid);
                        //return eval.totalCombos();
                       }
                    localGrid = copy(swap(x,y,(byte)(x+1),(byte)y,localGrid));
                    x++;

                    break;
                    
                case 4:
                    if (!eval.isValid(x,y+1,localGrid)){
                        return 0;
                        //eval.evaluate(localGrid);
                        //return eval.totalCombos();
                       }
                    localGrid = copy(swap(x,y,(byte)(x),(byte)(y+1),localGrid));
                    y++;
                    break;
                default:
                    eval.resetCombos();
                    eval.evaluate(localGrid);
                    //System.out.println("HOLY SHIT HUGE ERROR WTF");
                    return eval.totalCombos();
            }
            
            
        }
        
        
        
        
        eval.resetCombos();
        eval.evaluate(copy(localGrid));
            if(eval.totalCombos() > 30)//ITS JUST DOING THE FIRST SWAP
            //eval.printGrid(localGrid)
                    ;
            return eval.totalCombos();
    }
    
    
    public byte[][] swap(byte x1, byte y1, byte x2, byte y2, byte[][] grid){
        //byte[][] grid = copy(eval.grid);
        grid = copy(grid);
        byte temp;
        //System.out.println("\nPRESWAP:\n");
        //eval.printGrid(grid);
        temp = grid[y2][x2];
        grid[y2][x2] = grid[y1][x1];
        grid[y1][x1] = temp;
        //System.out.println("\nSWAP FROM:\t("+x1+","+y1+")\tto\t("+x2+","+y2+")\n");
        //eval.printGrid(grid);
        return grid;
    }
    
}
