package com.lethanh98.rp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpFactory {
    public <T> BaseReponse<T> getReponse(T t) {
        BaseReponse<T> baseReponse = new BaseReponse<>();
        if (t == null) {
            baseReponse.setStatus(1);
            return baseReponse;
        } else {
            baseReponse.setStatus(200);
            baseReponse.setData(t);
            return baseReponse;
        }
    }
}
