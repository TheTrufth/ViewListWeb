
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% if (request.getParameter("type").equals("editList")){ %>
        <title>Edit <%=request.getParameter("listToEdit") %></title>
    <% } %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
</head>
<body>
    <br>
    <br>
    <% if (request.getParameter("type").equals("editList")){ %>
        <form method="POST" action="/updateList.html">
            <input type="hidden" name="action" value="updateList" />
            <input type="hidden" name="parentList" value="<%=request.getParameter("listName") %>" />
            <input type="hidden" name="listToEdit" value="<%=request.getParameter("listToEdit") %>" />
            <input type="text" name="newListName" value="<%=request.getParameter("listToEdit") %>"/>
            <input type="submit" class="btn btn-outline-success btn-sm" value="Update list name"/>
        </form>
    <% }
    else if (request.getParameter("type").equals("editItem")){ %>
    <form method="POST" action="/updateItem.html">

        <input type="hidden" name="action" value="updateItem" />
        <input type="hidden" name="listName" value="<%=request.getParameter("listName") %>" />
        <input type="hidden" name="origText" value="<%=request.getParameter("origText") %>" />
        <input type="hidden" name="origUrl" value="<%=request.getParameter("origUrl") %>" />
        <input type="hidden" name="origImg" value="<%=request.getParameter("origImg") %>" />

        <label for="itemText">Item Text: </label>
        <input id="itemText" type="text" name="itemText" value="<%=request.getParameter("origText")%>" placeholder="Add text"/>
        <br>

        <label for="itemUrl">Item URL: </label>
        <input id="itemUrl" type="text" name="itemUrl" value="<%=request.getParameter("origUrl")%>" placeholder="Add url"/>
        <br>

        <label for="itemImage">Item Image (URL): </label>
        <input id="itemImage" type="text" name="itemImage" value="<%=request.getParameter("origImg")%>" placeholder="Add image (url)"/>
        <br>

        <input type="submit" class="btn btn-outline-success btn-sm" value="Update item"/>

    </form>
    <% } %>

</body>
</html>
