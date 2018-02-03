package be.superjoran.mint.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorandeboever
 * Date: 30-Apr-17.
 */
@Entity
@Table(name = "T_CATEGORY_GROUP")
public class CategoryGroup extends DomainObject {
    private static final long serialVersionUID = 6772160125295115979L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ADMINISTRATOR_UUID")
    private Person administrator;

    private String name;


    @Column(nullable = false, name = "CATEGORY_TYPE")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;


    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Category> categoryList = new ArrayList<>();

    public CategoryGroup() {
    }

    public CategoryGroup(Person administrator) {
        this.administrator = administrator;
    }

    public CategoryGroup(String name) {
        this.name = name;
    }

    public Person getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(Person administrator) {
        this.administrator = administrator;
    }

    public List<Category> getCategoryList() {
        return this.categoryList;
    }

    public void addCategory(Category category){
        category.setCategoryGroup(this);
        this.categoryList.add(category);
    }

    public CategoryType getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayValue() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
