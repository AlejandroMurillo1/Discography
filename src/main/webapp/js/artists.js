document.addEventListener('DOMContentLoaded', () => {
    cargarArtistas();
});

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
    fetch(`${BASE_PATH}/artists/search?name=${encodeURIComponent(searchTerm)}`)
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
        fetch('/discography_war_exploded/artists/1', {
        method: 'DELETE'
         })
         .then(response => {
         if (response.status === 204) {
         console.log('Artista eliminado correctamente');
         }
         });
}


// Función para cargar todos los artistas
function cargarArtistas() {
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
                <div class="artist-tracks">
                    ${artist.tracks && artist.tracks.length > 0 ?
                        artist.tracks.map(track =>
                            `<span class="track-tag">${track.title}</span>`).join('') :
                        '<em>Sin canciones asignadas</em>'
                    }
                </div>
            </div>
            
        `;

        console.log('Canciones de', artist.name, artist.tracks);
        container.appendChild(card);
    });

}

//funcion borrar artistas
function eliminarArtista(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este artista? Esta acción no se puede deshacer.')) {
        return;
    }
    
    fetch(`${BASE_PATH}/artists/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            cargarTodosLosArtistas(); 
            alert('Artista eliminado exitosamente');
        } else {
            throw new Error('Error al eliminar');
        }
    })
    .catch(error => {
        alert('Error al eliminar el artista');
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



