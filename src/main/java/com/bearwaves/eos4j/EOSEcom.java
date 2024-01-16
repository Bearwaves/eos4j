package com.bearwaves.eos4j;

import java.util.Date;

public class EOSEcom {

    private final long handle;

    EOSEcom(long handle) {
        this.handle = handle;
    }

    public void queryOffers(QueryOffersOptions options, OnQueryOffersCompleteCallback callback) {
        EOSEcomNative.queryOffers(handle, options, callback);
    }

    public int getOfferCount(GetOfferCountOptions options) {
        return EOSEcomNative.getOfferCount(handle, options);
    }

    public CatalogOffer copyOfferByIndex(CopyOfferByIndexOptions options) throws EOSException {
        return EOSEcomNative.copyOfferByIndex(handle, options);
    }

    public CatalogOffer copyOfferById(CopyOfferByIdOptions options) throws EOSException {
        return EOSEcomNative.copyOfferById(handle, options);
    }

    public static class CatalogOffer extends EOSHandle {
        public final int serverIndex;
        public final String catalogNamespace;
        public final String id;
        public final String titleText;
        public final String descriptionText;
        public final String longDescriptionText;
        public final String currencyCode;
        public final int priceResult;
        public final int discountPercentage;
        public final Date expiration;
        public final int purchaseLimit;
        public final boolean availableForPurchase;
        public final long originalPrice;
        public final long currentPrice;
        public final int decimalPoint;
        public final Date releaseDate;
        public final Date effectiveDate;

        public CatalogOffer(long ptr,
                            int serverIndex,
                            String catalogNamespace,
                            String id,
                            String titleText,
                            String descriptionText,
                            String longDescriptionText,
                            String currencyCode,
                            int priceResult,
                            int discountPercentage,
                            Date expiration,
                            int purchaseLimit,
                            boolean availableForPurchase,
                            long originalPrice,
                            long currentPrice,
                            int decimalPoint,
                            Date releaseDate,
                            Date effectiveDate) {
            super(ptr);
            this.serverIndex = serverIndex;
            this.catalogNamespace = catalogNamespace;
            this.id = id;
            this.titleText = titleText;
            this.descriptionText = descriptionText;
            this.longDescriptionText = longDescriptionText;
            this.currencyCode = currencyCode;
            this.priceResult = priceResult;
            this.discountPercentage = discountPercentage;
            this.expiration = expiration;
            this.purchaseLimit = purchaseLimit;
            this.availableForPurchase = availableForPurchase;
            this.originalPrice = originalPrice;
            this.currentPrice = currentPrice;
            this.decimalPoint = decimalPoint;
            this.releaseDate = releaseDate;
            this.effectiveDate = effectiveDate;
        }

        public void release() {
            EOSEcomNative.releaseCatalogOffer(ptr);
        }
    }

    public static class QueryOffersOptions {
        public final EOS.EpicAccountId localUserId;
        public final String overrideCatalogNamespace;

        public QueryOffersOptions(EOS.EpicAccountId localUserId, String overrideCatalogNamespace) {
            this.localUserId = localUserId;
            this.overrideCatalogNamespace = overrideCatalogNamespace;
        }
    }

    public static class GetOfferCountOptions {
        public final EOS.EpicAccountId localUserId;

        public GetOfferCountOptions(EOS.EpicAccountId localUserId) {
            this.localUserId = localUserId;
        }
    }

    public static class CopyOfferByIndexOptions {
        public final EOS.EpicAccountId localUserId;
        public final int offerIndex;

        public CopyOfferByIndexOptions(EOS.EpicAccountId localUserId, int offerIndex) {
            this.localUserId = localUserId;
            this.offerIndex = offerIndex;
        }
    }

    public static class CopyOfferByIdOptions {
        public final EOS.EpicAccountId localUserId;
        public final String offerId;

        public CopyOfferByIdOptions(EOS.EpicAccountId localUserId, String offerId) {
            this.localUserId = localUserId;
            this.offerId = offerId;
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
