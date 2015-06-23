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


    public void registerUser(String name,String login, String password) {
        for (User user : getListUser()) {
            if (user.getName().equals(name) && user.getLogin().equals(login) && user.getPassword().equals(password)) {
                listUser.add(user);
            }

        }
    }

    public boolean isLoginValid(String login,String password) {

        for(User user:getListUser()){
            if(user.getLogin().equals(login)&& user.getPassword().equals(password)){
                return true;
            }

        }
        return false;
      }


    //not implemented method
    public void login(String login,String password){


    }

     public void removeAccount(User user){
        for(User newUser: listUser) {
            if(newUser.equals(user)) {
                listUser.remove(user);
                }
        }

    }

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

    //is metodo not implemented//
    public void listEstablishment(List<Establishment> establishments){

    }
}
