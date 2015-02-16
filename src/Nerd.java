



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;


public class Nerd {
    public static void wipe(){
        PrintWriter writer;
        try{
        writer = new PrintWriter("results.txt","UTF-8");
        writer.print("");
        writer.close();
        }catch(Exception e){System.out.println("FILE NOT FOUND WAT");
    }
 
    }
    
    
    public static ArrayList<Integer> parse(){
        try{
        String total = readFile("results.txt");
        String[] info = total.split("X");
            //System.out.println(info.length);
            for (int i = 0; i < info.length; i++) {
               //System.out.println(info[i]+"\t"+i);
            }
            
            int max = 0;
            int winner = 3;
            
            for (int i = 3; i <=11; i+=4) {
                //System.out.println(info[i]);
                if(Integer.parseInt(info[i]) > max){
                    winner = i;
                    max = Integer.parseInt(info[i]);
                            }
            }
            //System.out.println("\n\n"+info[winner]);
        
            String bestPath = info[winner-1];
            
            ArrayList<Integer> pathList = new ArrayList<>();
            
            pathList.add(Integer.parseInt(info[winner-3].replaceAll("\\s", "")));
            pathList.add(Integer.parseInt(info[winner-2].replaceAll("\\s","")));
            pathList.add(Integer.parseInt(info[winner].replaceAll("\\s","")));
            for (int i = 0; i < bestPath.length(); i++) {
                if(Character.isDigit(bestPath.charAt(i)))
                    pathList.add(Character.getNumericValue(bestPath.charAt(i)));
            }
        return pathList;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("READ ERROR");}
        return null;
    }
    
 static String readFile(String path) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, StandardCharsets.UTF_8);
}
    public static void append(String str){
try {
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("results.txt", true)));
    out.println(str);
    out.close();
} catch (IOException e) {
    //exception handling left as an exercise for the reader
}
    }




}


