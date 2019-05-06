package CRUDOperations;

import java.io.*;
//import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class RowOperations {



    public static void AddRow(File file) {

        Scanner input = new Scanner(System.in);

        try {

            System.out.print("Product name:");
            String newProduct = input.nextLine();

            System.out.print("Product price:");
            double Price = input.nextDouble();

            System.out.print("Product quantity:");
            int Quantity = input.nextInt();


            String productPriceQuantityString;
            String product;
            int index;

            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Checking whether the name
            // of product already exists.
            // getFilePointer() give the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                productPriceQuantityString = raf.readLine();

                // finding the position of ','
                index = productPriceQuantityString.indexOf(',');

                // separating product and price
                product = productPriceQuantityString.substring(0, index);

                // if condition to find existence of record.
                if (Objects.equals(product, newProduct)) {
                    found = true;
                    break;
                }
            }

            if (found == false) {

                // Enter the if block when a record
                // is not already present in the file.
                productPriceQuantityString = newProduct + "," + Price + "," + Quantity;


                // writeBytes function to write a string
                // as a sequence of bytes.
                raf.writeBytes(productPriceQuantityString);

                // To insert the next record in new line.
                raf.writeBytes(System.lineSeparator());

                // Print the message
                System.out.println(" Product added.\n ");

                // Closing the resources.
                raf.close();
            }
            // The contact to be updated
            // could not be found
            else {

                // Closing the resources.
                raf.close();

                // Print the message
                System.out.println("Input product already exist!\n");
            }
        } catch (IOException ioe) {

            System.out.println("Input Output exception");
        } catch (NumberFormatException nef) {

            System.out.println(nef);
        } catch (InputMismatchException e) {
            System.out.println("Introduced input is not valid!\n");
        }
    }

    public static void ReadRow(int lineNumber) {
        try {
            String line = Files.readAllLines(Paths.get("data.csv")).get(lineNumber - 1);

            // finding the position of first ','
            int index = line.indexOf(',');


            // separating product from price and quantity
            String productName = line.substring(0, index);
            // finding the position of second ','
            String halfLine = line.substring(index+1);
            int secondIndex = halfLine.indexOf(',');

            // separating price and quantity

            double price = Double.parseDouble(halfLine.substring(0,secondIndex));

            int quantity = Integer.parseInt(halfLine.substring(secondIndex+1));


            System.out.println("Product name: "+productName+"\t\tProduct price: "+price+"\t\tProduct quantity: "+quantity);
            System.out.println("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void UpdateProduct(File file,int row)
    {

        Scanner input = new Scanner(System.in);

        try {
            System.out.print("New product name:");
            String newProductName = input.nextLine();

            System.out.print("New product price:");
            double newPrice = input.nextDouble();

            System.out.print("New product quantity:");
            int newQuantity = input.nextInt();

            String rowLine = Files.readAllLines(Paths.get("data.csv")).get(row - 1);

            // finding the position of first ','
            int index = rowLine.indexOf(',');


            // separating product from price and quantity
            String productUpdate = rowLine.substring(0, index);

            File newfile = new File("temp.csv");

            RandomAccessFile raf2 = new RandomAccessFile(newfile,"rw");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;
            String line, halfLine;

            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                line = raf.readLine();

                int index2 = line.indexOf(',');


                // separating product from price and quantity
                String name = line.substring(0, index2);
                // finding the position of second ','
                halfLine = line.substring(index2+1);
                int secondIndexx = halfLine.indexOf(',');

                // separating price and quantity

                double price = Double.parseDouble(halfLine.substring(0,secondIndexx));

                int quantity = Integer.parseInt(halfLine.substring(secondIndexx+1));

                if (Objects.equals(name, productUpdate)) {
                    raf2.writeBytes(newProductName + "," + newPrice + "," + newQuantity);
                    raf2.writeBytes(System.lineSeparator());
                    found = true;
                } else {
                    raf2.writeBytes(name + "," + price + "," + quantity);
                    raf2.writeBytes(System.lineSeparator());
                }

            }
            if (!found)
                throw new InputMismatchException();

            raf2.close();
            raf.close();
            file.delete();
            File updatedFile = new File ("data.csv");
            newfile.renameTo(updatedFile);
            System.out.println("The row "+row+" was updated successfully!\n");
        }
        catch (IOException | NumberFormatException ioe) {
            System.out.println(ioe);
        } catch (InputMismatchException e) {
            System.out.println("The input data is invalid!\n");
        }

    }


    public static void DeleteProduct(File file,int row)
    {

//        Scanner input = new Scanner(System.in);

        try {
//            System.out.print("Enter which row do you want to delete: ");
//            int row = input.nextInt();

            String rowLine = Files.readAllLines(Paths.get("data.csv")).get(row - 1);

            // finding the position of first ','
            int index = rowLine.indexOf(',');


            // separating product from price and quantity
            String productDelete = rowLine.substring(0, index);

            File newfile = new File("temp.csv");

            RandomAccessFile raf2 = new RandomAccessFile(newfile,"rw");

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;
            String line, halfLine;

            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                line = raf.readLine();

                int index2 = line.indexOf(',');


                // separating product from price and quantity
                String name = line.substring(0, index2);
                // finding the position of second ','
                halfLine = line.substring(index2+1);
                int secondIndexx = halfLine.indexOf(',');

                // separating price and quantity

                double price = Double.parseDouble(halfLine.substring(0,secondIndexx));

                int quantity = Integer.parseInt(halfLine.substring(secondIndexx+1));

                if (!Objects.equals(name, productDelete)) {
                    raf2.writeBytes(name + "," + price + "," + quantity);
                    raf2.writeBytes(System.lineSeparator());
                    found = false;
                }
                if (Objects.equals(name, productDelete)) {
                    found = true;
                }

            }
            if (!found)
                throw new InputMismatchException();


            raf2.close();
            raf.close();
            file.delete();
            File updatedFile = new File ("data.csv");
            newfile.renameTo(updatedFile);
            System.out.println("The row "+row+" was deleted successfully!\n");
        }
        catch (IOException | NumberFormatException ioe) {
            System.out.println(ioe);
        }
        catch(InputMismatchException ime)
        {
            System.out.println("The input data is invalid!\n");
        }

    }

    public static void main(String args[]) {


        Scanner input = new Scanner(System.in);

        // Using file pointer creating the file.
        File file = new File("data.csv");

        if (!file.exists()) {

            // Create a new file if not exists.
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            System.out.println("\t~ CRUD Operations Menu ~");
            System.out.println("1. Add Row\t2.Read Row\t3.Update Row\t4.Delete Row\t5.Exit");
            System.out.print("Select an operation: ");
            int operation = input.nextInt();


            switch (operation) {
                case 1: {
                    AddRow(file);
                    break;
                }
                case 2: {
                    try {
                        System.out.print("Enter which row do you want to read: ");
                        int rowNumber = input.nextInt();
                        ReadRow(rowNumber);
                    } catch (InputMismatchException ime) {
                        System.out.println("Introduced input is not valid!\n");
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("The selected row does not exist!\n");
                    }
                    break;
                }
                case 3: {
                    try {
                        System.out.print("Enter which row do you want to update: ");
                        int row = input.nextInt();
                        UpdateProduct(file, row);
                    }  catch (IndexOutOfBoundsException ex) {
                        System.out.println("The selected row does not exist!\n");
                    } catch (InputMismatchException e) {
                        System.out.println("Introduced input is not valid!\n");
                    }
                    break;
                }
                case 4: {

                    try {
                        System.out.print("Enter which row do you want to delete: ");
                        int row = input.nextInt();
                        DeleteProduct(file, row);
                    } catch (InputMismatchException e) {
                        System.out.println("Introduced input is not valid!\n");
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("The selected row does not exist!\n");
                    }
                    break;
                }
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Your selected option does not exist!");
            }
        }
    }
}

