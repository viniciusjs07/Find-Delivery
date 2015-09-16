package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOComment;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 16/09/2015.
 */
public class CommentController {

    private static DAOComment commentDAO;
    private static CommentController instance;

    // Construtor
    public static CommentController getInstance(Context context) {
        if (instance == null) {
            instance = new CommentController();
            commentDAO = new DAOComment(context);

        }
        return instance;
    }

    // insert
    public void insert(String idUser, String idEstab, String comment) throws Exception {
        commentDAO.insert(idUser, idEstab, comment);
    }

    //update
    public void update(String idUser, String idEstab, String comment) throws Exception {
        commentDAO.update(idUser, idEstab, comment);
    }

    public Map<User, String> searchCommentByEstablishment(String idEstab) throws Exception {
        return commentDAO.searchCommentByEstablishment(idEstab);
    }
}
