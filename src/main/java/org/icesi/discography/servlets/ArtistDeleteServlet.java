package org.icesi.discography.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.icesi.discography.services.ArtistService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Map;

@WebServlet("/artists/*")
public class ArtistDeleteServlet extends HttpServlet {

    private ArtistService artistService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        artistService = context.getBean(ArtistService.class);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(resp, "ID requerido en la URL (ej: /artists/5)", 
                     HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            // 2. Convertir ID de String a Long
            long id = Long.parseLong(pathInfo.substring(1));
            
            // 3. Eliminar artista usando tu lógica de negocio
            artistService.deleteArtistById(id);
            
            // 4. Respuesta exitosa (204 No Content)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (NumberFormatException e) {
            sendError(resp, "ID inválido (debe ser numérico)", 
                     HttpServletResponse.SC_BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            sendError(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendError(resp, "Error interno al eliminar artista", 
                     HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(HttpServletResponse resp, String message, int status) 
            throws IOException {
        
        resp.setStatus(status);
        resp.setContentType("application/json");
        
        Map<String, Object> error = Map.of(
            "error", message,
            "status", status
        );
        
        resp.getWriter().write(gson.toJson(error));
    }
}