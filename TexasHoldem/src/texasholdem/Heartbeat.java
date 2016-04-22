package texasholdem;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Heartbeat object sent to maintain membership in the game
 */
public class Heartbeat implements Serializable {

    private final static AtomicInteger counter = new AtomicInteger();

    private final int seqno;

    private final byte[] sender;

    public Heartbeat(byte[] sender) {
        this.sender = sender;
        this.seqno = counter.getAndIncrement();
    }

    public byte[] getSender() {
        return sender;
    }

    public int getSeqno() {
        return seqno;
    }
}