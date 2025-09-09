package com.indra;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SessionFactory factory = new Configuration()
            .configure()
            .buildSessionFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-------------------------------------------");
            System.out.println("            STUDENT DATA MENU");
            System.out.println("-------------------------------------------");
            System.out.println("1. Add Student(s)");
            System.out.println("2. View All Students");
            System.out.println("3. Update a Student Record");
            System.out.println("4. Delete a Student Record");
            System.out.println("5. Exit");
            System.out.println("-------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        createStudent(scanner);
                        break;
                    case 2:
                        readStudents();
                        break;
                    case 3:
                        updateStudent(scanner);
                        break;
                    case 4:
                        deleteStudent(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
                choice = 0; // Reset choice to stay in loop
            }
        } while (choice != 5);

        // Close resources
        factory.close();
        scanner.close();
    }

    // Create operation
    private static void createStudent(Scanner scanner) {
        String continueOption;
        do {
            Session session = factory.openSession();
            try {
                session.beginTransaction();

                System.out.println("\n--- Adding a New Student ---");
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();

                System.out.print("Enter student roll number: ");
                int rollNo = scanner.nextInt();

                System.out.print("Enter student attendance (%): ");
                double attendance = scanner.nextDouble();

                System.out.print("Enter student score: ");
                double score = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter fee details (e.g., 'Paid', 'Pending'): ");
                String feeDetails = scanner.nextLine();

                Student student = new Student(name, rollNo, attendance, score, feeDetails);
                session.persist(student);
                session.getTransaction().commit();

                System.out.println("\u2705 Student record saved successfully!");
                System.out.println("Student ID: " + student.getId());

            } catch (Exception e) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
                System.out.println("Error saving student record.");
                e.printStackTrace();
            } finally {
                session.close();
            }

            System.out.print("Do you want to continue inserting data? (Yes/No): ");
            continueOption = scanner.nextLine().trim();

        } while (continueOption.equalsIgnoreCase("yes"));
    }

    // Read operation
    private static void readStudents() {
        Session session = factory.openSession();
        try {
            System.out.println("\n--- Viewing All Students ---");
            session.beginTransaction();
            List<Student> students = session.createQuery("FROM Student", Student.class).getResultList();
            session.getTransaction().commit();

            if (students.isEmpty()) {
                System.out.println("No student records found.");
            } else {
                for (Student s : students) {
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error retrieving student records.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Update operation
    private static void updateStudent(Scanner scanner) {
        Session session = factory.openSession();
        try {
            System.out.println("\n--- Updating a Student Record ---");
            System.out.print("Enter the ID of the student to update: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            session.beginTransaction();
            // Use session.find() instead of the deprecated session.get()
            Student student = session.find(Student.class, studentId);

            if (student == null) {
                System.out.println("\u274C Student with ID " + studentId + " not found.");
            } else {
                System.out.print("Enter new attendance for student (current: " + student.getAttendance() + "): ");
                double newAttendance = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Enter new score for student (current: " + student.getScore() + "): ");
                double newScore = scanner.nextDouble();
                scanner.nextLine();

                student.setAttendance(newAttendance);
                student.setScore(newScore);

                session.merge(student);
                session.getTransaction().commit();
                System.out.println("\u2705 Student record updated successfully!");
                System.out.println(student);
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error updating student record.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Delete operation
    private static void deleteStudent(Scanner scanner) {
        Session session = factory.openSession();
        try {
            System.out.println("\n--- Deleting a Student Record ---");
            System.out.print("Enter the ID of the student to delete: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            session.beginTransaction();
            // Use session.find() instead of the deprecated session.get()
            Student student = session.find(Student.class, studentId);

            if (student == null) {
                System.out.println("\u274C Student with ID " + studentId + " not found.");
            } else {
                session.remove(student);
                session.getTransaction().commit();
                System.out.println("\u2705 Student with ID " + studentId + " deleted successfully.");
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error deleting student record.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}