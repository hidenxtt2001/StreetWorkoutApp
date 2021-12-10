package com.example.streetworkout.Fragment.AccountFragment;

import static com.google.common.truth.Truth.assertThat;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.User.UserInfor;

import org.junit.Test;

public class WeekExerciseUserTest {
    private WeekExerciseUser _weekExerciseUser;

    @Test
    public void testCheckBoundaryValueForWeekExercise1() {
        _weekExerciseUser = new WeekExerciseUser(false, true, true, true, true, true, true);
        assertThat(_weekExerciseUser.checkFinish()).isFalse();
    }

    @Test
    public void testCheckBoundaryValueForWeekExercise2() {
        _weekExerciseUser = new WeekExerciseUser(true, true, true, true, true, true, false);
        assertThat(_weekExerciseUser.checkFinish()).isFalse();

    }

    @Test
    public void testCheckAbNormalTrueValueForWeekExercise() {
        _weekExerciseUser = new WeekExerciseUser(true, 5 * 4 == 20, true, true, true, true, true);
        assertThat(_weekExerciseUser.checkFinish()).isTrue();
    }

    @Test
    public void testCheckAbNormalFalseValueForWeekExercise() {
        UserInfor _user = new UserInfor("Bui Thien Nhan", "!!@gmail.com", "male");
        _weekExerciseUser = new WeekExerciseUser(true, true, true, true, AccountEditProfile.validateInputFields(_user), true, true);
        assertThat(_weekExerciseUser.checkFinish()).isFalse();
    }

    @Test
    public void testWeekExerciseDone() {
        _weekExerciseUser = new WeekExerciseUser();
        _weekExerciseUser.setAllTrue();
        assertThat(_weekExerciseUser.checkFinish()).isTrue();
    }

    @Test
    public void testWeekExerciseNotDone() {
        _weekExerciseUser = new WeekExerciseUser();
        _weekExerciseUser.setAllFalse();
        assertThat(_weekExerciseUser.checkFinish()).isFalse();
    }
}