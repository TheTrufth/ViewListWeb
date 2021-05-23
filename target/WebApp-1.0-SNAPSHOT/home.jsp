<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.TodoList" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="uk.ac.ucl.model.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("items") != null){
        session.setAttribute("items", (LinkedList<Object>) request.getAttribute("items"));
        session.setAttribute("listName", request.getAttribute("listName"));
        session.setAttribute("model", request.getAttribute("model"));
    }%>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title><%=(String) session.getAttribute("listName")%></title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">

  <h2> <%=(String) session.getAttribute("listName")%> </h2>
  <ul>
      <%
      for (Object item : (LinkedList<Object>) session.getAttribute("items")){
        if (item instanceof TodoList){
          TodoList tdl = (TodoList) item;
      %>
          <li><a href="home.jsp" onclick="<% session.setAttribute("listName", tdl.getListName()); Model model = (Model) session.getAttribute("model"); session.setAttribute("items", model.getSubTodoItems(tdl.getListName()));%>"><%=tdl.getListName()%></a></li>
      <%  }
        else{
      %>
          <li><%=item.toString()%></li>
      <%  } %>


    <% } %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
