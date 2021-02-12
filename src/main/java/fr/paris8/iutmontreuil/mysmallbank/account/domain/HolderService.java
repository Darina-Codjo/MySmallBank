package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.BusinessException;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HolderService {

    private final HolderRepository holderRepository;

    public HolderService(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    public Holder getHolder(String id) {
        return holderRepository.getHolder(id);
    }

    public List<Holder> listHolders() {
        return holderRepository.listHolders();
    }

    public Holder create(Holder holder) {
        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        return holderRepository.create(holder);
    }
    public Holder update(String id, Holder newHolder){
        Holder oldHolder = getHolder(id);
        Holder holder = oldHolder.merge(newHolder);
        return holderRepository.save(holder);
    }

    public Holder updateAddress(String id, Address address){
        Holder holder = getHolder(id);
        Holder holderChanged = holder.updateAddress(address);
        return holderRepository.save(holderChanged);
    }

    private ValidationError validateBirthdayDate(Holder holder, LocalDate localDate){
        if(holder.getBirthDate() == null ) {
            return new ValidationError("holder birthday date empty or null");
        }
        if (LocalDate.now().compareTo(holder.getBirthDate()) < 18) {
            return new ValidationError("underaged holder");
        }
        return null;
    }

    private List<ValidationError> validateHolder(Holder holder) {
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        if (holder.getFirstName() == null || holder.getFirstName().isEmpty()){
            validationErrors.add(new ValidationError("holder FirstName empty or null"));
        }
        if (holder.getLastName() == null || holder.getLastName().isEmpty()){
            validationErrors.add(new ValidationError("holder LastName empty or null"));
        }
        if (validateBirthdayDate(holder, LocalDate.now()) != null){
            validationErrors.add(validateBirthdayDate(holder, LocalDate.now()));
        }

        if (holder.getAddress().getCountry() == null || holder.getAddress().getCountry().isEmpty()){
            validationErrors.add(new ValidationError(("holder country empty or null")));
        }
        if (holder.getAddress().getCity() == null || holder.getAddress().getCity().isEmpty()){
            validationErrors.add(new ValidationError("holder city empty or null"));
        }
        if (holder.getAddress().getStreet() == null || holder.getAddress().getStreet().isEmpty()){
            validationErrors.add(new ValidationError("holder street empty or null"));
        }
        if (holder.getAddress().getZipCode() == null || holder.getAddress().getZipCode().isEmpty()){
            validationErrors.add(new ValidationError("holder zipCode empty or null"));
        }
        return validationErrors;
    }

    public void delete(String id) {
        Holder holder = getHolder(id);
        if (holder.getAccounts() == null || holder.getAccounts().isEmpty()) {
            holderRepository.delete(id);
        }
    }
}
