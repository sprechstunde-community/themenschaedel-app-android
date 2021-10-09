package sprechstunde.community.themenschaedel;

public class SessionManagement {

    public static SessionManagement mInstance;
    private static ROLE mCurrentRole;

    enum ROLE {
        GUEST,
        USER
    }

    private SessionManagement() {

    }

    public static SessionManagement getInstance() {
        if(mInstance == null) {
            mInstance = new SessionManagement();
            mCurrentRole = ROLE.GUEST;
        }
        return mInstance;
    }
    public ROLE getCurrentRole() {
        return mCurrentRole;
    }

    public void setCurrentRole(ROLE currentRole) {
        mCurrentRole = currentRole;
    }
}
