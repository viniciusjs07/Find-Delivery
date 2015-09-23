package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOComment;
import finddelivery.es.projeto.finddelivery.database.DAOEvaluation;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 23/09/2015.
 */
public class EvaluationController {

    private static DAOEvaluation evaluationDAO;
    private static EvaluationController instance;

    // Construtor
    public static EvaluationController getInstance(Context context) {
        if (instance == null) {
            instance = new EvaluationController();
            evaluationDAO = new DAOEvaluation(context);

        }
        return instance;
    }

    // insert
    public void insert(String idUser, String idEstab, Float grade) throws Exception {
        evaluationDAO.insert(idUser, idEstab, grade);
    }

    //update
    public void update(String idUser, String idEstab, Float grade) throws Exception {
        evaluationDAO.update(idUser, idEstab, grade);
    }

    public Map<User, String> searchEvaluationByEstablishment(String idEstab) throws Exception {
        return evaluationDAO.searchEvaluationByEstablishment(idEstab);
    }
}
