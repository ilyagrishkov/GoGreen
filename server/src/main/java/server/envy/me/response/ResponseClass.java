package server.envy.me.response;

/**
 * Response Class that will answer messages.
 */
@SuppressWarnings("unused")
public class ResponseClass {
    /**
     * Payload is the message to be sent back.
     */
    private String payload;

    /**
     * Creating the message to be returned.
     *
     * @param payloadIn payload message
     */
    public ResponseClass(final String payloadIn) {
        this.payload = payloadIn;
    }

    /**
     * For Deserialization purposes.
     */
    public ResponseClass() {
    }

    /**
     * Getter for the message.
     *
     * @return returns the payload
     */
    public final String getPayload() {
        return payload;
    }

    /**
     * Setter for the message.
     *
     * @param payloadIn payload to replace current payload
     */
    public final void setPayload(final String payloadIn) {
        this.payload = payloadIn;
    }
}
