<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <form:form modelAttribute="productL2" method="post" action="/main/createProduct">
        <div class="form-group row">
            <label path="name" for="productName" class="col-sm-2 col-form-label">Product name</label>
            <div class="col-sm-3">
                <form:hidden path="id"/>
                <form:input path="productName" id="productName"/>
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
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productL2List}" varStatus="status" var="oneProduct">
            <tr>
                <c:set value="${oneProduct.id}" var="id"/>
                <th scope="row">${status.count}</th>
                <td>${oneProduct.productName}</td>
                <td><a href="/main/createProduct/edit/${id}" class="btn btn-sm btn-outline-info">Edit</a></td>
                <td><a href="/main/createProduct/delete/${id}" class="btn btn-sm btn-outline-danger">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>