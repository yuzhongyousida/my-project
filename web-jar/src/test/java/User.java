import java.io.Serializable;

/**
 * @author: wangteng
 * @description:
 * @date:2018/3/8
 */
public class User implements Serializable{

    private static final long serialVersionUID = -2046267374983440900L;

    private Integer id;

    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
