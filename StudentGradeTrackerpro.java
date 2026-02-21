import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTrackerpro {

    static class Student {
        String name;
        int marks;

        Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        String getGrade() {
            if (marks >= 90) return "A";
            else if (marks >= 75) return "B";
            else if (marks >= 60) return "C";
            else if (marks >= 50) return "D";
            else return "F";
        }
    }

    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Grade Tracker - Professional");
        frame.setSize(650, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ----------- INPUT PANEL -----------
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();

        JLabel marksLabel = new JLabel("Marks:");
        JTextField marksField = new JTextField();

        JButton addButton = new JButton("Add Student");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(marksLabel);
        inputPanel.add(marksField);
        inputPanel.add(new JLabel()); // empty space
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);


        String[] columns = {"Name", "Marks", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);

        frame.add(tableScroll, BorderLayout.CENTER);

        JTextArea summaryArea = new JTextArea(5, 20);
        summaryArea.setEditable(false);
        summaryArea.setBorder(BorderFactory.createTitledBorder("Summary"));

        frame.add(summaryArea, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String marksText = marksField.getText().trim();

            if (name.isEmpty() || marksText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both name and marks.");
                return;
            }

            try {
                int marks = Integer.parseInt(marksText);

                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(frame, "Marks must be between 0 and 100.");
                    return;
                }

                Student student = new Student(name, marks);
                students.add(student);

                model.addRow(new Object[]{
                        student.name,
                        student.marks,
                        student.getGrade()
                });

                nameField.setText("");
                marksField.setText("");

                updateSummary(summaryArea);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Marks must be a number.");
            }
        });

        frame.setVisible(true);
    }

    //  SUMMARY METHOD
    static void updateSummary(JTextArea summaryArea) {

        if (students.isEmpty()) {
            summaryArea.setText("");
            return;
        }

        int total = 0;
        int highest = students.get(0).marks;
        int lowest = students.get(0).marks;

        for (Student s : students) {
            total += s.marks;

            if (s.marks > highest)
                highest = s.marks;

            if (s.marks < lowest)
                lowest = s.marks;
        }

        double average = (double) total / students.size();

        summaryArea.setText(
                "Total Students: " + students.size() + "\n" +
                "Average Marks: " + String.format("%.2f", average) + "\n" +
                "Highest Marks: " + highest + "\n" +
                "Lowest Marks: " + lowest
        );
    }
}
