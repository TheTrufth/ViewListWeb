package uk.ac.ucl.model;

import java.util.*;

public class TodoList {
    private LinkedList<Object> todoList;
    private String listName;
    private LinkedHashSet<String> path;



    public TodoList(String listName){
        this.todoList = new LinkedList<>();
        this.listName = listName;
        this.path = new LinkedHashSet<>();
    }

    public void addItem(String[] params){
        Item i = new Item();
        if (params.length == 1){
            i.setText(params[0]);
        }
        if (params.length == 2){
            i.setText(params[0]);
            i.setUrl(params[1]);
        }
        if (params.length == 3){
            i.setText(params[0]);
            i.setUrl(params[1]);
            i.setImage(params[2]);
        }
        todoList.add(i);
    }

    public void addItem(Object obj){
        todoList.add(obj);
    }

    public void setListName(String newListName){
        this.listName = newListName;
    }

    public TodoList getSublist(String listName){
        for (Object element : todoList){
            if (element instanceof TodoList){
                TodoList tdl = (TodoList) element;
                if (tdl.listName.equals(listName)){
                    tdl.path.add(this.listName);
                    return tdl;
                }
                else {
                    TodoList ttdl = tdl.getSublist(listName);
                    if (ttdl != null){
                        //ttdl.path.add(ttdl.listName);
                        ttdl.path.add(tdl.listName);
                        return ttdl;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<String> getAllListNames() {
        ArrayList<String> listNames = new ArrayList<>();
        for (Object element : todoList){
            if (element instanceof TodoList){
                TodoList tdl = (TodoList) element;
                listNames.add(tdl.listName);
                listNames.addAll(tdl.getAllListNames());
                }
        }
        return listNames;
    }

    public void removeItem(String listName, Object itemToRemove){
        TodoList tdl = getSublist(listName);

        if (tdl != null) {
            tdl.todoList.remove(itemToRemove);
        }
        else {
            if (this.todoList.contains(itemToRemove)){
                todoList.remove(itemToRemove);
            }
        }
    }

    public void updateItem(String oldText, String oldUrl, String oldImg, Item updatedItem){
        System.out.println("HERE3");
        for (Object obj : this.todoList){
            if (obj instanceof Item){
                Item itemObj = (Item) obj;
                if (itemObj.getText().equals(oldText) && itemObj.getUrl().equals(oldUrl) && itemObj.getImage().equals(oldImg)){
                    System.out.println("HERE4");
                    itemObj.setText(updatedItem.getText());
                    itemObj.setUrl(updatedItem.getUrl());
                    itemObj.setImage(updatedItem.getImage());

                }
            }
        }
    }

    public void removeList(TodoList tl){
        if (this.todoList.contains(tl)){
            this.todoList.remove(tl);
        }
    }

    public LinkedList<Object> getItems(){
        return this.todoList;
    }

    public String getListName(){
        return this.listName;
    }

    public LinkedHashSet<String> getPath(){
        return this.path;
    }

    /*@Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Object element : todoList){
            if (element instanceof TodoList){
                TodoList tdl = (TodoList) element;
                sb.append(tdl.listName).append(": {\n").append(tdl.toString()).append("}");
            }
            else {
                sb.append(element).append("\n");
            }
        }
        return sb.toString();
    }*/

    public String listToString(int ident){
        StringBuilder sb = new StringBuilder();
        if (!listName.equals("HOME")){
            sb.append("\"" + listName + "\"" + ": {\n");
        }
        else {
            sb.append("{\n");
        }
        int i = 0;
        int index = 0;
        for (Object element : todoList){
            if (element instanceof TodoList) {
                TodoList tdl = (TodoList) element;
                sb.append(" ".repeat(ident+2)).append(tdl.listToString(ident + 2));
            }
            else {
                i++;
                sb.append(" ".repeat(ident+2) +"\"i").append(i).append("\": \"").append(element.toString()).append("\"");
            }
            if (index != this.todoList.size() - 1){
                sb.append(",\n");
            }
            index++;
        }
        sb.append("\n" + " ".repeat(ident) + "}");
        return sb.toString();
    }

}
