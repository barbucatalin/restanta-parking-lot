package org.parkinglot.parkinglot3.servlets;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "UserTerminationSession", urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Preluăm sesiunea actuală fără a crea una nouă dacă nu există
        HttpSession session = req.getSession(false);

        if (session != null) {
            // Curățăm datele stocate în sesiune
            session.invalidate();
        }

        // Deconectarea de la nivelul containerului (JAAS/Security)
        req.logout();

        // Redirecționare către rădăcina aplicației (pagina de pornire)
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
