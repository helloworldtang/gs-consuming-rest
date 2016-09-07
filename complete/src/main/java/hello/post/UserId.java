package hello.post;


import javax.validation.constraints.NotNull;

/**
 * Created by MyWorld on 2016/9/8.
 */
public class UserId {

    @NotNull(message = "ids是必填项")
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
