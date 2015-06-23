package finddelivery.es.projeto.finddelivery.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vinicius on 08/06/2015.
 */
public class User implements Serializable{

    private String name;
    private String login;
    private String photoUrl;
    private String password;
    private Set<Establishment> establishments;
    private ManagerFindDelivery manager;

    public User(String name,String login,String password)throws EmptyFieldException, ExceededCharacterException{
        this.name = name;
        this.login = login;
        this.password = password;
        this.establishments = new HashSet<Establishment>();
        this.manager = new ManagerFindDelivery();
    }

    public User(String name,String login,String password, String photoUrl){
        this.name = name;
        this.login = login;
        this.password = password;
        this.photoUrl = photoUrl;
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

    public void createdPassword(String password){
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

    public void addsPhotoUrl(String photoUrl) throws EmptyFieldException{
        this.photoUrl = photoUrl;
    }

    //methods for establishments
    public void createEstablishment(String name, String adress, String businessHour, SpecialityType speciality, Set<String> phones,String photoURL) {
        Establishment establishment = new Establishment(name, adress, businessHour, speciality, phones,photoURL);

        if(!establishments.contains(establishment)){
            establishments.add(establishment);
        }
    }

    public void evaluateEstablishment(Establishment establishment, int value) {
        if(establishment != null) {
            establishment.addEvaluation(this, value);
        }
    }

    public void insertCommentEstablishment(Establishment establishment, String comment) {
        if(establishment != null) {
            establishment.addComment(this, comment);
        }
    }

    public void removeCommentEstablishment(Establishment establishment, String comment) {
        establishment.removeComment(this, comment);
    }

    public void setEstablishments(Set<Establishment> newEstablishments) {
        this.establishments = newEstablishments;
    }

    public Set<Establishment> getEstablishments() {
        return establishments;
    }

    public void removeEstablishment(Establishment establishment) {
        if(establishments.contains(establishment)) {
            establishments.remove(establishment);
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

