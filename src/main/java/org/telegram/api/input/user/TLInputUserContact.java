package org.telegram.api.input.user;

import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The type TL input user user.
 */
public class TLInputUserContact extends TLAbsInputUser {
    /**
     * The constant CLASS_ID.
     */
    public static final int CLASS_ID = 0x86e94f65;

    private int userId;

    /**
     * Instantiates a new TL input user.
     */
    public TLInputUserContact() {
        super();
    }

    public int getClassId() {
        return CLASS_ID;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

   

    public void serializeBody(OutputStream stream)
            throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
    }

    public void deserializeBody(InputStream stream, TLContext context)
            throws IOException {
        this.userId = StreamingUtils.readInt(stream);
    }

    public String toString() {
        return "inputUserContact#86e94f65";
    }
}