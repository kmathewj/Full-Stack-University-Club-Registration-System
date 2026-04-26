package ryerson.ca.frontend.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import ryerson.ca.frontend.persistence.User_CRUD;
import ryerson.ca.frontend.auth.AuthHelper;

@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        String role = User_CRUD.validateLogin(user, pass);

        if (role != null) {
            String token = AuthHelper.createToken(user, role);
            HttpSession session = request.getSession();
            session.setAttribute("token", token);
            session.setAttribute("user", user);
            session.setAttribute("role", role);
            
            // route based on role identified in User_DB
            if ("admin".equals(role)) {
                response.sendRedirect("adminDash.jsp");
            } else {
                response.sendRedirect("main.jsp");
            }
        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }
}