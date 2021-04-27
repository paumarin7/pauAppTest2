package com.example.readbooks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.internal.viewaction.RatingBarActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.*;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.*;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem;
import static com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.*;
import static com.schibsted.spain.barista.interaction.BaristaSeekBarInteractions.*;
import static com.schibsted.spain.barista.interaction.BaristaSpinnerInteractions.*;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testRegister(){

        onView(withId(R.id.registerButton)).check(matches(isDisplayed()));
        onView(withId(R.id.registerUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.registerPassword)).check(matches(isDisplayed()));

        writeTo(R.id.registerUsername, "Pau");
        writeTo(R.id.registerPassword, "Pau");
        clickOn(R.id.registerButton);
    }

    @Test
    public void testLogin(){


        testRegister();
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.loginUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.loginPassword)).check(matches(isDisplayed()));

        writeTo(R.id.loginUsername, "Pau");
        writeTo(R.id.loginPassword, "Pau");
        clickOn(R.id.loginButton);
    }

    @Test
    public void navigationTest(){
        testLogin();
        clickListItem(R.id.recyclerview, 3);
        clickOn(R.id.returnButtonFragment);
        clickOn(R.id.addNewList);
        clickOn(R.id.newReturnButtonFragment);


    }

    @Test
    public void createBook(){
        testLogin();
        clickOn(R.id.addNewList);
        writeTo(R.id.newBookName, "El Increible professor");
        writeTo(R.id.newAuthorName, "Jordi");
        clickSpinnerItem(R.id.newSpinnerBook, 2);
        clickOn(R.id.newAddButton);


    }

    @Test
    public void deleteBook(){
        testLogin();
        onView(withId(R.id.recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT,
                        Press.FINGER)));

    }

    @Test
    public void modifyBook(){
        testLogin();
        clickListItem(R.id.recyclerview, 3);
        clickSpinnerItem(R.id.spinnerBook, 1);
        clickOn(R.id.returnButtonFragment);



    }





}