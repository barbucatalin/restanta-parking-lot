package org.parkinglot.parkinglot3.servlets;

// Importuri corectate conform structurii proiectului tău
import org.parkinglot.parkinglot3.common.UserDto;
import org.parkinglot.parkinglot3.ejb.CarsBean;
import org.parkinglot.parkinglot3.ejb.UsersBean;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"})
)
@WebServlet(name = "AddCarAction", urlPatterns = {"/AddCar"})
public class AddCar extends HttpServlet {

    @Inject
    private UsersBean userService;

    @Inject
    private CarsBean carService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluăm membrii pentru meniul de selecție folosind noul pachet
        Collection<UserDto> allUsers = userService.findAllUsers();
        req.setAttribute("users", allUsers);

        // Expediere către pagina de formular
        req.getRequestDispatcher("/WEB-INF/pages/addCar.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Extragerea informațiilor din formular
        String plate = req.getParameter("license_plate");
        String spot = req.getParameter("parking_spot");
        String rawOwnerId = req.getParameter("owner_id");

        try {
            if (rawOwnerId != null && !rawOwnerId.isEmpty()) {
                Long ownerId = Long.valueOf(rawOwnerId);

                // Apelăm logica de business din EJB-ul injectat corect
                carService.createCar(plate, spot, ownerId);
            }
        } catch (NumberFormatException e) {
            // Eroare de conversie dacă owner_id nu este un număr valid
        }

        // Întoarcere la lista de vehicule
        resp.sendRedirect(req.getContextPath() + "/Cars");
    }
}