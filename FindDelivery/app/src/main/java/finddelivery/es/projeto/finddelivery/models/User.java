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
    private byte[] photo;
    private String password;
    private Set<Establishment> establishments;
    private ManagerFindDelivery manager;

    public User(String name,String login,String password){

       // public User(String name,String login,String password)throws EmptyFieldException, ExceededCharacterException{
        this.name = name;
        this.login = login;
        this.password = password;
        this.establishments = new HashSet<Establishment>();
        this.manager = new ManagerFindDelivery();
    }

    public User(String name,String login,String password, byte[] photo){
        this.name = name;
        this.login = login;
        this.password = password;
        this.photo = photo;
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

    public void addsPhoto(byte[] photo) throws EmptyFieldException{
        this.photo = photo;
    }

    //This method register a new establishment
    public void createEstablishment(String name, String address, String businessHour, SpecialityType speciality, String phone1, String phone2,String photoURL) {
        Establishment establishment = new Establishment(name, address, businessHour, speciality, phone1, phone2, photoURL);

        if(!establishments.contains(establishment)){
            establishments.add(establishment);
        }
    }

    //This method evaluate an establishment
    public void evaluateEstablishment(Establishment establishment, int value) {
        if(establishment != null) {
            establishment.addEvaluation(this, value);
        }
    }

    //This method insert a comment to the establishment
    public void insertCommentEstablishment(Establishment establishment, String comment) {
        if(establishment != null) {
            establishment.addComment(this, comment);
        }
    }

    //This method remove a comment from an establishment
    public void removeCommentEstablishment(Establishment establishment, String comment) {
        establishment.removeComment(this, comment);
    }

    //This method allows change an establishment
    public void setEstablishments(Set<Establishment> newEstablishments) {
        this.establishments = newEstablishments;
    }

    public Set<Establishment> getEstablishments() {

        return establishments;
    }

    //This method remove an establishment
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

