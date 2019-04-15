package smartron.entities;

public class Student {

    private String name;

    private String grade;

    private int points;

    private int percent;

    public Student(String name, String grade, int points, int percent){
        this.name = name;
        this.grade = grade;
        this.points = points;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

}
