<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            Parking&nbsp;Lot
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#mainNavigation" aria-controls="mainNavigation"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNavigation">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link ${requestScope.activePage eq 'Home' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/">Home</a>
                </li>

                <c:if test="${pageContext.request.remoteUser != null}">
                    <li class="nav-item">
                        <a class="nav-link ${requestScope.activePage eq 'Cars' ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/Cars">Cars</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope.activePage eq 'Users' ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/Users">Users</a>
                    </li>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link ${requestScope.activePage eq 'About' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/about.jsp">About</a>
                </li>
            </ul>

            <div class="d-flex">
                <c:choose>
                    <c:when test="${pageContext.request.remoteUser == null}">
                        <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/Login">Login</a>
                    </c:when>
                    <c:otherwise>
                        <span class="navbar-text me-3 text-info">
                            Salut, ${pageContext.request.remoteUser}!
                        </span>
                        <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/Logout">Logout</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>