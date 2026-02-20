<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<t:pageTemplate pageTitle="Listă Utilizatori">
    <h2 class="display-6 mb-4">Membrii Comunității</h2>

    <div class="container-fluid">
        <div class="row">
            <c:choose>
                <c:when test="${not empty users}">
                    <c:forEach var="user" items="${users}">
                        <div class="col-md-4 col-sm-6 py-2">
                            <div class="p-2 border-bottom">
                                <strong>${user.username}</strong>
                                <span class="text-secondary mx-1">|</span>
                                    ${user.email}
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12 mt-3">
                        <p class="font-italic text-secondary">Momentan nu există înregistrări.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:pageTemplate>