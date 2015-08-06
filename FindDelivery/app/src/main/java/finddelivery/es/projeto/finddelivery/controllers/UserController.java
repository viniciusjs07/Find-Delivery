package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.List;

import finddelivery.es.projeto.finddelivery.database.DAOUser;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Daniela on 05/08/2015.
 */
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

    public void insert(User usuario) throws Exception {
        userDAO.insert(usuario);
    }

    public void update(User usuario) throws Exception {
        userDAO.update(usuario);
    }

    public List<User> findAll() throws Exception {
        return userDAO.findAll();
    }

    public boolean validaLogin(String usuario, String senha) throws Exception {
        User user = userDAO.findByLogin(usuario, senha);
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            return false;
        }
        String informado = usuario + senha;
        String esperado = user.getLogin() + user.getPassword();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;

    }


}
