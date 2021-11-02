package com.example.streetworkout.Fragment.AccountFragment;


import static com.google.common.truth.Truth.assertThat;

import com.example.streetworkout.User.UserInfor;

import org.junit.Test;

public class AccountEditProfileTest {

    @Test
    public void testValidateNullName() {
        UserInfor _user = new UserInfor("", "nguyenxuanhung@gmail.com", "male");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testContainsSpecialCharInName() {
        UserInfor _user = new UserInfor("!!@@", "nguyenxuanhung@gmail.com", "male");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isTrue();
    }

    @Test
    public void testValidateNullEmail() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "", "male");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testValidateInvalidEmail() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "nguyenxuanhung@", "");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testValidateSpecialEmail() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "!!@gmail.com", "male");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testValidateNullGender() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "nguyenxuanhung@gmail.com", "");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testValidateInvalidGender() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "nguyenxuanhung@gmail.com", "a");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isFalse();
    }

    @Test
    public void testValidMaleUserInfor() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "nguyenxuanhung@gmail.com", "male");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isTrue();
    }

    @Test
    public void testValidFemaleUserInfor() {
        UserInfor _user = new UserInfor("Nguyen Thanh Hieu", "nguyenxuanhung@gmail.com", "female");
        boolean check = AccountEditProfile.validateInputFields(_user);
        assertThat(check).isTrue();
    }
}