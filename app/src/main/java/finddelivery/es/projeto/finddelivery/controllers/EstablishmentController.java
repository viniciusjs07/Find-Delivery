package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.List;

import finddelivery.es.projeto.finddelivery.database.DAOEstablishment;
import finddelivery.es.projeto.finddelivery.models.Establishment;

/**
 * Created by Computação on 12/08/2015.
 */
public class EstablishmentController  {

    private static DAOEstablishment establishmentDAO;
    private static EstablishmentController instance;


    // Construtor
    public static EstablishmentController getInstance(Context context) {
        if (instance == null) {
            instance = new EstablishmentController();
            establishmentDAO = new DAOEstablishment(context);
        }
        return instance;
    }


    // insert
    public void insert(Establishment establishment) throws Exception {
        establishmentDAO.insert(establishment);
    }


    //update
    public void update(Establishment establishment) throws Exception {
        establishmentDAO.update(establishment);
    }


    // listar estabelecimentos
    public List<Establishment> findAll() throws Exception {
        return establishmentDAO.findAll();
    }


    // Valida nome do estabelecimento
    public boolean validatesEstablishmentName(String name) throws Exception {
        if(name == null || name.equals("")){
            return false;
        }
        for (Establishment establishment : findAll()){
            if (establishment.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    // valida o cadastro do estabelecimento
    public boolean validatesEstablishmentAddress (String address) throws  Exception {
        if (address == null || address.equals("")) {
            return false;
        }
        return  true;
    }

    public boolean validatesEstablishmentBusinesseHour (String businessHour) throws  Exception {
        if (businessHour == null || businessHour.equals("")) {
            return false;
        }
        return  true;
    }

    public  boolean validateEstablishmentPhone (String phone) throws Exception{
        if(phone == null || phone.equals("")) {
            return  false;
        }
        return  true;
    }

}
