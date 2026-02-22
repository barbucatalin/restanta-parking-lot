package org.parkinglot.parkinglot3.servlets;

import org.parkinglot.parkinglot3.common.CarDto;
import org.parkinglot.parkinglot3.ejb.CarsBean;

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

// Am actualizat rolurile pentru a corespunde cu web.xml
@DeclareRoles({"ADMIN", "USER"})
@ServletSecurity(
        // Oricine are rolul USER sau ADMIN poate vedea lista de masini (GET)
        value = @HttpConstraint(rolesAllowed = {"ADMIN", "USER"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(
                        value = "POST",
                        // Doar cine are rolul ADMIN poate sterge masini (POST)
                        rolesAllowed = {"ADMIN"}
                )
        }
)
@WebServlet(name = "Cars", urlPatterns = {"/Cars"})
public class Cars extends HttpServlet {

    @Inject
    private CarsBean carManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluare date din EJB
        List<CarDto> carList = carManager.findAllCars();

        // Calcul locuri disponibile
        final int MAX_CAPACITY = 100;
        int freeSpots = MAX_CAPACITY - carList.size();

        // Setare atribute pentru pagina JSP
        req.setAttribute("cars", carList);
        req.setAttribute("numberOfFreeParkingSpots", Math.max(0, freeSpots));
        req.setAttribute("activePage", "Cars");

        // Trimite datele catre fisierul JSP
        req.getRequestDispatcher("/WEB-INF/pages/cars.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluam array-ul de ID-uri pentru stergere din checkbox-urile din formular
        String[] selectedCarIds = req.getParameterValues("car_ids");

        if (selectedCarIds != null && selectedCarIds.length > 0) {
            // Conversie din String[] in List<Long>
            List<Long> idsToDelete = Arrays.stream(selectedCarIds)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            carManager.deleteCarsByIds(idsToDelete);
        }

        // Redirectionare inapoi la pagina de masini dupa stergere
        resp.sendRedirect(req.getContextPath() + "/Cars");
    }
}