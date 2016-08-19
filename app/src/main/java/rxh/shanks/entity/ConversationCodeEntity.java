package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ConversationCodeEntity {

    private String code;
    private String error;
    private String token;
    private ConversationEntity result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ConversationEntity getResult() {
        return result;
    }

    public void setResult(ConversationEntity result) {
        this.result = result;
    }
}
