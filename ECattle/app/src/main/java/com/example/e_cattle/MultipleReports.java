package com.example.e_cattle;

public class MultipleReports {
    String ReportID;
    String Latitude;
    String Longitude;
    String ImageURL;
    String  Status;


    public void setStatus(String status) {
        Status = status;
    }

    public void setReportID(String reportID) {
        ReportID = reportID;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getReportID() {
        return ReportID;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getStatus() { return Status; }


    public MultipleReports() {
        ReportID = "";
        Latitude = "";
        Longitude = "";
        ImageURL = "";
        Status="";
    }

    public MultipleReports(String reportID, String latitude, String longitude, String imageURL,String status) {
        ReportID = reportID;
        Latitude = latitude;
        Longitude = longitude;
        ImageURL = imageURL;
        Status=status;
    }
}
