package com.ou_software_testing.ou_software_testing.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ListCategory {
    private List<Category> listCategory;

    public ListCategory(List<Category> list) {
        this.listCategory =list;
    }

    public ListCategory() {
    }
    
    public String getNameById(int id){
        Category c = getCategoryById(id);
        return c.getName() + " " +c.getSex() ;
    }
    
    public List<String> getListCategoryName(){
        List<String> list = new ArrayList<>();
        for (Category cate: getListCategory()){
            list.add(cate.getName()+" "+cate.getSex());
        }
        return list;
    }
    
    public Category getCategoryById(int id){
        Category category = null;
        for (Category cate: listCategory){
            if (cate.getId() == id)
                category = cate;
        }
        return category;
    }
    /**
     * @return the listCategory
     */
    public List<Category> getListCategory() {
        return listCategory;
    }

    /**
     * @param listCategory the listCategory to set
     */
    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }
}
