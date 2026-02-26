package org.icesi.discography.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.icesi.discography.models.Track;
import org.icesi.discography.services.TrackService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/tracks/search")
public class TrackSearchServlet extends HttpServlet {

    private TrackService trackService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        trackService = context.getBean(TrackService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String title = req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", "Parámetro 'title' es obligatorio"
            )));
            return;
        }

        try {
            // Implementar búsqueda en TrackService o filtrar aquí
            List<Track> allTracks = trackService.getTracks();
            List<Track> results = allTracks.stream()
                    .filter(track -> track.getTitle().toLowerCase()
                            .contains(title.toLowerCase()))
                    .collect(Collectors.toList());

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(results));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", "Error en la búsqueda",
                    "details", e.getMessage()
            )));
        }
    }
}
