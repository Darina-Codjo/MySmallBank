package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.HolderService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holders")
public class HolderController {

    private final HolderService holderService;

    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping
    public List<HolderDTO> findAll() {
        List<Holder> holders = holderService.listHolders();
        return HolderMapper.toDto(holders);
    }

    @GetMapping("/{id}")
    public HolderDTO getOne(@PathVariable("id") String id) {
        Holder holder = holderService.getHolder(id);
        return HolderMapper.toDTO(holder);
    }

    @PostMapping
    public HolderDTO create(@RequestBody HolderDTO holderDTO) {
        Holder holder = holderService.create(HolderMapper.toHolder(holderDTO));
        return HolderMapper.toDTO(holder);
    }

    @PatchMapping("/{id}")
    public HolderDTO update(@RequestBody HolderDTO holderDTO,@PathVariable("id") String id){
        Holder holder = holderService.update(id,HolderMapper.toHolder((holderDTO)));
        return HolderMapper.toDTO(holder);
    }

    @PutMapping("/{id}/address")
    public HolderDTO updateAddress(@PathVariable("id") String id, @RequestBody AddressDTO addressDTO) {
        Holder holder = holderService.updateAddress(id,HolderMapper.toAddress(addressDTO));
        return HolderMapper.toDTO(holder);
    }
    /*@PutMapping("/{id}")
    public HolderDTO update(@PathVariable("id") String id, @RequestBody HolderDTO holderDTO){
        Holder

    }*/

    @DeleteMapping("/{id}")
    public void delete (@PathVariable("id") String id){
        holderService.delete(id);
    }
}
