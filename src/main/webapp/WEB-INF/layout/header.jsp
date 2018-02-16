<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="container">
    <security:authorize access="!isAuthenticated()">
        <div class="form-inline">
            <a href="/login" role="button" class="btn btn-primary">Login</a>
        </div>
    </security:authorize>
    <div class="col-md-offset-6 col-md-4">
        <div class="form-inline">
            <security:authorize access="isAuthenticated()">
                <security:authentication property="principal" var="user"/>
                <div class="form-group center-block">
                        ${user.username}
                </div>
                <form:form action="/logout" method="post" class="form-group">
                    <button type="submit" class="btn btn-default">Logout</button>
                </form:form>
                <div class="form-group">
                    <security:authorize access="hasRole('ADMIN')">
                        <a href="/main" class="btn btn-default" role="button">Admin</a>
                    </security:authorize>
                </div>
            </security:authorize>
        </div>
    </div>
</div>

