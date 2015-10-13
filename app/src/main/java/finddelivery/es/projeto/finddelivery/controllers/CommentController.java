package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOComment;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 16/09/2015.
 */
public class CommentController {

    private static DAOComment commentDAO;
    private static CommentController instance;
    private static UserController userController;
    private static EstablishmentController establishmentController;

    // Construtor
    public static CommentController getInstance(Context context) {
        if (instance == null) {
            instance = new CommentController();
            commentDAO = new DAOComment(context);
            userController = UserController.getInstance(context);
            establishmentController = EstablishmentController.getInstance(context);

        }
        return instance;
    }

    //PARA ADICIONAR UM COMENTÁRIO 12/10/2015
    public void addComment(String idUser, String idEstablishment, String comment) throws  Exception {
        Establishment establishment = establishmentController.getEstablishment(idEstablishment);
        User user = userController.getUser(idUser);
        user.insertCommentEstablishment(establishment, comment);
        commentDAO.insert(idUser, idEstablishment, comment);
    }

    //PARA REMOVER UM COMENTÁRIO 12/10/2015
    public void removeComment(String idUser, String idEstablishment, String comment){
        Establishment establishment = establishmentController.getEstablishment(idEstablishment);
        User user = userController.getUser(idUser);
        user.removeCommentEstablishment(establishment, comment);
        //Dao ?
    }

    //PARA RETORNAR UM MAPA  (User, comentário)
    public Map<User, String> getCommentsEstablishment(String idEstablishment){
        Establishment establishment = establishmentController.getEstablishment(idEstablishment);
        Map<User, String> comments = establishment.getComments();
        //DAO?
        return comments;
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
