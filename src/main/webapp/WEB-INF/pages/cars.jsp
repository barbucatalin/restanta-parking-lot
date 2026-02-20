<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Cars">
    <h1 class="mb-3">Cars</h1>

    <!-- Add Car – doar pentru WRITE_CARS -->
    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
        <a class="btn btn-primary btn-lg mb-3"
           href="${pageContext.request.contextPath}/AddCar">
            Add Car
        </a>
    </c:if>

    <!-- DELETE + EDIT – doar pentru WRITE_CARS -->
    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
        <form method="post" action="${pageContext.request.contextPath}/Cars">
            <div class="mb-3">
                <button type="submit" class="btn btn-danger">
                    Delete selected cars
                </button>
            </div>

            <div class="row gy-3">
                <c:forEach items="${cars}" var="car">
                    <div class="col-12 col-md-4">

                        <!-- Checkbox -->
                        <div class="form-check">
                            <input class="form-check-input"
                                   type="checkbox"
                                   name="car_ids"
                                   value="${car.id}"
                                   id="car_${car.id}">
                            <label class="form-check-label" for="car_${car.id}">
                                    ${car.licensePlate} • Spot ${car.parkingSpot} • ${car.ownerName}
                            </label>
                        </div>

                        <!-- Edit -->
                        <div class="mt-2">
                            <a class="btn btn-secondary btn-sm"
                               href="${pageContext.request.contextPath}/EditCar?id=${car.id}">
                                Edit
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </form>
    </c:if>

    <!-- Listă simplă (fără Edit/Delete) pentru utilizatori fără WRITE_CARS -->
    <c:if test="${not pageContext.request.isUserInRole('WRITE_CARS')}">
        <div class="row gy-3">
            <c:forEach items="${cars}" var="car">
                <div class="col-12 col-md-4">
                        ${car.licensePlate} • Spot ${car.parkingSpot} • ${car.ownerName}
                </div>
            </c:forEach>
        </div>
    </c:if>

    <h5 class="mt-4">
        Free parking spots: ${numberOfFreeParkingSpots}
    </h5>
    <div class="topnav">
        <input type="text" placeholder="Search..">
    </div>
</t:pageTemplate>
