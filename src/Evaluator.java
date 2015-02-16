

import java.util.Arrays;

//NULL 0
//FIRE 1
//WATER 2
//WOOD 3
//LIGHT 4
//DARK 5
//HP 6
//UNKNOWN = 7
//NULL = 10
//PRE-DELETE: 11-16

public class Evaluator {
    short hp;
    short fire, water, wood, light, dark;
    byte[][] grid;
    
    //orbs in combos for each type:
    byte[] combos = new byte[7];
    
    //takes in different attack values and a grid:
    public Evaluator(byte[][] grid, short fire, short water, short wood, short light, short dark, short hp){
        this.fire = fire;
        this.water = water;
        this.wood = wood;
        this.light = light;
        this.dark = dark;
        this.hp = hp;
        this.grid = grid;
}
    
    
    public byte totalCombos(){
        return (byte)(combos[1] + combos[2]+combos[3]+combos[4]+combos[5]+combos[6]);
    }
    
    public void getDamage(){
        
    }
    
    public void getHeal(){
        
    }
    
    
    public void setGrid(byte[][]grid){
        this.grid = copy(grid);
    }
    
    public void resetCombos(){
        combos[1] = (byte)0;
        combos[2] = (byte)0;
        combos[3] = (byte)0;
        combos[4] = (byte)0;
        combos[5] = (byte)0;
        combos[6] = (byte)0;
    }
    
    public int evaluate(byte[][] grid){
        byte[][] temp;
        do 
        {
        for (byte i = 0; i < 6; i++) {//X values
            for (byte j = 0; j < 5; j++) {//Y VALUES
                evalNode(i, j, grid);
            }
        }
        
        temp = copy(grid);
        grid = newFall(grid);

        }while(!equalGrids(grid,temp));
        //System.out.println("those grids are totally the same bro");
        
        return 0;
    }
    
    private boolean equalGrids(byte[][] a, byte[][] b){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (a[j][i]!=b[j][i]){
                    
                    //System.out.println("X: "+i+"\tY: "+j);   
                    return false;
                }
            }
        }
        return true;
    }
    
    private byte[][] newFall(byte[][] grid){
        boolean changeMade = true;
        while(changeMade){
        changeMade = false;
        for (int i = 5; i >= 0; i--) {//X VALUE
            for (int j = 4; j >= 0; j--) {//Y VALUE
                if(grid[j][i] > 9){//if we outta here
                    changeMade = true;
                    if(j==0)
                        grid[j][i] = 7;
                    else{
                        if(grid[j-1][i]>10)
                            grid[j][i] = 10;
                        else{
                            grid[j][i] = grid[j-1][i];
                            grid[j-1][i] = 10;
                        }
                    }
                }
            }
        }
        }
        return grid;
    }
    
    public void printGrid(byte[][] grid){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------");
        
    }
    
    
    
    public void evalNode(byte x, byte y, byte[][] grid){
        byte h;
        byte v;
        byte temp = grid[y][x];
        byte cherries = 0;
        if(temp == 7)
            return;
        if (temp > 10)
            temp-=10;
        
        for (h = 0;  h <= 5; h++) {//POST: h is rightwards combo
            //if ball in invalid, not equal to origin, or is not pre-break version of origin... break out
            if (!(isValid((x+h),y,grid) && ((grid[y][x+h]==temp)||(temp==grid[y][x+h]-10))) )
                break;
        }
        cherries = 0;
        if(h >= 3){
            for (int i = 0;  i < h; i++) {
                if(grid[y][x+i]<10){
                    cherries++;
                grid[y][x+i] += 10;
                    //System.out.println(temp);
                combos[temp]++;
                }
            }
        if(cherries>2)
            combos[temp]++
            ;
        }
        for (v = 0;  v <= 4 ; v++) {//POST: v is downwards combo
            //if ball in invalid, not equal to origin, or is not pre-break version of origin... break out
            if (!(isValid((x),y+v,grid) && ((grid[y+v][x]==temp)||(temp==grid[y+v][x]-10))))
                break;
        }
        cherries = 0;
        if(v >= 3){
            for (int i = 0;  i < v; i++) {
                if(grid[y+i][x] < 10){
                cherries++;
                combos[temp]++;
                grid[y+i][x]+=10;
                }
            }
            if(cherries>2)
                combos[temp]++
                ;
        }
    }
    
    
    
    public boolean isValid(int x, int y, byte[][]grid){
        if (x<0||y<0||x>5||y>4)
            return false;
        if (grid[y][x] == 0 )//|| grid[y][x] == 7)
            return false;
        return true;
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
    
}

