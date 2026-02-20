package org.parkinglot.parkinglot3.ejb;

// Import corectat către DTO-ul tău
import org.parkinglot.parkinglot3.common.UserDto;

// Import corectat către Entitatea User
import example.parkinglot.entities.User;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class UsersBean {

    private static final Logger LOGGER = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returnează toți utilizatorii înregistrați.
     */
    public List<UserDto> findAllUsers() {
        try {
            LOGGER.info("Interogare listă completă de utilizatori din sistem");

            // Interogarea JPQL
            List<User> userEntities = entityManager
                    .createQuery("SELECT usr FROM User usr", User.class)
                    .getResultList();

            // Mapare către UserDto folosind structura pachetelor tale
            return userEntities.stream()
                    .map(u -> new UserDto(
                            u.getId(),
                            u.getUsername(),
                            u.getEmail()
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            LOGGER.severe("Eroare la recuperarea utilizatorilor: " + e.getMessage());
            throw new EJBException("Nu s-a putut încărca lista de membri.", e);
        }
    }
}