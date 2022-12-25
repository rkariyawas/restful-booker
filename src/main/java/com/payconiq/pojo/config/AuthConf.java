package com.payconiq.pojo.config;


import com.payconiq.services.utils.CommonUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class AuthConf {
    private String username;
    @Setter(AccessLevel.NONE)
    private String password;

    /**
     * This method set the decoded password
     * @return
     */
    public void setPassword(String password) {
        this.password = CommonUtils.passwordDecode(password);
    }
}
