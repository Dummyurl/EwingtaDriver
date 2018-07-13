package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private String status;
    private List<Category> categorylist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Category> getCategorylist() {

        if (categorylist == null) {
            categorylist = new ArrayList<>();
        }

        return categorylist;
    }

    public void setCategorylist(List<Category> categorylist) {
        this.categorylist = categorylist;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "status='" + status + '\'' +
                ", categorylist=" + categorylist +
                '}';
    }
}
