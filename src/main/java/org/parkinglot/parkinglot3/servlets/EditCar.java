package org.parkinglot.parkinglot3.servlets;

// Importuri corectate conform ierarhiei proiectului tău
import org.parkinglot.parkinglot3.common.CarDto;
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
        value = @HttpConstraint(rolesAllowed = {"ADMIN"})
)
@WebServlet(name = "ModifyCarInfo", urlPatterns = {"/EditCar"})
public class EditCar extends HttpServlet {

    @Inject
    private CarsBean carService;

    @Inject
    private UsersBean userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluăm ID-ul vehiculului din cerere
        String idParam = req.getParameter("id");

        if (idParam != null) {
            Long id = Long.valueOf(idParam);

            // Folosim serviciile injectate corect prin noile pachete
            CarDto currentCar = carService.findById(id);
            Collection<UserDto> allUsers = userService.findAllUsers();

            req.setAttribute("car", currentCar);
            req.setAttribute("users", allUsers);

            req.getRequestDispatcher("/WEB-INF/pages/editCar.jsp")
                    .forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/Cars");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Colectăm noile date trimise prin formular
        Long id = Long.parseLong(req.getParameter("car_id"));
        String plate = req.getParameter("license_plate");
        String spot = req.getParameter("parking_spot");
        Long owner = Long.parseLong(req.getParameter("owner_id"));

        // Actualizăm vehiculul în baza de date
        carService.updateCar(id, plate, spot, owner);

        // Redirecționare către lista de mașini
        resp.sendRedirect(req.getContextPath() + "/Cars");
    }
}