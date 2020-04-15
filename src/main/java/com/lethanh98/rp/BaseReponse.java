package com.lethanh98.rp;

import lombok.Data;

@Data
public class BaseReponse<T> {
    Integer status;
    T data;
}
