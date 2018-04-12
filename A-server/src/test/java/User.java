import java.io.Serializable;

/**
 * @author: wangteng
 * @description:
 * @date:2018/3/8
 */
public class User implements Serializable{
    private static final long serialVersionUID = -7020619477594468968L;


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

    /**
     * 默认生成的重写equals方法，同时还会且一定要重写hashCode方法
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        boolean isIdEqual = (id!=null && id.equals(user.id)) || (id==null && user.id==null);
        boolean isNameEqual = (name!=null && name.equals(user.name)) || (name==null && user.name==null);

        return isIdEqual && isNameEqual;
    }

    /**
     * 重写的hashCode方法
     * @return
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
