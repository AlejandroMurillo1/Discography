package org.icesi.discography.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.jspecify.annotations.NonNull;
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

        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("springContext");

        artistService = context.getBean(ArtistService.class);
    }

    // GET: Listar artistas
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            List<Artist> artists = artistService.getAllArtists();
            List<Map<String, Object>> jsonResponse = new ArrayList<>();

            for(Artist artist: artists){
                Map<String, Object> artistMap = getStringObjectMap(artist);
                jsonResponse.add(artistMap);
            }

            System.out.println(gson.toJson(jsonResponse));

            resp.setContentType("application/json");
            String rp = gson.toJson(jsonResponse);

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

    private static Map<String, Object> getStringObjectMap(Artist artist) {
        Map<String, Object> artistMap = new HashMap<>();
        artistMap.put("id", artist.getId());
        artistMap.put("name", artist.getName());
        artistMap.put("nationality", artist.getNationality());

        List<Map<String, Object>> songsList = new ArrayList<>();
        if (artist.getTracks() != null) {
            for (Track track : artist.getTracks()) {
                Map<String, Object> trackMap = new HashMap<>();
                trackMap.put("id", track.getId());
                trackMap.put("title", track.getTitle());
                songsList.add(trackMap);
            }
        }
        artistMap.put("tracks", songsList);
        return artistMap;
    }

    // DELETE borrar artistas por id
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        try {

            long id = Long.parseLong(pathInfo.substring(1));
            artistService.deleteArtistById(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "ID inválido (debe ser numérico)", e);
        } catch (IllegalArgumentException e) {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    private void sendError(HttpServletResponse resp, int status, String message, Exception e)
            throws IOException {

        resp.setStatus(status);
        resp.setContentType("application/json");

        JsonObject error = new JsonObject();
        error.addProperty("error", message);
        error.addProperty("details", e != null ? e.getMessage() : "N/A");

        resp.getWriter().write(gson.toJson(error));
    }


}