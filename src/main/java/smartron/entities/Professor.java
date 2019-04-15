package smartron.entities;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    public List<Exam> getExams() {
        return examList;
    }

    public void setExams(List<Exam> list){
        examList = list;
    }

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private List<Exam> examList;

    public Professor(){
        examList = new ArrayList<>();
    }

}
