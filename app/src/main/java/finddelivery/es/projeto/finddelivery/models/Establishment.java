package finddelivery.es.projeto.finddelivery.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Vinicius on 10/06/2015.
 */
public class Establishment implements Serializable {

    private String name, address, businessHour;
    private byte[] photo;
    private String speciality;
    private String phone1;
    private String phone2;
    Map<User, Integer> evaluations;
    Map<User, List<String>> comments;



    public Establishment(String name){
        this.name = name;
    }

    public Establishment(String name, String address, String businessHour, String speciality, String phone1, String phone2, byte[] photo) {

        this.name = name;
        this.address = address;
        this.businessHour = businessHour;
        this.speciality = speciality;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.photo = photo;
        this.evaluations = new HashMap<User, Integer>();
        this.comments = new HashMap<User, List<String>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getBusinessHour() {
        return businessHour;
    }

    public void setBusinessHour(String businessHour) {
        this.businessHour = businessHour;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photoURL) {
        this.photo = photoURL;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Map<User, Integer> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Map<User, Integer> evaluations) {
        this.evaluations = evaluations;
    }

    public Map<User, List<String>> getComments() {
        return comments;
    }

    public void setComments(Map<User, List<String>> comments) {
        this.comments = comments;
    }

    public void addEvaluation(User user, Integer value) {
        if (user != null && value != null && value >= 0) {
            this.evaluations.put(user, value);
        }
    }

    public void addComment(User user, String comment) {
        if(user != null && comment != null) {
            if(comments.containsKey(user)) {
                this.comments.get(user).add(comment);
            } else {
                List<String> newComment = new ArrayList<String>();
                newComment.add(comment);
                this.comments.put(user, newComment);
            }
        }
    }

    public void removeComment(User user, String comment) {
        if(user != null) {
            this.comments.get(user).remove(comment);
        }
    }

    public float averageEvaluations() {
        if(getEvaluations().size() > 0) {
            return  getSumEvaluations()/getEvaluations().size();
        }
        return 0;
    }

    private int getSumEvaluations() {
        int sum = 0;
        for (int valor : getEvaluations().values()) {
            sum += valor;
        }
        return sum;
    }

}