package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class VendorServiceImplTest {

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {

        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Home Fruits");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Home Fruits ltd");

        Mockito.when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2, vendorDTOS.size());

        Mockito.verify(vendorRepository).findAll();
    }

    @Test
    public void getVendorById() {

        Vendor vendor1 = new Vendor();
        vendor1.setId(1l);
        vendor1.setName("Home Fruits");

        Mockito.when(vendorRepository.findById(1l)).thenReturn(Optional.of(vendor1));

        VendorDTO vendorDTO = vendorService.getVendorById(1l);

        assertEquals("Home Fruits", vendorDTO.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound()
    {
        Mockito.when(vendorRepository.findById(1l)).thenThrow(ResourceNotFoundException.class);

        VendorDTO vendorDTO = vendorService.getVendorById(1l);
        assertNull(vendorDTO);
    }

    @Test
    public void createNewVendor(){

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Home Fruits");

        Mockito.when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(vendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendor.getName());
        VendorDTO savedVendorDto = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendor.getName(), savedVendorDto.getName());
        assertEquals("/api/v1/vendors/1", savedVendorDto.getVendorUrl());

        Mockito.verify(vendorRepository, Mockito.times(1)).save(Mockito.any(Vendor.class));
    }

    @Test
    public void updateVendor()
    {

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Home Fruits");

        Mockito.when(vendorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(vendor));
        Mockito.when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(vendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendor.getName());
        VendorDTO savedVendorDto = vendorService.updateVendor(1L, vendorDTO);

        assertEquals(vendor.getName(), savedVendorDto.getName());
        assertEquals("/api/v1/vendors/1", savedVendorDto.getVendorUrl());

        Mockito.verify(vendorRepository, Mockito.times(1)).save(Mockito.any(Vendor.class));
    }
}