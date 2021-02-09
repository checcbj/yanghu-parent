package com.yanghu.manage.base;


import java.util.Objects;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/9
 */

public class DataResult<DATA> extends BaseResult {
    private DATA data;

    private DataResult(DATA data) {
        this.data = data;
    }

    private DataResult(Integer code, String msg, boolean status) {
        super(code, msg, status);
    }

    private DataResult(Integer code, String msg, boolean status, DATA data) {
        super(code, msg, status);
        this.data = data;
    }

    public static <DATA> DataResult<DATA> build(Integer code, String msg, boolean status, DATA data) {
        return new DataResult(code, msg, status, data);
    }

    public static DataResult build(Integer code, String msg, boolean status) {
        return new DataResult(code, msg, status);
    }

    public static <DATA> DataResult<DATA> build(DATA data) {
        return new DataResult(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
                return false;
            } else {
                DataResult<?> that = (DataResult)o;
                return this.isStatus() == that.isStatus() && Objects.equals(this.getCode(), that.getCode()) && Objects.equals(this.getMsg(), that.getMsg()) && Objects.equals(this.getData(), that.getData());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode(), this.getCode(), this.getMsg(), this.isStatus(), this.getData()});
    }

    public DATA getData() {
        return this.data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResult(data=" + this.getData() + ")";
    }

    public DataResult() {
    }
}
