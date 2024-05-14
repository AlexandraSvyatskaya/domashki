
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        File file;
        File fileNew;
        StringBuilder builder = new StringBuilder();

            Scanner scanner = new Scanner(System.in);

            do {
                System.out.println("Введите путь к папке или файлу которые должны быть скопированы");
                String patch = scanner.nextLine();
                file = new File(patch);
            }
            while (!file.exists());
            do {
                System.out.println("Введите новый путь ");
                String newPatch = scanner.nextLine();
                fileNew = new File(newPatch);
            }
            while (fileNew.mkdir());
            sortingThroughFiles(file,fileNew);

        }
    public static void sortingThroughFiles(File file, File fileNew) throws IOException {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1 : files){
                if(file1.isDirectory()){
                    File fileDir = new File(fileNew + "\\" + file1.getName());
                    fileDir.mkdir();
                    sortingThroughFiles(file1, fileDir);
                } else {
                    copyFile(file1, fileNew);
                }

            }
        }
    }
    public static void copyFile(File file1, File fileNew) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(file1);
            String name = file1.getName();
            File fileN = new File(fileNew+ "\\" + name);
            fileN.createNewFile();
            os = new FileOutputStream(fileN);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0){
                os.write(buffer,0,length);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            is.close();
            os.close();
        }
    }


    }




