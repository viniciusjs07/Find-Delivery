package finddelivery.es.projeto.finddelivery.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vinicius on 10/06/2015.
 */
public class ManagerFindDelivery {

    public List<User> listUser;

    public ManagerFindDelivery(){
        listUser = new ArrayList<User>();
    }

    //This method returns a list users
    public List<User> getListUser(){
        return listUser;
    }

    //This method register a new user
    public void registerUser(User user)  {
        if(!listUser.contains(user)){
            listUser.add(user);
        }
    }

    //This method remove an user
    public void removeUser(User user){
        for(User u: listUser) {
            if(u.equals(user)) {
                listUser.remove(u);
            }
        }
    }

    public void updateDataUser(String name, String login, String password, byte[] photo){
        for (User u: listUser){
            if(u.getLogin().equals(login)){
                u.setName(name);
                u.setPassword(password);
                u.setPhoto(photo);
            }
        }
    }

    //This method valid a login
    public boolean isLoginValid(String login, String password) {
        for(User user : getListUser()){
            if(user.getLogin().equals(login)&& user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
      }


    /*------------------------------------------------------------------------------------------------*/
    //This method search an establishment by especiality and returns a list
    public Set<Establishment> findEstablishmentBySpeciality(String speciality) {
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

    //This method not implemented//
    public void listEstablishment(){
        List<Establishment> estab = new ArrayList<>();
        for (User user: listUser){
            for(Establishment estabelecimento: user.getEstablishments()){
                estab.add(estabelecimento);
            }
        }
    }
/*

    //this method user updates
    public void setUser(List<User> newUser){
        this.listUser = newUser;
    }
*/

}
