package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HolderRepository {

    private final HolderDAO holderDAO;

    public HolderRepository(HolderDAO holderDAO) {
        this.holderDAO = holderDAO;
    }

    public Holder getHolder(String id) {
        HolderEntity holder = holderDAO.getOne(id);
        return HolderMapper.toHolder(holder);
    }

    public List<Holder> listHolders() {
        return holderDAO.findAll().stream().map(HolderMapper::toHolder).collect(Collectors.toList());
    }

    public Holder create(Holder holder) {
        HolderEntity entityToSave = HolderMapper.toEntity(holder);
        HolderEntity createdHolder = holderDAO.save(entityToSave);
        return HolderMapper.toHolder(createdHolder);
    }

    public Holder save(Holder holder){
        HolderEntity entityToSave = HolderMapper.toEntity(holder);
        //HolderEntity createHolder = holderDAO.saveAndFlush(entityToSave);
        HolderEntity createHolder = holderDAO.save(entityToSave);
        return HolderMapper.toHolder(createHolder);
    }

    public void delete(String id) {
        holderDAO.deleteById(id);
    }
}
