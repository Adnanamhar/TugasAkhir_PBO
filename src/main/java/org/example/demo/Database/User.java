package org.example.demo.Database;

import java.util.ArrayList;

public class User {

    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<ArrayList<String>> borrowBooks = new ArrayList<>();
    public static String loginStudent;

    public static boolean login(String email, String password) {
        if (email.equals("student@example.com") && password.equals("password123")) {
            loginStudent = email;
            return true;
        }
        return false;
    }
}
