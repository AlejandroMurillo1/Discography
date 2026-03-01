
document.addEventListener('DOMContentLoaded', () => {
    // Cargar todos los artistas al iniciar
    cargarTodosLosArtistas();
    
    // Agregar evento para Enter en el campo de búsqueda
    document.getElementById('searchTerm').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            buscarArtista();
        }
    });
});

// Función para crear un nuevo artista
function crearArtista() {
    const name = document.getElementById('name').value.trim();
    const nationality = document.getElementById('nationality').value.trim();
    const messageDiv = document.getElementById('createMessage');
    
    // Validación básica
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
    fetch(`${BASE_PATH}/artists`, {
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
        
        // Limpiar formulario
        document.getElementById('name').value = '';
        document.getElementById('nationality').value = '';
        
        // Recargar lista
        cargarTodosLosArtistas();
    })
    .catch(error => {
        const errorMsg = error.error || 'Error desconocido al crear el artista';
        mostrarMensaje(messageDiv, errorMsg, 'error');
    });
}

// Función para buscar un artista por nombre
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
    fetch(`${BASE_PATH}/artists/search?name=${encodeURIComponent(searchTerm)}`)
    .then(response => {
        if (response.status === 404) {
            throw new Error('Artista no encontrado');
        }
        return response.json();
    })
    .then(data => {
        // Manejar caso donde la API devuelve un solo objeto o una lista
        const artists = Array.isArray(data) ? data : [data];
        mostrarArtistas(artists);
        
        if (artists.length > 0) {
            mostrarMensaje(messageDiv, `Se encontraron ${artists.length} artista(s)`, 'success');
        } else {
            mostrarMensaje(messageDiv, 'No se encontraron artistas', 'error');
            document.getElementById('artistsContainer').innerHTML = 
                '<div class="no-results">No se encontraron artistas que coincidan con la búsqueda</div>';
        }
    })
    .catch(error => {
        mostrarMensaje(messageDiv, error.message || 'Error en la búsqueda', 'error');
        document.getElementById('artistsContainer').innerHTML = 
            '<div class="no-results">Error al realizar la búsqueda</div>';
    });
}

// Función para cargar todos los artistas
function cargarTodosLosArtistas() {
    document.getElementById('artistsContainer').innerHTML = 
        '<div class="no-results">Cargando artistas...</div>';
    
    fetch(`${BASE_PATH}/artists`)
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

// Función para eliminar un artista
function eliminarArtista(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este artista? Esta acción no se puede deshacer.')) {
        return;
    }
    
    fetch(`${BASE_PATH}/artists/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            cargarTodosLosArtistas(); // Recargar lista
            alert('Artista eliminado exitosamente');
        } else {
            throw new Error('Error al eliminar');
        }
    })
    .catch(error => {
        alert('Error al eliminar el artista');
    });
}

// Función para mostrar artistas en la interfaz
function mostrarArtistas(artists) {
    const container = document.getElementById('artistsContainer');
    
    if (!artists || artists.length === 0) {
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
                <button class="delete-btn" onclick="eliminarArtista(${artist.id})">Eliminar</button>
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