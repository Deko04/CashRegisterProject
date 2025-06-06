/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject2;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
public class Mavenproject2 {

    public static void inputProducts(String product, double price, Scanner sc, ArrayList<String> productName, ArrayList<Double> productPrice, ArrayList<Integer> productQuantity) {
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        productName.add(product);
        productPrice.add(price);
        productQuantity.add(quantity);
    }
    
    public static void removeProduct(Scanner sc, ArrayList<String> productName, ArrayList<Double> productPrice, ArrayList<Integer> productQuantity){
        System.out.print("Enter product name to remove: ");
        String productToRemove = sc.nextLine();
        int index = productName.indexOf(productToRemove);
        if (index != -1) {
            productName.remove(index);
            productPrice.remove(index);
            productQuantity.remove(index);
            System.out.println(productToRemove + " removed from your order.");
        } else {
            System.out.println("Invalid Input.");
        }
    }
    
    public static void calculatePayment(int payment, double totalPrice){
        if (payment >= totalPrice){
            double change = payment - totalPrice;
            System.out.println("Payment recieved. Please enjoy your school supplies!");
            System.out.println("Incoming change: " + change);
        } else {
            System.out.println("Invalid payment. Please redo Transaction to reattempt payment.");
        }
    }
    
    public static void signup(Scanner sc, ArrayList<String> userName, ArrayList<String> password){
        System.out.println("Please create an account in order to login.");
    
        while (true) {
            System.out.print("Enter Username: ");
            String newUsername = sc.nextLine();
        
            if (!isValidUsername(newUsername)) {
                System.out.println("Error, invalid Username. (Must be alphanumeric and 5-15 characters long.)");
                continue;
            }
        
            if (userNameExists(newUsername, userName)) {
                System.out.println("Error, unable to create account. (Username already exists.)");
                continue;
            }

            System.out.print("Enter Password: ");
            String newPassword = sc.nextLine();
        
            if (!isValidPassword(newPassword)) {
                System.out.println("Error, invalid Password. (Must contain at least one uppercase letter, one number, and be 8-20 characters long.)");
                continue;
            }

            userName.add(newUsername);
            password.add(newPassword);
            System.out.println("Account created successfully!\n");
            break;
        }
    }
    
    public static boolean userNameExists(String username, ArrayList<String> userNameList) {
        for (String existingUser : userNameList) {
            if (existingUser.equals(username)) {
                return true;
            }
        }
    return false;
    }
    
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]{5,15}$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d).{8,20}$");
    }
    
    public static void login(Scanner sc, ArrayList<String> userName, ArrayList<String> password){
        System.out.println("Please login to continue.");

        while (true) {
            System.out.print("Enter Username: ");
            String inputUsername = sc.nextLine();
            System.out.print("Enter Password: ");
            String inputPassword = sc.nextLine();
        
            boolean loginSuccess = false;
            for (int i = 0; i < userName.size(); i++) {
                if (userName.get(i).equals(inputUsername) && password.get(i).equals(inputPassword)) {
                    loginSuccess = true;
                    break;
                }
            }
        
            if (loginSuccess) {
                System.out.println("Login successful!\n");
                break;
            } else {
                System.out.println("Invalid username or password. Please try again.\n");
            }
        }
    }
    
    public static void recordTransaction(double totalPrice, String username, ArrayList<String> productName, ArrayList<Double> productPrice, ArrayList<Integer> productQuantity){ 
        LocalDateTime today = LocalDateTime.now();
        String fileName = "transaction_" + today.toString().replace(":", "-") + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Username: " + username);
            writer.newLine();
            writer.write("Items purchased: ");
            writer.newLine();
            for (int i = 0; i < productName.size(); i++) {
                String name = productName.get(i);
                int quantity = productQuantity.get(i);
                double price = productPrice.get(i);
                double itemTotal = quantity * price;

                writer.write(name + " - " + quantity + " pcs @ " + price + " Php = " + itemTotal + " Php");
                writer.newLine();
            }

        writer.write("Total amount: " + totalPrice + " Php");
        writer.newLine();
        
	} catch (IOException e) {
	    System.out.println("Failed to create transaction.");
            e.printStackTrace();
            
	}
    }
    
    public static void readTransactions(){
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith("transaction_") && name.endsWith(".txt"));

    if (listOfFiles == null || listOfFiles.length == 0) {
        System.out.println("No transaction files found.");
        return;
    }

    for (File file : listOfFiles) {
        System.out.println(file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> userName = new ArrayList<>();
        ArrayList<String> password = new ArrayList<>();
        signup(sc, userName, password);
        login(sc, userName, password);
        
        ArrayList<String> productName = new ArrayList<>();
        ArrayList<Double> productPrice = new ArrayList<>();
        ArrayList<Integer> productQuantity = new ArrayList<>();
        String continueTransaction;
        do {
            System.out.println("Please type the corresponding code a school supply to buy:");
            System.out.println("A1 - Erasers 10Php");
            System.out.println("A2 - Papers 50Php");
            System.out.println("A3 - Pencils 30Php");
            System.out.println("Type any input outside of the options to order.");
            System.out.println("Type 'REMOVE' to remove an order.");
            while (true) {
                System.out.print("Enter order: ");
                String order = sc.nextLine();
                if (order.equalsIgnoreCase("A1")) {
                    inputProducts("Erasers", 10, sc, productName, productPrice,
                    productQuantity);
                } else if (order.equalsIgnoreCase("A2")) {
                    inputProducts("Papers", 50, sc, productName, productPrice, productQuantity);
                } else if (order.equalsIgnoreCase("A3")) {
                    inputProducts("Pencils", 30, sc, productName, productPrice, productQuantity);
                } else if (order.equalsIgnoreCase("REMOVE")) {
                    removeProduct(sc, productName, productPrice, productQuantity);
                } else {
                    break;
                }
            }
            double totalPrice = 0;
            System.out.println("\nOrder Summary:");
            for (int i = 0; i < productName.size(); i++) {
                double itemTotal = productPrice.get(i) * productQuantity.get(i);
                totalPrice += itemTotal;
                System.out.println(productName.get(i) + " - " + productQuantity.get(i) + " pcs - " + (productPrice.get(i) * productQuantity.get(i)) + " Php");
            }
            System.out.println("\nTotal Price: " + totalPrice + " Php");
            System.out.print("Please enter your payment: ");
            int payment = sc.nextInt();
            sc.nextLine();
            calculatePayment(payment, totalPrice);
            recordTransaction(totalPrice, userName.get(0), productName, productPrice, productQuantity);
            System.out.println("Would you like to make another transaction? (y/n): ");
            continueTransaction = sc.nextLine();
            productName.clear();
            productPrice.clear();
            productQuantity.clear();
        } while(continueTransaction.equalsIgnoreCase("y"));
            System.out.println("Transaction Terminated. Thanks for buying!");
            readTransactions();
}
}
