<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <form:form modelAttribute="characterL2" method="post" action="/main/createCharacter">
        <div class="form-group row">
            <label path="name" for="name" class="col-sm-2 col-form-label">Character name</label>
            <div class="col-sm-3">
                <form:hidden path="id"/>
                <form:input path="name" id="name"/>
            </div>
            <div class="col-sm-3">
                <form:select path="serverNames" items="${serverList}"/>
            </div>
            <div class="col-sm-3">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>

    </form:form>
    <p/>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Server</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${characterL2List}" varStatus="status" var="oneCharacter">
            <tr>
                <c:set value="${oneCharacter.id}" var="id"/>
                <th scope="row">${status.count}</th>
                <td>${oneCharacter.name}</td>
                <td>${oneCharacter.serverNames}</td>
                <td><a href="/main/createCharacter/edit/${id}" class="btn btn-sm btn-outline-info">Edit</a></td>
                <td><a href="/main/createCharacter/delete/${id}" class="btn btn-sm btn-outline-danger">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>