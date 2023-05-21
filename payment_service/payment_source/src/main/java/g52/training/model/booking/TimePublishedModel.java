package g52.training.model.booking;

public class TimePublishedModel {
    private Long time;

    public TimePublishedModel() {
    }

    public TimePublishedModel(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TimePublishedModel{" +
                "time=" + time +
                '}';
    }
}
