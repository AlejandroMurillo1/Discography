<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Discografía - Gestión de Canciones</title>

  <!-- Estilos -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/track.css">
</head>
<body>
<div class="container">
  <header>
    <h1>🎵 Discografía</h1>
    <p class="subtitle">Sistema de gestión de canciones y álbumes</p>
  </header>

  <div class="dashboard">
    <!-- Panel de creación -->
    <div class="panel">
      <h2>Crear Nueva Canción</h2>
      <div class="panel-content">
        <div class="form-group">
          <label for="title">Título de la Canción</label>
          <input type="text" id="title" placeholder="Ej: Despacito">
        </div>

        <div class="form-group">
          <label for="genre">Género Musical</label>
          <input type="text" id="genre" placeholder="Ej: Reggaetón">
        </div>

        <div class="form-group">
          <label for="duration">Duración (segundos)</label>
          <input type="number" id="duration" placeholder="Ej: 240">
        </div>

        <div class="form-group">
          <label for="albumTitle">Álbum</label>
          <input type="text" id="albumTitle" placeholder="Ej: Vida">
        </div>

        <div class="form-group">
          <label>Asignar artistas</label>
          <div id="artistCheckboxes" class="artist-checkboxes">
            <!-- Checkboxes se cargarán dinámicamente -->
          </div>
        </div>

        <button onclick="crearCancion()">Crear Canción</button>
        <div id="createMessage" class="message"></div>
      </div>
    </div>

    <!-- Panel de búsqueda y listado -->
    <div class="panel">
      <h2>Buscar y Listar Canciones</h2>
      <div class="panel-content">
        <div class="form-group">
          <div class="track-search">
            <input type="text" id="searchTerm" placeholder="Buscar por título..." autocomplete="off">
            <button onclick="buscarCancion()">🔍 Buscar</button>
          </div>
        </div>

        <div id="searchMessage" class="message"></div>

        <h2>Canciones Registradas</h2>
        <div id="tracksContainer" class="tracks-list">
          <!-- Las canciones se cargarán aquí dinámicamente -->
        </div>
      </div>
    </div>
  </div>

  <footer class="footer">
    <p>Sistema de gestión de canciones - Discografía 2026</p>
  </footer>
</div>

<!-- Scripts -->
<script>
  const BASE_PATH = '${pageContext.request.contextPath}';
  console.log('BASE_PATH:', BASE_PATH);
  console.log('¿crearCancion está definida?', typeof crearCancion);
  // Depuración adicional
  console.log('Scripts cargados:', {
    hasBasePath: typeof BASE_PATH !== 'undefined',
    basePathValue: typeof BASE_PATH !== 'undefined' ? BASE_PATH : 'n/a',
    hasCrearCancion: typeof crearCancion !== 'undefined'
  });
</script>
<script src="${pageContext.request.contextPath}/js/track.js"></script>
</body>
</html>