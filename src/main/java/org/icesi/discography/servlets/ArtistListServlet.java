package org.icesi.discography.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
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

@WebServlet("/artists")
public class ArtistListServlet extends HttpServlet {

    private ArtistService artistService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {

        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());

        artistService = context.getBean(ArtistService.class);
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

}