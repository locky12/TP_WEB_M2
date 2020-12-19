<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Tags List</title>

  </head>
  <body>
    <h1>Tags List</h1>

    <br/><br/>
    <div>
      <table border="1">
        <tr>
          <th>Tag Name</th>
        </tr>
        <c:forEach  items="${tags}" var ="tag">
        <tr>
          <td><a href="${pageContext.request.contextPath}/tags/${tag.tagName}">${tag.tagName}</a></td>
        </tr>
        </c:forEach>
      </table>
    </div>
  </body>

</html>