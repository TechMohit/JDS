package com.jds.varadhi.jds.Model;

/**
 * Created by varadhi on 24/5/18.
 */

public class MemberAddModel {

    public MemberAddModel booth_details, personal_details,
            id_proof_details, address_details;


    private String str_boothState, str_boothLokshabha, str_boothAssembly,str_PanchayatWard,
    str_pollingNumber,str_pollingName;

    private String str_profileImage,str_FirstName, str_Employee_MiddleName,str_Employee_LastName,
            str_Employee_gender,str_Employee_BirthPlace,str_Employee_DOB,str_Employee_MobileNumber,
            str_Employee_Nationality,str_Employee_EmailID,str_education,str_religion,str_community,
    str_caste,str_subcaste,str_designation,str_fatherName,str_motherName,str_marriedStatus,str_spouseName,
    str_marriageAnn,str_numberChildren ,age;

    private String str_AadharCardNumber, str_PancardNumber,str_VoterCardNumber,str_PassportNumber;


    public MemberAddModel(String str_AadharCardNumber, String str_PancardNumber, String str_VoterCardNumber, String str_PassportNumber) {
        this.str_AadharCardNumber = str_AadharCardNumber;
        this.str_PancardNumber = str_PancardNumber;
        this.str_VoterCardNumber = str_VoterCardNumber;
        this.str_PassportNumber = str_PassportNumber;
    }

    public MemberAddModel(MemberAddModel booth_details, MemberAddModel personal_details, MemberAddModel address_details) {
        this.booth_details = booth_details;
        this.personal_details = personal_details;
        this.address_details = address_details;
    }



    public MemberAddModel(MemberAddModel booth_details, MemberAddModel personal_details, MemberAddModel address_details, MemberAddModel id_proof_details) {
        this.id_proof_details = id_proof_details;
        this.booth_details = booth_details;
        this.personal_details = personal_details;
        this.address_details = address_details;
    }



    public MemberAddModel(String str_FirstName, String str_Employee_MiddleName, String str_Employee_LastName,
                          String str_Employee_gender, String str_Employee_BirthPlace,
                          String str_Employee_DOB, String age, String str_Employee_MobileNumber,
                          String str_Employee_Nationality, String str_Employee_EmailID,
                          String str_education, String str_religion, String str_community, String str_caste, String str_subcaste, String str_designation, String str_fatherName, String str_motherName, String str_marriedStatus, String str_spouseName, String str_marriageAnn, String str_numberChildren) {
        this.str_FirstName = str_FirstName;
        this.str_Employee_MiddleName = str_Employee_MiddleName;
        this.str_Employee_LastName = str_Employee_LastName;
        this.str_Employee_gender = str_Employee_gender;
        this.str_Employee_BirthPlace = str_Employee_BirthPlace;
        this.str_Employee_DOB = str_Employee_DOB;
        this.str_Employee_MobileNumber = str_Employee_MobileNumber;
        this.str_Employee_Nationality = str_Employee_Nationality;
        this.str_Employee_EmailID = str_Employee_EmailID;
        this.str_education = str_education;
        this.str_religion = str_religion;
        this.str_community = str_community;
        this.str_caste = str_caste;
        this.str_subcaste = str_subcaste;
        this.str_designation = str_designation;
        this.str_fatherName = str_fatherName;
        this.str_motherName = str_motherName;
        this.str_marriedStatus = str_marriedStatus;
        this.str_spouseName = str_spouseName;
        this.str_marriageAnn = str_marriageAnn;
        this.str_numberChildren = str_numberChildren;
        this.age =age;
    }

    public MemberAddModel(String str_boothState, String str_boothLokshabha, String str_boothAssembly, String str_PanchayatWard, String str_pollingNumber, String str_pollingName) {
        this.str_boothState = str_boothState;
        this.str_boothLokshabha = str_boothLokshabha;
        this.str_boothAssembly = str_boothAssembly;
        this.str_PanchayatWard = str_PanchayatWard;
        this.str_pollingNumber = str_pollingNumber;
        this.str_pollingName = str_pollingName;
    }

    public String  str_Employee_PresentDoorNumber,
            str_Employee_PresentStreet, str_Employee_PresentLocality,
             str_Employee_PresentLandmark,str_Employee_PresentPincode,
             str_Employee_PresentCity,str_Employee_PresentState,
             str_Employee_PresentCountry,str_Employee_PresentContact,
             str_Employee_PermanentDoorNumber, str_Employee_PermanentStreet,
             str_Employee_PermanentLocality, str_Employee_PermanentLandmark,
             str_Employee_PermanentPincode, str_Employee_PermanentCity,
             str_Employee_PermanentState, str_Employee_PermanentCountry,
             str_Employee_PermanentContact;







