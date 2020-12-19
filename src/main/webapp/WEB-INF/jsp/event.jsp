<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Event</title>

</head>
<body>
<h1>Event details</h1>

<br/><br/>
<div>
    <table border="1">
        <tr>
            <th>Date</th>
            <th>Value</th>
            <th>Comment</th>
            <th>Tags</th>
        </tr>
        <tr>
            <th>${event.date}</th>
            <th>${event.value}</th>
            <th>${event.comment}</th>
            <c:forEach  items="${tags}" var ="tag">
                <td><a href="${pageContext.request.contextPath}/tags/${tag.tagName}">${tag.tagName}</a></td>
            </c:forEach>
        </tr>
    </table>
</div>
</body>

</html>