## Discography Manager – Trabajo Final Compu2
### Descripción del Proyecto
Discography Manager es una aplicación web desarrollada en Java + Jakarta Servlet + Spring Framework, cuyo objetivo es gestionar artistas y canciones (tracks).
El sistema permite:
*  Crear artistas
*  Listar artistas
*  Buscar artistas
*  Eliminar artistas
*  Crear tracks
*  Listar tracks
*  Buscar tracks
*  Eliminar tracks
*  Visualizar dashboards

La aplicación implementa arquitectura en capas con separación clara entre:

* Modelo
* Repositorio
* Servicio
* Controladores (Servlets)
* Vistas (JSP)
  
### Arquitectura del Proyecto
El proyecto sigue una arquitectura por capas:

org.icesi.discography
│
├── config
├── models
├── repositories
├── services
├── servlets
└── utils

#### config
Contiene la configuración de Spring:
* AppConfiguration.java
* Initializer.java
#### models
Clases del dominio:
* Artist
* Track
#### repositories
Manejo de datos en memoria:
* ArtistRepository
* TrackRepository
#### services
Lógica de negocio:
* ArtistService
* TrackService
#### servlets
Controladores que manejan las peticiones HTTP:
* ArtistCreateServlet
* ArtistDeleteServlet
* ArtistListServlet
* ArtistManagementServlet
* ArtistSearchServlet
* TrackCreateServlet
* TrackDashboardServlet
* TrackDeleteServlet
* TrackListServlet
* TrackSearchServlet
#### utils
* DataLoader
* DataWrapper
* Carga de datos desde data.json

### Versiones Implementadas
El proyecto fue desarrollado en tres configuraciones distintas:

#### VERSIÓN A — Configuración con XML
* Uso de web.xml
* Declaración manual de servlets
* Uso de applicationContext.xml
* Configuración tradicional basada en XML

#### VERSIÓN B — Configuración con Anotaciones
* Uso de @Repository
* Uso de @Service
* Inyección de dependencias con @Autowired
* @ComponentScan

Configuración Java con:

* AppConfiguration
* Initializer

Eliminación de configuración XML innecesaria

Se utilizó:

WebApplicationContextUtils.getWebApplicationContext(getServletContext())

para obtener los servicios desde el contexto de Spring en los Servlets.

#### VERSIÓN C — Configuración Manual con Java Config (Sin ComponentScan)
En esta versión:

* No se usa XML
* No se usa @ComponentScan
* No se usan anotaciones @Service ni @Repository
* Se definen manualmente los Beans en AppConfiguration
Ejemplo:

@Bean
public ArtistService artistService() {
    return new ArtistService(artistRepository());
}

La inyección se realiza completamente de forma manual mediante configuración explícita.

### Vistas (JSP)

Las vistas están ubicadas en:

WEB-INF/views/

Se corrigió la nomenclatura para que los JSP no tuvieran el nombre del Servlet.

Ejemplo correcto:

artist-create.jsp
track-dashboard.jsp

Los Servlets realizan forward así:

request.getRequestDispatcher("/WEB-INF/views/artist-create.jsp")
       .forward(request, response);
       
### Interfaz

Se mejoró la interfaz visual agregando:

* Diseño moderno en index.jsp
* CSS personalizado
* Dashboards organizados
* Mejor experiencia visual sin afectar la lógica del sistema

### Persistencia

Los datos se cargan desde:

src/main/resources/data.json

Mediante:

* DataLoader
* DataWrapper

Los repositorios almacenan la información en memoria.

### Cómo Ejecutar el Proyecto

1. Clonar el repositorio
2. Abrir en IntelliJ
3. Ejecutar con Tomcat (Smart Tomcat o externo)
4. Acceder desde:

http://localhost:8080/discography-1.0-SNAPSHOT/

### Tecnologías Utilizadas

* Java 17
* Jakarta Servlet
* Spring Framework
* Maven
* JSP
* JSON
* Tomcat

Nota para el profesor:

El usuario que aparece realizando los commits como "K0R0Zz" corresponden a José David Valdés esto se debe a que ese es el usuario configurado en Git en el equipo utilizado, pero todos los commits realizados bajo ese nombre pertenecen a José David Valdés, los demas commits si son correspondientes a los demas integrantes del equipo 

### Autores

José David Valdés
Alejandro Murillo 
Damy Villegas 
