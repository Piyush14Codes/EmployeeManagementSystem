package com.ems.services;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private EmployeeRepository repository = new EmployeeRepository();

//    private HashMap<Integer,Employee> mp = new HashMap<>();

    private void displayEmployees(List<Employee> employees){
        System.out.println("Employee ID     Name    Department  Dept ID     Salary");
        for(Employee emp : employees){
            System.out.println("    "+emp.getEmployeeId()+"         "+emp.getName()+"       "+emp.getDept()+"           "+emp.getDeptId()+"      "+emp.getSalary());
        }
    }

    public boolean isIdPresent(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee emp = session.find(Employee.class,id);
            return emp != null;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addEmployee(Employee emp){
        repository.addEmployee(emp);
    }

    public void displayAllEmployees(){
        List<Employee> empList = repository.getAllEmployees();
        displayEmployees(empList);
    }

    public void getEmployeeById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee emp = session.find(Employee.class,id);

            if(emp != null) {
                List<Employee> list = new ArrayList<>();
                list.add(emp);
                displayEmployees(list);
            } else {
                System.out.println("Could not find employee with ID : "+id);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getEmployeeByName(String name){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("from Employee e where e.name like :name",Employee.class);
            query.setParameter("name","%"+name+"%");

            List<Employee> searchResult = query.list();

            if(searchResult.isEmpty()) {
                System.out.println("No employees found with name : " + name);
            } else {
                displayEmployees(searchResult);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public long getTotalNoOfEmployees() {
        return repository.getNoOfEmployees();
    }

    public void exportToCSV() {
        List<Employee> employees = repository.getAllEmployees();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Employee Details.csv"))) {
            bw.write("ID,Name,Department,Dept_ID,Salary");
            bw.newLine();

            for(Employee emp : employees) {
                bw.write(emp.getEmployeeId()+","
                        + emp.getName() + ","
                        + emp.getDept() + ","
                        + emp.getDeptId() + ","
                        + emp.getSalary());
                bw.newLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeDetails(int id) {
        Scanner sc = new Scanner(System.in);
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Employee emp = session.find(Employee.class,id);

            if(emp == null) {
                System.out.println("No employee with id : "+id);
                return;
            }

            System.out.println("Enter Name: ");
            String name = sc.nextLine();

            System.out.println("Enter Department ID: ");
            int dept_id = sc.nextInt();

            System.out.println("Enter Salary: ");
            double salary = sc.nextDouble();

            emp.setName(name);
            emp.setDeptId(dept_id);
            emp.setSalary(salary);

            session.merge(emp);
            tx.commit();

            System.out.println("Employee Updated Successfully");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Employee emp = session.find(Employee.class,id);
            if(emp != null) {
                session.remove(emp);
                tx.commit();
                System.out.println("Employee removed successfully.");
            } else {
                System.out.println("No such employee exists.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
