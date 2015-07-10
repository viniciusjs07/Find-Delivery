package finddelivery.es.projeto.finddelivery.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vinicius on 10/06/2015.
 */
public class ManagerFindDelivery {

    private User user;
    private List<User> listUser;


    public ManagerFindDelivery(){
        listUser = new ArrayList<User>();
    }

    public List<User> getListUser(){
        return listUser;
    }

    //This method register a new user
    public void registerUser(String name,String login, String password) throws ExceededCharacterException, EmptyFieldException {
        User user = new User(name,login,password);
        if(!listUser.contains(user)){
            listUser.add(user);
        }


    }

    //This method valid a login
    public boolean isLoginValid(String login,String password) {
        for(User user:getListUser()){
            if(user.getLogin().equals(login)&& user.getPassword().equals(password)){
                return true;
            }

        }
        return false;
      }

    //This method remove an user
     public void removeAccount(User user){
        for(User newUser: listUser) {
            if(newUser.equals(user)) {
                listUser.remove(user);
                }
        }

    }

    //This method search an establishment by especiality and returns a list
    public Set<Establishment> findEstablishmentBySpeciality(SpecialityType speciality) {
        Set<Establishment> listEstablishments = new HashSet<Establishment>();
        for (User user: getListUser()) {
            for(Establishment establishment: user.getEstablishments()) {
                if(speciality.equals(establishment.getSpeciality())) {
                    listEstablishments.add(establishment);
                }
            }
        }
        return listEstablishments;
    }

    //This method search an establishment by name and returns a list
    public Establishment findEstablishmentByName(String name) {
        for (User user: getListUser()) {
            for(Establishment establishment: user.getEstablishments()) {
                if(establishment.getName().equals(name)) {
                    return establishment;
                }
            }
        }
        return null;
    }

    //is method not implemented//
    public void listEstablishment(List<Establishment> establishments){

    }
    //this method user updates
    public void setUser(List<User> newUser){
        this.listUser = newUser;
    }
}
