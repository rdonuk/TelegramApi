/*
 * This is the source code of Telegram Bot v. 2.0
 * It is licensed under GNU GPL v. 3 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Ruben Bermudez, 13/11/14.
 */
package org.telegram.api.chat;

import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Information of a chat where the user has no access
 * @author Ruben Bermudez
 * @version 2.0
 * @date 02 of May of 2015
 */
public class TLChatForbidden extends TLAbsChat {
    /**
     * The constant CLASS_ID.
     */
    public static final int CLASS_ID = 0x7328bdb;

    /**
     * Instantiates a new TL chat forbidden.
     */
    public TLChatForbidden() {
        super();
    }

    public int getClassId() {
        return CLASS_ID;
    }

    public void serializeBody(OutputStream stream)
            throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(getTitle(), stream);
    }

    public void deserializeBody(InputStream stream, TLContext context)
            throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.setTitle(StreamingUtils.readTLString(stream));
    }

    public String toString() {
        return "chatForbidden#7328bdb";
    }
    
    @Override
    public String getUsername() {
        return null;
    }
}