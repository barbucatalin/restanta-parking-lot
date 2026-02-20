<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<t:pageTemplate pageTitle="Modificare Vehicul">
    <div class="py-3">
        <h2 class="mb-4 text-secondary">Editare Detalii Mașină</h2>

        <form action="${pageContext.request.contextPath}/EditCar" method="POST"
              class="needs-validation" novalidate>

            <input type="hidden" name="car_id" value="${car.id}"/>

            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="row gy-4">

                        <div class="col-12">
                            <label for="select_owner" class="form-label fw-bold">Proprietar alocat</label>
                            <select name="owner_id" id="select_owner" class="form-select" required>
                                <option disabled value="">Alegeți din listă...</option>
                                <c:forEach var="item" items="${users}">
                                    <option value="${item.id}" ${item.username eq car.ownerName ? 'selected' : ''}>
                                            ${item.username}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Selecția unui proprietar este obligatorie.</div>
                        </div>

                        <div class="col-md-6">
                            <label for="input_plate" class="form-label">Număr înmatriculare</label>
                            <input type="text" name="license_plate" id="input_plate"
                                   class="form-control" value="${car.licensePlate}" required>
                            <div class="invalid-feedback">Introduceți numărul de pe plăcuță.</div>
                        </div>

                        <div class="col-md-6">
                            <label for="input_parking" class="form-label">Loc parcare</label>
                            <input type="text" name="parking_spot" id="input_parking"
                                   class="form-control" value="${car.parkingSpot}" required>
                            <div class="invalid-feedback">Specificați locul de parcare.</div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="mt-4 d-grid gap-2 d-md-block">
                <button type="submit" class="btn btn-primary px-5 shadow-sm">
                    Actualizează Datele
                </button>
                <a href="${pageContext.request.contextPath}/Cars" class="btn btn-outline-secondary px-4">
                    Anulează
                </a>
            </div>
        </form>
    </div>
</t:pageTemplate>