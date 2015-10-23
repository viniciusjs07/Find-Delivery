package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.database.DAOEstablishment;
import finddelivery.es.projeto.finddelivery.models.Establishment;


public class EstablishmentController {

    private static DAOEstablishment establishmentDAO;
    private static EstablishmentController instance;
    public List<Establishment> establishmentsByName;
    public List<Establishment> establishmentsBySpeciality;

    public static boolean isSearchAdvanced = false;
    public static boolean isSearchAdvancedByName = true;

    public static UserController userController;

    public static EstablishmentController getInstance(Context context) {
        if (instance == null) {
            instance = new EstablishmentController();
            establishmentDAO = new DAOEstablishment(context);
            userController = UserController.getInstance(context);
        }
        return instance;
    }

    public void insertEstablishment(String name, String address, String workHour, String specialityType, String phone1, String phone2, byte[] photo, String idUser) throws Exception {
        Establishment establishment = new Establishment(name, address, workHour, specialityType, phone1, phone2, photo);
        establishmentDAO.insert(establishment, idUser);
    }

    public void updateEstablishment(String name, String address, String workHour, String specialityType, String phone1, String phone2, byte[] photo, String idUser) throws Exception {
        Establishment establishment = new Establishment(name, address, workHour, specialityType, phone1, phone2, photo);
        establishmentDAO.update(establishment, idUser);
    }

    public void deleteEstablishment(String idEstablishment) {
        establishmentDAO.delete(idEstablishment);

    }

    public boolean isOwnerEstablishment(String idEstablishment, String loginUser) {
        return establishmentDAO.isOwnerEstablishment(idEstablishment, loginUser);
    }


    public boolean validatesEstablishmentName(String name) throws Exception {
        if(name == null || name.trim().equals("")){
            return false;
        }
        for (Establishment establishment : listAllEstablishments()){
            if (establishment.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean validatesEstablishmentAddress (String address) throws  Exception {
        if (address == null || address.trim().equals("")) {
            return false;
        }
        return  true;
    }

    public boolean validatesEstablishmentBusinesseHour (String businessHour) throws  Exception {
        if (businessHour == null || businessHour.trim().equals("")) {
            return false;
        }
        return  true;
    }

    public  boolean validateEstablishmentPhone (String phone) throws Exception{
        return phone.matches(".([0][1-9][1-9].)[6-9][0-9]{3}-[0-9]{4}") || phone.matches(".([0][1-9][1-9].)[2-5][0-9]{3}-[0-9]{4}");
    }

    public List<Establishment> listAllEstablishments() throws Exception {
        return establishmentDAO.findAll();
    }

    public List<Establishment> listMyEstablishments(String idUser) throws Exception {
        return establishmentDAO.findByUser(idUser);
    }

    public Establishment getEstablishment(String name) {
        return establishmentDAO.findByName(name);
    }

    public void listBySpeciality(String speciality) throws Exception {
        establishmentsBySpeciality = new ArrayList<>();
        establishmentsBySpeciality = establishmentDAO.findBySpeciality(speciality);;
    }

    public void listByName(String name) throws Exception {
        if (establishmentDAO.findByName(name) != null) {
            Establishment establishment = establishmentDAO.findByName(name);
            establishmentsByName = new ArrayList<>();
            if(!establishmentsByName.contains(establishment)){
                establishmentsByName.add(establishment);
            }
        }
    }
}
