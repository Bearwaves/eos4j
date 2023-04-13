package com.bearwaves.eos4j;

public class EOSPlatform {

    private final long handle;

    public EOSPlatform(Options options) throws EOSException {
        this.handle = EOSPlatformNative.create(options);
        if (this.handle == 0) {
            throw new EOSException("Failed to create platform handle");
        }
    }

    public void release() {
        EOSPlatformNative.release(handle);
    }

    public static class Options {
        public final String productId;
        public final String sandboxId;
        public final String deploymentId;
        public final String clientId;
        public final String clientSecret;

        public Options(String productId, String sandboxId, String deploymentId, String clientId, String clientSecret) {
            this.productId = productId;
            this.sandboxId = sandboxId;
            this.deploymentId = deploymentId;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }
    }
}
