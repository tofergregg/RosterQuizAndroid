package com.cocoadrillosoftware.rosterquiz;

import android.media.Image;

/**
 * Created by chrisgregg on 6/21/16.
 */
public class Student {
    String firstName;
    String lastName;
    String year;
    String notes;
    Image picture;

    void addDetails(String last, String first, String yr, String nts, Image pic){
        firstName = first;
        lastName = last;
        year = yr;
        notes = notes;
        picture = pic;
    }

    void printStudent(){
        System.out.println("Name: " + lastName + " " + firstName + ", year:" + year);
    }

    String commaName() {
        return lastName + ", " + firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
