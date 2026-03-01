<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Discography Compu2</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #1f4037, #99f2c8);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background-color: white;
            padding: 50px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            text-align: center;
            width: 420px;
        }

        h1 {
            margin-bottom: 15px;
            color: #333;
        }

        p {
            color: #666;
            margin-bottom: 35px;
        }

        .btn {
            display: block;
            text-decoration: none;
            padding: 14px;
            margin: 12px 0;
            border-radius: 8px;
            font-weight: bold;
            transition: 0.3s;
            color: white;
        }

        .artists {
            background-color: #007bff;
        }

        .artists:hover {
            background-color: #0056b3;
        }

        .tracks {
            background-color: #6c757d;
        }

        .tracks:hover {
            background-color: #545b62;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Discography Application</h1>
    <p>Sistema de gestión de artistas y canciones</p>

    <a class="btn artists"
       href="${pageContext.request.contextPath}/artists/dashboard">
        Artists Dashboard
    </a>

    <a class="btn tracks"
       href="${pageContext.request.contextPath}/tracks/dashboard">
        Tracks Dashboard
    </a>
</div>

</body>
</html>