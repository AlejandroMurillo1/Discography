package org.icesi.discography.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.services.TrackService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/tracks")
public class TrackListServlet extends HttpServlet {
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

        try {
            List<Track> tracks = trackService.getTracks();
            List<Map<String,Object>> jsonResponse = new ArrayList<>();

            tracks.forEach(track -> {
                Map<String, Object> trackMap = new HashMap<>();

                trackMap.put("id", track.getId());
                trackMap.put("title", track.getTitle());
                trackMap.put("genre", track.getGenre());
                trackMap.put("durationInSeconds", track.getDurationInSeconds());
                trackMap.put("albumTitle", track.getAlbumTitle());

                List<Map<String, Object>> artistsList = new ArrayList<>();
                List<Artist> singers = track.getSingers();

                singers.forEach(singer -> {
                    Map<String, Object> singerMap = new HashMap<>();
                    singerMap.put("name", singer.getName());
                    artistsList.add(singerMap);
                });

                trackMap.put("singers", artistsList);
                jsonResponse.add(trackMap);
            });

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(Map.of(
                    "error", "Error al obtener canciones",
                    "details", e.getMessage()
            )));
        }
    }
}
