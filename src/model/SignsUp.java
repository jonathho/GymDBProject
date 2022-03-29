package model;

public class SignsUp {
    private final int cid;
    private final int class_code;
    private final int confirmation;

    public SignsUp(int cid, int class_code, int confirmation) {
        this.cid = cid;
        this.class_code = class_code;
        this.confirmation = confirmation;
    }

    public int getCid() {
        return cid;
    }

    public int getClass_code() {
        return class_code;
    }

    public int getConfirmation() {
        return confirmation;
    }
}
