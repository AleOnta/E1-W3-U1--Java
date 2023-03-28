package Connector;

import Students.Gender;
import Students.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class DB_Connection {
    private static Logger log = LoggerFactory.getLogger(DB_Connection.class);
    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String dbName = "e1w3u1_DB";
    private final String username = "postgres";
    private final String password = "root";
    private Statement st;

    public DB_Connection() throws SQLException {
        Connection connect = DriverManager.getConnection(url + dbName, username, password);
        st = connect.createStatement();
        // addGenderEnum(); used only at first run to create TYPE gender as ENUM
        initializeDB();
    }

    public void addGenderEnum() throws SQLException {
        String sql = "CREATE TYPE gen AS ENUM ('f', 'm');";
        st.executeUpdate(sql);
    }

    public void initializeDB() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS school_students("
                + "student_id SERIAL PRIMARY KEY,"
                + "name VARCHAR NOT NULL,"
                + "lastname VARCHAR NOT NULL,"
                + "gender gen NOT NULL,"
                + "birthdate DATE NOT NULL,"
                + "avg DECIMAL NOT NULL,"
                + "min_vote DECIMAL NOT NULL,"
                + "max_vote DECIMAL NOT NULL"
                + ")";
        st.executeUpdate(sql);
    }

    public void addStudent(String n, String ln, Gender gen, String bd, double avg, double min, double max) throws SQLException {
        Student nS = new Student(n, ln, gen, bd, avg, min, max);
        String sql = "INSERT INTO school_students(name, lastname, gender, birthdate, avg, min_vote, max_vote)" +
                "VALUES ('"+nS.getName()+"', '"+nS.getLastname()+"', '"+nS.getGender()+"', '"+ nS.getBirthdate()+"'," +
                ""+nS.getAvg()+", "+nS.getMin_vote()+", "+nS.getMax_vote()+");";
        st.executeUpdate(sql);
    }

    public void updateStudent(int id, Map<String, Student> s) throws SQLException {
        Student toUse = s.get(String.valueOf(id));
        String sql = "UPDATE school_students SET name='"+toUse.getName()+"', lastname='"+toUse.getLastname()+"', " +
                "gender='"+toUse.getGender()+"', birthdate='"+toUse.getBirthdate()+"', avg="+toUse.getAvg() + ", "
                +"min_vote="+toUse.getMin_vote()+", max_vote="+toUse.getMax_vote()+" WHERE student_id ="+ id;
        st.executeUpdate(sql);
        log.info("Student correctly updated.");
    }

    public boolean checkId(int id) throws SQLException {
        ArrayList<Long> idArrayList = new ArrayList<Long>();
        String sql = "SELECT student_id FROM school_students";
        ResultSet idSet = st.executeQuery(sql);
        while (idSet.next()) {
            Long s_id = idSet.getLong("student_id");
            idArrayList.add(s_id);
        }
        return idArrayList.contains((long) id);
    }

    public void deleteStudent(int id) throws SQLException {
        boolean doesExists = checkId(id);
        if (doesExists) {
            String sql = "DELETE FROM school_students WHERE student_id = " + id;
            st.executeUpdate(sql);
            log.info("Student correctly removed from the DB.");
        } else {
            log.warn("User you're trying to delete doesn't exists; Check the ID");
        }
    }

    public Gender checkGender(String s) {
        if (Objects.equals(s, "m")){
            return Gender.m;
        } else {
            return Gender.f;
        }
    }

    public Student getBest() throws SQLException {
        Student bestStudent = null;
        String sql = "SELECT * FROM school_students WHERE school_students.avg = "
                + "(SELECT MAX(school_students.avg) FROM school_students);";
        ResultSet res = st.executeQuery(sql);
        if (res.next()) {
            Long student_id = res.getLong("student_id");
            String name = res.getString("name");
            String lastname = res.getString("lastname");
            Gender gender = checkGender(res.getString("gender"));
            String birthdate = res.getString("birthdate");
            double avg = res.getDouble("avg");
            double min_vote = res.getDouble("min_vote");
            double max_vote = res.getDouble("max_vote");
            bestStudent = new Student(student_id, name, lastname, gender, birthdate, avg, min_vote, max_vote);
        }
        return bestStudent;
    }

    public void getVoteRange(int min, int max) throws SQLException {
        String sql = "SELECT * FROM school_students WHERE min_vote >= "+min+" AND max_vote <= "+max+";";
        ResultSet res = st.executeQuery(sql);
        while (res.next()) {
            Long student_id = res.getLong("student_id");
            String name = res.getString("name");
            String lastname = res.getString("lastname");
            Gender gender = checkGender(res.getString("gender"));
            String birthdate = res.getString("birthdate");
            double avg = res.getDouble("avg");
            double min_vote = res.getDouble("min_vote");
            double max_vote = res.getDouble("max_vote");
            Student st = new Student(student_id, name, lastname, gender, birthdate, avg, min_vote, max_vote);
            log.info("STUDENT IN RANGE --> " + st);
        }
    }
}
