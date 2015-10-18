package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOComment;
import finddelivery.es.projeto.finddelivery.models.User;

public class CommentController {

    private static DAOComment commentDAO;
    private static CommentController instance;

    public static CommentController getInstance(Context context) {
        if (instance == null) {
            instance = new CommentController();
            commentDAO = new DAOComment(context);
        }
        return instance;
    }

    public void insertComment(String idUser, String idEstablishment, String comment) throws  Exception {
        commentDAO.insert(idUser, idEstablishment, comment);
    }

    public void updateComment(String idUser, String idEstablishment, String comment) throws Exception {
        commentDAO.update(idUser, idEstablishment, comment);
    }

    public Map<User, String> searchCommentByEstablishment(String idEstablishment) throws Exception {
        return commentDAO.searchCommentByEstablishment(idEstablishment);
    }
}
