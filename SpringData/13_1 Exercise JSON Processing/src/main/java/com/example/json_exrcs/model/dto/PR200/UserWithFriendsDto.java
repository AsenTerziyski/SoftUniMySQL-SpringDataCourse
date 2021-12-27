package com.example.json_exrcs.model.dto.PR200;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserWithFriendsDto {
    @Expose
    private String lastName;
    @Expose
    private List<UsersFriendDto> friends;

    public UserWithFriendsDto() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UsersFriendDto> getFriends() {
        return friends;
    }

    public void setFriends(List<UsersFriendDto> friends) {
        this.friends = friends;
    }
}
