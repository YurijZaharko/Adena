<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: SC
  Date: 25.01.2018
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Adena earned');

            <c:forEach items="${chartData.characterNames}" var="name">
                data.addColumn('number', '${name}');
            </c:forEach>

            <c:forEach items="${chartData.dataMap}" var="entry">
                data.addRows([
                [new Date(${entry.key})
                    <c:forEach items="${entry.value}" var="one">
                    , ${one}
                    </c:forEach>
                ]
                ]);
            </c:forEach>

            var options = {
                title: 'Adena earned',
                curveType: 'function',
                legend: {position: 'bottom'}
            };

            var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

            chart.draw(data, options);
        }
    </script>
</head>
<body>
<div id="curve_chart" style="width: 900px; height: 500px"></div>
</body>

