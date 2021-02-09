package com.yanghu.manage.base;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/9
 */
public class BaseResult {
    @ApiModelProperty(
            value = "状态码",
            dataType = "Integer",
            example = "200",
            allowableValues = "200,400,401,403,404,500"
    )
    private Integer code;
    @ApiModelProperty(
            value = "返回的消息",
            dataType = "String"
    )
    private String msg;
    @ApiModelProperty(
            value = "操作的状态，true成功，false失败",
            dataType = "Boolean"
    )
    private boolean status;

    public static BaseResult build(boolean status) {
        return new BaseResult(status);
    }

    public static BaseResult build(Integer code, String msg, boolean status) {
        return new BaseResult(code, msg, status);
    }

    public static BaseResult build() {
        return new BaseResult();
    }

    public BaseResult() {
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
        this.status = true;
    }

    public BaseResult(boolean status) {
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
        this.status = true;
        if (!status) {
            this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            this.msg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            this.status = false;
        }

    }

    public BaseResult(Integer code, String msg, boolean status) {
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
        this.status = true;
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResult)) {
            return false;
        } else {
            BaseResult other = (BaseResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label39: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label39;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label39;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                if (this.isStatus() != other.isStatus()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseResult;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
         result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        result = result * 59 + (this.isStatus() ? 79 : 97);
        return result;
    }

    @Override
    public String toString() {
        return "BaseResult(code=" + this.getCode() + ", msg=" + this.getMsg() + ", status=" + this.isStatus() + ")";
    }
}
