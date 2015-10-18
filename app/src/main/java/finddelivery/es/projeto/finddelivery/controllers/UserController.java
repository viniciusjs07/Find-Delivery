package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.List;

import finddelivery.es.projeto.finddelivery.database.DAOUser;
import finddelivery.es.projeto.finddelivery.models.User;


public class UserController {

    private static DAOUser userDAO;
    private static UserController instance;

    public static UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController();
            userDAO = new DAOUser(context);
        }
        return instance;
    }

    public void insertUser(String name, String login, String password, byte[] photo) throws Exception{
        User user = new User(name, login, password, photo);
        userDAO.insert(user);
    }

    public void deleteUser(String login) throws Exception {
        userDAO.delete(login);
    }

    public void updateUser(String name, String login, String newPassword, byte[] photo) throws Exception{
        User user = getUser(login);
        user.setName(name);
        user.setPassword(newPassword);
        user.setPhoto(photo);
        userDAO.update(user);
    }

    public boolean validatesLoginUser(String login, String password) throws Exception {
        User user = userDAO.findByLogin(login, password);
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            return false;
        }
        String input = login + password;
        String expected = user.getLogin() + user.getPassword();
        if (input.equals(expected)) {
            return true;
        }
        return false;

    }

    public  boolean validatesPasswords(String password, String passwordConfirm){
        if(password == null || password.equals("")){
            return false;
        }
        if(passwordConfirm == null || passwordConfirm.equals("")){
            return false;
        }
        if(password.length() <= 5  || password.length() > 15  || passwordConfirm.length() <=5 || passwordConfirm.length() > 15 ){
            return false;
        }
        if (password.equals(passwordConfirm)){
            return true;
        }
        return false;
    }

    public boolean validatesUserName(String login) throws Exception {
        if(login == null || login.equals("")){
            return false;
        }
        for (User user : listAllUsers()){
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        return true;

    }

    public List<User> listAllUsers() throws Exception {
        return userDAO.findAll();
    }

    public User getUser(String login){
        return userDAO.findById(login);
    }


}
