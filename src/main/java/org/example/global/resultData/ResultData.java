package org.example.global.resultData;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultData<T> {
    private ResultCode code;
    private String msg;
    private T data;
    private boolean isSuccess;

    public static <T> ResultData<T> of(ResultCode code, String msg) {
        return new ResultData<>(code, msg, null, code.getCode().startsWith("S"));
    }
    public static <T> ResultData<T> of(ResultCode code, String msg, T data) {
        return new ResultData<>(code, msg, data, code.getCode().startsWith("S"));
    }
}
