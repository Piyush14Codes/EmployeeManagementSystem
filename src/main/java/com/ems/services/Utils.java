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
import java.util.ArrayList;
import java.util.HashMap;

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
}
