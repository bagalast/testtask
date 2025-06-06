package ru.bagalast;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final Pattern LOG_PATTERN = Pattern.compile(
            "^\\[(.+?)\\] (\\w+) (balance inquiry|transferred|withdrew)\\s+(\\d+[\\.,]?\\d*)(?:\\s+to\\s+(\\w+))?$",
            Pattern.CASE_INSENSITIVE
    );
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            return;
        }
        Path dir = Paths.get(args[0]);
        Path outDir = dir.resolve("transactions");
        Files.createDirectories(outDir);

        Map<String, List<Transaction>> transactions = new HashMap<>();
        Map<String, Double> balances = new HashMap<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.log")){
            for (Path file : stream){
                readLogFile(file,transactions,balances);
            }
        }
        writeLogFile(outDir,transactions,balances);
    }
    private static void readLogFile(Path file, Map<String,List<Transaction>> transactions, Map<String,Double> balances) throws IOException {
        List<String> lines = Files.readAllLines(file);
        for (String line : lines){
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (!matcher.find()){
                System.out.println("Ошибка: " + line);
                continue;
            }
            LocalDateTime time = LocalDateTime.parse(matcher.group(1),DATE_FORMATTER);
            String user = matcher.group(2);
            String operation = matcher.group(3);
            double amount = Double.parseDouble(matcher.group(4).replace(',', '.'));
            String otherUser = matcher.group(5);
            Transaction transaction = new Transaction(time,user,operation,amount,otherUser);
            if (!transactions.containsKey(user)){
                transactions.put(user, new ArrayList<>());
            }
            transactions.get(user).add(transaction);

            if (!balances.containsKey(user)){
                balances.put(user,0.0);
            }
            if (operation.equalsIgnoreCase("transferred")){
                balances.put(user,balances.get(user) - amount);
                if (!balances.containsKey(otherUser)){
                    balances.put(otherUser,0.0);
                }
                balances.put(otherUser,balances.get(otherUser) +amount);
                Transaction transaction_otherUser = new Transaction(time,otherUser,"received",amount,user);
                if (!transactions.containsKey(otherUser)){
                    transactions.put(otherUser, new ArrayList<>());
                }
                transactions.get(otherUser).add(transaction_otherUser);

            } else if (operation.equalsIgnoreCase("withdrew")) {
                balances.put(user, balances.get(user) - amount);

            }
        }

    }
    private static void writeLogFile(Path outDir, Map<String,List<Transaction>> transactions, Map<String, Double> balances) throws IOException {
        for (String user : transactions.keySet()){
            List<Transaction> trs = transactions.get(user);
            trs.sort(Comparator.comparing(Transaction::getTime));
            List<String> lines = new ArrayList<>();
            for (Transaction transaction : trs){
                lines.add(transaction.toString());
            }
            double balance = balances.getOrDefault(user,0.0);
            String stringBalance = String.format("[%s] %s final balance %.2f",LocalDateTime.now().format(DATE_FORMATTER),user,balance);
            lines.add(stringBalance);

            Path userFile = outDir.resolve(user + ".log");
            Files.write(userFile,lines);
        }
    }
}