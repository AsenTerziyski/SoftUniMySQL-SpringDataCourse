package com.example.json_exrcs.model.dto.PR200;

import com.google.gson.annotations.Expose;

public class UsersFriendDto {
    @Expose
    private String lastName;

    public UsersFriendDto() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
