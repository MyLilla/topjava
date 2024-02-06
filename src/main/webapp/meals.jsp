<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>

    <link rel="stylesheet" href="styles/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<div class="container text-center">

    <h2>Meals</h2>

    <div class="row">
        <div class="col-3">
            <a class="btn btn-primary" href="create.jsp">Crete</a>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <table class="table table-striped" id="datatable" aria-describedby="datatable_info"
                   style="width: 1113px;">
                <thead class="thead-light">
                <tr class="sorting sorting_desc">
                    <th>Date/Time</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <c:forEach var="meal" items="${meals}">
                    <tr>

                        <%
                            String color = "green";
                        %>
                        <c:if test="${meal.excess == true}">
                            <% color = "red"; %>
                        </c:if>

                        <td style="color: <%=color%>">
                                ${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}
                        </td>
                        <td style="color: <%=color%>">${meal.description}</td>
                        <td style="color: <%=color%>">${meal.calories}</td>
                        <td><a href="meals?action=edit&id=${meal.id}">edit</a></td>
                        <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
                        <td hidden="hidden"><input name="id" value="${meal.id}"/></td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

</div>
</body>
</html>