package org.icesi.discography.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import org.icesi.discography.models.Artist;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.icesi.discography.services.ArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArtistCreateServlet extends HttpServlet {

    private ArtistService artistService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        artistService = context.getBean(ArtistService.class);
    }

    // POST: Crear artistas
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JsonObject jsonRequest;
        try (BufferedReader reader = req.getReader()) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            jsonRequest = gson.fromJson(json.toString(), JsonObject.class);
            //MANEJOR DE ERRORES//
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");

            String error = String.format(
                    "{\"error\": \"Solicitud inválida: El cuerpo debe ser JSON (%s)\", \"status\": %d}",
                    e.getMessage(),
                    HttpServletResponse.SC_BAD_REQUEST
            );

            resp.getWriter().write(error);
            return;
        }

        String name = jsonRequest.has("name") ? jsonRequest.get("name").getAsString() : null;
        String nationality = jsonRequest.has("nationality") ? jsonRequest.get("nationality").getAsString() : null;

        if(checkParam(name)) {
            sendError(resp);
            return;
        }

        if(checkParam(nationality)){
            sendError(resp);
            return;
        }

        try {
            Artist createdArtist = artistService.createArtist(name, nationality);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(createdArtist));

        } catch (Exception e) {
            //MANEJO DE ERRORES//
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");

            String error = String.format(
                    "{\"error\": \"Error al crear artista: %s\", \"status\": %d}",
                    e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            resp.getWriter().write(error);
        }
    }

    private boolean checkParam(String param){
        return param == null || param.trim().isEmpty();
    }

    private void sendError(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");

        String error = String.format(
                "{\"error\": \"El campo 'nationality' es obligatorio\", \"status\": %d}",
                HttpServletResponse.SC_BAD_REQUEST
        );

        resp.getWriter().write(error);
    }
}