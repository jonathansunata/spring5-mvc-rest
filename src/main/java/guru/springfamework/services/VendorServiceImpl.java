package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;


    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());

    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> vendor =  vendorRepository.findById(id);
        return vendor
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + vendor.get().getId());
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    public VendorDTO createNewVendor(VendorDTO vendorDTO)
    {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnVendorDTO(vendor);
    }

    public VendorDTO saveAndReturnVendorDTO(Vendor vendor)
    {
        Vendor saveVendor = vendorRepository.save(vendor);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(saveVendor);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + saveVendor.getId());

        return vendorDTO;
    }

    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO)
    {
        Vendor vendor = getVendor(id);

        return saveAndReturnVendorDTO(vendor);
    }

    public void deleteVendor(Long id)
    {
        Vendor vendor = getVendor(id);

        vendorRepository.delete(vendor);
    }

    private Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
