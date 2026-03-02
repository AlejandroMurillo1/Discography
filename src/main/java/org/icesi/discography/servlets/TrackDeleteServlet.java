package org.icesi.discography.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.icesi.discography.services.TrackService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Map;

@WebServlet("/tracks/delete/*")
public class TrackDeleteServlet extends HttpServlet {

    private TrackService trackService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("springContext");
        trackService = context.getBean(TrackService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", "ID requerido en la URL (ej: /tracks/5)"
            )));
            return;
        }

        try {
            long id = Long.parseLong(pathInfo.substring(1));
            trackService.deleteTrack(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", "ID inválido (debe ser numérico)"
            )));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", e.getMessage()
            )));
        }
    }
}