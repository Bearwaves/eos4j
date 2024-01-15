package com.bearwaves.eos4j;

public class EOSEcom {

    private final long handle;

    EOSEcom(long handle) {
        this.handle = handle;
    }

    public void queryOffers(QueryOffersOptions options, OnQueryOffersCompleteCallback callback) {
        EOSEcomNative.queryOffers(handle, options, callback);
    }

    public int getOfferCount() {
        return EOSEcomNative.getOfferCount(handle);
    }

    public static class QueryOffersOptions {
        public final EOS.EpicAccountId localUserId;
        public final String overrideCatalogNamespace;

        public QueryOffersOptions(EOS.EpicAccountId localUserId, String overrideCatalogNamespace) {
            this.localUserId = localUserId;
            this.overrideCatalogNamespace = overrideCatalogNamespace;
        }
    }

    public static class QueryOffersCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;

        public QueryOffersCallbackInfo(int resultCode, EOS.EpicAccountId localUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
        }
    }

    public interface OnQueryOffersCompleteCallback {
        void run(QueryOffersCallbackInfo data);
    }
}
