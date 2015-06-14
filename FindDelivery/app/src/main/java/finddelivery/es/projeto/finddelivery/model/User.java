package finddelivery.es.projeto.finddelivery.model;

import java.io.Serializable;

/**
 * Created by Vinicius on 08/06/2015.
 */
public class User implements Serializable{

    private String name;
    private String login;
    private String photoUrl;
    private String password;

    public User(String name,String login,String password)throws EmptyFieldException, ExceededCharacterException{
        this.name = name;
        this.login = login;
        this.password = password;
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

    @Override
    public String toString() { return "User " + getName() + " login " + getLogin();
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

