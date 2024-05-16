import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;



public class Main{
    private static String patch = "data/movementList.csv";

    public static void main(String[] args) {
       List<Transaction> transactions = creatingList();

        System.out.print("Сумма расходов: ");
        ExpensesSumm(transactions);
        System.out.println(" руб. ");
        System.out.print("Сумма доходов: ");
        IncomeSumm(transactions);
        System.out.println(" руб. ");
        System.out.println("Суммы расходов по организациям: ");
        printMap(ExpensesSummCompani(transactions));


        }
    public static List<Transaction> creatingList(){
        List<String[]> line;
        try (CSVReader reader = new CSVReader(new FileReader(patch))) {
            line = reader.readAll();
            line.remove(0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        List<Transaction> transactions = new ArrayList<>();

        for(String[] arrays : line){
            if(arrays.length !=8){
                System.out.println("Количество столбцов не совпадает" + line);
               continue;
            }


           transactions.add(new Transaction(arrays[0],arrays[1],arrays[2],arrays[3],arrays[4],
                   arrays[5],arrays[6],arrays[7] = arrays[7].replaceAll(",",".")));
        }
        return transactions;
    }
    public static void IncomeSumm(List<Transaction> transactions){
        Stream<Transaction> streamIncome = transactions.stream();
        streamIncome.map(t -> t.getIncome()).map(Float::parseFloat).reduce((t1,t2) -> t1 + t2).ifPresent(System.out ::print);
    }
    public static void ExpensesSumm(List<Transaction> transactions){
        Stream<Transaction> streamExpenses = transactions.stream();
        streamExpenses.map(t -> t.getExpense()).map(Float::parseFloat).reduce((t1,t2) -> t1 + t2).ifPresent(System.out ::print);
    }
    public static Map<String,Float> ExpensesSummCompani(List<Transaction> transactions){
        Map<String,Float> expensesList = new HashMap<>();
        String compani;
        Float expensesCompani;
        String[] deskription;
        for(Transaction transaction : transactions){
           if(transaction.getExpense().equals("0")) {
               continue;
           }
           deskription = transaction.getDescription().split("    ");
           expensesCompani = Float.parseFloat( transaction.getExpense());
           compani = deskription[1].trim();
           if(expensesList.containsKey(compani)){
               expensesList.put(compani, expensesList.get(compani) + expensesCompani);
           } else {
               expensesList.put(compani,expensesCompani);
           }
        }
        return expensesList;
    }
    public static void printMap(Map<String,Float> map){
        for(String key : map.keySet()){
            System.out.println(key + " " + map.get(key) + "руб.");
        }
    }

    }