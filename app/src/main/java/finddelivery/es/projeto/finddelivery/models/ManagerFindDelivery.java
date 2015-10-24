package finddelivery.es.projeto.finddelivery.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerFindDelivery {

    public List<User> listUser;

    public ManagerFindDelivery(){
        listUser = new ArrayList<User>();
    }

    public List<User> getListUser(){
        return listUser;
    }

    public void registerUser(User user)  {
        if(!listUser.contains(user)){
            listUser.add(user);
        }
    }

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

    public boolean isLoginValid(String login, String password) {
        for(User user : getListUser()){
            if(user.getLogin().equals(login)&& user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }


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

}
