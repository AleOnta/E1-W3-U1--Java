package Students;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Student {

    private long id;
    @Setter
    private String name;
    @Setter
    private String lastname;
    @Setter
    private Gender gender;
    @Setter
    private String birthdate;
    @Setter
    private double avg;
    @Setter
    private double min_vote;
    @Setter
    private double max_vote;

    public Student(String name, String lastname, Gender gen, String birthdate, double avg, double min_vote, double max_vote) {
        super();
        this.name = name;
        this.lastname = lastname;
        this.gender = gen;
        this.birthdate = birthdate;
        this.avg = avg;
        this.min_vote = min_vote;
        this.max_vote = max_vote;
    }

    public Student(long id, String name, String lastname, Gender gen, String birthdate, double avg, double min_vote, double max_vote) {
        super();
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.gender = gen;
        this.birthdate = birthdate;
        this.avg = avg;
        this.min_vote = min_vote;
        this.max_vote = max_vote;
    }

    @Override
    public String toString() {
        return "Student{"
                + "id: " + id
                + ", name: " + name
                + ", lastname: " + lastname
                + ", gender: " + gender
                + ", birthdate: " + birthdate
                + ", avg=" + avg
                + ", min_vote: " + min_vote
                + ", max_vote: " + max_vote
                + '}';
    }
}
