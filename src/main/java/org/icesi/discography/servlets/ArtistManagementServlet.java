package org.icesi.discography.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/artists/dashboard")
public class ArtistManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write("""
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Discografía - Gestión de Artistas</title>
                <style>
                    * {
                        box-sizing: border-box;
                        margin: 0;
                        padding: 0;
                    }
                    
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background-color: #f5f7fa;
                        color: #333;
                        line-height: 1.6;
                        padding: 20px;
                    }
                    
                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                    }
                    
                    header {
                        text-align: center;
                        margin-bottom: 30px;
                        padding: 20px 0;
                        border-bottom: 1px solid #eaeaea;
                    }
                    
                    h1 {
                        color: #2c3e50;
                        margin-bottom: 10px;
                    }
                    
                    .subtitle {
                        color: #7f8c8d;
                        font-size: 1.1rem;
                    }
                    
                    .dashboard {
                        display: grid;
                        grid-template-columns: 1fr 3fr;
                        gap: 25px;
                    }
                    
                    @media (max-width: 768px) {
                        .dashboard {
                            grid-template-columns: 1fr;
                        }
                    }
                    
                    .panel {
                        background: white;
                        border-radius: 10px;
                        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                        padding: 25px;
                        margin-bottom: 20px;
                    }
                    
                    .panel h2 {
                        color: #2c3e50;
                        margin-bottom: 20px;
                        padding-bottom: 10px;
                        border-bottom: 1px solid #eee;
                    }
                    
                    .form-group {
                        margin-bottom: 20px;
                    }
                    
                    label {
                        display: block;
                        margin-bottom: 8px;
                        font-weight: 600;
                        color: #2c3e50;
                    }
                    
                    input, button {
                        width: 100%;
                        padding: 12px 15px;
                        border: 1px solid #ddd;
                        border-radius: 6px;
                        font-size: 16px;
                    }
                    
                    input:focus {
                        outline: none;
                        border-color: #3498db;
                        box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
                    }
                    
                    button {
                        background: #3498db;
                        color: white;
                        border: none;
                        cursor: pointer;
                        font-weight: 600;
                        transition: background 0.3s;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                        font-size: 14px;
                    }
                    
                    button:hover {
                        background: #2980b9;
                    }
                    
                    .search-box {
                        display: flex;
                        gap: 10px;
                    }
                    
                    .search-box input {
                        flex: 1;
                    }
                    
                    .message {
                        padding: 15px;
                        border-radius: 6px;
                        margin: 15px 0;
                        display: none;
                    }
                    
                    .success {
                        background: #d4edda;
                        color: #155724;
                        border: 1px solid #c3e6cb;
                    }
                    
                    .error {
                        background: #f8d7da;
                        color: #721c24;
                        border: 1px solid #f5c6cb;
                    }
                    
                    .artists-list {
                        display: grid;
                        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                        gap: 20px;
                    }
                    
                    .artist-card {
                        background: white;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.08);
                        transition: transform 0.2s;
                    }
                    
                    .artist-card:hover {
                        transform: translateY(-3px);
                    }
                    
                    .artist-header {
                        background: linear-gradient(135deg, #3498db, #2c3e50);
                        color: white;
                        padding: 15px;
                        text-align: center;
                    }
                    
                    .artist-name {
                        font-size: 1.4rem;
                        margin-bottom: 5px;
                    }
                    
                    .artist-nationality {
                        font-size: 0.9rem;
                        opacity: 0.9;
                    }
                    
                    .artist-body {
                        padding: 15px;
                    }
                    
                    .artist-id {
                        color: #7f8c8d;
                        font-size: 0.85rem;
                        margin-top: 10px;
                        text-align: right;
                    }
                    
                    .no-results {
                        text-align: center;
                        padding: 30px;
                        color: #7f8c8d;
                        grid-column: 1 / -1;
                    }
                    
                    footer {
                        text-align: center;
                        margin-top: 40px;
                        padding: 20px 0;
                        color: #7f8c8d;
                        font-size: 0.9rem;
                        border-top: 1px solid #eaeaea;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <header>
                        <h1>Discografía</h1>
                        <p class="subtitle">Sistema de gestión de artistas</p>
                    </header>
                    
                    <div class="dashboard">
                        <div class="panel">
                            <h2>Crear Artista</h2>
                            <div class="form-group">
                                <label for="name">Nombre del Artista</label>
                                <input type="text" id="name" placeholder="Ej: Bad Bunny">
                            </div>
                            
                            <div class="form-group">
                                <label for="nationality">Nacionalidad</label>
                                <input type="text" id="nationality" placeholder="Ej: Puertorriqueña">
                            </div>
                            
                            <button onclick="crearArtista()">Crear Artista</button>
                            
                            <div id="createMessage" class="message"></div>
                        </div>
                        
                        <div class="panel">
                            <h2>Buscar Artista</h2>
                            <div class="search-input-group">
                                        <input\s
                                            type="text"\s
                                            id="searchTerm"\s
                                            placeholder="Escribe aquí para buscar por nombre..."\s
                                            autocomplete="off"
                                            spellcheck="false"
                                            style="font-size: 1.1rem; padding: 14px 20px; height: 50px;"
                                        >
                                        <button\s
                                            onclick="buscarArtista()"\s
                                            style="width: auto; padding: 0 25px; height: 50px; margin-left: 10px;"
                                        >
                                            <span style="font-size: 1.1rem;">🔍 Buscar</span>
                                        </button>
                                    </div>
                                    <div class="search-hint" style="margin-top: 10px; color: #7f8c8d; font-size: 0.9rem;">
                                        Ejemplos: "Bad" para encontrar "Bad Bunny", "sha" para "Shakira"
                                    </div>
                            <div class="form-group">    
                            </div>
                            
                            <div id="searchMessage" class="message"></div>
                            
                            <h2>Lista de Artistas</h2>
                            <div id="artistsContainer" class="artists-list">
                                <!-- Los artistas se cargarán aquí dinámicamente -->
                            </div>
                        </div>
                    </div>
                    
                    <footer>
                        <p>Sistema de gestión de artistas - Discografía 2026</p>
                    </footer>
                </div>
                
                <script>
                    // Cargar artistas al iniciar
                    document.addEventListener('DOMContentLoaded', () => {
                        cargarArtistas();
                    });
                    
                    // Función para crear un nuevo artista
                    function crearArtista() {
                        const name = document.getElementById('name').value.trim();
                        const nationality = document.getElementById('nationality').value.trim();
                        const messageDiv = document.getElementById('createMessage');
                        
                        // Validación
                        if (!name || !nationality) {
                            mostrarMensaje(messageDiv, 'Por favor completa ambos campos', 'error');
                            return;
                        }
                        
                        // Preparar datos
                        const artistData = {
                            name: name,
                            nationality: nationality
                        };
                        
                        // Enviar a la API
                        fetch('/discography_war_exploded/artists/create', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(artistData)
                        })
                        .then(response => {
                            if (!response.ok) {
                                return response.json().then(err => { throw err; });
                            }
                            return response.json();
                        })
                        .then(data => {
                            mostrarMensaje(messageDiv, `¡Artista creado exitosamente! ID: ${data.id}`, 'success');
                            document.getElementById('name').value = '';
                            document.getElementById('nationality').value = '';
                            cargarArtistas(); // Actualizar lista
                        })
                        .catch(error => {
                            const errorMsg = error.error || 'Error desconocido al crear el artista';
                            mostrarMensaje(messageDiv, errorMsg, 'error');
                        });
                    }
                    
                    // Función para buscar artistas
                    function buscarArtista() {
                        const searchTerm = document.getElementById('searchTerm').value.trim();
                        const messageDiv = document.getElementById('searchMessage');
                        
                        if (!searchTerm) {
                            mostrarMensaje(messageDiv, 'Por favor ingresa un término de búsqueda', 'error');
                            return;
                        }
                        
                        // Mostrar indicador de carga
                        document.getElementById('artistsContainer').innerHTML = 
                            '<div class="no-results">Buscando artistas...</div>';
                        
                        // Realizar búsqueda
                        fetch(`/discography_war_exploded/artists/search?name=${encodeURIComponent(searchTerm)}`)
                        .then(response => {
                            if (response.status === 404) {
                                throw new Error('Artista no encontrado');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Manejar caso donde la API devuelve un solo objeto (no un array)
                            const artists = Array.isArray(data) ? data : [data];
                            mostrarArtistas(artists);
                            mostrarMensaje(messageDiv, `Se encontró ${artists.length} artista(s)`, 'success');
                        })
                        .catch(error => {
                            mostrarMensaje(messageDiv, error.message || 'No se encontraron artistas', 'error');
                            document.getElementById('artistsContainer').innerHTML = 
                                '<div class="no-results">No se encontraron artistas que coincidan con la búsqueda</div>';
                        });
                    }
                    
                    // Función para cargar todos los artistas
                    function cargarArtistas() {
                        document.getElementById('artistsContainer').innerHTML = 
                            '<div class="no-results">Cargando artistas...</div>';
                        
                        fetch('/discography_war_exploded/artists')
                        .then(response => response.json())
                        .then(artists => {
                            mostrarArtistas(artists);
                        })
                        .catch(error => {
                            console.error('Error cargando artistas:', error);
                            document.getElementById('artistsContainer').innerHTML = 
                                '<div class="no-results">Error al cargar los artistas</div>';
                        });
                    }
                    
                    // Función para mostrar artistas en la interfaz
                    function mostrarArtistas(artists) {
                        const container = document.getElementById('artistsContainer');
                        
                        if (artists.length === 0) {
                            container.innerHTML = '<div class="no-results">No hay artistas registrados</div>';
                            return;
                        }
                        
                        container.innerHTML = '';
                        
                        artists.forEach(artist => {
                            const card = document.createElement('div');
                            card.className = 'artist-card';
                            card.innerHTML = `
                                <div class="artist-header">
                                    <div class="artist-name">${artist.name}</div>
                                    <div class="artist-nationality">${artist.nationality}</div>
                                </div>
                                <div class="artist-body">
                                    <div class="artist-id">ID: ${artist.id}</div>
                                </div>
                            `;
                            container.appendChild(card);
                        });
                    }
                    
                    // Función para mostrar mensajes
                    function mostrarMensaje(element, mensaje, tipo) {
                        element.textContent = mensaje;
                        element.className = `message ${tipo}`;
                        element.style.display = 'block';
                        
                        // Ocultar después de 5 segundos
                        setTimeout(() => {
                            element.style.display = 'none';
                        }, 5000);
                    }
                </script>
            </body>
            </html>
            """);
    }
}