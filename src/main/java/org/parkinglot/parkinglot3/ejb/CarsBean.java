package org.parkinglot.parkinglot3.ejb;

// Importuri corectate către DTO-uri
import org.parkinglot.parkinglot3.common.CarDto;
import org.parkinglot.parkinglot3.common.UserDto;

// Importuri corectate către Entități (bazat pe structura ta din folderul 'entities')
import example.parkinglot.entities.Car;
import example.parkinglot.entities.User;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class CarsBean {

    private static final Logger LOGGER = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    private EntityManager em;

    /**
     * Returnează lista tuturor mașinilor mapate la obiecte DTO.
     */
    public List<CarDto> findAllCars() {
        try {
            LOGGER.info("Preluare listă completă vehicule");

            // Atenție: Dacă în clasa Car ai redenumit variabila owner în carOwner,
            // interogarea trebuie să fie: "SELECT c FROM Car c JOIN FETCH c.carOwner"
            List<Car> vehicleList = em.createQuery(
                    "SELECT c FROM Car c JOIN FETCH c.carOwner",
                    Car.class
            ).getResultList();

            return convertToDtoList(vehicleList);
        } catch (Exception e) {
            throw new EJBException("Eroare la extragerea mașinilor", e);
        }
    }

    public CarDto findById(Long id) {
        Car entity = em.find(Car.class, id);
        if (entity == null) return null;

        return new CarDto(
                entity.getId(),
                entity.getLicensePlate(),
                entity.getParkingSpot(),
                entity.getOwner().getUsername()
        );
    }

    public void createCar(String plate, String spot, Long uId) {
        try {
            LOGGER.info("Salvare vehicul nou în sistem");

            User usr = em.find(User.class, uId);
            if (usr == null) throw new EJBException("Utilizatorul " + uId + " nu există.");

            Car newVehicle = new Car();
            newVehicle.setLicensePlate(plate);
            newVehicle.setParkingSpot(spot);
            newVehicle.setOwner(usr);

            // Actualizăm și colecția utilizatorului
            usr.getCars().add(newVehicle);

            em.persist(newVehicle);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateCar(Long id, String plate, String spot, Long uId) {
        try {
            LOGGER.info("Actualizare date pentru mașina cu ID: " + id);

            Car targetCar = em.find(Car.class, id);
            User targetUser = em.find(User.class, uId);

            if (targetCar == null || targetUser == null) {
                throw new EJBException("Resurse inexistente pentru update.");
            }

            // Gestionare logică relație veche
            User previousOwner = targetCar.getOwner();
            if (previousOwner != null && !previousOwner.equals(targetUser)) {
                previousOwner.getCars().remove(targetCar);
            }

            // Setare date noi
            targetCar.setLicensePlate(plate);
            targetCar.setParkingSpot(spot);
            targetCar.setOwner(targetUser);

            // Sincronizare relație nouă
            if (!targetUser.getCars().contains(targetCar)) {
                targetUser.getCars().add(targetCar);
            }

            em.merge(targetCar);
        } catch (Exception e) {
            throw new EJBException("Eroare la update: " + e.getMessage());
        }
    }

    public void deleteCarsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return;

        try {
            LOGGER.info("Eliminare vehicule selectate");
            em.createQuery("DELETE FROM Car c WHERE c.id IN :targetIds")
                    .setParameter("targetIds", ids)
                    .executeUpdate();
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    // Helper redenumit și modernizat folosind Java Streams
    private List<CarDto> convertToDtoList(List<Car> cars) {
        return cars.stream()
                .map(c -> new CarDto(
                        c.getId(),
                        c.getLicensePlate(),
                        c.getParkingSpot(),
                        c.getOwner().getUsername()))
                .collect(Collectors.toList());
    }
}
