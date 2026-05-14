package br.com.altoalegremercado.aamuserapi.controller.dto;

import javax.validation.constraints.NotBlank;
import br.com.altoalegremercado.aamuserapi.validation.CNPJ;

public class StoreDTO {

    @NotBlank
    private String name;

    @CNPJ
    private String cnpj;

    private String phone;
    private String email;
    private Boolean active;
    private Long addressId;
    private String street;
    private String zipCode;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String country;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
