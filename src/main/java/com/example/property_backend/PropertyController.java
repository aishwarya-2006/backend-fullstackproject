package com.example.property_backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyRepository repo;

    public PropertyController(PropertyRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/properties")
    public Property saveProperty(@RequestBody Property property) {
        return repo.save(property);
    }

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return repo.findAll();
    }

    @GetMapping("/properties/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @PutMapping("/properties/{id}")
    public Property updateProperty(@PathVariable Long id, @RequestBody Property newData) {

        Property property = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setOwnerName(newData.getOwnerName());
        property.setLocation(newData.getLocation());
        property.setPropertyType(newData.getPropertyType());
        property.setPropertyAge(newData.getPropertyAge());
        property.setCurrentValue(newData.getCurrentValue());
        property.setBudget(newData.getBudget());

        return repo.save(property);
    }

    @DeleteMapping("/properties/{id}")
    public String deleteProperty(@PathVariable Long id) {

        Property property = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        repo.delete(property);
        return "Property Deleted Successfully";
    }

    @PostMapping("/recommendations")
    public Map<String, Object> getRecommendations(@RequestBody Map<String, Object> data) {

        String location = (String) data.get("location");
        String propertyType = (String) data.get("propertyType");

        Map<String, Object> response = new HashMap<>();
        response.put("location", location);
        response.put("propertyType", propertyType);
        response.put("suggestion", "Property in " + location + " can be improved with smart renovation for better ROI");

        return response;
    }
}
