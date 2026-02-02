package com.ems.main;

import com.ems.model.Employee;
import com.ems.services.Utils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    private static void displayMenu() {
        System.out.println("1. Add Employee.");
        System.out.println("2. Search Employee.");
        System.out.println("3. Display All Employees.");
        System.out.println("4. Show Total No Employees.");
        System.out.println("5. Export Details to a File");
        System.out.println("6. Update Employee Details.");
        System.out.println("7. Delete an Employee Record");
        System.out.println("8. Exit.");
    }

    private static void searchMenu() {
        System.out.println("Search Menu");
        System.out.println("Enter Your Choice");
        System.out.println("1.Search By Name");
        System.out.println("2.Search By ID");
    }

    private static void displayErrorMessage() {
        System.out.println("Error: Invalid Choice! Try Again");
    }

    public static void main(String[] args) {
        System.out.println("Employee Management System");
        Scanner sc = new Scanner(System.in);
        Utils obj = new Utils();

        while(true) {
            try {
                displayMenu();
                System.out.println("Enter Your Choice.");
                int choice = sc.nextInt();


                if(choice == 1) {
                    System.out.println("Enter Employee Name");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("Enter Department ID");
                    int deptId = sc.nextInt();
//                    String deptName = sc.nextLine();
                    System.out.println("Enter Salary");
                    int salary = sc.nextInt();
                    obj.addEmployee(new Employee(name,deptId,salary));
                }
                else if(choice == 2) {
                    searchMenu();
                    int searchChoice = sc.nextInt();

                    if(searchChoice == 1) {
                        System.out.println("Enter Employee Name");
                        sc.nextLine();
                        String searchName = sc.nextLine();
                        obj.getEmployeeByName(searchName);
                    }
                    else if(searchChoice == 2) {
                        System.out.println("Enter Employee ID");
                        int searchId = sc.nextInt();
                        obj.getEmployeeById(searchId);
                    }
                    else {
                        displayErrorMessage();
                    }
                }
                else if(choice == 3) {
                    obj.displayAllEmployees();
                }
                else if(choice == 4) {
                    System.out.println("Total No of Employees : "+obj.getTotalNoOfEmployees());
                }
                else if(choice == 5) {
                    obj.writeToFile();
                }
                else if(choice == 6) {
                    System.out.println("Enter Employee ID to Update");
                    int id = sc.nextInt();

                    obj.updateEmployeeDetails(id);
                }
                else if(choice == 7) {
                    System.out.println("Enter Employee ID to Delete");
                    int id = sc.nextInt();

                    obj.deleteEmployee(id);
                }
                else if(choice == 8) {
                    System.out.println("Thank You!");
                    break;
                }
                else {
                    displayErrorMessage();
                }
            } catch(InputMismatchException e) {
                System.out.println("Input should be a number. Try again.");
                sc.nextLine();
            }
        }
    }
}
