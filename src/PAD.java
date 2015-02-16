
public class PAD {
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
    public static void main(String[] args) {
        short p=1;
        byte[][] array = {{1,2,3,4,5,6},
                          {2,3,4,5,6,1},
                          {1,2,3,4,5,6},
                          {2,3,4,5,6,1},
                          {1,2,3,4,5,6}
                          };
        
        
       Evaluator eval = new Evaluator(array,p,p,p,p,p,p);
       Solver solv = new Solver(eval,(byte)0,(byte)6);
       byte z = 0;
       byte o = 3;
       
       //byte[][] arrayPostSwap = solv.swap(z, o, (byte)(z+1), o, array);
       //eval.printGrid(array);
       //eval.printGrid(arrayPostSwap);
      
       
       
       //eval.evaluate(array);
       
       //solv.Solve((byte)32, (byte)0, (byte)2);
        for (int i = 0; i < 7; i++) {
            System.out.println(eval.combos[i]);
        }
        System.out.println(eval.totalCombos());
    }
    
}
