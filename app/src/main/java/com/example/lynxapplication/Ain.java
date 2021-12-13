package com.example.lynxapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Ain extends AppCompatActivity
{
    public Resume resume;
    DrawerLayout DL;
    String error = "Error! Try Again";
    String m = "Data Has Been Stored";
    String clear = "Data Has Been Deleted";
    String msg = "Data Has Been Updated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ain);

        //Assign Variable
        DL = findViewById(R.id.drawer_layout);

        try
        {
            readData();
            setData();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        //delete Ain's data
        Button clearButton = (Button) findViewById(R.id.buttonDeleteAin);
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Create a new user with a first and last name
                Map<String, Object> resumefb = new HashMap<>();
                resumefb.put("Name", resume.name);
                resumefb.put("Phone", resume.phone);
                resumefb.put("Email", resume.email);
                resumefb.put("Address", resume.address);
                resumefb.put("Position", resume.position);
                resumefb.put("Social", Arrays.toString(resume.social));
                resumefb.put("Summary", resume.summary);
                resumefb.put("Skill", Arrays.toString(resume.skill));
                resumefb.put("Experience", Arrays.toString(resume.experience));
                resumefb.put("Education", Arrays.toString(resume.education));
                resumefb.put("Interest", resume.interest);

                db.collection("Resume").document("Ain").delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                FancyToast.makeText(Ain.this, clear, Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                FancyToast.makeText(Ain.this, error, Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        });
                //DBHelper helper = new DBHelper(Syahmina.this);
                //helper.clearDB();
                //spinner.setAdapter(null);
                //FancyToast.makeText(Syahmina.this, clear, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                try {
                    readData();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                clearData();
            }

        });

        //update Ain's Data
        Button updateButton = (Button) findViewById(R.id.buttonUpdateAin);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                EditText txtNameAin = (EditText) findViewById(R.id.txtNameAin);
                String myName = txtNameAin.getText().toString().trim();

                EditText txtPhoneAin = (EditText) findViewById(R.id.txtPhoneAin);
                String PhoneAin = txtPhoneAin.getText().toString().trim();

                EditText txtEmailAin = (EditText) findViewById(R.id.txtEmailAin);
                String EmailAin = txtEmailAin.getText().toString().trim();

                EditText txtAddressAin = (EditText) findViewById(R.id.txtAddressAin);
                String PostalAddressAin = txtAddressAin.getText().toString().trim();

                EditText txtPositionAin = (EditText) findViewById(R.id.txtPositionAin);
                String PositionAin = txtPositionAin.getText().toString().trim();

                EditText txtSummaryAin = (EditText) findViewById(R.id.txtSummaryAin);
                String SummaryAin = txtSummaryAin.getText().toString().trim();

                EditText textInterestAin = (EditText) findViewById(R.id.textInterestAin);
                String InterestAin = textInterestAin.getText().toString().trim();

                //update in firebase
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Create a new user with a first and last name
                Map<String, Object> resumefb = new HashMap<>();
                resumefb.put("Name", myName);
                resumefb.put("Phone", PhoneAin);
                resumefb.put("Email", EmailAin);
                resumefb.put("Address", PostalAddressAin);
                resumefb.put("Position", PositionAin);
                resumefb.put("Social", Arrays.toString(resume.social));
                resumefb.put("Summary", SummaryAin);
                resumefb.put("Skill", Arrays.toString(resume.skill));
                resumefb.put("Experience", Arrays.toString(resume.experience));
                resumefb.put("Education", Arrays.toString(resume.education));
                resumefb.put("Interest", InterestAin);

                db.collection("Resume").document("Ain").set(resumefb)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                FancyToast.makeText(Ain.this, msg, Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                FancyToast.makeText(Ain.this, error, Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        });

            }

        });
    }

    //store Ain data
    public void buttStoreAin(View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> resumefb = new HashMap<>();
        resumefb.put("Name", resume.name);
        resumefb.put("Phone", resume.phone);
        resumefb.put("Email", resume.email);
        resumefb.put("Address", resume.address);
        resumefb.put("Position", resume.position);
        resumefb.put("Social", Arrays.toString(resume.social));
        resumefb.put("Summary", resume.summary);
        resumefb.put("Skill", Arrays.toString(resume.skill));
        resumefb.put("Experience", Arrays.toString(resume.experience));
        resumefb.put("Education", Arrays.toString(resume.education));
        resumefb.put("Interest", resume.interest);

        db.collection("Resume").document("Ain").set(resumefb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        FancyToast.makeText(Ain.this, m, Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(Ain.this, error, Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                });

    }

    //set value for each attribute in text and spinner
    private void setData()
    {
        EditText txtNameAin = (EditText) findViewById(R.id.txtNameAin);
        txtNameAin.setText(resume.name);

        EditText txtPhoneAin = (EditText) findViewById(R.id.txtPhoneAin);
        txtPhoneAin.setText(resume.phone);

        EditText txtEmailAin = (EditText) findViewById(R.id.txtEmailAin);
        txtEmailAin.setText(resume.email);

        EditText txtAddressAin = (EditText) findViewById(R.id.txtAddressAin);
        txtAddressAin.setText(resume.address);

        EditText txtPositionAin = (EditText) findViewById(R.id.txtPositionAin);
        txtPositionAin.setText(resume.position);

        EditText txtSocialAin = (EditText) findViewById(R.id.txtSocialsAin);
        txtSocialAin.setText(resume.social[0]);

        EditText txtSummaryAin = (EditText) findViewById(R.id.txtSummaryAin);
        txtSummaryAin.setText(resume.summary);

        Spinner spinnerSkillsAin = (Spinner) findViewById(R.id.spinnerSkillsAin);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSkillsAin.setAdapter(skillAdapter);

        EditText txtExperienceAin = (EditText) findViewById(R.id.txtExperienceAin);
        txtExperienceAin.setText(resume.experience[0]);

        Spinner spinnerEduAin = (Spinner) findViewById(R.id.spinnerEduAin);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEduAin.setAdapter(educationAdapter);

        EditText textInterestAin = (EditText) findViewById(R.id.textInterestAin);
        textInterestAin.setText(resume.interest);
    }

    //read data from xml to java class
    private void readData() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Ain.xml"));

        doc.getDocumentElement().normalize();

        String myName = doc.getElementsByTagName("name").item(0).getTextContent();
        String myPhone = doc.getElementsByTagName("phone").item(0).getTextContent();
        String myEmail = doc.getElementsByTagName("email").item(0).getTextContent();
        String myAddress = doc.getElementsByTagName("address").item(0).getTextContent();
        String myPosition = doc.getElementsByTagName("position").item(0).getTextContent();
        String mySummary = doc.getElementsByTagName("summary").item(0).getTextContent();
        String myInterest = doc.getElementsByTagName("interest").item(0).getTextContent();

        //social
        NodeList mySocial = doc.getElementsByTagName("social");
        String[] listSocial = new String[mySocial.getLength()];
        for (int  i = 0; i < mySocial.getLength(); i++)
        {
            listSocial[i] = mySocial.item(i).getTextContent();
        }
        //Skills
        NodeList mySkills = doc.getElementsByTagName("skill");
        String[] listSkills = new String[mySkills.getLength()];
        for (int  i = 0; i < mySkills.getLength(); i++)
        {
            listSkills[i] = mySkills.item(i).getTextContent();
        }
        //Experience
        NodeList myExperience = doc.getElementsByTagName("experience");
        String[] listExperience = new String[myExperience.getLength()];
        for (int  i = 0; i < myExperience.getLength(); i++)
        {
            listExperience [i] = myExperience.item(i).getTextContent();
        }
        //Education
        NodeList myEducation = doc.getElementsByTagName("education");
        String[] listEducation = new String[myEducation.getLength()];
        for (int  i = 0; i < myEducation.getLength(); i++)
        {
            listEducation[i] = myEducation.item(i).getTextContent();
        }

        resume = new Resume(myName, myPhone, myEmail, myAddress, myPosition,listSocial, mySummary,
                listSkills, listExperience, listEducation, myInterest);
    }

    //clear text and spinner
    private void clearData()
    {
        EditText txtNameAin = (EditText) findViewById(R.id.txtNameAin);
        txtNameAin.setText("");

        EditText txtPhoneAin = (EditText) findViewById(R.id.txtPhoneAin);
        txtPhoneAin.setText("");

        EditText txtEmailAin = (EditText) findViewById(R.id.txtEmailAin);
        txtEmailAin.setText("");

        EditText txtAddressAin = (EditText) findViewById(R.id.txtAddressAin);
        txtAddressAin.setText("");

        EditText txtPositionAin = (EditText) findViewById(R.id.txtPositionAin);
        txtPositionAin.setText("");

        EditText txtSocialAin = (EditText) findViewById(R.id.txtSocialsAin);
        txtSocialAin.setText("");

        EditText txtSummaryAin = (EditText) findViewById(R.id.txtSummaryAin);
        txtSummaryAin.setText("");

        Spinner spinnerSkillsAin = (Spinner) findViewById(R.id.spinnerSkillsAin);
        spinnerSkillsAin.setAdapter(null);

        EditText txtExperienceAin = (EditText) findViewById(R.id.txtExperienceAin);
        txtExperienceAin.setText("");

        Spinner spinnerEduAin = (Spinner) findViewById(R.id.spinnerEduAin);
        spinnerEduAin.setAdapter(null);

        EditText textInterestAin = (EditText) findViewById(R.id.textInterestAin);
        textInterestAin.setText("");
    }

    public static void openDrawer(DrawerLayout DL)
    {
        //Open Navigation Drawer Layout
        DL.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout DL)
    {
        //Close Navigation Drawer Layout
        if(DL.isDrawerOpen(GravityCompat.START))
        {
            DL.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMenu(View view)
    {
        openDrawer(DL); //Open Navigation Drawer
    }

    public void ClickOut()
    {
        closeDrawer(DL); //Close Drawer
    }

    public void ClickHome(View view) throws IOException, SAXException, ParserConfigurationException
    {
        HomeActivity.redActivity(this, HomeActivity.class); //redirect to Main activity
    }

    public void ClickAlya(View view) throws IOException, SAXException, ParserConfigurationException //open Shafiqah Alya Navigation
    {
        String msg="Shafiqah Alya";
        HomeActivity.redActivity(this, Alya.class); //redirect to Alya activity
        Intent intent = new Intent(this, Alya.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickMadihah(View view) //open Madihah Hannani Navigation
    {
        String msg="Madihah Hannani";
        HomeActivity.redActivity(this,Madihah.class); //redirect to madihah activity
        Intent intent = new Intent(this, Madihah.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAin(View view) //open Ain Emylia Navigation
    {
        String msg="Ain Emylia";
        recreate(); //recreate Ain activity
        Intent intent = new Intent(this, Ain.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickSyahmina(View view) //open Syahmina Navigation
    {
        String msg="Nurul Syahmina";
        HomeActivity.redActivity(this,Syahmina.class); //redirect to syahmina activity
        Intent intent = new Intent(this, Syahmina.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAdlina(View view) //open Wan Adlina Navigation
    {
        String msg="Wan Adlina";
        HomeActivity.redActivity(this,Adlina.class); //redirect to adlina activity
        Intent intent = new Intent(this, Adlina.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickLogout(View view)
    {
        //close app
        logout(this);
    }

    public static void logout(Activity activity)
    {
        //logout confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //Show Dialog
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(DL);
    }
}