package org.icesi.discography.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tracks/dashboard")
public class TrackDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Redirigimos al JSP ubicado en WEB-INF/jsp/tracks-dashboard.jsp
        try {
            req.getRequestDispatcher("/WEB-INF/views/TrackDashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el dashboard");
        }
    }
}