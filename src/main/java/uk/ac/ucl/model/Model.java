package uk.ac.ucl.model;

import java.io.*;
import java.util.*;


public class Model {
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a DataFrame.
  // private DataFrame frame = ...
  private TodoList myTodoModel;
  private String currentListName;
  private ArrayList<String> currentPath;
  private String fileName;



  public Model(){
    this.myTodoModel = new TodoList("HOME");
    this.currentListName = "HOME";
    this.currentPath = new ArrayList<>();
  }

  public LinkedList<Object> getItems()
  {
    return myTodoModel.getItems(); // This is just dummy data
  }

  public ArrayList<String> getCurrentPath() {
    return currentPath;
  }

  public String getCurrentListName(){
    return currentListName;
  }

  public void addList(String parentList, String listName){
    TodoList newList = new TodoList(listName);
    TodoList subTodo = myTodoModel.getSublist(parentList);
    if (subTodo == null){
      currentListName = myTodoModel.getListName();
      myTodoModel.addItem(newList);
      currentPath = new ArrayList<String>();
      currentPath.add("HOME");
    }
    else {
      currentListName = listName;
      updatePath(subTodo, listName);
      subTodo.addItem(newList);
    }
    this.writeToFile();
  }

  private void updatePath(TodoList subTodo, String listName){
    currentPath = new ArrayList<String>(subTodo.getPath());
    Collections.reverse(currentPath);
    if (!currentPath.contains("HOME")){
      currentPath.add(0, "HOME");
    }
    currentPath.add(listName);
  }

  public void removeList(String listName, String listToRemove){
    TodoList tdl = myTodoModel.getSublist(listName);
    TodoList ttdl;
    if (tdl != null) {
      ttdl = tdl.getSublist(listToRemove);
      if(ttdl != null){
        tdl.removeList(ttdl);
        currentListName = tdl.getListName();
        updatePath(ttdl, tdl.getListName());
      }
    }
    else {
      ttdl = myTodoModel.getSublist(listToRemove);
      if(ttdl != null){
        myTodoModel.removeList(ttdl);
        currentListName = "HOME";
        currentPath = new ArrayList<String>(Collections.singleton("HOME"));
      }
    }
    this.writeToFile();
  }

  public void removeItem(String listName, Item item){
    LinkedList<Object> items = getSubTodoItems(listName);
    for (Object obj : items){
      if (obj instanceof Item){
        Item itemObj = (Item) obj;
        if (itemObj.getText().equals(item.getText()) && itemObj.getUrl().equals(item.getUrl()) && itemObj.getImage().equals(item.getImage())){
          this.myTodoModel.removeItem(listName, itemObj);
          break;
        }
      }
    }
    this.writeToFile();
  }

  public void updateList(String parentList, String listToEdit, String editName){
    TodoList tdl = myTodoModel.getSublist(parentList);
    if (tdl != null){
      TodoList ttdl = tdl.getSublist(listToEdit);
      if (ttdl != null && !editName.equals("")){
        ttdl.setListName(editName);
      }
    }
    else if (myTodoModel.getListName().equals(parentList)){
      tdl = myTodoModel.getSublist(listToEdit);
      tdl.setListName(editName);
    }
    this.writeToFile();
  }

  public void updateItem(String listName, String oldText, String oldUrl, String oldImg, Item updatedItem){
    TodoList subTodo = this.myTodoModel.getSublist(listName);
    if (subTodo != null){
      subTodo.updateItem(oldText, oldUrl, oldImg, updatedItem);
    }
    else {
      myTodoModel.updateItem(oldText, oldUrl, oldImg, updatedItem);
    }
    this.writeToFile();
  }

  public void readFile(String fileName) throws IOException {
    this.fileName = fileName;
    // Read a .json data file and create the LinkedList.
    BufferedReader bf = new BufferedReader(new FileReader(fileName));
    String line = bf.readLine();
    int currentList = 0;
    Deque<TodoList> stack = new ArrayDeque<>();
    stack.push(myTodoModel);
    while (line != null){
      if (line.contains(":")){
        String[] kv = line.split(":", 2);
        if (line.contains("{")){
          stack.push(new TodoList(kv[0].replaceAll("\"", "").strip()));
          currentList += 1;
        }
        else {
          //System.out.println("Value: " + kv[1] + " in TodoList: " + currentList + " (" + stack.peek().getListName() + ") ");
          assert stack.peek() != null;
          stack.peek().addItem(kv[1].replaceAll("\"", "").replaceAll(",", "").split(" ; "));
        }
      }
      if (line.contains("}")){
        currentList -= 1;
        TodoList todoL = stack.pop();
        if (stack.peek() != null) {
          stack.peek().addItem(todoL);
        }
      }
      line = bf.readLine();
    }
    //System.out.println(myTodoList.toString());
  }

  public LinkedList<Object> getSubTodoItems(String listName){
    TodoList subTodo = myTodoModel.getSublist(listName);
    if (subTodo == null){
      currentListName = myTodoModel.getListName();
      currentPath = new ArrayList<String>();
      currentPath.add("HOME");
      return myTodoModel.getItems();
    }
    currentListName = listName;
    currentPath = new ArrayList<String>(subTodo.getPath());
    updatePath(subTodo, listName);
    return subTodo.getItems();
  }

  // Returns a string list of listNames that match the keyword in one or more ways.
  public List<String> searchFor(String keyword) {
    keyword = keyword.toLowerCase(Locale.ROOT);
    ArrayList<String> searchResult = new ArrayList<>();
    ArrayList<String> allLists = myTodoModel.getAllListNames();
    for (String listName : allLists){
      if (listName.toLowerCase(Locale.ROOT).equals(keyword) || listName.toLowerCase(Locale.ROOT).startsWith(keyword.toLowerCase(), 3) || listName.toLowerCase(Locale.ROOT).endsWith(keyword) || listName.toLowerCase(Locale.ROOT).contains(keyword)){
        searchResult.add(listName);
      }
    }
    return searchResult;
  }

  public void addItemToList(String listName, Object obj){
    TodoList subTodo = myTodoModel.getSublist(listName);
    if (subTodo == null){
      myTodoModel.addItem(obj);
    }
    else {
      subTodo.addItem(obj);
    }
    this.writeToFile();
  }

  public void writeToFile(){
        try {
          FileWriter fw = new FileWriter(fileName);
          fw.write(this.toString());
          fw.close();
          //System.out.println("[AUTO] Successfully wrote changes to JSON.");
        } catch (IOException e) {
          System.out.println("[ERROR] Could not save changes to JSON.");
          e.printStackTrace();
        }
  }

  /*
  public void testt(){
    StringBuilder sb = new StringBuilder("{");
    int ident = 2;
    for (Object element: myTodoModel.getItems()){
      if (element instanceof TodoList){
        TodoList tdl = (TodoList) element;
        sb.append(" ".repeat(ident) + "\"" + tdl.getListName() + "\": {");
      }
    }
  }*/

  @Override
  public String toString() {
    return myTodoModel.listToString(0);
  }

  public static void main(String[] args) throws IOException {
    Model m = new Model();
    m.readFile("data/listOlist.json");
    //m.removeItem("Shopping list");
    System.out.println(m.toString());
    //m.removeItem("Shopping List", ";;Eggs;;http://bit.ly/3eSSTaw;;http://bit.ly/3oxA2oF");


  }
}


