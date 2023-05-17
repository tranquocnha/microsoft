package g52.training.event;

public class CreateAccountPaymentSuccessEvent {

    private String requestId;
    private String userId;
    private String userName;
    private Long createdAt;

    public CreateAccountPaymentSuccessEvent() {
    }

    public CreateAccountPaymentSuccessEvent(String requestId, String userId, String userName, Long createdAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
