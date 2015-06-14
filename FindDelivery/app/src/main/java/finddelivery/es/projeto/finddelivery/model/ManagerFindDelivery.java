package finddelivery.es.projeto.finddelivery.model;

import java.util.ArrayList;
import java.util.List;

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

    //is metodo not implemented//
    public void listEstablishment(List<Establishment> establishments){

    }
}
