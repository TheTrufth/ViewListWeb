<%--
  Created by IntelliJ IDEA.
  User: Dinesh
  Date: 12/05/2021
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="uk.ac.ucl.model.TodoList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="uk.ac.ucl.model.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title><%=(String) request.getAttribute("listName")%></title>
</head>
<body>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous"><jsp:include page="/header.jsp"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<div class="main">

    <form method="POST" action="/runsearch.html">
        <input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" />
        <input type="text" name="searchValue" placeholder="Search"/>
        <input type="submit" class="btn btn-outline-info btn-sm" value="Search for a list"/>
    </form>

    <ul>
    <%
        ArrayList<String> path = (ArrayList<String>) request.getAttribute("PATH");
        int i = 0;
        for (String listName : path){
            if (i != path.size() - 1){
    %>
                <li class="inline-list"><a href="list.html?listName=<%=listName%>"><%=listName%></a></li>
                <li class="inline-list"><p> -> </p></li>
            <%} else{%>
                <li class="inline-list"><a href="list.html?listName=<%=listName%>"><%=listName%></a></li>
            <% } i++; %>

            <%}
    %>
    </ul>
    <h2> <%=(String) request.getAttribute("listName")%> </h2>
    <ul>
        <%
            for (Object item : (LinkedList<Object>) request.getAttribute("items")){
                if (item instanceof TodoList){
                    TodoList tdl = (TodoList) item;
        %>
        <li><form class="inline-list align-middle" method="POST" action="edit.jsp"><input type="hidden" name="type" value="editList" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="listToEdit" value="<%=tdl.getListName()%>" /><input class="btn btn-outline-dark btn-sm" type="submit" value="✏️"></form><a href="list.html?listName=<%=tdl.getListName()%>" class="inline-list"><%=tdl.getListName()%></a><form class="inline-list align-middle" method="POST" action="/updateList.html"><input type="hidden" name="action" value="removeList" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="listToRemove" value="<%=tdl.getListName()%>" /><input class="btn btn-outline-danger btn-sm" type="submit" value="❌"></form></li>
        <%  }
            else if (item instanceof Item){
                Item objItem = (Item) item;
        %>
        <li><form class="inline-list align-middle" method="POST" action="edit.jsp" class="inline-list"><input type="hidden" name="type" value="editItem" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="itemToEdit" value="<%=item%>" /><input type="hidden" name="origText" value="<%=objItem.getText()%>" /><input type="hidden" name="origUrl" value="<%=objItem.getUrl()%>" /><input type="hidden" name="origImg" value="<%=objItem.getImage()%>" /><input class="btn btn-outline-dark btn-sm" type="submit" value="✏️"></form>
            <a href="<%=objItem.getUrl()%>"><img src="<%=objItem.getImage()%>" alt="<%=objItem.getImage()%>" class="rounded" width="50" height="50" /></a><%=objItem.getText()%>
        <form class="inline-list align-middle" method="POST" action="/updateItem.html" class="inline-list"><input type="hidden" name="action" value="removeItem" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="origText" value="<%=objItem.getText() %>" /><input type="hidden" name="origUrl" value="<%=objItem.getUrl() %>" /><input type="hidden" name="origImg" value="<%=objItem.getImage() %>" /><input class="btn btn-outline-danger btn-sm" type="submit" value="❌"></form></li>
        <% }
        else{
        %>
        <li><form class="inline-list align-middle" method="POST" action="edit.jsp" class="inline-list"><input type="hidden" name="type" value="editItem" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="itemToEdit" value="<%=item.toString()%>" /><input class="btn btn-outline-dark btn-sm" type="submit" value="✏️"></form><%=item.toString()%>&nbsp;&nbsp;<form class="inline-list align-middle" method="POST" action="/updateItem.html" class="inline-list"><input type="hidden" name="action" value="removeItem" /><input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" /><input type="hidden" name="itemToRemove" value="<%=item.toString()%>" /><input class="btn btn-outline-danger btn-sm" type="submit" value="❌"></form></li>
        <%  } %>


        <% } %>
    </ul>
</div>

<form method="POST" action="/updateList.html">
    <input type="hidden" name="action" value="addList" />
    <input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" />
    <input type="text" name="newListName" placeholder="Add list"/>
    <input type="submit" class="btn btn-outline-success btn-sm" value="Add List"/>
</form>

<form method="POST" action="/updateItem.html">
    <input type="hidden" name="action" value="addItem" />
    <input type="hidden" name="listName" value="<%=request.getAttribute("listName") %>" />
    <input type="text" name="itemText" placeholder="Add text"/>
    <input type="url" name="itemUrl" placeholder="Add url"/>
    <input type="url" name="itemImage" placeholder="Add image (url)"/>
    <input type="submit" class="btn btn-outline-success btn-sm" value="Add item"/>
</form>
<jsp:include page="/footer.jsp"/>
</body>
</html>
