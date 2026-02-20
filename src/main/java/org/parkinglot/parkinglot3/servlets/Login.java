package org.parkinglot.parkinglot3.servlets;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserAuthentication", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    private static final String LOGIN_VIEW = "/WEB-INF/pages/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Redirecționare simplă către pagina de autentificare
        req.getServletContext().getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    /**
     * Metodă apelată în caz de eșec al autentificării (configurat în web.xml).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Adăugăm un atribut de eroare cu un mesaj personalizat
        req.setAttribute("errorMessage", "Credentiale nevalide. Va rugam sa reincercati.");

        // Reîncărcăm formularul de login
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }
}
