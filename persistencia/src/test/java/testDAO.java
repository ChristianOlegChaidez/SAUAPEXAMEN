import mx.desarrollo.persistence.dao.ProfesorDAO;
import mx.desarrollo.persistence.persistence.HibernateUtil;
import mx.desarrollo.entity.Profesor;

import java.util.List;

public class testDAO {

    public static void main(String[] args) {
        ProfesorDAO profesorDAO = new ProfesorDAO(HibernateUtil.getEntityManager());

        for (Profesor profesor : profesorDAO.findAll()) {
            System.out.println(profesor.getNombres() + " " + profesor.getApellidoPaterno() + " || id [" + profesor.getId() + "]");
        }
    }
}