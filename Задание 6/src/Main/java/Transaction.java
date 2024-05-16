import com.opencsv.bean.CsvBindByName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    @CsvBindByName(column = "Тип счета")
    private String typeBankAccount;
    @CsvBindByName(column = "Номер счета")
    private String numberBankAccount;
    @CsvBindByName(column = "Валюта")
    private String currency;
    @CsvBindByName(column = "Дата операции")
    private String transactionDate;
    @CsvBindByName(column = "Референс проводки")
    private String transactionReference;
    @CsvBindByName(column = "Описание операции")
    private String description;
    @CsvBindByName(column = "Приход")
    private String income;
    @CsvBindByName(column = "Расход")
    private String expense;

    public Transaction(String typeBankAccount, String numberBankAccount, String currency, String  transactionDate,
                       String transactionReference, String description, String income,  String expense){
        this.typeBankAccount = typeBankAccount;
        this.numberBankAccount = numberBankAccount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.transactionReference = transactionReference;
        this.description = description;
        this.income = income;
        this.expense = expense;
    }
    public String getTypeBankAccount() {
        return typeBankAccount;
    }

    public void setTypeBankAccount(String typeBankAccount) {
        this.typeBankAccount = typeBankAccount;
    }

    public String getNumberBankAccount() {
        return numberBankAccount;
    }

    public void setNumberBankAccount(String numberBankAccount) {
        this.numberBankAccount = numberBankAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    @Deprecated
    public String toString(){
        return typeBankAccount + " - " + numberBankAccount + " - " + currency + " - " +
                transactionDate + " - " + transactionReference
                + " - " + description + " - " + income + " - " + expense;
    }


}