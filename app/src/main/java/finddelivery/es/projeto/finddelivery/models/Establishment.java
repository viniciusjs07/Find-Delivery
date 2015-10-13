package finddelivery.es.projeto.finddelivery.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Vinicius on 10/06/2015.
 */
public class Establishment implements Serializable {

    private String name, address, businessHour;
    private byte[] photo;
    private String speciality;
    private String phone1;
    private String phone2;
    private Map<User, Float> evaluations;
    private Map<User, String> comments;


    public Establishment(String name, String address, String businessHour, String speciality, String phone1, String phone2, byte[] photo) {

        this.name = name;
        this.address = address;
        this.businessHour = businessHour;
        this.speciality = speciality;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.photo = photo;
        this.evaluations = new HashMap<>();
        this.comments = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBusinessHour() {
        return businessHour;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusinessHour(String businessHour) {
        this.businessHour = businessHour;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setPhoto(byte[] photoURL) {
        this.photo = photoURL;
    }
    
    public Map<User, Float> getEvaluations() {
        return evaluations;
    }

    public Map<User, String> getComments() {
        return comments;
    }

    public void setEvaluations(Map<User, Float> evaluations) {
        this.evaluations = evaluations;
    }

    public void setComments(Map<User, String> comments) {
        this.comments = comments;
    }

    public void addEvaluation(User user, Float value) {
        if (user != null && value != null && value >= 0) {
            this.evaluations.put(user, value);
        }
    }

    public void addComment(User user, String comment) {
        if(user != null && comment != null) {
                comments.put(user, comment);
            }
    }

    public void removeComment(User user, String comment) {
        if(user != null) {
            comments.remove(user);
        }
    }

    public Float calculatesAverage(Map<User,String> mapEvaluation){
        Collection<String> grades = mapEvaluation.values();
        Float sum = Float.valueOf(0);
        for (String grade: grades){
            sum += Float.valueOf(grade);
        }
        Float average = Float.valueOf(0);
        if(grades.size() != 0){
            average = sum / grades.size();
        }
        return average;
    }

}