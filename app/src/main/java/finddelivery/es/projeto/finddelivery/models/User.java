package finddelivery.es.projeto.finddelivery.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

    private String name;
    private String login;
    private byte[] photo;
    private String password;
    private List<Establishment> establishments;

    public User(String name,String login,String password, byte[] photo){
        this.name = name;
        this.login = login;
        this.password = password;
        this.photo = photo;
        establishments = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password;}

    public byte[] getPhoto(){
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void createEstablishment(Establishment establishment) {
        if(!establishments.contains(establishment)){
            establishments.add(establishment);
        }
    }

    public void removeEstablishment(Establishment establishment) {
        if(establishments.contains(establishment)) {
            establishments.remove(establishment);
        }
    }

    public void updateDataEstablishment(String name, String address, String businessHour, String speciality, String phone1, String phone2, byte[] photo){
        for (Establishment est: establishments){
            if(est.getName().equals(name)){
                est.setAddress(address);
                est.setBusinessHour(businessHour);
                est.setSpeciality(speciality);
                est.setPhone1(phone1);
                est.setPhone2(phone2);
                est.setPhoto(photo);
            }
        }
    }

    public List<Establishment> getEstablishments() {
        return establishments;
    }

    public void evaluateEstablishment(Establishment establishment, float value) {
        if(establishment != null) {
            establishment.addEvaluation(this, value);
        }
    }

    public void insertCommentEstablishment(Establishment establishment, String comment) {
        if(establishment != null) {
            establishment.addComment(this, comment);
        }
    }

    @Override
    public String toString() {
        return "User " + getName() + " login " + getLogin();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof User)){
            return false;
        }
        User newUser = (User) object;
        return getName().equals(newUser.getName()) && getLogin().equals(newUser.getLogin());

    }
}