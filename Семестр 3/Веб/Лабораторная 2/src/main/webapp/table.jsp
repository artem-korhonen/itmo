<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.PointResponse" %>
<% List<PointResponse> history = (List<PointResponse>) session.getAttribute("history");
        if (history == null) history = new java.util.ArrayList<>();
            %>

            <table class="results-table">
                <tr>
                    <th>x</th>
                    <th>y</th>
                    <th>r</th>
                    <th>test</th>
                    <th>current time</th>
                    <th>request time (ms)</th>
                </tr>
                <% for (int i=history.size() - 1; i >= 0; i--) {
                    PointResponse p = history.get(i); %>
                    <tr>
                        <td>
                            <%= p.getX() %>
                        </td>
                        <td>
                            <%= p.getY() %>
                        </td>
                        <td>
                            <%= p.getR() %>
                        </td>
                        <td>
                            <%= p.getTest() ? "Попал" : "Мимо" %>
                        </td>
                        <td>
                            <%= p.getCurrentTime().toString().replace("T", " ").split("\\.")[0] %>
                        </td>
                        <td>
                            <%= p.getRequestTime() / 1_000_000.0 %>
                        </td>
                    </tr>
                    <% } %>
            </table>