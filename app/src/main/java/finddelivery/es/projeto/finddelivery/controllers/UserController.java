package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.List;

import finddelivery.es.projeto.finddelivery.database.DAOUser;
import finddelivery.es.projeto.finddelivery.models.ManagerFindDelivery;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Daniela on 05/08/2015.
 */
public class UserController {

    private static DAOUser userDAO;
    private static UserController instance;

    private static ManagerFindDelivery managerFD;

    public static UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController();
            userDAO = new DAOUser(context);
            managerFD = new ManagerFindDelivery();
        }
        return instance;
    }

    //PARA ADICIONAR NOVO USUÁRIO 12/10/2015
    public void addUser(String name, String login, String password, byte[] photo) throws Exception{
        User user = new User(name, login, password, photo);
        managerFD.registerUser(user);
        userDAO.insert(user);
    }

    //PARA REMOVER NOVO USUÁRIO 12/10/2015
    public void removeUser(String login) throws Exception {
        User user = getUser(login);
        managerFD.removeUser(user);
        userDAO.delete(login);
    }

    //PARA EDITAR OS DADOS DO USUÁRIO 12/10/2015
    public void updateData(String name, String login, String newPassword, byte[] photo) throws Exception{
        managerFD.updateDataUser(name,login, newPassword, photo);

        User user = getUser(login);
        user.setName(name);
        user.setPassword(newPassword);
        user.setPhoto(photo);
        userDAO.update(user);
    }

    //PARA VALIDAR O LOGIN (username, senha) 12/10/2015
    public boolean validatesUserLogin(String login, String password) throws Exception {
        managerFD.isLoginValid(login, password);
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

    public void insert(User user) throws Exception {
        userDAO.insert(user);
    }

    public void update(User user) throws Exception {
        userDAO.update(user);
    }

    public void delete(String login) throws Exception {
        userDAO.delete(login);
    }

    public List<User> findAllUsers() throws Exception {
        return userDAO.findAll();
    }

    public boolean validatesLogin(String login, String password) throws Exception {
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
        for (User user : findAllUsers()){
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        return true;

    }

    public User getUser(String login){
        return userDAO.findById(login);
    }



}
