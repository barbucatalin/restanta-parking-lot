package org.parkinglot.parkinglot1.servlets;

// Importuri corectate conform structurii tale
import org.parkinglot.parkinglot1.common.CarDto;
import org.parkinglot.parkinglot1.ejb.CarsBean;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles({"READ_CARS", "WRITE_CARS"})
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(
                        value = "POST",
                        rolesAllowed = {"WRITE_CARS"}
                )
        }
)
@WebServlet(name = "CarsManagement", urlPatterns = {"/Cars"})
public class Cars extends HttpServlet {

    @Inject
    private CarsBean carManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluare date din EJB-ul injectat folosind pachetul corect
        List<CarDto> carList = carManager.findAllCars();

        // Calcul locuri disponibile
        final int MAX_CAPACITY = 100;
        int freeSpots = MAX_CAPACITY - carList.size();

        // Setare atribute pentru pagina JSP
        req.setAttribute("cars", carList);
        req.setAttribute("numberOfFreeParkingSpots", Math.max(0, freeSpots));
        req.setAttribute("activePage", "Cars");

        req.getRequestDispatcher("/WEB-INF/pages/cars.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluam array-ul de ID-uri pentru stergere
        String[] selectedCarIds = req.getParameterValues("car_ids");

        if (selectedCarIds != null && selectedCarIds.length > 0) {
            // Conversie folosind Stream API
            List<Long> idsToDelete = Arrays.stream(selectedCarIds)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            carManager.deleteCarsByIds(idsToDelete);
        }

        // Redirectionare catre lista actualizata
        resp.sendRedirect(req.getContextPath() + "/Cars");
    }
}