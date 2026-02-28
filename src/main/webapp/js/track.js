document.addEventListener('DOMContentLoaded', () => {
    cargarTodasLasCanciones();

    document.getElementById('searchTerm').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            buscarCancion();
        }
    });
});

function crearCancion() {
    const title = document.getElementById('title').value.trim();
    const genre = document.getElementById('genre').value.trim();
    const duration = parseInt(document.getElementById('duration').value);
    const albumTitle = document.getElementById('albumTitle').value.trim();
    const messageDiv = document.getElementById('createMessage');

    if (!title || !genre || isNaN(duration) || duration <= 0 || !albumTitle) {
        mostrarMensaje(messageDiv, 'Por favor completa todos los campos correctamente', 'error');
        return;
    }

    const trackData = {
        title: title,
        genre: genre,
        duration: duration,
        albumTitle: albumTitle,
        singers: []
    };


    fetch(`${BASE_PATH}/tracks/create`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(trackData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            mostrarMensaje(messageDiv, `¡Canción creada exitosamente! ID: ${data.id}`, 'success');

            document.getElementById('title').value = '';
            document.getElementById('genre').value = '';
            document.getElementById('duration').value = '';
            document.getElementById('albumTitle').value = '';

            cargarTodasLasCanciones();
        })
        .catch(error => {
            const errorMsg = error.error || 'Error desconocido al crear la canción';
            mostrarMensaje(messageDiv, errorMsg, 'error');
        });
}

function buscarCancion() {
    const searchTerm = document.getElementById('searchTerm').value.trim();
    const messageDiv = document.getElementById('searchMessage');

    if (!searchTerm) {
        mostrarMensaje(messageDiv, 'Por favor ingresa un término de búsqueda', 'error');
        return;
    }

    document.getElementById('tracksContainer').innerHTML =
        '<div class="no-results">Buscando canciones...</div>';


    fetch(`${BASE_PATH}/tracks/search?title=${encodeURIComponent(searchTerm)}`)
        .then(response => {
            if (response.status === 404) {
                throw new Error('No se encontraron canciones');
            }
            return response.json();
        })
        .then(data => {
            const tracks = Array.isArray(data) ? data : [data];
            mostrarCanciones(tracks);

            if (tracks.length > 0) {
                mostrarMensaje(messageDiv, `Se encontraron ${tracks.length} canción(es)`, 'success');
            } else {
                mostrarMensaje(messageDiv, 'No se encontraron canciones', 'error');
                document.getElementById('tracksContainer').innerHTML =
                    '<div class="no-results">No se encontraron canciones que coincidan con la búsqueda</div>';
            }
        })
        .catch(error => {
            mostrarMensaje(messageDiv, error.message || 'Error en la búsqueda', 'error');
            document.getElementById('tracksContainer').innerHTML =
                '<div class="no-results">Error al realizar la búsqueda</div>';
        });
}

function cargarTodasLasCanciones() {
    document.getElementById('tracksContainer').innerHTML =
        '<div class="no-results">Cargando canciones...</div>';


    fetch(`${BASE_PATH}/tracks`)
        .then(response => response.json())
        .then(tracks => {
            mostrarCanciones(tracks);
        })
        .catch(error => {
            console.error('Error cargando canciones:', error);
            document.getElementById('tracksContainer').innerHTML =
                '<div class="no-results">Error al cargar las canciones</div>';
        });
}

function eliminarCancion(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta canción? Esta acción no se puede deshacer.')) {
        return;
    }


    fetch(`${BASE_PATH}/tracks/delete/${id}`, {
        method: 'POST'
    }).then(response => {
            if (response.ok) {
                cargarTodasLasCanciones();
                alert('Canción eliminada exitosamente');
            } else {
                throw new Error('Error al eliminar');
            }
        })
        .catch(error => {
            alert('Error al eliminar la canción');
        });
}

function mostrarCanciones(tracks) {
    const container = document.getElementById('tracksContainer');

    if (!tracks || tracks.length === 0) {
        container.innerHTML = '<div class="no-results">No hay canciones registradas</div>';
        return;
    }

    container.innerHTML = '';

    tracks.forEach(track => {
        const card = document.createElement('div');
        card.className = 'track-card';
        card.innerHTML = `
            <div class="track-header">
                <div class="track-title">${track.title}</div>
                <div class="track-album">${track.albumTitle}</div>
            </div>
            <div class="track-body">
                <div class="track-info">
                    <div class="track-label">Género</div>
                    <div class="track-value">${track.genre}</div>
                </div>
                <div class="track-info">
                    <div class="track-label">Duración</div>
                    <div class="track-value">${formatDuration(track.durationInSeconds)}</div>
                </div>
                <div class="track-info">
                    <div class="track-label">ID</div>
                    <div class="track-value">${track.id}</div>
                </div>
                <div class="track-info">
                    <div class="track-label">Cantantes</div>
                    <div class="track-value">
                        ${track.singers && track.singers.length > 0 ?
            `<div class="track-singers">
                                ${track.singers.map(singer => `<span class="singer-tag">${singer.name}</span>`).join('')}
                            </div>` :
            '<em>Sin cantantes asignados</em>'
        }
                    </div>
                </div>
                <div style="margin-top: 15px; text-align: center;">
                    <button class="delete" onclick="eliminarCancion(${track.id})">Eliminar</button>
                </div>
            </div>
        `;
        container.appendChild(card);
    });
}

function formatDuration(seconds) {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins}:${secs.toString().padStart(2, '0')}`;
}

function mostrarMensaje(element, mensaje, tipo) {
    element.textContent = mensaje;
    element.className = `message ${tipo}`;
    element.style.display = 'block';

    setTimeout(() => {
        element.style.display = 'none';
    }, 5000);
}



