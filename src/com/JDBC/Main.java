package com.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        PersonDAO p = new PersonDAO();
        System.out.println(p.getPersonById(1).Name);

        Person newPerson = new Person();

        newPerson.Name = "Fernando";
        newPerson.Age = 20;
        newPerson.Department = "Executive";
//        p.addPerson(newPerson);

        for ( Person person: p.getAllPersons() ) {
            System.out.println(person.Name);
        }


    }
}

class PersonDAO{

    Connection con = null;

    public Person getPersonById(int id) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javatest", "root", "");
        Person p = new Person();
        String query = "SELECT * FROM person where id = ?";

        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1 , id);

        ResultSet rs = st.executeQuery();

        rs.next();

        p.id = rs.getInt(1);
        p.Age = rs.getInt("Age");
        p.Department = rs.getString("Department");
        p.Name = rs.getString("Name");

        st.close();
        con.close();

        return p;
    }

    public List<Person> getAllPersons() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javatest", "root", "");


        List<Person> persons = new ArrayList<>();

        String query = "SELECT * FROM person";
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()){

            Person person = new Person();

            person.id = rs.getInt(1);
            person.Age = rs.getInt("Age");
            person.Department = rs.getString("Department");
            person.Name = rs.getString("Name");

            persons.add(person);
        }

        return persons;
    }

    public void addPerson(Person newPerson) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javatest", "root", "");


        String query = "insert into person(Name , Age , Department) values (?,?,?)";
        PreparedStatement st = con.prepareStatement(query);

        st.setString(1 , newPerson.Name);
        st.setInt(2 , newPerson.Age);
        st.setString(3 , newPerson.Department);

        int rs = st.executeUpdate();
        System.out.println(rs + " person added");

    }
}

class Person{
    public int id;
    public String Name;
    public int Age;
    public String Department;
}
