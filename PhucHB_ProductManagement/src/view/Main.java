/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author hoang
 */
public class Main {
      public static void main(String[] args) {
        ViewProduct view = new ViewProduct();
        while (true) {
            System.out.println("==========================================================================");
            System.out.println("Product Management Program");
            System.out.println("1. Create a Product");
            System.out.println("2. Check Existence of Product");
            System.out.println("3. Search Product Information by Name");
            System.out.println("4. Update Product");
            System.out.println("5. Save Products to File");
            System.out.println("6. Print List of Products from File");
            System.out.println("7. Quit");
            System.out.print("Enter your choice: ");

            int choice = InputValid.getInteger("", "Invalid input. Please enter a valid choice.", 1, 7);

            switch (choice) {
                case 1:
                    view.createProduct();
                    break;
                case 2:
                    view.checkExistProduct();
                    break;
                case 3:
                    view.search();
                    break;
                case 4:
                    updateProductMenu(view);
                    break;
                case 5:
                    view.saveData();
                    break;
                case 6:
                    view.printListFromFile();
                    break;
                case 7:
                    System.exit(0);
            }
        }
    }


    private static void updateProductMenu(ViewProduct view) {
        while (true) {
            System.out.println("=============================== Update Product ===============================");
            System.out.println("1. Update product");
            System.out.println("2. Delete product by id");
            System.out.println("3. Back to previous menu");

            int choice = InputValid.getInteger("Enter your choice: ",
                    "Choice must be number", 1, 3);
            switch (choice) {
                case 1:
                    view.updateProduct();
                    break;
                case 2:
                    view.deleteProduct();
                    break;
                case 3:
                    //back to menu
                    return;
                default:
                    System.err.println("Invalid choice. Please choose a valid option.");
            }

        }
    }

    
}