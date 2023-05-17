package g52.training.event.publisher;


public interface MessageSender<T> {
    public void send(T t);
}
