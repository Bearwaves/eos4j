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

    public void queryEntitlements(QueryEntitlementsOptions options, OnQueryEntitlementsCallback callback) {
        EOSEcomNative.queryEntitlements(handle, options, callback);
    }

    public int getEntitlementsCount(GetEntitlementsCountOptions options) {
        return EOSEcomNative.getEntitlementsCount(handle, options);
    }

    public int getEntitlementsByNameCount(GetEntitlementsByNameCountOptions options) {
        return EOSEcomNative.getEntitlementsByNameCount(handle, options);
    }

    public Entitlement copyEntitlementByIndex(CopyEntitlementByIndexOptions options) throws EOSException {
        return EOSEcomNative.copyEntitlementByIndex(handle, options);
    }

    public Entitlement copyEntitlementById(CopyEntitlementByIdOptions options) throws EOSException {
        return EOSEcomNative.copyEntitlementById(handle, options);
    }

    public Entitlement copyEntitlementByNameAndIndex(CopyEntitlementByNameAndIndexOptions options) throws EOSException {
        return EOSEcomNative.copyEntitlementByNameAndIndex(handle, options);
    }

    public void queryEntitlementToken(QueryEntitlementTokenOptions options, OnQueryEntitlementTokenCallback callback) {
        EOSEcomNative.queryEntitlementToken(handle, options, callback);
    }

    public void redeemEntitlements(RedeemEntitlementsOptions options, OnRedeemEntitlementsCallback callback) {
        EOSEcomNative.redeemEntitlements(handle, options, callback);
    }

    public void queryOwnership(QueryOwnershipOptions options, OnQueryOwnershipCallback callback) {
        EOSEcomNative.queryOwnership(handle, options, callback);
    }

    public void queryOwnershipToken(QueryOwnershipTokenOptions options, OnQueryOwnershipTokenCallback callback) {
        EOSEcomNative.queryOwnershipToken(handle, options, callback);
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

    public static class Entitlement extends EOSHandle {
        public final String entitlementName;
        public final String entitlementId;
        public final String catalogItemId;
        public final int serverIndex;
        public final boolean redeemed;
        public final Date end;

        public Entitlement(long ptr,
                           String entitlementName,
                           String entitlementId,
                           String catalogItemId,
                           int serverIndex,
                           boolean redeemed,
                           Date end) {
            super(ptr);
            this.entitlementName = entitlementName;
            this.entitlementId = entitlementId;
            this.catalogItemId = catalogItemId;
            this.serverIndex = serverIndex;
            this.redeemed = redeemed;
            this.end = end;
        }

        public void release() {
            EOSEcomNative.releaseEntitlement(ptr);
        }
    }

    public static class ItemOwnership {
        public final String id;
        public final OwnershipStatus ownershipStatus;

        public ItemOwnership(String id, OwnershipStatus ownershipStatus) {
            this.id = id;
            this.ownershipStatus = ownershipStatus;
        }
    }

    // Options structs

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

    public static class QueryEntitlementsOptions {
        public final EOS.EpicAccountId localUserId;
        public final String[] entitlementNames;
        public final boolean includeRedeemed;

        public QueryEntitlementsOptions(EOS.EpicAccountId localUserId, String[] entitlementNames, boolean includeRedeemed) {
            this.localUserId = localUserId;
            this.entitlementNames = entitlementNames;
            this.includeRedeemed = includeRedeemed;
        }
    }

    public static class GetEntitlementsCountOptions {
        public final EOS.EpicAccountId localUserId;

        public GetEntitlementsCountOptions(EOS.EpicAccountId localUserId) {
            this.localUserId = localUserId;
        }
    }

    public static class GetEntitlementsByNameCountOptions {
        public final EOS.EpicAccountId localUserId;
        public final String entitlementName;

        public GetEntitlementsByNameCountOptions(EOS.EpicAccountId localUserId, String entitlementName) {
            this.localUserId = localUserId;
            this.entitlementName = entitlementName;
        }
    }

    public static class CopyEntitlementByIndexOptions {
        public final EOS.EpicAccountId localUserId;
        public final int entitlementIndex;

        public CopyEntitlementByIndexOptions(EOS.EpicAccountId localUserId, int entitlementIndex) {
            this.localUserId = localUserId;
            this.entitlementIndex = entitlementIndex;
        }
    }

    public static class CopyEntitlementByIdOptions {
        public final EOS.EpicAccountId localUserId;
        public final String entitlementId;

        public CopyEntitlementByIdOptions(EOS.EpicAccountId localUserId, String entitlementId) {
            this.localUserId = localUserId;
            this.entitlementId = entitlementId;
        }
    }

    public static class CopyEntitlementByNameAndIndexOptions {
        public final EOS.EpicAccountId localUserId;
        public final String entitlementName;
        public final int index;

        public CopyEntitlementByNameAndIndexOptions(EOS.EpicAccountId localUserId, String entitlementName, int index) {
            this.localUserId = localUserId;
            this.entitlementName = entitlementName;
            this.index = index;
        }
    }

    public static class QueryEntitlementTokenOptions {
        public final EOS.EpicAccountId localUserId;
        public final String[] entitlementNames;

        public QueryEntitlementTokenOptions(EOS.EpicAccountId localUserId, String[] entitlementNames) {
            this.localUserId = localUserId;
            this.entitlementNames = entitlementNames;
        }
    }

    public static class RedeemEntitlementsOptions {
        public final EOS.EpicAccountId localUserId;
        public final String[] entitlementIds;

        public RedeemEntitlementsOptions(EOS.EpicAccountId localUserId, String[] entitlementIds) {
            this.localUserId = localUserId;
            this.entitlementIds = entitlementIds;
        }
    }

    public static class QueryOwnershipOptions {
        public final EOS.EpicAccountId localUserId;
        public final String[] catalogItemIds;
        public final String catalogNamespace;

        public QueryOwnershipOptions(EOS.EpicAccountId localUserId, String[] catalogItemIds, String catalogNamespace) {
            this.localUserId = localUserId;
            this.catalogItemIds = catalogItemIds;
            this.catalogNamespace = catalogNamespace;
        }
    }

    public static class QueryOwnershipTokenOptions {
        public final EOS.EpicAccountId localUserId;
        public final String[] catalogItemIds;
        public final String catalogNamespace;

        public QueryOwnershipTokenOptions(EOS.EpicAccountId localUserId, String[] catalogItemIds, String catalogNamespace) {
            this.localUserId = localUserId;
            this.catalogItemIds = catalogItemIds;
            this.catalogNamespace = catalogNamespace;
        }
    }

    // Callback structs

    public static class QueryOffersCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;

        QueryOffersCallbackInfo(int resultCode, EOS.EpicAccountId localUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
        }
    }

    public static class QueryEntitlementsCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;

        QueryEntitlementsCallbackInfo(int resultCode, EOS.EpicAccountId localUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
        }
    }

    public static class QueryEntitlementTokenCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;
        public final String entitlementToken;

        QueryEntitlementTokenCallbackInfo(int resultCode, EOS.EpicAccountId localUserId, String entitlementToken) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.entitlementToken = entitlementToken;
        }
    }

    public static class RedeemEntitlementsCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;
        public final int redeemedEntitlementIdsCount;

        RedeemEntitlementsCallbackInfo(int resultCode, EOS.EpicAccountId localUserId, int redeemedEntitlementIdsCount) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.redeemedEntitlementIdsCount = redeemedEntitlementIdsCount;
        }
    }

    public static class QueryOwnershipCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;
        public final ItemOwnership[] itemOwnerships;

        QueryOwnershipCallbackInfo(int resultCode, EOS.EpicAccountId localUserId, ItemOwnership[] itemOwnerships) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.itemOwnerships = itemOwnerships;
        }
    }

    public static class QueryOwnershipTokenCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;
        public final String ownershipToken;

        QueryOwnershipTokenCallbackInfo(int resultCode, EOS.EpicAccountId localUserId, String ownershipToken) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.ownershipToken = ownershipToken;
        }
    }

    // Callback interfaces

    public interface OnQueryOffersCompleteCallback {
        void run(QueryOffersCallbackInfo data);
    }

    public interface OnQueryEntitlementsCallback {
        void run(QueryEntitlementsCallbackInfo data);
    }

    public interface OnQueryEntitlementTokenCallback {
        void run(QueryEntitlementTokenCallbackInfo data);
    }

    public interface OnRedeemEntitlementsCallback {
        void run(RedeemEntitlementsCallbackInfo data);
    }

    public interface OnQueryOwnershipCallback {
        void run(QueryOwnershipCallbackInfo data);
    }

    public interface OnQueryOwnershipTokenCallback {
        void run(QueryOwnershipTokenCallbackInfo data);
    }

    // Enums

    public enum OwnershipStatus {
        NOT_OWNED, OWNED;

        static final OwnershipStatus[] values;

        static {
            values = OwnershipStatus.values();
        }

        static OwnershipStatus fromInt(int i) {
            for (OwnershipStatus value : values) {
                if (value.ordinal() == i) {
                    return value;
                }
            }
            return null;
        }
    }
}
