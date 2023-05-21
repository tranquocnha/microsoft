package g52.training.model.booking;

public class AccountPublishedModel {
    private String id;
    private String name;

    public AccountPublishedModel() {
    }

    public AccountPublishedModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AccountPublishedModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
