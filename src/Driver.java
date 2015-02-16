



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Driver {



    private static ExecutorService service = Executors.newFixedThreadPool(10);
    
    
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
    
    static byte[] sample =  {0,0,0,0,0,0
                            ,0,0,0,0,0,0
                            ,0,0,0,0,0,0
                            ,0,0,0,0,0,0
                            ,0,0,0,0,0,0
                            };
    
    short dmg = 1;
    byte path = 25;
     ArrayList<Integer> finalList = new ArrayList<>();
    public void insertByte(int index, int input){
        sample[index] = (byte)input;
    }
    
    int x = -1;
    int y = -1;
    int combos = -1;
    public void parse(){
        finalList = Nerd.parse();
        x = finalList.remove(0);
        y = finalList.remove(0);
        combos = finalList.remove(0);
        System.out.println(finalList.toString());
        System.out.println(combos);
    }
    
    public void kill(){
        System.exit(0);
    }
    
    public int get(int index){
        return finalList.get(index);
    }
    
    
    public int getStep(int index){
        return finalList.get(index);
    }
    
    public void wipe(){
        Nerd.wipe();
        finalList = new ArrayList();
        x = -1;
        y = -1;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    private boolean containsZero(byte[] a){
        for (int i = 0; i < a.length; i++)
            if (a[i] ==0)
                return true;
        return false;
    }
    
    public void drive(int path){
        if(containsZero(sample)){
            System.out.println("Error, grid countains unreadable value");
            return;
        }
        this.path = (byte)path;
        Callable<Void> callablePuzzler1 = new Callable<Void>(){
            //@Override
            public Void call() throws Exception{
                puzzle(0,2);
                return null;
            }
        };
        Callable<Void> callablePuzzler2 = new Callable<Void>(){
            //@Override
            public Void call() throws Exception{
                puzzle(2,4);
                return null;
            }
        };
        Callable<Void> callablePuzzler3 = new Callable<Void>(){
            //@Override
            public Void call() throws Exception{
                puzzle(4,6);
                return null;
            }
        };

    List<Callable<Void>> taskList = new ArrayList<>();
    taskList.add(callablePuzzler1);
    taskList.add(callablePuzzler2);
    taskList.add(callablePuzzler3);

    ExecutorService executor = Executors.newFixedThreadPool(3);
    
    try{
        List<Future<Void>> futureList = executor.invokeAll(taskList);
        for(Future<Void> voidFuture : futureList){
            try{
                
                voidFuture.get(10,TimeUnit.SECONDS);
                
            }catch(Exception e){
                e.printStackTrace();
               
                
                System.out.println("woops");}
            
            
            
        }
    }catch(Exception e){
        System.out.println("surprise surprise...");
    
    }
    
    }
    public void puzzle(int a, int b){
        byte p = 0;
        byte[][] array = new byte[5][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                array[j][i] = sample[p++];
            }
        }
        Evaluator eval = new Evaluator(array, dmg,dmg,dmg,dmg,dmg,dmg);
        eval.setGrid(array);
        
        Solver solv = new Solver(eval, (byte)a, (byte)b);
        solv.clear(12);
        solv.Solve(path,(byte)a,(byte)b);
        Nerd.append(solv.mx + "X"+solv.my + "X"+solv.trueMaster.toString()+"X"+solv.trueMax+"X");
        //System.out.println("\n\n"+solv.mx+"\t"+solv.my+"\n\n"+solv.trueMaster.toString()+"\n\n"+solv.trueMax+"\n");
        Thread.currentThread().interrupt();
        return;
    }
    
    
    //taskList.add(callablePuzzler);
    
}

