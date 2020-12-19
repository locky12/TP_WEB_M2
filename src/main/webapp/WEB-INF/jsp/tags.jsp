<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Tags List</title>

  </head>
  <body>
    <h1>Person List</h1>

    <br/><br/>
    <div>
      <table border="1">
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
        </tr>
        <c:forEach  items="${tags.list}" var ="tag">
        <tr>
          <td>${tag.tagName}</td>
        </tr>
        </c:forEach>
      </table>
    </div>
  </body>

</html>