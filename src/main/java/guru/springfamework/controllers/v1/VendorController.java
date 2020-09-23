package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        return new VendorListDTO(vendorDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable  Long id)
    {
        return new ResponseEntity<>(vendorService.getVendorById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VendorDTO> saveVendor(@RequestBody VendorDTO vendorDTO)
    {
        return new ResponseEntity<>(vendorService.createNewVendor(vendorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO)
    {
        return new ResponseEntity<>(vendorService.updateVendor(id, vendorDTO), HttpStatus.ACCEPTED);
    }

}
