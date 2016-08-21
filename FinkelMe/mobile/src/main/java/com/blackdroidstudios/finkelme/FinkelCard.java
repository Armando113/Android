package com.blackdroidstudios.finkelme;

import android.graphics.Bitmap;

/**
 * Created by Armando on 7/6/2015.
 */
public class FinkelCard
{
    //This is the info of the actual cards. Try to keep it to simple variables, for it to be compatible with other Operating Systems
    //Parameters for the Card

    //Card information
    private String fullName;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private String profession;
    private String company;
    private String website;
    private Bitmap profilePicture;
    private Bitmap companyLogo;

    //Layout type of the Card
    protected int layoutType;

    //Constructors
    public FinkelCard()
    {

    }

    //Get Methods
    public String getFullName()
    {
        return fullName;
    }
    public String getEmailAddress()
    {
        return emailAddress;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getAddress()
    {
        return address;
    }
    public String getProfession()
    {
        return profession;
    }
    public String getCompany()
    {
        return company;
    }
    public String getWebsite()
    {
        return website;
    }
    public Bitmap getProfilePicture()
    {
        return profilePicture;
    }
    public Bitmap getCompanyLogo()
    {
        return companyLogo;
    }
    public int getLayoutType()
    {
        return layoutType;
    }

    //Set Methods
    public void setFullName(String newName)
    {
        fullName = newName;
    }
    public void setEmailAddress(String newEmail)
    {
        emailAddress = newEmail;
    }
    public void setPhoneNumber(String newPhone)
    {
        phoneNumber = newPhone;
    }
    public void setAddress(String newAddress)
    {
        address = newAddress;
    }
    public void setProfession(String newProfession)
    {
        profession = newProfession;
    }
    public void setCompany(String newCompany)
    {
        company = newCompany;
    }
    public void setWebsite(String newWebsite)
    {
        website = newWebsite;
    }
    public void setProfilePicture(Bitmap newProfPic)
    {
        profilePicture = newProfPic;
    }
    public void setCompanyLogo(Bitmap newLogo)
    {
        companyLogo = newLogo;
    }
    public void setLayoutType(int newLayout)
    {
        layoutType = newLayout;
    }
}
