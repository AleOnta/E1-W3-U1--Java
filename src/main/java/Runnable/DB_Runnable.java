package Runnable;

import Connector.DB_Connection;
import java.sql.SQLException;

import Students.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB_Runnable {
protected static Logger log = LoggerFactory.getLogger(DB_Runnable.class);
    public static void main(String[] args) {
        try {
            DB_Connection schoolStudentsDb = new DB_Connection();

            // TASK 1 - add students to db:
            // schoolStudentsDb.addStudent("Alessandro", "Ontani", Gender.m, "1999-01-11", 9.2, 9, 10);
            // schoolStudentsDb.addStudent("Giorgio", "Rossi", Gender.m, "1995-04-19", 7.8, 6.5, 9);
            // schoolStudentsDb.addStudent("Sara", "Gialli", Gender.f, "1998-12-22", 8.6, 7.5, 10);
            // schoolStudentsDb.addStudent("Laura", "Blu", Gender.f, "1989-06-02", 9.5, 9, 10);

            // TASK 2 - update a student into the db


            // TASK 3 - delete a student on the db by id
            // schoolStudentsDb.deleteStudent(3);

            // TASK 4 - get the student with the best avg
            log.info(schoolStudentsDb.getBest().toString());

            // TASK 5 - print list of students in range of votes
            schoolStudentsDb.getVoteRange(8, 10);
        } catch (SQLException e) {
            e.printStackTrace();
            log.warn(String.valueOf(e));
        }
    }

}
