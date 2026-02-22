package org.icesi.discography.servlets;

import java.io.IOException;

import org.icesi.discography.models.Artist;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

import org.icesi.discography.services.ArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/artists/search")
public class ArtistSearchServlet extends HttpServlet {

    private ArtistService artistService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        artistService = context.getBean(ArtistService.class);
    }

    // GET: Buscar artista por nombre
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");

            String error = "{\"error\": \"Parámetro 'name' es obligatorio (ej: ?name=shakira)\", \"status\": 400}";
            resp.getWriter().write(error);
            return;
        }

        try {
            Artist artist = artistService.getArtistWithTracks(name);

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(artist));

        } catch (Exception e) {

            if (e.getMessage() != null && e.getMessage().contains("no encontrado")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("application/json");

                String error = String.format(
                    "{\"error\": \"Artista no encontrado con nombre: %s\", \"status\": 404}",
                    name
                );
                resp.getWriter().write(error);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");

                String error = String.format(
                        "{\"error\": \"Error al buscar artista: %s\", \"status\": 500}",
                        e.getMessage()
                );
                resp.getWriter().write(error);
            }
        }
    }
}