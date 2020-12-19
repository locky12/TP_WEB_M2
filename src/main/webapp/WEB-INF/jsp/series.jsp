<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>My Series</title>

  </head>
  <body>
    <h1>My Series</h1>

    <br/><br/>
    <div>
      <table border="1">

       <ul>
        <c:forEach  items="${series.list}" var ="serie">

            <li><a href="${pageContext.request.contextPath}/series/${serie.id}">${serie.title}</a></li>
        </ul>
        </c:forEach>
      </table>
    </div>


      </body>
  </body>

</html>