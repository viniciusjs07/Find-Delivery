package finddelivery.es.projeto.finddelivery.controllers;

import finddelivery.es.projeto.finddelivery.models.User;
import android.app.Activity;

/**
 * Created by Daniela on 23/06/2015.
 */
public abstract class Controller  {

    public static User loggedUser;

    protected Activity uContext;

    public Controller(Activity context) {
        this.uContext = context;
    }

    public Activity getContext() {
        return uContext;
    }


}

