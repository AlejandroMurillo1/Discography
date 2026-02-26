package org.icesi.discography.servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import org.icesi.discography.models.Artist;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

import org.icesi.discography.services.ArtistService;

import jakarta.servlet.ServletException;
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

            String error = String.format(
                    "{\"error\": \"Parámetro 'name' es obligatorio (ej: ?name=shakira)\", \"status\": %d}",
                    HttpServletResponse.SC_BAD_REQUEST
            );

            resp.getWriter().write(error);
            return;
        }

        try {

            List<Artist> artists = artistService.searchArtists(name);


            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(artists));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");

            String error = String.format(
                    "{\"error\": \"Error al buscar artista: %s\", \"status\": %d}",
                    e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            resp.getWriter().write(error);
        }
    }
}