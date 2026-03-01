<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Discografía - Gestión de Artistas</title>

  <!-- Estilos -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">

  <!-- Favicon opcional -->
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
<div class="container">
  <header>
    <h1>Discografía</h1>
    <p class="subtitle">Sistema de gestión de artistas</p>
  </header>

  <div class="dashboard">
    <!-- PANEL CREAR ARTISTA -->
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

    <!-- PANEL BUSCAR Y LISTAR ARTISTAS -->
    <div class="panel">
      <h2>Buscar Artista</h2>

      <div class="search-input-group">
        <input
                type="text"
                id="searchTerm"
                placeholder="Escribe aquí para buscar por nombre..."
                autocomplete="off"
                spellcheck="false"
                style="font-size: 1.1rem; padding: 14px 20px; height: 50px;"
        >

        <button
                onclick="buscarArtista()"
                style="width: auto; padding: 0 25px; height: 50px; margin-left: 10px;"
        >
          <span style="font-size: 1.1rem;">🔍 Buscar</span>
        </button>
      </div>

      <div class="search-hint" style="margin-top: 10px; color: #7f8c8d; font-size: 0.9rem;">
        Ejemplos: "Bad" para encontrar "Bad Bunny", "sha" para "Shakira"
      </div>

      <div id="searchMessage" class="message"></div>

      <h2>Lista de Artistas</h2>
      <div id="artistsContainer" class="artists-list">

      </div>
    </div>
  </div>

  <footer>
    <p>Sistema de gestión de artistas - Discografía 2026</p>
  </footer>
</div>

<script>
    const BASE_PATH = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/js/artists.js"></script>
</body>
</html>