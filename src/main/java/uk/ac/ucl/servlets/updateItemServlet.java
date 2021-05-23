package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Item;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// The servlet invoked to perform a search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/updateItem.html")
public class updateItemServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        Model model = ModelFactory.getModel();
        LinkedList<Object> items = null;

        if (request.getParameter("action").equals("removeItem")){
            model.removeItem(request.getParameter("listName"), new Item(request.getParameter("origText"), request.getParameter("origUrl"), request.getParameter("origImg")));
            items = model.getSubTodoItems(request.getParameter("listName"));
        }
        else if (request.getParameter("action").equals("addItem")){
            model.addItemToList(request.getParameter("listName"),  new Item(request.getParameter("itemText"), request.getParameter("itemUrl"), request.getParameter("itemImage")));
            items = model.getSubTodoItems(request.getParameter("listName"));
        }
        else if (request.getParameter("action").equals("updateItem")){
            model.updateItem(request.getParameter("listName"), request.getParameter("origText"), request.getParameter("origUrl"), request.getParameter("origImg"), new Item(request.getParameter("itemText"), request.getParameter("itemUrl"), request.getParameter("itemImage")));
            items = model.getSubTodoItems(request.getParameter("listName"));
        }

        if (items == null){
            items = model.getSubTodoItems(request.getParameter("listName"));
        }

        ArrayList<String> path = model.getCurrentPath();

        request.setAttribute("listName", model.getCurrentListName());
        request.setAttribute("items", items);
        request.setAttribute("PATH", path);

        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/list.jsp");
        dispatch.forward(request, response);
    }
}