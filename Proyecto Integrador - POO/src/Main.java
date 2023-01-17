import module.Product;
import module.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("NEIGHBORHOOD SHOP");
        showMenu();
    }

    static void showMenu(){
        System.out.println();
        System.out.println(" MENU: ");
        System.out.println("1. See complete inventory");
        System.out.println("2. Find a product price");
        System.out.println("3. Add new product");
        System.out.println("4. Register purchases: ");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter option: ");
        int opcion = scanner.nextInt();

        switch (opcion){
            case 1 -> viewInventory();
            case 2 -> showPrice();
            case 3 -> addProduct();
            case 4 -> registerPurchase();
            default -> {
                System.out.println("Invalid option");
                showMenu();
            }
        }
        showMenu();
    }

    //VER INVENTARIO COMPLETO
    static void viewInventory(){
        System.out.println("COMPLETE INVENTORY: ");
        System.out.println("NAME | DESCRIPTION | CATEGORY | TAG | PRICE  |  URL");

        try {
            File file = new File("resources/Inventario.csv");
            Scanner fileScanner = new Scanner(file);
            //Saltar el encabezado del CSV
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //BUSCAR PRODUCTO Y MOSTRAR PRECIO
    static void showPrice(){
        System.out.println("SEARCH PRICE OF A PRODUCT: ");
        System.out.println("Enter product name: ");
        Scanner scanner = new Scanner(System.in);
        String nombre = scanner.nextLine();

        try {
            File file = new File("resources/Inventario.csv");
            Scanner fileScanner = new Scanner(file);
            //Saltar el encabezado del CSV
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] datos = line.split(",");
                if (datos[0].equals(nombre)){
                    System.out.println("The price of " + nombre + " is " + datos[4]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //AGREGAR NUEVO PRODUCTO
    static void addProduct(){
        System.out.println("ADD NEW PRODUCT: ");
        System.out.println("Enter product name: ");
        Scanner scanner = new Scanner(System.in);
        String nombre = scanner.nextLine();
        System.out.println("Enter product description: ");
        String descripcion = scanner.nextLine();
        System.out.println("Enter product category: ");
        String categoria = scanner.nextLine();
        System.out.println("Enter product label: ");
        String etiqueta = scanner.nextLine();
        System.out.println("Enter product price: ");
        float precio = scanner.nextFloat();
        System.out.println("Enter product image url: ");
        String url = scanner.nextLine();

        //Agregar nuevo producto al archivo csv
        try {
            FileWriter fileWriter = new FileWriter("resources/Inventario.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println("\r" + nombre + "," + descripcion + "," + precio+ "," + categoria + "," + etiqueta + "," + url);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //REGISTRAR COMPRAS DE UN CLIENTE
    static void registerPurchase(){
        List<Product> products = new ArrayList<>();
        float total = 0;
        System.out.println("REGISTER PURCHASE: ");
        System.out.println("Enter client name: ");
        Scanner scanner = new Scanner(System.in);
        String nameClient = scanner.nextLine();

        System.out.println("Enter product name (enter 0 to exit): ");
        String nameProduct = scanner.nextLine();
        while (!nameProduct.equals("0")){
            try {
                File file = new File("resources/Inventario.csv");
                Scanner fileScanner = new Scanner(file);
                //Saltar el encabezado del CSV
                fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] datos = line.split(",");
                    if (datos[0].equals(nameProduct)){
                        //Agregar nombre del producto a la lista de productos
                        products.add(new Product(datos[0], datos[1], Float.parseFloat(datos[4]), datos[2], datos[3], datos[5]));
                        //Sumar precio del producto al total
                        total += Float.parseFloat(datos[4]);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Enter product name: ");
            nameProduct = scanner.nextLine();
        }
        //Tipo de pago
        System.out.println("Enter payment type: ");
        String paymentType = scanner.nextLine();

        //Crear objeto de user
        Date date1 = new Date();
        User user = new User(nameClient, paymentType, total, products,  date1);

        //Agregar nueva compra al archivo csv
        try {
            FileWriter fileWriter = new FileWriter("resources/Purchases.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(user.getName() + "," + user.getPaymentType() + "," + user.getTotal() + "," + user.getProducts() + "," + user.getDate());
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}