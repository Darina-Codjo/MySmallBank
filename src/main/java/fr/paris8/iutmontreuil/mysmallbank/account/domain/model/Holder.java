package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;

import java.time.LocalDate;
import java.util.List;

public class Holder {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final Address address;
    private final LocalDate birthDate;

    private final List<Account> accounts;


    public Holder(String id, String lastName, String firstName, Address address, LocalDate birthDate, List<Account> accounts) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.birthDate = birthDate;
        this.accounts = accounts;
    }

    public Holder merge (Holder newHolder){
        String lastName = newHolder.getLastName() == null ? this.getLastName() : newHolder.getLastName();
        String firstName = newHolder.getFirstName() == null ? this.getFirstName() : newHolder.getFirstName();
        LocalDate birthDate = newHolder.getBirthDate() == null ? this.getBirthDate() : newHolder.getBirthDate();
        String street = newHolder.getAddress().getStreet() == null ? this.getAddress().getStreet() : newHolder.getAddress().getStreet();
        String zipCode = newHolder.getAddress().getZipCode() == null ? this.getAddress().getZipCode() : newHolder.getAddress().getZipCode();
        String city = newHolder.getAddress().getCity() == null ? this.getAddress().getCity() : newHolder.getAddress().getCity();
        String country = newHolder.getAddress().getCountry() == null ? this.getAddress().getCountry() : newHolder.getAddress().getCountry();
        Address address = new Address(street,zipCode,city,country);
        return new Holder(this.id,lastName,firstName,address,birthDate,this.accounts);
    }

    public Holder updateAddress(Address address){
        return  new Holder(this.id,this.lastName,this.firstName,address,this.birthDate,this.accounts);
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
