package org.icesi.discography.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@WebServlet("/artists")
public class ArtistServlet extends HttpServlet {

    private ArtistService artistService;

    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {

        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());

        artistService = context.getBean(ArtistService.class);

        //NOTA: Estas lineas de codigo solo estan para confirmar que se esta haciendo la inyecion
        //      de beans correctamente
        System.out.println(" TEST:: Existe ArtistRepository? " + context.containsBean("artistRepository"));
        System.out.println("TEST:: Existe ArtisService?? " + context.containsBean("artistService"));
    }

    // GET: Listar artistas
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            List<Artist> artists = artistService.getAllArtists();

            resp.setContentType("application/json");
            String rp = gson.toJson(artists);

            resp.getWriter().write(rp);

        } catch (Exception e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String errorDoGet = gson.toJson(Map.of(
                    "error", "Json Error",
                    "details", e.getMessage()
            ));
            resp.getWriter().write(errorDoGet);
        }
    }

    // POST: Crear artista
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        Map<String,String> data = gson.fromJson(json.toString(), HashMap.class);

        artistService.createArtist(data.get("name"),data.get("nationality"));

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println("Artista creado: " + data.get("name"));
    }
}