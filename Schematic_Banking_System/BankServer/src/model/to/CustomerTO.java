package model.to;

import java.io.Serializable;

public class CustomerTO  implements Serializable {

    private Long id;
    private String name;
    private String family;
    private String fatherName;
    private long nationalId;
    private long birthCertificateId;
    private long birthDate;
    private String birthPlace;
    private String documentPictureAddress;
    private CustomerInformationTO customerInformationTO;

    public CustomerInformationTO getCustomerInformationTO() {
        return customerInformationTO;
    }

    public void setCustomerInformationTO(CustomerInformationTO customerInformationTO) {
        this.customerInformationTO = customerInformationTO;
    }

    public String getDocumentPictureAddress() {
        return documentPictureAddress;
    }

    public void setDocumentPictureAddress(String documentPictureAddress) {
        this.documentPictureAddress = documentPictureAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public long getNationalId() {
        return nationalId;
    }

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

    public long getBirthCertificateId() {
        return birthCertificateId;
    }

    public void setBirthCertificateId(long birthCertificateId) {
        this.birthCertificateId = birthCertificateId;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
