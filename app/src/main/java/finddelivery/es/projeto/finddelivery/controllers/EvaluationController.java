package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.Collection;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.database.DAOComment;
import finddelivery.es.projeto.finddelivery.database.DAOEvaluation;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 23/09/2015.
 */
public class EvaluationController {

    private static DAOEvaluation evaluationDAO;
    private static EvaluationController instance;
    private static UserController userController;
    private static EstablishmentController establishmentController;

    // Construtor
    public static EvaluationController getInstance(Context context) {
        if (instance == null) {
            instance = new EvaluationController();
            evaluationDAO = new DAOEvaluation(context);
            userController = UserController.getInstance(context);
            establishmentController = EstablishmentController.getInstance(context);
        }
        return instance;
    }

    //PARA INSERIR AVALIACAO 12/10/2015
    public void insertEvaluation(String idUser, String idEstablishment, Float grade) throws Exception {
        Establishment establishment = establishmentController.getEstablishment(idEstablishment);
        User user = userController.getUser(idUser);
        user.evaluateEstablishment(establishment, grade);
        evaluationDAO.insert(idUser, idEstablishment, grade);
    }

   //PARA EDITAR AVALIAÇÃO 12/10/2015
    public void updateEvaluation(String idUser, String idEstablishment, Float grade) throws Exception {
        Establishment establishment = establishmentController.getEstablishment(idEstablishment);
        User user = userController.getUser(idUser);
        user.editEvaluationEstablishment(establishment, grade);
        evaluationDAO.insert(idUser, idEstablishment, grade);
    }

    //PARA RETORNAR A MÉDIA 12/10/2015
    public Float average(Map<User, String> mapEvaluation, String idEstablisment){
        Establishment establishment = establishmentController.getEstablishment(idEstablisment);
        Float average = establishment.calculatesAverage(mapEvaluation);
        return average;
    }

    //PARA RETORNAR O MAPA (User, avaliação)
    public Map<User, Float> getEvaluations(String idEstablishment){
        Establishment establisment = establishmentController.getEstablishment(idEstablishment);
        Map<User, Float> evaluations = establisment.getEvaluations();
        return evaluations;
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



    public Float average(Map<User,String> mapEvaluation){
        Collection<String> grades = mapEvaluation.values();
        Float sum = Float.valueOf(0);
        for (String grade: grades){
            sum += Float.valueOf(grade);
        }
        Float average = Float.valueOf(0);
        if(grades.size() != 0){
            average = sum / grades.size();
        }
        return average;
    }

}
