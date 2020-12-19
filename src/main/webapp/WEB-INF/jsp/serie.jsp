  <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Person JSP View!!!</title>
        <style>
        td,
        th {
            border: 1px solid #999;
            padding: 0.2rem;
        }
        </style>
    </head>

    <body>
        <h2>My Serie  </h2>
            <ul>
                <li>${serie.title}</li>
                <li>${serie.description}</li>
                 <li><a href="${pageContext.request.contextPath}/series/${serie.id}"></a>Events</li>

            </ul>
            <p><a href="${pageContext.request.contextPath}/series/${serie.id}/events">events</a> events</p>

    </body>

    </html>