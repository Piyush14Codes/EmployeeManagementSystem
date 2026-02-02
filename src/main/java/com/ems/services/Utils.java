package com.ems.services;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.util.DBConnection;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Utils {

    private EmployeeRepository repository = new EmployeeRepository();

//    private HashMap<Integer,Employee> mp = new HashMap<>();

    private void displayEmployees(ArrayList<Employee> employees){
        System.out.println("Employee ID     Name    Department  Dept ID     Salary");
        for(Employee emp : employees){
            System.out.println("    "+emp.getEmployeeId()+"         "+emp.getName()+"       "+emp.getDept()+"           "+emp.getDeptId()+"      "+emp.getSalary());
        }
    }

    public boolean isIdPresent(int id){
        String sql = "SELECT COUNT(*) FROM employee WHERE id = "+id;
        int isPresent = 0;
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            rs.next();
            isPresent = rs.getInt("COUNT(*)");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return isPresent == 1;
    }

    public void addEmployee(Employee emp){
        repository.addEmployee(emp);
    }

    public void displayAllEmployees(){
        ArrayList<Employee> empList = repository.getAllEmployees();
        displayEmployees(empList);
    }

    public void getEmployeeById(int id){
        if(isIdPresent(id)){
            ArrayList<Employee> empList = new ArrayList<>();
//            emp.add(mp.get(id));
            String sql = "SELECT * FROM employee WHERE id = "+id;
            try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                rs.next();
                Employee emp = new Employee(
                        rs.getString("name"),
                        rs.getInt("dept_id"),
                        rs.getDouble("salary")
                );
                emp.setEmployeeId(rs.getInt("id"));
                empList.add(emp);
            } catch(Exception e) {
                e.printStackTrace();
            }
            displayEmployees(empList);
        }
        else {
            System.out.println("Could not find employee with ID : "+id);
        }
    }

    public void getEmployeeByName(String name){
        ArrayList<Employee> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE name LIKE \"%"+name+"%\"";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Employee emp = new Employee(
                        rs.getString("name"),
                        rs.getInt("dept_id"),
                        rs.getDouble("salary")
                );
                emp.setEmployeeId(rs.getInt("id"));
                searchResult.add(emp);
            }
            displayEmployees(searchResult);
        } catch(Exception e) {
            e.printStackTrace();
        }
//        for(Employee emp : repository.getAllEmployees()) {
//            if(emp.getName().equals(name)) {
//                searchResult.add(emp);
//            }
//        }
        displayEmployees(searchResult);
    }

    public int getTotalNoOfEmployees() {
        return repository.getNoOfEmployees();
    }

    public void writeToFile() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("employeeDetails.txt"))) {
            bw.write("  Employee ID     Employee Name       Dept ID     Dept Name       Salary");
            bw.newLine();
            for(Employee emp : repository.getAllEmployees()) {
                bw.write("  "+emp.getEmployeeId()+"         "+emp.getName()+"       "+emp.getDeptId()+"     "+emp.getDept()+"       "+emp.getSalary());
                bw.newLine();
            }
            System.out.println("Export Successful.");
        }
        catch(IOException e) {
            System.out.println("There was some error creating a file");
        }
    }

    public void updateEmployeeDetails(int id) {
        String sql = "UPDATE employee SET name=? , dept_id=?, department = ? , salary=? WHERE id = ?";
        Scanner sc = new Scanner(System.in);

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("Enter name");
//            sc.nextLine();
            String name = sc.nextLine();

            System.out.println("Enter Department ID");
            int dept_id = sc.nextInt();

            System.out.println("Enter Salary");
            double salary = sc.nextDouble();

            Employee emp = new Employee(name,dept_id,salary);

            ps.setString(1,emp.getName());
            ps.setInt(2,emp.getDeptId());
            ps.setString(3,emp.getDept());
            ps.setDouble(4,emp.getSalary());
            ps.setInt(5,id);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected == 0) {
                System.out.println("No employee found with ID: " + id);
            } else {
                System.out.println("Employee Updated Successfully.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
