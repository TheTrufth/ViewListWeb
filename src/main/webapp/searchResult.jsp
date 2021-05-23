<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>List viewer!</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search Result</h1>
  <%
    List<String> listNames = (List<String>) request.getAttribute("result");
    if (listNames.size() !=0)
    {
    %>
    <ul>
      <%
        for (String listName : listNames)
        {
      %>
      <li><a href="list.html?listName=<%=listName%>">Found a list: <%=listName%></a></li>
     <% }
    } else
    {%>
      <p>Nothing found</p>
  <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>