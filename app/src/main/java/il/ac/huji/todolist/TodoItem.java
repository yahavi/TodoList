package il.ac.huji.todolist;


import java.util.Date;

public class TodoItem {
    private final String m_ItemName;
    private final Date m_Date;

    public TodoItem(String itemName, Date date){
        m_ItemName = itemName;
        m_Date = date;
    }

    public String getItemName(){
        return m_ItemName;
    }

    public Date getDate(){
        return m_Date;
    }

}
