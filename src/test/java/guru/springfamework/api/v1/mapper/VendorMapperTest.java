package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final long ID = 1L;
    public static final String HOME_FRUITS = "Home Fruits";
    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO()
    {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(HOME_FRUITS);

        VendorDTO dto = vendorMapper.vendorToVendorDTO(vendor);

       assertEquals(HOME_FRUITS, dto.getName());

    }
}
