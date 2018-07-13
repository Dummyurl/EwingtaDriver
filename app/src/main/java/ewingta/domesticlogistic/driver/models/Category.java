package ewingta.domesticlogistic.driver.models;

public class Category {
    private long id;
    private String published;
    private String cat_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", published='" + published + '\'' +
                ", cat_name='" + cat_name + '\'' +
                '}';
    }
}
