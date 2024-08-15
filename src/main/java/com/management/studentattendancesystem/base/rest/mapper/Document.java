package com.management.studentattendancesystem.base.rest.mapper;

public class Document {

    private String docName;

    private String docId;

    private String base64Code;

    private String status;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getBase64Code() {
        return base64Code;
    }

    public void setBase64Code(String base64Code) {
        this.base64Code = base64Code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docName='" + docName + '\'' +
                ", docId='" + docId + '\'' +
                ", base64Code='" + base64Code + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
