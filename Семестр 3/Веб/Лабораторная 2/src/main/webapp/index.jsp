<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лабораторная №2</title>
    <link rel="stylesheet" href="style.css" type="text/css">
</head>

<body>
<div class="container">

    <!-- Заголовок -->
    <table class="header-table">
        <tr>
            <td class="name">Корхонен Артём Андреевич</td>
            <td class="isu">466298</td>
            <td class="group">P3232</td>
            <td class="labwork">Лабораторная №2</td>
            <td class="variant">Вариант №83927</td>
        </tr>
    </table>

    <!-- График -->
    <table class="graph-table">
        <tr>
            <td>
                <svg id="graph-svg" width="400" height="400" viewBox="0 0 400 400">

                    <!-- Четверть круга -->
                    <circle cx="200" cy="200" r="160" fill="cyan" stroke="black" />
                    <polygon points="0,0 0,200 400,200 400,0" fill="white" stroke="white" />
                    <polygon points="0,0 200,0 200,400 0,400" fill="white" stroke="white" />

                    <!-- Прямоугольник -->
                    <polygon points="200,40 360,40 360,200 200,200" fill="cyan" stroke="black" />

                    <!-- Треугольник -->
                    <polygon points="200,200 120,200 200,280" fill="cyan" stroke="black" />

                    <!-- Оси -->
                    <line x1="200" y1="0" x2="200" y2="400" stroke="black" />
                    <line x1="200" y1="0" x2="210" y2="10" stroke="black" />
                    <line x1="200" y1="0" x2="190" y2="10" stroke="black" />

                    <line x1="0" y1="200" x2="400" y2="200" stroke="black" />
                    <line x1="400" y1="200" x2="390" y2="190" stroke="black" />
                    <line x1="400" y1="200" x2="390" y2="210" stroke="black" />

                    <!-- Чёрточки по X -->
                    <line x1="40" y1="190" x2="40" y2="210" stroke="black" />
                    <line x1="120" y1="190" x2="120" y2="210" stroke="black" />
                    <line x1="280" y1="190" x2="280" y2="210" stroke="black" />
                    <line x1="360" y1="190" x2="360" y2="210" stroke="black" />

                    <!-- Чёрточки по Y -->
                    <line x1="190" y1="40" x2="210" y2="40" stroke="black" />
                    <line x1="190" y1="120" x2="210" y2="120" stroke="black" />
                    <line x1="190" y1="280" x2="210" y2="280" stroke="black" />
                    <line x1="190" y1="360" x2="210" y2="360" stroke="black" />

                    <!-- Подписи по X -->
                    <text x="40" y="185" font-size="10" text-anchor="middle">-R</text>
                    <text x="120" y="185" font-size="10" text-anchor="middle">-R/2</text>
                    <text x="280" y="185" font-size="10" text-anchor="middle">R/2</text>
                    <text x="360" y="185" font-size="10" text-anchor="middle">R</text>

                    <!-- Подписи по Y -->
                    <text x="215" y="40" font-size="10" alignment-baseline="middle">R</text>
                    <text x="215" y="120" font-size="10" alignment-baseline="middle">R/2</text>
                    <text x="215" y="280" font-size="10" alignment-baseline="middle">-R/2</text>
                    <text x="215" y="360" font-size="10" alignment-baseline="middle">-R</text>
                </svg>
            </td>
        </tr>
    </table>

    <!-- Опции -->
    <form action="controller" method="post">
        <table class="options-table">
            <!-- X -->
            <tr><td class="change-x-label">Изменение X</td></tr>
            <tr>
                <td>
                    <select class="change-x-select">
                        <% for (int x = -4; x <= 4; x++) { %>
                            <option data-x="<%= x %>"><%= x %></option>
                        <% } %>
                    </select>
                </td>
            </tr>

            <tr id="error-x" class="hidden">
                <td><label class="error-label">Отсутствует x</label></td>
            </tr>

            <!-- Y -->
            <tr><td class="change-y-label">Изменение Y</td></tr>
            <tr class="change-y"><td><input id="y" name="y"></td></tr>

            <tr id="error-y" class="hidden">
                <td><label id="error-y-label" class="error-label">Отсутствует y</label></td>
            </tr>

            <!-- R -->
            <tr><td class="change-r-label">Изменение R</td></tr>
            <tr>
                <td>
                    <% for (int r = 1; r <= 5; r++) { %>
                        <input class="change-r" type="checkbox" data-r="<%= r %>"><%= r %></button>
                    <% } %>
                </td>
            </tr>

            <tr id="error-r" class="hidden">
                <td><label class="error-label">Отсутствует r</label></td>
            </tr>

            <!-- Отправка -->
            <tr>
                <td><button id="send" type="button">Проверить</button></td>
            </tr>

            <tr id="error-main" class="hidden">
                <td><label id="error-main-label" class="error-label">Ошибка</label></td>
            </tr>
        </table>
    </form>

    <!-- Таблица результатов -->
    <div id="results-container" class="results-container">
        <jsp:include page="table.jsp"></jsp:include>
    </div>
</div>

<script src="script.js"></script>
</body>
</html>
