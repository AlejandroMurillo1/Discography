package org.icesi.discography.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.icesi.discography.services.TrackService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tracks/create")
public class TrackCreateServlet extends HttpServlet {

    private TrackService trackService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        trackService = context.getBean(TrackService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Parsear JSON del cuerpo
        JsonObject jsonRequest;
        try (BufferedReader reader = req.getReader()) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            jsonRequest = gson.fromJson(json.toString(), JsonObject.class);
        } catch (Exception e) {
            sendError(resp, "Solicitud inválida: El cuerpo debe ser JSON",
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 2. Extraer campos requeridos
        String title = getAsString(jsonRequest, "title");
        String genre = getAsString(jsonRequest, "genre");
        long duration = getAsLong(jsonRequest, "duration");
        String albumTitle = getAsString(jsonRequest, "albumTitle");

        // 3. Validación
        if (isNullOrEmpty(title)) {
            sendError(resp, "El campo 'title' es obligatorio",
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (isNullOrEmpty(genre)) {
            sendError(resp, "El campo 'genre' es obligatorio",
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (duration <= 0) {
            sendError(resp, "El campo 'duration' debe ser mayor que 0",
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (isNullOrEmpty(albumTitle)) {
            sendError(resp, "El campo 'albumTitle' es obligatorio",
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 4. Procesar cantantes (opcional)
        List<Artist> singers = new ArrayList<>();
        if (jsonRequest.has("singers")) {
            JsonArray singersJson = jsonRequest.getAsJsonArray("singers");
            // Aquí deberías tener lógica para convertir JSON a Artist
            // Por ahora, pasamos una lista vacía o null
            // En un sistema real, necesitarías obtener los artistas de la base
        }

        // 5. Crear pista usando TU constructor
        try {
            trackService.createTrack(
                    0L,           // ID será generado automáticamente
                    title,
                    genre,
                    (int) duration, // Asumiendo que duration es int en el servicio
                    albumTitle,
                    singers       // Lista de cantantes (puede estar vacía)
            );

            // 6. Respuesta exitosa - devolveremos un objeto similar al creado
            JsonObject response = new JsonObject();
            response.addProperty("message", "Canción creada exitosamente");
            response.addProperty("title", title);
            response.addProperty("genre", genre);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response));

        } catch (Exception e) {
            sendError(resp, "Error al crear la canción: " + e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Métodos auxiliares
    private String getAsString(JsonObject obj, String name) {
        return obj.has(name) ? obj.get(name).getAsString() : null;
    }

    private long getAsLong(JsonObject obj, String name) {
        return obj.has(name) ? obj.get(name).getAsLong() : -1;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void sendError(HttpServletResponse resp, String message, int status)
            throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");

        JsonObject error = new JsonObject();
        error.addProperty("error", message);
        error.addProperty("status", status);

        resp.getWriter().write(gson.toJson(error));
    }
}