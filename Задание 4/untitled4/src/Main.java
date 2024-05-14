import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Scanner;

public class Main {
    static long sizeFolder = 0;

    public static void main(String[] args){
        for (;;){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Для выхода из программы введите exit");
            System.out.println("ВВедите путь к папке: ");
            String patch = scanner.nextLine();
            if(patch.equals("exit"))
                break;
            File folder = new File(patch);
            sortingThroughFiles(folder);
            System.out.println(sizeFolderFormat(sizeFolder));

            } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sizeFolder = 0;
        }


        }
}
public static void sortingThroughFiles(File folder){
        File[] files = folder.listFiles();
        for(File file: files){
           if(file.isDirectory()){
               sortingThroughFiles(file);
               continue;
           } else sizeFolder+=file.length();

        }
}
public static String sizeFolderFormat(long sizeFolder){
        int i =0;
        while ( sizeFolder > 1024){
            sizeFolder = sizeFolder/1024;
            i++;
        }
        if(i == 0) return sizeFolder+"Байт";
        if(i == 1) return sizeFolder+"Кбайт";
        if(i == 2) return sizeFolder+"Mбайт";
        if(i == 3) return sizeFolder+"Гбайт";
        if(i == 4) return sizeFolder+"Тбайт";
        else  return "Слишком большой объем папки";

}


}
