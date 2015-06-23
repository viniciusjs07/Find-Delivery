package finddelivery.es.projeto.finddelivery.controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Daniela on 23/06/2015.
 */
public class UserController extends Controller {

    private SharedPreferences uSession;
    private Editor uEditor;

    public UserController(Activity context) {
        super(context);
        this.uSession = context.getSharedPreferences("User", 0);
        this.uEditor = uSession.edit();
    }

    private void login(User user) {
        loggedUser = user;
        uEditor.putString("login", user.getLogin());
        uEditor.commit();
    }

    public void logout() {
        loggedUser = null;
        uEditor.clear();
        uEditor.commit();
    }


}
