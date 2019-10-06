package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

public class Mainn {

  public static void main(String[] args) {
    // Product(Id, Barcode, Name, Price, Cost, ExpireDate)
    Scanner input = new Scanner(System.in);
    int max_product, index = 0;
    //declare 2d array as data source
    String[][] products = null;
    //setup file
    String system_file_separator = System.getProperty("file.separator");
    String data_file_name = "." + system_file_separator + "Products.pro";
    Path path = Paths.get(data_file_name);
    //check if file is exist then read data
    if (Files.exists(path)) {
      long line_count = 0;
      try (Stream<String> stream = Files.lines(path)) {
        line_count = stream.count();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("Data Found: " + line_count + ".");
      System.out.print("Add more row : ");
      max_product = input.nextInt();
      products = new String[(int) line_count + max_product][6];
      TextFileReader reader =
          TextFileReader.newInputConfigurationBuilder().sourceFile(path.toString()).build();
      reader.readInto2DArray(products, ";");
      index = (int) line_count;
    } else {
      System.out.print("Enter maximum number of product : ");
      max_product = input.nextInt();
      products = new String[max_product][6];
    }
    do {
      System.out.println("1. Add.");
      System.out.println("2. List.");
      System.out.println("3. Search(Id, Barcode, name)");
      System.out.println("4. List Expired Products.");
      System.out.println("5. List Not Expired Products.");
      System.out.println("6. Delete By Id.");
      System.out.println("7. Update By Id.");
      // ...
      System.out.println("0.Exit.");
      System.out.print("==> ");
      int menu = input.nextInt();
      switch (menu) {
        case 0:
          {
            char answer;
            System.out.println("Save to file? y? ");
            answer = input.next().charAt(0);
            if (answer == 'y' || answer == 'Y') {
              TextFileWriter writer =
                  TextFileWriter.newConfigurationBuilder().destinationFile(path.toString()).build();
              writer.write2DArray(products, ";", index);
              System.out.println("Saved");
            }
            System.exit(0);
          }
          break;
        case 1:
          {
            if (index == max_product) {
              System.out.println("Product List is full.");
              break;
            }
            System.out.print("Input ID = ");
            products[index][0] = input.next();
            System.out.print("\nInput Barcode = ");
            products[index][1] = input.next();
            System.out.print("\nInput Name = ");
            products[index][2] = input.next();
            System.out.print("\nInput Price = ");
            products[index][3] = input.next();
            System.out.print("\nInput Cost = ");
            products[index][4] = input.next();
            System.out.print("\nInput Expire Date = ");
            products[index][5] = input.next();
            index++;
          }
          break;
        case 2:
          {
            System.out.println("ID\tBarcode\tName\tPrice\tCost\tExpireDate");
            System.out.println("--------------------------------------------------------");
            for (int i = 0; i < index; i++) {
              for (int j = 0; j < products[i].length; j++) {
                System.out.print(products[i][j] + "\t");
              }
              System.out.println("--------------------------------------------------------");
            }
          }
          break;
        case 3:
          {
            String search;
            System.out.print("Search (Id, Barcode, Name) = ");
            search = input.nextLine();
            System.out.println("ID\tBarcode\tName\tPrice\tCost\tExpireDate");
            System.out.println("--------------------------------------------------------");
            for (int i = 0; i < index; i++) {
              if (products[i][0].compareToIgnoreCase(search) == 0
                  || products[i][1].compareToIgnoreCase(search) == 0
                  || products[i][2].contains(search)) {
                for (int j = 0; j < products[i].length; j++) {
                  System.out.print(products[i][j] + "\t");
                }
                System.out.println("--------------------------------------------------------");
              }
            }
          }
          break;
        case 4:
          {
            System.out.println("ID\tBarcode\tName\tPrice\tCost\tExpireDate");
            System.out.println("--------------------------------------------------------");
            for (int i = 0; i < index; i++) {
              String product_expire_date = products[i][products[i].length - 1];
              String pattern = "dd-MM-yyyy";
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
              LocalDate expire_date = LocalDate.parse(product_expire_date, formatter);
              if (expire_date.isBefore(LocalDate.now())) {
                for (int j = 0; j < products[i].length; j++) {
                  System.out.print(products[i][j] + "\t");
                }
                System.out.println("--------------------------------------------------------");
              }
            }
          }
          break;
        case 5:
          {
            System.out.println("ID\tBarcode\tName\tPrice\tCost\tExpireDate");
            System.out.println("--------------------------------------------------------");
            for (int i = 0; i < index; i++) {
              String product_expire_date = products[i][products[i].length - 1];
              String pattern = "dd-MM-yyyy";
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
              LocalDate expire_date = LocalDate.parse(product_expire_date, formatter);
              LocalDate now = LocalDate.now();
              if (expire_date.equals(now) || expire_date.isAfter(now)) {
                for (int j = 0; j < products[i].length; j++) {
                  System.out.print(products[i][j] + "\t");
                }
                System.out.println("--------------------------------------------------------");
              }
            }
          }
          break;
        case 6:
          {
            String search_id;
            System.out.print("Input Product's ID = ");
            search_id = input.next();
            for (int i = 0; i < index; i++) {
              String product_id = products[i][0];
              if (search_id.compareToIgnoreCase(product_id) == 0) {
                // Rotate Rows
                for (int j = i; j < index - 1; j++) {
                  for (int col = 0; col < products[j].length; col++) {
                    products[j][col] = products[j + 1][col];
                  }
                }
                // Clear last row
                for (int col = 0; col < products[i].length; col++) {
                  products[index - 1][col] = "";
                }
                //
                index--;
                break; // loop
              }
            }
          }
          break; // switch
        case 7:
          {
            String search_id;
            System.out.print("Input Product's ID = ");
            search_id = input.next();
            for (int i = 0; i < products.length; i++) {
              String product_id = products[i][0];
              if (search_id.compareToIgnoreCase(product_id) == 0) {
                System.out.print("\nInput New Barcode = ");
                products[i][1] = input.next();
                System.out.print("\nInput New Name = ");
                products[i][2] = input.next();
                System.out.print("\nInput New Price = ");
                products[i][3] = input.next();
                System.out.print("\nInput New Cost = ");
                products[i][4] = input.next();
                System.out.print("\nInput New Expire Date = ");
                products[i][5] = input.next();
                System.out.println("Updated");
                break;
              }
            }
          }
          break;
        default:
          System.out.println("Please Select From 0 -> 2");
          break;
      }
    } while (true);
  }
}
