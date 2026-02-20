package org.parkinglot.parkinglot3.servlets;

// Importuri corectate pentru a se potrivi cu structura proiectului tău
import org.parkinglot.parkinglot3.common.UserDto;
import org.parkinglot.parkinglot3.ejb.UsersBean;

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
import java.util.Collection;

@DeclareRoles({"READ_USERS", "WRITE_USERS"})
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(
                        value = "POST",
                        rolesAllowed = {"WRITE_USERS"}
                )
        }
)
@WebServlet(name = "UserListController", urlPatterns = {"/Users"})
public class Users extends HttpServlet {

    @Inject
    private UsersBean userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Utilizăm serviciul injectat pentru a prelua colecția de utilizatori
        Collection<UserDto> userList = userService.findAllUsers();

        // Mapăm datele pentru a fi accesibile în pagina JSP
        req.setAttribute("users", userList);
        req.setAttribute("activePage", "Users");

        // Trimitem cererea către pagina de afișare
        req.getRequestDispatcher("/WEB-INF/pages/users.jsp")
                .forward(req, resp);
    }
}