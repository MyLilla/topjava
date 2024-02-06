<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create</title>

    <link rel="stylesheet" href="styles/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<div class="container">

    <c:if test="${meal != null}">
        <h2>Edit meal</h2>
    </c:if>

    <c:if test="${meal == null}">
        <h2>Create meal</h2>
    </c:if>

    <form action="meals">
        <input type="text" hidden="hidden" name="action" value="create">
        <input type="text" hidden="hidden" name="id" value="${meal.id}">
        <div class="mb-3">
            <label for="date" class="form-label">Date Time: </label>
            <input type="datetime-local" class="form-control" id="date" name="date" value="${meal.dateTime}">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description: </label>
            <input type="text" class="form-control" id="description" name="description" value="${meal.description}">
        </div>
        <div class="mb-3">
            <label for="calories" class="form-label">Calories:</label>
            <input type="text" class="form-control" id="calories" name="calories" value="${meal.calories}">
        </div>

        <button class="btn btn-primary" type="submit">Save</button>
        <button class="btn btn-primary" onclick="window.history.back()" type="button">Cancel</button>
    </form>
</div>

</body>
</html>
