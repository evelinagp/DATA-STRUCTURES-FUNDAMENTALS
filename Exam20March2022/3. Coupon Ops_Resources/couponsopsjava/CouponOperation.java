package couponsopsjava;

import java.util.*;
import java.util.stream.Collectors;


public class
CouponOperation implements ICouponOperation {

    private Map<String, Coupon> couponsByCode;
    private Map<String, List<Coupon>> websitesWithCoupons;
    private Map<String, Website> websitesByDomain;


    public CouponOperation() {
        this.couponsByCode = new HashMap<>();
        this.websitesWithCoupons = new HashMap<>();
        this.websitesByDomain = new HashMap<>();
    }

    public void registerSite(Website w) {
        if (websitesByDomain.containsKey(w.domain)) {
            throw new IllegalArgumentException();
        }
        websitesByDomain.put(w.domain, w);
    }


    public void addCoupon(Website w, Coupon c) {
        if (exist(c) || !exist(w)) {
            throw new IllegalArgumentException();
        }

        websitesWithCoupons.putIfAbsent(w.domain, new ArrayList<>());
        couponsByCode.put(c.code, c);
        websitesWithCoupons.get(w.domain).add(c);
    }

    public Website removeWebsite(String domain) {
        Website website = this.websitesByDomain.values().stream().filter(w -> w.domain.equals(domain)).findFirst().orElse(null);
        /* if (website == null) {*/
        if (website == null) {
            throw new IllegalArgumentException();
        }

        Website removedWebsite = websitesByDomain.remove(website.domain);
        List<Coupon> removedPatients = websitesWithCoupons.remove(website.domain);
        /*  removedPatients.forEach(patient -> patientsByNames.remove(patient.name));*/
        return removedWebsite;
    }

    public Coupon removeCoupon(String code) {
        Coupon coupon = this.couponsByCode.values().stream().filter(c -> c.code.equals(code)).findFirst().orElse(null);
        if (coupon == null) {
            throw new IllegalArgumentException();
        }

        Coupon removedCoupon = couponsByCode.remove(coupon.code);
        List<Coupon> removedCoupons = websitesWithCoupons.remove(coupon.code);
        /*  removedPatients.forEach(patient -> patientsByNames.remove(patient.name));*/
        return removedCoupon;
    }

    public boolean exist(Website w) {
        return this.websitesByDomain.containsKey(w.domain);
    }

    public boolean exist(Coupon c) {
        return this.couponsByCode.containsKey(c.code);

    }

    public Collection<Website> getSites() {
        return this.websitesByDomain.values();
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        return this.websitesWithCoupons.get(w.domain);

    }

    public void useCoupon(Website w, Coupon c) {
        boolean contains = getCouponsForWebsite(w).contains(c);
        if (!exist(w) || !exist(c) || !contains) {
            throw new IllegalArgumentException();
        }
        getCouponsForWebsite(w).remove(c);
        couponsByCode.remove(c.code);
    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        return couponsByCode.values().stream().sorted((c1, c2) -> {
                    int result = Integer.compare(c2.validity, c1.validity);

                    if (result == 0) {
                        result = Integer.compare(c2.discountPercentage, c1.discountPercentage);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {
        return null;
    }
}

