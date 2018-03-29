<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <form:form modelAttribute="adenaReportForm" method="post" action="/main/createAdenaReport">
        <div class="row">
            <form:hidden path="id"/>
            <label path="name" for="characterL2" class="col-sm-2">Character name</label>
            <div class="col-sm-3 ">
                <form:select path="characterL2" id="characterL2" items="${characterList}" itemLabel="name"/>
            </div>
        </div>
        <div class="row">
            <label path="adenaQuantity" for="adenaQuantity" class="col-sm-2">Adena quantity</label>
            <div class="col-sm-3 ">
                <form:input path="adenaQuantity"/>
            </div>
            <div class="col-sm-5 ">
                <form:errors path="adenaQuantity" cssStyle="color: red"/>
            </div>
        </div>
        <div class="form-group row">
            <form:checkboxes path="productL2s" items="${productL2List}" itemLabel="productName"
                             element="span class='form-check'"/>
            <div class="col-sm-5 ">
                <form:errors path="productL2s" cssStyle="color: red"/>
            </div>
        </div>

        <div class="row">
            <label path="adenaSold" for="adenaSold" class="col-sm-2">Adena sold</label>
            <div class="col-sm-3 ">
                <form:input path="adenaSold"/>
            </div>
            <div class="col-sm-5 ">
                <form:errors path="adenaSold" cssStyle="color: red"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button type="submit" value="addProduct" name="addProduct" class="btn btn-primary">Add product
                </button>
            </div>
        </div>
        <table class="table table-bordered table-dark">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Product</th>
                <th scope="col">Quantity</th>
                <th scope="col">Price</th>
            </tr>
            </thead>
            <tbody>
            <form:errors path="productAndPriceHolders" cssStyle="color: red"/>
            <c:forEach items="${adenaReportForm.productAndPriceHolders}" var="holder" varStatus="status">
                <tr>
                    <th>${status.count}</th>
                    <td>
                        <form:select path="productAndPriceHolders[${status.index}].productL2" items="${productL2List}"
                                     itemLabel="productName">
                            <form:options/>
                        </form:select>
                    </td>
                    <td>
                        <input type="text" name="productAndPriceHolders[${status.index}].productQuantity"
                               value="${holder.productQuantity}">
                    </td>
                    <td>
                        <input type="text" name="productAndPriceHolders[${status.index}].productPrice"
                               value="${holder.productPrice}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <div class="col-sm-3">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary" name="saveReport">Submit</button>
            </div>
        </div>
    </form:form>

    <div class="mt-5">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Date</th>
                <th scope="col">Server name</th>
                <th scope="col">Character name</th>
                <th scope="col">Pure adena</th>
                <th scope="col">Adena in product</th>
                <th scope="col">Total adena</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adenaReportList}" varStatus="status" var="oneReport">
                <c:set var="productSumm" value="0"/>
                <tr>
                    <th class="d-none">
                        <c:forEach items="${oneReport.productAndPriceHolders}" var="holder">
                            ${productSumm = holder.productQuantity * holder.productPrice}
                        </c:forEach>
                    </th>
                    <th scope="row">${status.count}</th>
                    <td><fmt:formatDate var="date" value="${oneReport.calendar.time}" type="both"
                                        dateStyle="short"/> ${date}</td>
                    <td>${oneReport.characterL2.serverNames}</td>
                    <td>${oneReport.characterL2.name}</td>
                    <td>${oneReport.adenaQuantity}</td>
                    <td>${productSumm}</td>
                    <td>${productSumm + oneReport.adenaQuantity}</td>
                    <td><a href="/main/report/delete/${oneReport.id}" class="btn btn-sm btn-outline-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>