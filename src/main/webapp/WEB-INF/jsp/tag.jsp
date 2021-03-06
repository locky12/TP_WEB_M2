<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${tagName}</title>
</head>
<body>
<h1>${tagName} events</h1>

<br/><br/>
<div>
    <table border="1">
        <tr>
            <th>Date</th>
            <th>Value</th>
            <th>Comment</th>
        </tr>
        <c:forEach  items="${events}" var ="event">
            <tr>
                <td>${event.date}</td>
                <td>${event.value}</td>
                <td>${event.comment}</td>
                <td><a href="${pageContext.request.contextPath}/series/${event.serie.id}/events/${event.id}">details</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>