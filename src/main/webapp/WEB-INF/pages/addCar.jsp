<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<t:pageTemplate pageTitle="Înregistrare Vehicul">
    <div class="container mt-2">
        <h2 class="h3 mb-4 text-primary">Adăugare Autoturism Nou</h2>

        <form method="POST" action="${pageContext.request.contextPath}/AddCar"
              class="needs-validation" novalidate>

            <div class="row gx-4 gy-3">
                <div class="col-12 col-md-6">
                    <label for="ownerId" class="form-label font-weight-bold">Proprietar</label>
                    <select id="ownerId" name="owner_id" class="form-select" required>
                        <option selected disabled value="">Selectează utilizatorul...</option>
                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}">${user.username}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">Trebuie să selectați un proprietar valid.</div>
                </div>

                <div class="col-12 col-md-6">
                    <label for="id_license" class="form-label">Număr înmatriculare</label>
                    <input type="text" name="license_plate" id="id_license"
                           class="form-control" placeholder="B-01-ABC" required>
                    <div class="invalid-feedback">Introdu un număr de înmatriculare.</div>
                </div>

                <div class="col-12">
                    <label for="id_parking" class="form-label">Cod Loc Parcare</label>
                    <input type="text" name="parking_spot" id="id_parking"
                           class="form-control" required>
                    <div class="invalid-feedback">Specificați locul de parcare alocat.</div>
                </div>
            </div>

            <div class="mt-5 border-top pt-3">
                <button type="submit" class="btn btn-success btn-lg px-5">
                    Înregistrează Mașina
                </button>
            </div>
        </form>
    </div>
</t:pageTemplate>