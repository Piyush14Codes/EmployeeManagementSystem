package com.ems.repository;
import com.ems.model.Employee;
import com.ems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class EmployeeRepository {
//    private ArrayList<Employee> employeeList = new ArrayList<>();


    public void addEmployee(Employee emp){
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emp);
            tx.commit();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback(); //rollback all the changes made during the session
            }
            e.printStackTrace();
        }
//        employeeList.add(emp);
    }

    public List<Employee> getAllEmployees(){
//        return employeeList;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }

    public long getNoOfEmployees() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("select count(e) from Employee e", Long.class);
            return query.getSingleResult();
        }
    }


}