    public MemberAddModel(String str_Employee_PresentDoorNumber,
                          String str_Employee_PresentStreet, String str_Employee_PresentLocality,
                          String str_Employee_PresentLandmark, String str_Employee_PresentPincode,
                          String str_Employee_PresentCity, String str_Employee_PresentState,
                          String str_Employee_PresentCountry, String str_Employee_PresentContact,
                          String str_Employee_PermanentDoorNumber, String str_Employee_PermanentStreet,
                          String str_Employee_PermanentLocality, String str_Employee_PermanentLandmark,
                          String str_Employee_PermanentPincode, String str_Employee_PermanentCity,
                          String str_Employee_PermanentState, String str_Employee_PermanentCountry,
                          String str_Employee_PermanentContact){

        this.str_Employee_PresentDoorNumber = str_Employee_PresentDoorNumber;
        this.str_Employee_PresentStreet = str_Employee_PresentStreet;
        this.str_Employee_PresentLocality = str_Employee_PresentLocality;
        this.str_Employee_PresentLandmark = str_Employee_PresentLandmark;
        this.str_Employee_PresentPincode = str_Employee_PresentPincode;
        this.str_Employee_PresentCity = str_Employee_PresentCity;
        this.str_Employee_PresentState = str_Employee_PresentState;
        this.str_Employee_PresentCountry = str_Employee_PresentCountry;
        this.str_Employee_PresentContact = str_Employee_PresentContact;
        this.str_Employee_PermanentDoorNumber = str_Employee_PermanentDoorNumber;
        this.str_Employee_PermanentStreet = str_Employee_PermanentStreet;
        this.str_Employee_PermanentLocality = str_Employee_PermanentLocality;
        this.str_Employee_PermanentLandmark = str_Employee_PermanentLandmark;
        this.str_Employee_PermanentPincode = str_Employee_PermanentPincode;
        this.str_Employee_PermanentCity = str_Employee_PermanentCity;
        this.str_Employee_PermanentState = str_Employee_PermanentState;
        this.str_Employee_PermanentCountry = str_Employee_PermanentCountry;
        this.str_Employee_PermanentContact = str_Employee_PermanentContact;



    }

    public String getStr_AadharCardNumber() {
        return str_AadharCardNumber;
    }

    public String getStr_PancardNumber() {
        return str_PancardNumber;
    }

    public String getStr_VoterCardNumber() {
        return str_VoterCardNumber;
    }

    public String getStr_PassportNumber() {
        return str_PassportNumber;
    }






    /*--------------------------------Education Model-------------------------------------*/

    public String getAge() {
        return age;
    }

    public String getStr_boothState() {
        return str_boothState;
    }

    public String getStr_boothLokshabha() {
        return str_boothLokshabha;
    }

    public String getStr_boothAssembly() {
        return str_boothAssembly;
    }

    public String getStr_PanchayatWard() {
        return str_PanchayatWard;
    }

    public String getStr_pollingNumber() {
        return str_pollingNumber;
    }

    public String getStr_pollingName() {
        return str_pollingName;
    }

    public String getStr_profileImage() {
        return str_profileImage;
    }

    public String getStr_FirstName() {
        return str_FirstName;
    }

    public String getStr_Employee_MiddleName() {
        return str_Employee_MiddleName;
    }

    public String getStr_Employee_LastName() {
        return str_Employee_LastName;
    }

    public String getStr_Employee_gender() {
        return str_Employee_gender;
    }

    public String getStr_Employee_BirthPlace() {
        return str_Employee_BirthPlace;
    }

    public String getStr_Employee_DOB() {
        return str_Employee_DOB;
    }

    public String getStr_Employee_MobileNumber() {
        return str_Employee_MobileNumber;
    }

    public String getStr_Employee_Nationality() {
        return str_Employee_Nationality;
    }

    public String getStr_Employee_EmailID() {
        return str_Employee_EmailID;
    }

    public String getStr_education() {
        return str_education;
    }

    public String getStr_religion() {
        return str_religion;
    }

    public String getStr_community() {
        return str_community;
    }

    public String getStr_caste() {
        return str_caste;
    }

    public String getStr_subcaste() {
        return str_subcaste;
    }

    public String getStr_designation() {
        return str_designation;
    }

    public String getStr_fatherName() {
        return str_fatherName;
    }

    public String getStr_motherName() {
        return str_motherName;
    }

    public String getStr_marriedStatus() {
        return str_marriedStatus;
    }

    public String getStr_spouseName() {
        return str_spouseName;
    }

    public String getStr_marriageAnn() {
        return str_marriageAnn;
    }

    public String getStr_numberChildren() {
        return str_numberChildren;
    }
}
