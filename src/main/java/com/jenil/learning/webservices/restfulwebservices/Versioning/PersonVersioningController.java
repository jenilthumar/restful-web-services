package com.jenil.learning.webservices.restfulwebservices.Versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping(value="/person/param", params = "version=1")
    public PersonV1 paramV1(){
        return new PersonV1("Jenil Thummar");
    }

    @GetMapping(value="/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("Jenil", "Thummar"));
    }

}
