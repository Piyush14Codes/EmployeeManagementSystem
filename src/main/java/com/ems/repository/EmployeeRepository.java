package com.ems.repository;
import com.ems.model.Employee;
import com.ems.util.DBConnection;
import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeRepository {
//    private ArrayList<Employee> employeeList = new ArrayList<>();


    public void addEmployee(Employee emp){
        String sql = "INSERT INTO employee(name , department , salary , dept_id) VALUES (?,?,?,?)";

        try(Connection con = DBConnection.getConnection() ;
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,emp.getName());
            ps.setString(2,emp.getDept());
            ps.setDouble(3,emp.getSalary());
            ps.setInt(4,emp.getDeptId());

            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
//        employeeList.add(emp);
    }

    public ArrayList<Employee> getAllEmployees(){
//        return employeeList;
        ArrayList<Employee> list = new ArrayList<>();

        String sql = "SELECT * FROM employee";

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
                list.add(emp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNoOfEmployees() {
        String sql = "SELECT COUNT(*) FROM employee";
        int numOfEmployees = 0;
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            rs.next();
            numOfEmployees = rs.getInt("COUNT(*)");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return numOfEmployees;
//        return employeeList.size();
    }


}
