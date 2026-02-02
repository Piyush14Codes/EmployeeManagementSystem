package com.ems.model;
enum Department {
    DEV(1,"Dev"),
    QA(2 , "QA"),
    HR(3, "HR");

    private int deptId;
    private String deptName;

    private Department(int deptId , String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public static String getDepartmentNameById(int id) {
        for (Department d : Department.values()){
            if(id == d.deptId)  return d.deptName;
        }
        return "Unknown";
    }

}
public class Employee {

//    private static int empCount = 1;

    private int employeeId;

    private String name;

    private String dept;

    private int deptId;

    private double salary;

    public Employee(String name , int deptId , double salary){
        setDeptId(deptId);
        setDept(Department.getDepartmentNameById(deptId));
//        setEmployeeId();
        setName(name);
        setSalary(salary);


    }

    //getter
    public int getEmployeeId(){
        return employeeId;
    }
    public String getName(){
        return name;
    }
    public String getDept(){
        return dept;
    }
    public int getDeptId(){
        return deptId;
    }
    public double getSalary(){
        return salary;
    }

    //setter
    public void setEmployeeId(int employeeId){
        this.employeeId = employeeId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }
}
