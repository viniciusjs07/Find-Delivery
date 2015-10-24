package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOEvaluation;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;


public class EvaluationController {

    private static DAOEvaluation evaluationDAO;
    private static EvaluationController instance;
    private static EstablishmentController establishmentController;

    public static EvaluationController getInstance(Context context) {
        if (instance == null) {
            instance = new EvaluationController();
            evaluationDAO = new DAOEvaluation(context);
            establishmentController = EstablishmentController.getInstance(context);
        }
        return instance;
    }

    public void insertEvaluation(String idUser, String idEstablishment, Float grade) throws Exception {
        evaluationDAO.insert(idUser, idEstablishment, grade);
    }


    public void updateEvaluation(String idUser, String idEstablishment, Float grade) throws Exception {
        evaluationDAO.update(idUser, idEstablishment, grade);
    }

    public Float average(Map<User, String> mapEvaluation, String idEstablisment){
        Establishment establishment = establishmentController.getEstablishment(idEstablisment);
        return establishment.calculatesAverage(mapEvaluation);
    }


    public Map<User, String> searchEvaluationByEstablishment(String idEstab) throws Exception {
        return evaluationDAO.searchEvaluationByEstablishment(idEstab);
    }

}
